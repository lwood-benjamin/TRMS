package com.revature.project_one.data;

import java.util.List;

import com.revature.project_one.models.Employee;

public interface EmpDao {
	
	public List<Employee> getEmployees();
	public List<Employee> getBenCos(); 
	public Employee getEmployee(String id);
	public void updateEmployee(Employee e);
	
}
