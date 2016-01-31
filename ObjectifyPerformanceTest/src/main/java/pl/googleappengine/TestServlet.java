/**
 * 
 */
package pl.googleappengine;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.googleappengine.domain.ObjectifyTestChildEntry;
import pl.googleappengine.domain.ObjectifyTestEntry;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.ObjectifyService;

/**
 * @author Maciek
 *
 */
public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String action = req.getParameter("action");
		resp.setContentType("text/plain");

		switch (action) {
		case "create":
			handleCreateEntries(req, resp);
			break;
		case "readAll":
			handleReadAllEntries(req, resp);
			break;
		case "removeAll":
			handleRemoveAllEntries(req, resp);
			break;
		default:
			break;
		}

	}
	
	public void handleCreateEntries(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		long start = System.currentTimeMillis();
		
		int count = Integer.parseInt(req.getParameter("count"));
		int childCount = Integer.parseInt(req.getParameter("childCount"));
		createEntries(count, childCount);
		PrintWriter writer = resp.getWriter();
		writer.write("Created " + count + " entries, each having " + childCount + " child entries. Duration: " + (System.currentTimeMillis() - start) + " ms");
		writer.close();
	}
	
	public void handleReadAllEntries(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String result = readAllEntries();
		PrintWriter writer = resp.getWriter();
		writer.write(result);
		writer.close();
	}
	
	protected void handleRemoveAllEntries(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		String result = removeAllEntries();
		PrintWriter writer = resp.getWriter();
		writer.write(result);
		writer.close();
	}

	public void createEntries(int count, int childCountForEachEntry) {

		ObjectifyService.register(ObjectifyTestEntry.class);
		ObjectifyService.register(ObjectifyTestChildEntry.class);

		List<ObjectifyTestEntry> entries = createParentEntries(count);

		if( childCountForEachEntry > 0 ) {
			for( ObjectifyTestEntry entry : entries ) {
				List<ObjectifyTestChildEntry> childEntries = createChildEntries(childCountForEachEntry);
				Set<Key<ObjectifyTestChildEntry>> childKeys = ofy().save().entities(childEntries).now().keySet();
				entry.setChildren(childKeys);
			}
		}

		ofy().save().entities(entries).now();

	}

	public String readAllEntries() {
		long startTime = System.currentTimeMillis();
		StringBuilder resultBuilder = new StringBuilder();
		int count = 0;
		ObjectifyService.register(ObjectifyTestEntry.class);
		ObjectifyService.register(ObjectifyTestChildEntry.class);
		List<ObjectifyTestEntry> entries = ofy().load().type(ObjectifyTestEntry.class).list();

		for( ObjectifyTestEntry entry : entries ) {
			resultBuilder.append("\n" + entry.toString());
		}

		count = entries.size();

		resultBuilder.insert(0, "Loaded " + count + " entries in " + (System.currentTimeMillis() - startTime) + " ms\n");
		return resultBuilder.toString();

	}

	public String removeAllEntries() {

		long startTime = System.currentTimeMillis();
		StringBuilder resultBuilder = new StringBuilder();

		List<Key<ObjectifyTestEntry>> keys = ofy().load().type(ObjectifyTestEntry.class).keys().list();
		ofy().delete().keys(keys).now();

		resultBuilder.append("Removed all entries in " + (System.currentTimeMillis() - startTime) + " ms");
		return resultBuilder.toString();
	}

	private List<ObjectifyTestChildEntry> createChildEntries(int count) {
		List<ObjectifyTestChildEntry> result = new ArrayList<ObjectifyTestChildEntry>();
		for( int i=0; i<count; ++i ) {
			ObjectifyTestChildEntry entry = new ObjectifyTestChildEntry();
			entry.setFirstName("First" + i);
			entry.setLastName("last" + i);
			entry.setValueObject("someValue");
			entry.setCreateDate(new Date());
			result.add(entry);
		}
		return result;
	}

	private List<ObjectifyTestEntry> createParentEntries(int count) {
		List<ObjectifyTestEntry> result = new ArrayList<ObjectifyTestEntry>();
		for( int i=0; i<count; ++i ) {

			ObjectifyTestEntry entry = new ObjectifyTestEntry();
			entry.setCityName("City" + i);
			entry.setContactEmail("contact@email.com" + i);
			entry.setContactPhone("789654321" + i);
			entry.setFacebookPage("facebook_profile" + i);
			entry.setGooglePlusPage("google_plus_profile" + i);
			entry.setName("Name" + i);
			entry.setSearchable(true);
			entry.setStreetAddress("StreetAddress" + i);
			entry.setWebSite("www.website.com" + i);
			entry.setZipCode("00-950_" + i);

			result.add(entry);

		}

		return result;
	}


}
