package com.revature.project_one.models;

import java.util.List;
import java.util.stream.Collectors;

public class Employee {
	
	//variables:
	private final String empID; // part key: id
	private String name; // name
	private String passcode; // pass
	private boolean superStatus = false; // superstatus
	private boolean deptHeadStatus = false; //deptheadstatus
	private boolean benCoStatus = false; // cluster key: bencostatus
	private String supervisorID; // supervisorid
	private List<String> activeRequests; // activerequests
	private List<String> closedRequests; // closedrequests
	private int benefitsUsed; // benefitsused
	private int benefitsRemaining; //benefitsremaining
	
	//constructor
	public Employee(String empID) {
		super();
		this.empID = empID;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPasscode() {
		return passcode;
	}

	public void setPasscode(String passcode) {
		this.passcode = passcode;
	}

	public boolean isSuperStatus() {
		return superStatus;
	}

	public void setSuperStatus(boolean superStatus) {
		this.superStatus = superStatus;
	}

	public boolean isDeptHeadStatus() {
		return deptHeadStatus;
	}

	public void setDeptHeadStatus(boolean deptHeadStatus) {
		this.deptHeadStatus = deptHeadStatus;
	}

	public boolean isBenCoStatus() {
		return benCoStatus;
	}

	public void setBenCoStatus(boolean benCoStatus) {
		this.benCoStatus = benCoStatus;
	}

	public String getSupervisorID() {
		return supervisorID;
	}

	public void setSupervisorID(String superID) {
		this.supervisorID = superID;
	}

	public List<String> getActiveRequests() {
		return activeRequests;
	}

	public void setActiveRequests(List<String> activeRequests) {
		this.activeRequests = activeRequests;
	}
	
	public void addActiveRequest(String activeRequest) {
		this.activeRequests.add(activeRequest);
	}

	public void closeRequest(String closingRequest) {
		//filter out closing request
		this.activeRequests = activeRequests.stream()
				.filter(x -> x.equals(closingRequest) == false)
				.collect(Collectors.toList());
		//add closing request to closed list
		this.closedRequests.add(closingRequest);
	}
	
	public List<String> getClosedRequests() {
		return closedRequests;
	}

	public void setClosedRequests(List<String> closedRequests) {
		this.closedRequests = closedRequests;
	}

	public String getEmpID() {
		return empID;
	}



	public int getBenefitsUsed() {
		return benefitsUsed;
	}

	public void setBenefitsUsed(int benefitsUsed) {
		this.benefitsUsed = benefitsUsed;
	}

	public int getBenefitsRemaining() {
		return benefitsRemaining;
	}

	public void setBenefitsRemaining(int benefitsRemaining) {
		this.benefitsRemaining = benefitsRemaining;
	}
	
	

}
