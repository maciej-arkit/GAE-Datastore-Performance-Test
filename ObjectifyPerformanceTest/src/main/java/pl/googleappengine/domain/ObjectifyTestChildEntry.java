package pl.googleappengine.domain;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

/**
 * @author Maciek
 *
 */
@Entity
public class ObjectifyTestChildEntry {
	
	@Id private Long id;
	
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
		return "ChildEntry [id=" + id + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", valueObject=" + valueObject
				+ ", createDate=" + createDate + "]";
	}
	
}
