package com.revature.project_one.data;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.project_one.models.Form;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.revature.project_one.util.CassUtil;

public class FormDaoImpl implements FormDao {

	private static final Logger log = LogManager.getLogger(FormDaoImpl.class);
	private CqlSession session = CassUtil.getInstance().getSession();
	private static FormDaoImpl formSession = null;

	private FormDaoImpl() {
		super();
	}
	
	public static FormDaoImpl instance() {
		if(formSession == null) {
			formSession = new FormDaoImpl();
		}
		return formSession;
	}
	@Override
	public List<Form> getForms() {
		log.trace("Getting all forms");
		List<Form> forms = new ArrayList<Form>();
		String query ="select * from forms";
		ResultSet rs = session.execute(query);
		log.trace("Query run with results: "+ rs.toString());
		
		rs.forEach(data -> {
			forms.add(createForm(data));
		});
		
		log.trace("Returning Forms");
		return forms;
	}
	
	@Override
	public List<Form> getEmployeeForms(String empID){
		log.trace("Getting all employee owned forms");
		List<Form> forms = new ArrayList<Form>();
		String query ="select * from forms "+
				"where employeeid = ?";
		BoundStatement bound = session.prepare(query).bind(empID);
		ResultSet rs = session.execute(bound);
		log.trace("Query run with results: "+ rs.toString());
		
		rs.forEach(data -> {
			forms.add(createForm(data));
		});
		
		log.trace("Returning Forms");
		return forms;
	}
	
	@Override
	public List<Form> getSuperForms(String superID){
		log.trace("Getting all super assigned forms");
		List<Form> forms = new ArrayList<Form>();
		String query ="select * from forms "+
				"where supervisorid = ?";
		BoundStatement bound = session.prepare(query).bind(superID);
		ResultSet rs = session.execute(bound);
		log.trace("Query run with results: "+ rs.toString());
		
		rs.forEach(data -> {
			forms.add(createForm(data));
		});
		
		log.trace("Returning Forms");
		return forms;
	}

	@Override
	public List<Form> getDeptHeadForms(String deptID){
		log.trace("Getting all department head assigned forms");
		List<Form> forms = new ArrayList<Form>();
		String query ="select * from forms "+
				"where deptheadid = ?";
		BoundStatement bound = session.prepare(query).bind(deptID);
		ResultSet rs = session.execute(bound);
		log.trace("Query run with results: "+ rs.toString());
		
		rs.forEach(data -> {
			forms.add(createForm(data));
		});
		
		log.trace("Returning Forms");
		return forms;
	}
	
	@Override
	public List<Form> getBenCoForms(String benCoID){
		log.trace("Getting all BenCo assigned forms");
		List<Form> forms = new ArrayList<Form>();
		String query ="select * from forms "+
				"where bencoid = ?";
		BoundStatement bound = session.prepare(query).bind(benCoID);
		ResultSet rs = session.execute(bound);
		log.trace("Query run with results: "+ rs.toString());
		
		rs.forEach(data -> {
			forms.add(createForm(data));
		});
		
		log.trace("Returning Forms");
		return forms;
	}
	
	@Override
	public Form getForm(String id) {
		log.trace("Getting form");
		String query = "select * from forms "+
				"where id = ?";
		BoundStatement bound = session.prepare(query).bind(id);
		ResultSet rs = session.execute(bound);
		log.trace("Query returns with results: "+rs.toString());
		Row data = rs.one();
		if(data != null) {
			log.trace("Returning form");
			return createForm(data);
		}
		return null;
	}

	@Override
	public void updateForm(Form f) {
		log.trace("Updating form "+ f.getFormID());
		String query = "update forms set "+
				"submitteddate = ?, eventdate = ?, superapproveddate "+
				"deptapproveddate = ?, benapproveddate = ?, employeeid = ? "+
				"supervisorid = ?, deptheadid = ?,  bencoid = ?, "+
				"superapproval = ?, deptapproval = ?, benapproval = ?, "+
				"formapprovalstatus = ?, requestedammount = ?, eventinfo = ?, "+
				"requestedammount = ?, eventinfo = ?, gradeformat = ?, "+
				"eventtype = ?, attachments = ?, justification = ?, "+
				"denialjust = ?, deniedby = ?, lastupdated = ?, lastupdatedby = ? "+
				"where id = ?";
		
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(
				f.getSubmittedDate(), f.getEventDate(), f.getSuperApproveDate(),
				f.getDeptApproveDate(), f.getBenApproveDate(), f.getEmployeeID(),
				f.getSupervisorID(), f.getDeptHeadID(), f.getBenCoID(),
				f.isSuperApproval(), f.isDeptApproval(), f.isBenApproval(),
				f.getFormApprovalStatus(), f.getRequestedAmount(), f.getEventInfo(),
				f.getRequestedAmount(), f.getEventInfo(), f.getGradeFormat(),
				f.getEventType(), f.getAttachments(), f.getJustification(),
				f.getDenialJustification(), f.getDeniedBy(), f.getLastUpdated(), f.getLastUpdatedBy(),
				f.getFormID());
		session.execute(bound);
		log.trace("Query executed");
	}
	
	@Override
	public void createForm(Form f) {
		log.trace("Creating form "+ f.getFormID());
		String query = "insert into forms "+
				"( id, employeeid, supervisorid, deptheadid, bencoid, "+
				"submitteddate, eventdate, superapproveddate, "+
				"deptapproveddate, benapproveddate, "+
				"superapproval, deptapproval, benapproval, "+
				"formapprovalstatus, requestedammount, "+
				"eventinfo,  gradeformat, "+
				"eventtype, attachments, justification, "+
				"denialjust, deniedby, lastupdated, lastupdatedby) "+
				"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) "+
				"if not exists";
		
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(
				f.getFormID(), f.getEmployeeID(), f.getSupervisorID(), f.getDeptHeadID(), f.getBenCoID(),
				f.getSubmittedDate(), f.getEventDate(), f.getSuperApproveDate(),
				f.getDeptApproveDate(), f.getBenApproveDate(),
				f.isSuperApproval(), f.isDeptApproval(), f.isBenApproval(),
				f.getFormApprovalStatus().toString(), f.getRequestedAmount(),
				f.getEventInfo(), f.getGradeFormat(),
				f.getEventType().toString(), f.getAttachments(), f.getJustification(),
				f.getDenialJustification(), f.getDeniedBy(), f.getLastUpdated(), f.getLastUpdatedBy());
		session.execute(bound);
		log.trace("Query executed");
	}
	
	private Form createForm(Row data) {
		log.trace("Creating form from database");
		Form f = new Form(data.getString("id"));
		f.setSubmittedDate(data.getLocalDate("submitteddate"));
		f.setEventDate(data.getLocalDate("eventdate"));
		f.setSuperApproveDate(data.getLocalDate("superapproveddate"));
		f.setDeptApproveDate(data.getLocalDate("deptapproveddate"));
		f.setBenApproveDate(data.getLocalDate("benapproveddate"));
		f.setEmployeeID(data.getString("employeeid"));
		f.setSupervisorID(data.getString("supervisorid"));
		f.setDeptHeadID(data.getString("deptheadid"));
		f.setBenCoID(data.getString("bencoid"));
		f.setSuperApproval(data.getBoolean("superapproval"));
		f.setDeptApproval(data.getBoolean("deptapproval"));
		f.setBenApproval(data.getBoolean("benapproval"));
		f.setFormApprovalStatus(data.getString("formapprovalstatus"));
		f.setRequestedAmount(data.getInt("requestedammount"));
		f.setEventInfo(data.getString("eventinfo"));
		f.setGradeFormat(data.getString("gradeformat"));
		f.setEventType(data.getString("eventtype"));
		f.setAttachments(data.getList("attachments", String.class));
		f.setJustification(data.getString("justification"));
		f.setDenialJustification(data.getString("denialjust"));
		f.setDeniedBy(data.getString("deniedby"));
		f.setLastUpdated(data.getLocalDate("lastupdated"));
		f.setLastUpdatedBy(data.getString("lastupdatedby"));
		return f;
	}
}
