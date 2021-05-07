package com.revature.project_one.services;

import java.util.List;

import com.revature.project_one.models.Employee;
import com.revature.project_one.models.Form;
import com.revature.project_one.models.Validation;

public interface FormService {
	
	public List<Form> getForms();
	public List<Form> getForms(Employee e);
	//public List<Form> getForms(Employee e, Boolean x, Boolean y, Boolean z);
	public List<Form> getFormsSuper(Employee e);
	
	public Form getForm(String formID);
	
	public Validation newForm(Employee e, Form f);
	
	public Validation updateForm(Employee e, Form f);
	public Validation updateForm(Employee e, Form f, boolean b);
	public Validation updateForm(Employee e, String id, String status);
}
