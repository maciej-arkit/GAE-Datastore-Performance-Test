package pl.googleappengine.domain;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.Ref;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Load;

/**
 * @author Maciej Arkit
 * 
 *
 */
@com.googlecode.objectify.annotation.Entity
public class ObjectifyTestEntry implements Serializable {
	
	@Id private Long id;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String name;
	private String streetAddress;
	private String zipCode;
	private String cityName;
	
	//contact address - email + phone
	private String contactEmail;
	private String contactPhone;
	private String webSite;
	private String facebookPage;
	private String googlePlusPage;
	
	/**
	 * Indicates if this location can be searched
	 */
	private boolean searchable;
	
	@Load
	private Set<Ref<ObjectifyTestChildEntry>> children = new java.util.HashSet<Ref<ObjectifyTestChildEntry>>();
	
	public Set<Ref<ObjectifyTestChildEntry>> getChildren() {
		return children;
	}

	public void setChildren(Set<Key<ObjectifyTestChildEntry>> children) {
		this.children.clear();
		for( Key<ObjectifyTestChildEntry> key : children ) {
			this.children.add(Ref.create(key));
		}
	}

	public Collection<ObjectifyTestChildEntry> getChildrenValues() {
		return ofy().load().refs(children).values();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String streetAddress) {
		this.streetAddress = streetAddress;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public String getWebSite() {
		return webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	public String getFacebookPage() {
		return facebookPage;
	}

	public void setFacebookPage(String facebookPage) {
		this.facebookPage = facebookPage;
	}

	public String getGooglePlusPage() {
		return googlePlusPage;
	}

	public void setGooglePlusPage(String googlePlusPage) {
		this.googlePlusPage = googlePlusPage;
	}

	public boolean isSearchable() {
		return searchable;
	}

	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}

	@Override
	public String toString() {
		return "ObjectifyTestEntry [id=" + id + ", name=" + name
				+ ", streetAddress=" + streetAddress + ", zipCode=" + zipCode
				+ ", cityName=" + cityName + ", contactEmail=" + contactEmail
				+ ", contactPhone=" + contactPhone + ", webSite=" + webSite
				+ ", facebookPage=" + facebookPage + ", googlePlusPage="
				+ googlePlusPage + ", searchable=" + searchable + ", children="
				+ getChildrenValues() + "]";
	}
	
}
