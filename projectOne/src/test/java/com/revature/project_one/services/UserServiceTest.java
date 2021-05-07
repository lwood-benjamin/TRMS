package com.revature.project_one.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.revature.project_one.data.EmpDaoImpl;
import com.revature.project_one.models.Employee;

public class UserServiceTest {

	private UserServiceImpl usi;
	
	@Before
	public void setUp()throws Exception {
		usi = new UserServiceImpl(mock(EmpDaoImpl.class));
	}
	
	@Test
	public void testGetUser() {
		Employee test = new Employee("0");
		EmpDaoImpl dao = mock(EmpDaoImpl.class);
		usi.setEmployeeDao(dao);
		
		when(dao.getEmployee("0")).thenReturn(test);
		
		Employee e = usi.getUser(test.getEmpID());
		assertEquals("Test employee should be: " + test.getEmpID(),
				test.getEmpID(), e.getEmpID());
	}
	
	@Test
	public void testGetBenCo() {
		Employee test = new Employee("0");
		Employee test2 = new Employee("2");
		List<Employee> bencos = new ArrayList<Employee>();
		bencos.add(test2);
		bencos.add(test);
		EmpDaoImpl dao = mock(EmpDaoImpl.class);
		usi.setEmployeeDao(dao);
		
		when(dao.getBenCos()).thenReturn(bencos);
		
		List<Employee> benList = usi.getBenCos();
		assertEquals("BenCos should be: " + bencos.toString(),
				bencos, benList);
	}
	


}
