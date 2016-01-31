/**
 * 
 */
package pl.googleappengine.domain;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.google.appengine.api.datastore.Key;

/**
 * @author Maciek
 *
 */
@Entity
public class JPATestEntry {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Key key;

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
	
	@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<JPATestChildEntry> children;
	
	
	public List<JPATestChildEntry> getChildren() {
		return children;
	}

	public void setChildren(List<JPATestChildEntry> children) {
		this.children = children;
	}

	public Key getKey() {
		return key;
	}

	public void setKey(Key key) {
		this.key = key;
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
		return "Entry [id=" + key.getId() + ", name=" + name
				+ ", streetAddress=" + streetAddress + ", zipCode=" + zipCode
				+ ", cityName=" + cityName + ", contactEmail=" + contactEmail
				+ ", contactPhone=" + contactPhone + ", webSite=" + webSite
				+ ", facebookPage=" + facebookPage + ", googlePlusPage="
				+ googlePlusPage + ", searchable=" + searchable + ", children="
				+ children + "]";
	}
	
}
