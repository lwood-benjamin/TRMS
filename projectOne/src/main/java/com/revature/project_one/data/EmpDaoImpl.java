package com.revature.project_one.data;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.DefaultConsistencyLevel;
import com.datastax.oss.driver.api.core.cql.BoundStatement;
import com.datastax.oss.driver.api.core.cql.ResultSet;
import com.datastax.oss.driver.api.core.cql.Row;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.SimpleStatementBuilder;
import com.revature.project_one.models.Employee;
import com.revature.project_one.util.CassUtil;

public class EmpDaoImpl implements EmpDao{

	private static final Logger log = LogManager.getLogger(EmpDaoImpl.class);
	private CqlSession session = CassUtil.getInstance().getSession();
	private static EmpDaoImpl daoSession = null;

	private EmpDaoImpl() {
		super();
	}
	
	public static EmpDaoImpl instance() {
		if (daoSession == null) {
			daoSession = new EmpDaoImpl();
		}
		return daoSession;
	}
	
	public List<Employee> getEmployees() {
		log.trace("Getting all employees");
		List<Employee> employees = new ArrayList<Employee>();
		String query = "select * from employees";
		ResultSet rs = session.execute(query);
		log.trace("Query run with results: "+ rs.toString());
		
		rs.forEach(data -> {
			Employee e = new Employee(data.getString("id"));
			e.setName(data.getString("name"));
			e.setPasscode(data.getString("pass"));
			e.setSuperStatus(data.getBoolean("superstatus"));
			e.setDeptHeadStatus(data.getBoolean("deptheadstatus"));
			e.setBenCoStatus(data.getBoolean("bencostatus"));
			e.setSupervisorID(data.getString("supervisorid"));
			e.setActiveRequests(data.getList("activerequests", String.class));
			e.setClosedRequests(data.getList("closedrequests", String.class));
			e.setBenefitsUsed(data.getInt("benefitsused"));
			e.setBenefitsRemaining(data.getInt("benefitsremaining"));
			employees.add(e);
		});
		
		log.trace("Returning employees");
		return employees;
	}

	public List<Employee> getBenCos() {
		log.trace("Getting all BenCos");
		List<Employee> employees = new ArrayList<Employee>();
		String query = "select * from employees where benCoStatus = true allow filtering";
		ResultSet rs = session.execute(query);
		log.trace("Query run with results: "+rs.toString());
		
		rs.forEach(data -> {
			Employee e = new Employee(data.getString("id"));
			e.setName(data.getString("name"));
			e.setPasscode(data.getString("pass"));
			e.setSuperStatus(data.getBoolean("superstatus"));
			e.setDeptHeadStatus(data.getBoolean("deptheadstatus"));
			e.setBenCoStatus(data.getBoolean("bencostatus"));
			e.setSupervisorID(data.getString("supervisorid"));
			e.setActiveRequests(data.getList("activerequests", String.class));
			e.setClosedRequests(data.getList("closedrequests", String.class));
			e.setBenefitsUsed(data.getInt("benefitsused"));
			e.setBenefitsRemaining(data.getInt("benefitsRemaining"));
			employees.add(e);
		});
		log.trace("Returning BenCos");
		return employees;
	}

	public Employee getEmployee(String id) {
		log.trace("Getting specific employee");
		Employee e = null;
		String query = "select * " +
				"from employees where id = ?";
		BoundStatement bound = session.prepare(query).bind(id);
		ResultSet rs = session.execute(bound);
		log.trace("Query returns with results: "+rs.toString());
		Row data = rs.one();
		if(data != null) {
			e = new Employee(data.getString("id"));
			e.setName(data.getString("name"));
			e.setPasscode(data.getString("pass"));
			e.setSuperStatus(data.getBoolean("superstatus"));
			e.setDeptHeadStatus(data.getBoolean("deptheadstatus"));
			e.setBenCoStatus(data.getBoolean("bencostatus"));
			e.setSupervisorID(data.getString("supervisorid"));
			e.setActiveRequests(data.getList("activerequests", String.class));
			e.setClosedRequests(data.getList("closedrequests", String.class));
			e.setBenefitsUsed(data.getInt("benefitsused"));
			e.setBenefitsRemaining(data.getInt("benefitsremaining"));
		}
		log.trace("Returning employee");
		return e;
	}

	public void updateEmployee(Employee e) {
		log.trace("Updating employee "+ e.getName());
		String query = "update employees set "+
				"name = ?, pass = ?, superstatus = ?, deptheadstatus = ?, "+
				"supervisorid = ?, activerequests = ?, closedrequests = ?, "+
				"benefitsused = ?, benefitsremaining = ? "+
				"where id = ?";
		SimpleStatement s = new SimpleStatementBuilder(query).setConsistencyLevel(DefaultConsistencyLevel.LOCAL_QUORUM).build();
		BoundStatement bound = session.prepare(s).bind(
				e.getName(), e.getPasscode(), e.isSuperStatus(), e.isDeptHeadStatus(),
				e.getSupervisorID(), e.getActiveRequests(), e.getClosedRequests(),
				e.getBenefitsUsed(), e.getBenefitsRemaining(), e.getEmpID());
		session.execute(bound);
		log.trace("Query executed");
	}
}
