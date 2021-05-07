package com.revature.project_one.services;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.revature.project_one.data.EmpDao;
import com.revature.project_one.data.EmpDaoImpl;
import com.revature.project_one.models.Employee;

public class UserServiceImpl implements UserService {

	private static final Logger log = LogManager.getLogger(UserServiceImpl.class);
	private EmpDaoImpl edi;

	public UserServiceImpl(EmpDaoImpl mock) {
		super();
		this.edi = mock;
	}
	
	public UserServiceImpl()	{
		this.edi = EmpDaoImpl.instance();
	}

	@Override
	public Employee getUser(String name) {
		log.trace("US: returning employee");
		return edi.getEmployee(name);
	}

	@Override
	public List<Employee> getBenCos() {
		log.trace("US: returning bencos");
		return edi.getBenCos();
	}

	@Override
	public void addFormToEmp(String empID, String formID) {
		log.trace("Adding form: "+formID+" to user: "+empID);
		Employee e = edi.getEmployee(formID);
		e.addActiveRequest(formID);
		log.trace("Updating employee");
		edi.updateEmployee(e);
	}

	public void setEmployeeDao(EmpDaoImpl dao) {
		edi = dao;
	}
}
