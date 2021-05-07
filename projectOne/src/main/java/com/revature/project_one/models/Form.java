package com.revature.project_one.models;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class Form {

	private final String formID; // part key: id
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate submittedDate; // submitteddate
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate eventDate; // eventdate
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate superApproveDate; // superapproveddate
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate deptApproveDate; // deptapproveddate
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate benApproveDate; // benapproveddate
	private String employeeID; // cluster key: employeeid
	private String supervisorID; // cluster key: supervisorid
	private String deptHeadID; // cluster key: deptheadid
	private String benCoID; // cluster key: bencoid
	private boolean superApproval; // superapproval
	private boolean deptApproval; // deptapproval
	private boolean benApproval; // benapproval

	

	private FormStatus formApprovalStatus; // formapprovalstatus
	private int requestedAmount; // requestedammount
	private String eventInfo; // eventinfo
	private String gradeFormat; // gradeformat
	private EventType eventType; // eventtype
	private List<String> attachments; // attachments // holds S3 keys
	private String justification; // justification
	private String denialJustification; // denialjust
	private String deniedBy; // deniedby // Employee ID of who denied it
	@JsonDeserialize(using = LocalDateDeserializer.class)
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate lastUpdated; // lastupdated
	private String lastUpdatedBy; // lastupdatedby

	public Form() {
		super();
		this.formID = null;
	}
	
	public Form(String formID) {
		super();
		this.formID = formID;
		this.superApproval = false;
		this.deptApproval = false;
		this.benApproval = false;
		this.formApprovalStatus = FormStatus.NEW;
		this.lastUpdated = LocalDate.now();
	}

	public Form(String formID, String empID) {
		super();
		this.formID = formID;
		this.employeeID = empID;
		this.superApproval = false;
		this.deptApproval = false;
		this.benApproval = false;
		this.formApprovalStatus = FormStatus.NEW;
		this.lastUpdated = LocalDate.now();
		this.lastUpdatedBy = empID;
	}

	public String getFormID() {
		return formID;
	}

	public LocalDate getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(LocalDate submittedDate) {
		this.submittedDate = submittedDate;
	}

	public LocalDate getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDate eventDate) {
		this.eventDate = eventDate;
	}

	public LocalDate getSuperApproveDate() {
		return superApproveDate;
	}

	public void setSuperApproveDate(LocalDate superApproveDate) {
		this.superApproveDate = superApproveDate;
	}

	public LocalDate getDeptApproveDate() {
		return deptApproveDate;
	}

	public void setDeptApproveDate(LocalDate deptApproveDate) {
		this.deptApproveDate = deptApproveDate;
	}

	public LocalDate getBenApproveDate() {
		return benApproveDate;
	}

	public void setBenApproveDate(LocalDate benApproveDate) {
		this.benApproveDate = benApproveDate;
	}

	public String getEmployeeID() {
		return employeeID;
	}

	public void setEmployeeID(String employeeID) {
		this.employeeID = employeeID;
	}

	public String getSupervisorID() {
		return supervisorID;
	}

	public void setSupervisorID(String supervisorID) {
		this.supervisorID = supervisorID;
	}

	public String getDeptHeadID() {
		return deptHeadID;
	}

	public void setDeptHeadID(String deptHeadID) {
		this.deptHeadID = deptHeadID;
	}

	public String getBenCoID() {
		return benCoID;
	}

	public void setBenCoID(String benCoID) {
		this.benCoID = benCoID;
	}

	public boolean isSuperApproval() {
		return superApproval;
	}

	public void setSuperApproval(boolean superApproval) {
		this.superApproval = superApproval;
	}

	public boolean isDeptApproval() {
		return deptApproval;
	}

	public void setDeptApproval(boolean deptApproval) {
		this.deptApproval = deptApproval;
	}

	public boolean isBenApproval() {
		return benApproval;
	}

	public void setBenApproval(boolean benApproval) {
		this.benApproval = benApproval;
	}

	public FormStatus getFormApprovalStatus() {
		return formApprovalStatus;
	}

	public void setFormApprovalStatus(FormStatus formApprovalStatus) {
		this.formApprovalStatus = formApprovalStatus;
	}
	public void setFormApprovalStatus(String formApprovalStatus) {
		this.formApprovalStatus = FormStatus.valueOf(formApprovalStatus);
	}

	public int getRequestedAmount() {
		return requestedAmount;
	}

	public void setRequestedAmount(int requestedAmount) {
		this.requestedAmount = requestedAmount;
	}

	public String getEventInfo() {
		return eventInfo;
	}

	public void setEventInfo(String eventInfo) {
		this.eventInfo = eventInfo;
	}

	public String getGradeFormat() {
		return gradeFormat;
	}

	public void setGradeFormat(String gradeFormat) {
		this.gradeFormat = gradeFormat;
	}

	public EventType getEventType() {
		return eventType;
	}

	public void setEventType(EventType eventType) {
		this.eventType = eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = EventType.valueOf(eventType);
	}

	public List<String> getAttachments() {
		return attachments;
	}

	public void setAttachments(List<String> attachments) {
		this.attachments = attachments;
	}
	
	public void addAttachment(String attachmentID) {
		this.attachments.add(attachmentID);
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getDenialJustification() {
		return denialJustification;
	}

	public void setDenialJustification(String denialJustification) {
		this.denialJustification = denialJustification;
	}

	public String getDeniedBy() {
		return deniedBy;
	}

	public void setDeniedBy(String deniedBy) {
		this.deniedBy = deniedBy;
	}

	public LocalDate getLastUpdated() {
		return lastUpdated;
	}

	public void setLastUpdated(LocalDate lastUpdated) {
		this.lastUpdated = lastUpdated;
	}

	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}

	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}

	@Override
	public String toString() {
		return "Form [formID=" + formID + ", submittedDate=" + submittedDate + ", eventDate=" + eventDate
				+ ", superApproveDate=" + superApproveDate + ", deptApproveDate=" + deptApproveDate
				+ ", benApproveDate=" + benApproveDate + ", employeeID=" + employeeID + ", supervisorID=" + supervisorID
				+ ", deptHeadID=" + deptHeadID + ", benCoID=" + benCoID + ", superApproval=" + superApproval
				+ ", deptApproval=" + deptApproval + ", benApproval=" + benApproval + ", formApprovalStatus="
				+ formApprovalStatus + ", requestedAmount=" + requestedAmount + ", eventInfo=" + eventInfo
				+ ", gradeFormat=" + gradeFormat + ", eventType=" + eventType + ", attachments=" + attachments
				+ ", justification=" + justification + ", denialJustification=" + denialJustification + ", deniedBy="
				+ deniedBy + ", lastUpdated=" + lastUpdated + ", lastUpdatedBy=" + lastUpdatedBy + "]";
	}

}
