package com.revature.project_one.data;

import java.util.List;

import com.revature.project_one.models.Form;

public interface FormDao {
	
	public List<Form> getForms();
	public List<Form> getEmployeeForms(String id);
	public List<Form> getSuperForms(String id);
	public List<Form> getDeptHeadForms(String id);
	public List<Form> getBenCoForms(String id);
	
	public Form getForm(String id);
	
	public void updateForm(Form f);
	
	public void createForm(Form f);

}