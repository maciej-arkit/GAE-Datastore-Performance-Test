/**
 * 
 */
package pl.googleappengine;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.googleappengine.domain.JPATestChildEntry;
import pl.googleappengine.domain.JPATestEntry;


/**
 * @author Maciek
 *
 */
public class TestServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final Logger logger = Logger.getLogger(TestServlet.class.getName());

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

		List<JPATestEntry> entries = createJPAEntries(count, childCountForEachEntry);
		EntityManager em = EntityManagerFactoryCache.getInstance().createEntityManager();
		
		long persistStart = System.currentTimeMillis();
		
		for( JPATestEntry entry : entries ) {
			EntityTransaction tx = em.getTransaction();
			tx.begin();
			em.persist(entry);
			tx.commit();

		}
		
		logger.info("Persisting entities took: " + (System.currentTimeMillis() - persistStart) + " ms");
		long startClose = System.currentTimeMillis();
		em.close();
		logger.info("EntityManager.close() took: " + (System.currentTimeMillis() - startClose) + " ms");

	}

	public String readAllEntries() {
		long startTime = System.currentTimeMillis();
		StringBuilder resultBuilder = new StringBuilder();
		int count = 0;

		//TODO: Implement reading all entries
		EntityManager em = EntityManagerFactoryCache.getInstance().createEntityManager();
		Query query = em.createQuery("select entry from " + JPATestEntry.class.getSimpleName() + " entry");
		List<JPATestEntry> entries = query.getResultList();

		for( JPATestEntry entry : entries ) {
			resultBuilder.append("\n" + entry.toString());
		}

		count = entries.size();
		em.close();
		resultBuilder.insert(0, "Loaded " + count + " entries in " + (System.currentTimeMillis() - startTime) + " ms\n");
		return resultBuilder.toString();

	}

	public String removeAllEntries() {

		long startTime = System.currentTimeMillis();
		StringBuilder resultBuilder = new StringBuilder();

		EntityManager em = EntityManagerFactoryCache.getInstance().createEntityManager();
		em.createQuery("DELETE from " + JPATestEntry.class.getSimpleName() + " entry").executeUpdate();
		long startClose = System.currentTimeMillis();
		em.close();
		logger.info("EntityManager.close() took" + (System.currentTimeMillis() - startClose) + " ms");
		resultBuilder.append("Removed all entries in " + (System.currentTimeMillis() - startTime) + " ms");
		return resultBuilder.toString();
	}


	private List<JPATestEntry> createJPAEntries(int count, int childEntriesCount) {
		List<JPATestEntry> result = new ArrayList<JPATestEntry>();
		for( int i=0; i<count; ++i ) {
			
			JPATestEntry entry = new JPATestEntry();
			entry.setCityName("City" + i);
			entry.setContactEmail("contact@email.com" + i);
			entry.setContactPhone("608678277" + i);
			entry.setFacebookPage("facebook_profile" + i);
			entry.setGooglePlusPage("google_plus_profile" + i);
			entry.setName("Name" + i);
			entry.setSearchable(true);
			entry.setStreetAddress("StreetAddress" + i);
			entry.setWebSite("www.website.com" + i);
			entry.setZipCode("00-950_" + i);
			
			if( childEntriesCount > 0 ) {
				List<JPATestChildEntry> childEntries = createJPAChildEntries(childEntriesCount);
				entry.setChildren(childEntries);
			}
			
			result.add(entry);
			
		}
		
		return result;
		
	}

	private List<JPATestChildEntry> createJPAChildEntries(int count) {
		List<JPATestChildEntry> result = new ArrayList<JPATestChildEntry>();
		
		for( int i=0; i < count; ++i ) {
			JPATestChildEntry entry = new JPATestChildEntry();
			entry.setFirstName("First" + i);
			entry.setLastName("last" + i);
			entry.setValueObject("someValue");
			entry.setCreateDate(new Date());
			result.add(entry);
		}
		
		return result;
	}


}
