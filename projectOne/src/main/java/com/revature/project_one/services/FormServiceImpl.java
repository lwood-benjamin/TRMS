package com.revature.project_one.services;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_one.data.EmpDaoImpl;
import com.revature.project_one.data.FormDaoImpl;
import com.revature.project_one.models.Employee;
import com.revature.project_one.models.Form;
import com.revature.project_one.models.FormStatus;
import com.revature.project_one.models.Validation;

public class FormServiceImpl implements FormService {

	private static final Logger log = LogManager.getLogger(FormServiceImpl.class);
	private FormDaoImpl fdi;
	private EmpDaoImpl edi;

	public FormServiceImpl(FormDaoImpl mock, EmpDaoImpl mock2) {
		super();
		this.fdi = mock;
		this.edi = mock2;
	}
	public FormServiceImpl() {
		this.fdi = FormDaoImpl.instance();
		this.edi = EmpDaoImpl.instance();
	}

	@Override
	public List<Form> getForms() {
		log.trace("Getting all forms");
		return fdi.getForms();
	}

	@Override
	public List<Form> getForms(Employee e) {
		log.trace("Getting " + e.getName() + "'s forms");
		List<Form> formbox = fdi.getForms();
		// keep only user's forms
		return formbox.stream().filter(x -> x.getEmployeeID().equals(e.getEmpID())).collect(Collectors.toList());
	}

	@Override
	public List<Form> getFormsSuper(Employee e) {
		log.trace("Getting forms assigned to " + e.getName());
		List<Form> formbox = fdi.getForms();
		// keep only forms assigned to user
		return formbox.stream().filter(x -> x.getSupervisorID().equals(e.getEmpID())
				|| x.getDeptHeadID().equals(e.getEmpID()) || x.getBenCoID().equals(e.getEmpID()))
				.collect(Collectors.toList());
	}

	@Override
	public Form getForm(String formID) {
		log.trace("Getting form");
		return fdi.getForm(formID);
	}

	@Override
	public Validation newForm(Employee e, Form f) {
		Employee supervisor = edi.getEmployee(e.getSupervisorID());
		log.trace("New Form validation");
		if (f == null) {
			return Validation.EMPTY;
		}

		log.trace("Ensuring fields filled");
		if (f.getRequestedAmount() <= 0 || f.getEventInfo().isEmpty() || f.getEventType() == null) {
			return Validation.EMPTY;
		}

		log.trace("Setting form defaults.");
		f.setFormApprovalStatus(FormStatus.NEW);
		f.setSubmittedDate(LocalDate.now());
		f.setLastUpdated(LocalDate.now());
		f.setLastUpdatedBy(e.getEmpID());
		f.setEmployeeID(e.getEmpID());
		f.setSupervisorID(e.getSupervisorID());
		// assign department head
		if (supervisor.isDeptHeadStatus()) {
			f.setDeptHeadID(supervisor.getEmpID());
		} else {
			f.setDeptHeadID(supervisor.getSupervisorID());
		}
		// assign benco to request (as long as employee isn't the benco
		f.setBenCoID(edi.getBenCos().stream().filter(x -> x.getEmpID() != e.getEmpID()).findAny().get().getEmpID());

		log.trace("Voiding fields that should be empty");
		f.setSuperApproveDate(null);
		f.setDeptApproveDate(null);
		f.setBenApproveDate(null);
		f.setGradeFormat(null);
		f.setDenialJustification(null);
		f.setDeniedBy(null);
		f.setSuperApproval(false);
		f.setDeptApproval(false);
		f.setBenApproval(false);
		f.setAttachments(null);

		log.trace("Form sanitized. Sending to DAO");
		fdi.createForm(f);
		return Validation.VALID;
	}

	@Override
	public Validation updateForm(Employee e, Form f, boolean b) {
		FormDaoImpl fdi = FormDaoImpl.instance();
		Form blank = new Form(f.getFormID());
		Form priorForm = fdi.getForm(f.getFormID());
		
		if(	priorForm.getEmployeeID().equals(e.getEmpID()) ||
			!priorForm.getSupervisorID().equals(e.getEmpID()) ||
			!priorForm.getDeptHeadID().equals(e.getEmpID())	||
			!priorForm.getBenCoID().equals(e.getEmpID())) {
			log.warn("User: "+e+" tried to edit form: "+ priorForm.getFormID());
			priorForm.setSuperApproval(true);
			priorForm.setDeptApproval(true);
			priorForm.setSuperApproveDate(LocalDate.now());
			priorForm.setDeptApproveDate(LocalDate.now());
			priorForm.setFormApprovalStatus(FormStatus.PARTIAL);
			return Validation.VALID;
		}
		
		
		if(e.isSuperStatus() && !e.isDeptHeadStatus()) {
			if(f.getFormApprovalStatus() == FormStatus.DECLINED) {
				log.trace("Supervisor declined form");
				priorForm.setDeniedBy(e.getEmpID());
				priorForm.setDenialJustification(f.getDenialJustification());
				priorForm.setFormApprovalStatus(FormStatus.CLOSED);
			} else {
				log.trace("Supervisor approved form");
				priorForm.setSuperApproval(true);
				priorForm.setSuperApproveDate(LocalDate.now());
				priorForm.setFormApprovalStatus(FormStatus.PARTIAL);
			}
			priorForm.setLastUpdated(LocalDate.now());
			priorForm.setLastUpdatedBy(e.getEmpID());
			fdi.updateForm(priorForm);
			return Validation.VALID;
		}
		else if(e.isDeptHeadStatus() && priorForm.isSuperApproval()) {
			if(f.getFormApprovalStatus() == FormStatus.DECLINED) {
				log.trace("DeptHead declined form");
				priorForm.setDeniedBy(e.getEmpID());
				priorForm.setDenialJustification(f.getDenialJustification());
				priorForm.setFormApprovalStatus(FormStatus.CLOSED);
			} else {
				log.trace("DeptHead Approved form");
				priorForm.setDeptApproval(true);
				priorForm.setDeptApproveDate(LocalDate.now());
				priorForm.setFormApprovalStatus(FormStatus.PARTIAL);
			}
			priorForm.setLastUpdated(LocalDate.now());
			priorForm.setLastUpdatedBy(e.getEmpID());
			fdi.updateForm(priorForm);
			return Validation.VALID;
		} else if(e.isBenCoStatus()) {
			if(f.getFormApprovalStatus() == FormStatus.DECLINED) {
				log.trace("BenCo declined form");
				priorForm.setDeniedBy(e.getEmpID());
				priorForm.setDenialJustification(f.getDenialJustification());
				priorForm.setFormApprovalStatus(FormStatus.CLOSED);
			} else {
				log.trace("BenCo Approved form");
				priorForm.setBenApproval(true);
				priorForm.setBenApproveDate(LocalDate.now());
				priorForm.setFormApprovalStatus(FormStatus.PARTIAL);
			}
			priorForm.setLastUpdated(LocalDate.now());
			priorForm.setLastUpdatedBy(e.getEmpID());
			fdi.updateForm(priorForm);
			return Validation.VALID;
			}
		return Validation.EMPTY;
	}



	@Override
	public Validation updateForm(Employee e, String formID, String status) {
		Form priorForm = fdi.getForm(formID);
		
		if(	!priorForm.getEmployeeID().equals(e.getEmpID()) ||
			!priorForm.getSupervisorID().equals(e.getEmpID()) ||
			!priorForm.getDeptHeadID().equals(e.getEmpID())	||
			!priorForm.getBenCoID().equals(e.getEmpID())) {
			log.warn("User: "+e+" tried to edit form: "+ priorForm.getFormID());
			return Validation.INVALID;
		}
		
		
		if(e.isSuperStatus() && !e.isDeptHeadStatus()) {
			if(status.equals(FormStatus.DECLINED.toString())) {
				log.trace("Supervisor declined form");
				priorForm.setDeniedBy(e.getEmpID());
				priorForm.setDenialJustification("Justification Here");
				priorForm.setFormApprovalStatus(FormStatus.CLOSED);
			} else {
				log.trace("Supervisor approved form");
				priorForm.setBenApproval(true);
				priorForm.setBenApproveDate(LocalDate.now());
				priorForm.setFormApprovalStatus(FormStatus.PARTIAL);
			}
			priorForm.setLastUpdated(LocalDate.now());
			priorForm.setLastUpdatedBy(e.getEmpID());
			fdi.updateForm(priorForm);
			return Validation.VALID;
		}
		else if(e.isDeptHeadStatus() && priorForm.isSuperApproval()) {
			if(status.equals(FormStatus.DECLINED.toString())) {
				log.trace("DeptHead declined form");
				priorForm.setDeniedBy(e.getEmpID());
				priorForm.setDenialJustification("Justification Here");
				priorForm.setFormApprovalStatus(FormStatus.CLOSED);
			} else {
				log.trace("DeptHead Approved form");
				priorForm.setBenApproval(true);
				priorForm.setBenApproveDate(LocalDate.now());
				priorForm.setFormApprovalStatus(FormStatus.PARTIAL);
			}
			priorForm.setLastUpdated(LocalDate.now());
			priorForm.setLastUpdatedBy(e.getEmpID());
			fdi.updateForm(priorForm);
			return Validation.VALID;
		} else if(e.isBenCoStatus()) {
			if(status.equals(FormStatus.DECLINED.toString())) {
				log.trace("BenCo declined form");
				priorForm.setDeniedBy(e.getEmpID());
				priorForm.setDenialJustification("Justification Here");
				priorForm.setFormApprovalStatus(FormStatus.CLOSED);
			} else {
				log.trace("BenCo Approved form");
				priorForm.setBenApproval(true);
				priorForm.setBenApproveDate(LocalDate.now());
				priorForm.setFormApprovalStatus(FormStatus.PARTIAL);
			}
			priorForm.setLastUpdated(LocalDate.now());
			priorForm.setLastUpdatedBy(e.getEmpID());
			fdi.updateForm(priorForm);
			return Validation.VALID;
			}
		return Validation.EMPTY;
	}

	public void setFormDao(FormDaoImpl fdao, EmpDaoImpl edao) {
		fdi = fdao;
		edi = edao;
	}
	public void setFormDao(FormDaoImpl dao) {
		fdi = dao;
	}
	@Override
	public Validation updateForm(Employee e, Form f) {
		FormDaoImpl fdi = FormDaoImpl.instance();
		Form blank = new Form(f.getFormID());
		Form priorForm = fdi.getForm(f.getFormID());
		
		if(	!priorForm.getEmployeeID().equals(e.getEmpID()) ||
			!priorForm.getSupervisorID().equals(e.getEmpID()) ||
			!priorForm.getDeptHeadID().equals(e.getEmpID())	||
			!priorForm.getBenCoID().equals(e.getEmpID())) {
			log.warn("User: "+e+" tried to edit form: "+ priorForm.getFormID());

			return Validation.INVALID;
		}
		
		
		if(e.isSuperStatus() && !e.isDeptHeadStatus()) {
			if(f.getFormApprovalStatus() == FormStatus.DECLINED) {
				log.trace("Supervisor declined form");
				priorForm.setDeniedBy(e.getEmpID());
				priorForm.setDenialJustification(f.getDenialJustification());
				priorForm.setFormApprovalStatus(FormStatus.CLOSED);
			} else {
				log.trace("Supervisor approved form");
				priorForm.setSuperApproval(true);
				priorForm.setSuperApproveDate(LocalDate.now());
				priorForm.setFormApprovalStatus(FormStatus.PARTIAL);
			}
			priorForm.setLastUpdated(LocalDate.now());
			priorForm.setLastUpdatedBy(e.getEmpID());
			fdi.updateForm(priorForm);
			return Validation.VALID;
		}
		else if(e.isDeptHeadStatus() && priorForm.isSuperApproval()) {
			if(f.getFormApprovalStatus() == FormStatus.DECLINED) {
				log.trace("DeptHead declined form");
				priorForm.setDeniedBy(e.getEmpID());
				priorForm.setDenialJustification(f.getDenialJustification());
				priorForm.setFormApprovalStatus(FormStatus.CLOSED);
			} else {
				log.trace("DeptHead Approved form");
				priorForm.setDeptApproval(true);
				priorForm.setDeptApproveDate(LocalDate.now());
				priorForm.setFormApprovalStatus(FormStatus.PARTIAL);
			}
			priorForm.setLastUpdated(LocalDate.now());
			priorForm.setLastUpdatedBy(e.getEmpID());
			fdi.updateForm(priorForm);
			return Validation.VALID;
		} else if(e.isBenCoStatus()) {
			if(f.getFormApprovalStatus() == FormStatus.DECLINED) {
				log.trace("BenCo declined form");
				priorForm.setDeniedBy(e.getEmpID());
				priorForm.setDenialJustification(f.getDenialJustification());
				priorForm.setFormApprovalStatus(FormStatus.CLOSED);
			} else {
				log.trace("BenCo Approved form");
				priorForm.setBenApproval(true);
				priorForm.setBenApproveDate(LocalDate.now());
				priorForm.setFormApprovalStatus(FormStatus.PARTIAL);
			}
			priorForm.setLastUpdated(LocalDate.now());
			priorForm.setLastUpdatedBy(e.getEmpID());
			fdi.updateForm(priorForm);
			return Validation.VALID;
			}
		return Validation.EMPTY;
	
	}
}
