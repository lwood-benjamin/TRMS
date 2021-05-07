package com.revature.project_one.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.revature.project_one.data.EmpDaoImpl;
import com.revature.project_one.data.FormDaoImpl;
import com.revature.project_one.models.Employee;
import com.revature.project_one.models.Form;

public class FormServiceTest {

	private FormServiceImpl fsi;
	
	@Before
	public void setUp() throws Exception{
		fsi = new FormServiceImpl(mock(FormDaoImpl.class), mock(EmpDaoImpl.class));
	}
	
	@Test
	public void testGetForms() {
		Form test = new Form("0");
		Form test2 = new Form("2");
		List<Form> forms = new ArrayList<Form>();
		FormDaoImpl dao = mock(FormDaoImpl.class);
		fsi.setFormDao(dao);
		
		when(dao.getForms()).thenReturn(forms);
		
		List<Form> testForms = fsi.getForms();
		assertEquals("Test forms should be: " + forms.toString(),
				forms, testForms);
	}
	
	@Test
	public void testGetFormsEmp() {
		Form test = new Form("0");
		test.setEmployeeID("0");
		Form test2 = new Form("2");
		test2.setEmployeeID("5");
		List<Form> forms = new ArrayList<Form>();
		forms.add(test);
		forms.add(test2);
		Employee e = new Employee("0");
		FormDaoImpl dao = mock(FormDaoImpl.class);
		fsi.setFormDao(dao);
		
		when(dao.getForms()).thenReturn(forms);
		
		forms.remove(1);
		
		List<Form> testForms = fsi.getForms(e);
		assertEquals("Test forms should be: " + forms.toString()+"\n",
				forms, testForms);
	}
	
	@Test
	public void testGetFormsSuper() {
		Form test = new Form("0");
		test.setEmployeeID("0");
		Form test2 = new Form("2");
		test2.setEmployeeID("5");
		test2.setSupervisorID("0");
		List<Form> forms = new ArrayList<Form>();
		forms.add(test);
		forms.add(test2);
		Employee e = new Employee("0");
		FormDaoImpl dao = mock(FormDaoImpl.class);
		fsi.setFormDao(dao);
		
		when(dao.getForms()).thenReturn(forms);
		
		forms.remove(0);
		
		List<Form> testForms = fsi.getFormsSuper(e);
		assertEquals("Test forms should be: " + forms.toString()+"\n",
				forms, testForms);
	}

	@Test
	public void testGetForm() {
		Form test = new Form("0");
		FormDaoImpl dao = mock(FormDaoImpl.class);
		fsi.setFormDao(dao);
		
		when(dao.getForm("0")).thenReturn(test);
		
		Form testForms = fsi.getForm("0");
		assertEquals("Test forms should be: " + test.toString(),
				test, testForms);
	}
	
}
