package com.evoke.researchlabs.row.hr.domain;

import java.io.Serializable;

/**
 * POJO for holding transaction details.
 * 
 * @author Zama
 *
 */
public class Transaction implements Serializable{
	private static final long serialVersionUID = -4588767421637863828L;
	private String id;
	private String username;
	private String address;
	private String email;
	private String requestType;
	private String requestTime;
	private String requestedBy;
	
	public Transaction(){
		//Default Constructor
	}

	public Transaction(String id, String username, String address, String email, String requestType, String requestTime, String requestedBy) {
		super();
		this.id = id;
		this.username = username;
		this.address = address;
		this.email = email;
		this.requestType = requestType;
		this.requestTime = requestTime;
		this.requestedBy = requestedBy;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRequestType() {
		return requestType;
	}

	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

	@Override
	public String toString() {
		return "Transaction [id=" + id + ", username=" + username + ", address=" + address + ", email=" + email
				+ ", requestType=" + requestType + ", requestTime=" + requestTime + ", requestedBy=" + requestedBy
				+ "]";
	}
}
