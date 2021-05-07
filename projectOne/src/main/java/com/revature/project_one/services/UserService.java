package com.revature.project_one.services;

import java.util.List;

import com.revature.project_one.models.Employee;

public interface UserService {

	Employee getUser(String name);
	
	List<Employee> getBenCos();
	
	void addFormToEmp(String empID, String formID);
}
