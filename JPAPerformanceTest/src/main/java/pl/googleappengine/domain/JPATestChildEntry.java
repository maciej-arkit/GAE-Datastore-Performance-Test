package pl.googleappengine.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.google.appengine.api.datastore.Key;

@Entity
public class JPATestChildEntry {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Key key;
	

	private String firstName;
	private String lastName;
	private String valueObject;
	private Date createDate = new Date();
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getValueObject() {
		return valueObject;
	}
	public void setValueObject(String valueObject) {
		this.valueObject = valueObject;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	@Override
	public String toString() {
		return "ChildEntry [id=" + key.getId() + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", valueObject=" + valueObject
				+ ", createDate=" + createDate + "]";
	}

}
