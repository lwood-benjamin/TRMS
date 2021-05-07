package com.revature.project_one.data;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.revature.project_one.Employee;
import com.revature.project_one.data.EmpDaoImpl;

import junit.framework.Assert;

@RunWith(MockitoJUnitRunner.class)
public class EmpDaoTest {
	private static List<?> empsAll, empsSome;
	private static Employee emp1, emp2, emp3, emp4;
	
	@Mock
	// database connection
	
	@InjectMocks
	EmpDaoImpl injectedEmployee;
	
	@Captor
	private ArgumentCaptor<?> // define variable captor
	private ArgumentCaptor<?> // define variable captor

	@Before
	public void setUpTest() {
		emp1 = new Employee(/* set overload param*/);
		emp2 = new Employee(/* set overload param*/);
		emp3 = new Employee(/* set overload param*/);
		emp4 = new Employee(/* set overload param*/);
		empsAll  = new ArrayList<Employee>(Arrays.asList(emp1,emp1,emp3,emp4));
	}
	
	@After
	public void putDownTest() {
		
	}
	
	@Test
	public void testGetEmps() {
		when(/* mocked object*/.readFromDatabase()).thenReturn(empsAll);
		Assert.assertEquals("Got all users?: ", empsAll,
				injectedEmployee.getEmployees());
	}
}
