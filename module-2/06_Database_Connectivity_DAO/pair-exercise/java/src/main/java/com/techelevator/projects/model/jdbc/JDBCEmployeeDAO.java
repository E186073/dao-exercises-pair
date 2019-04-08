package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import com.techelevator.projects.model.Department;
import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.projects.model.Employee;
import com.techelevator.projects.model.EmployeeDAO;
import org.springframework.jdbc.support.SQLErrorCodes;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCEmployeeDAO implements EmployeeDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCEmployeeDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> employees = new ArrayList<>();

		String sqlGetAllEmployees = "SELECT employee_id, department_id, first_name, last_name, birth_date, gender, hire_date "+
								    "FROM employee ";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllEmployees);
		employees = mapRowSetEmployeeList(results);

		return employees;
	}

	@Override
	public List<Employee> searchEmployeesByName(String firstNameSearch, String lastNameSearch) {
		List<Employee> employees = new ArrayList<>();

		String sqlSearchEmployeesByName = "SELECT * " +
										  "FROM employee " +
										  "WHERE first_name ILIKE ? AND last_name ILIKE ?";
		firstNameSearch = "%" + firstNameSearch;
		lastNameSearch = "%" + lastNameSearch;
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchEmployeesByName,firstNameSearch, lastNameSearch);

		employees = mapRowSetEmployeeList(results);
		return employees;
	}

	@Override
	public List<Employee> getEmployeesByDepartmentId(long id) {
		List<Employee> employees = new ArrayList<>();

		String sqlGetEmployeesByDepartmentId = "SELECT * " +
				                               "FROM employee " +
				                               "WHERE department_id = ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetEmployeesByDepartmentId, id);

		employees = mapRowSetEmployeeList(results);

		return employees;
	}

	@Override
	public List<Employee> getEmployeesWithoutProjects() {
		List<Employee> employees = new ArrayList<>();

		String sqlGetEmployeeWithoutProjects = "SELECT * " +
				                               "FROM employee " +
				                               "LEFT JOIN project_employee ON employee.employee_id = project_employee.employee_id " +
				                               "WHERE project_employee.employee_id IS NULL";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetEmployeeWithoutProjects);

		employees = mapRowSetEmployeeList(results);

		return employees;
	}

	@Override
	public List<Employee> getEmployeesByProjectId(Long projectId) {
		List<Employee> employees;

		String sqlGetEmployeesByProjectId = "SELECT * FROM employee " +
											"JOIN project_employee ON employee.employee_id = project_employee.employee_id "+
											"WHERE project_employee.project_id = ?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetEmployeesByProjectId, projectId);

		employees = mapRowSetEmployeeList(results);

		return employees;
	}

	@Override
	public void changeEmployeeDepartment(Long employeeId, Long departmentId) {
		
	}
	private Employee mapRowToEmployee(SqlRowSet results) {
		Employee theEmployee = new Employee();

		theEmployee.setId(results.getLong("employee_id"));
		theEmployee.setDepartmentId(results.getLong("department_id"));
		theEmployee.setFirstName(results.getString("first_name"));
		theEmployee.setLastName(results.getString("last_name"));
		theEmployee.setBirthDay(results.getDate("birth_date").toLocalDate());
		theEmployee.setGender(results.getString("gender").charAt(0));
		theEmployee.setHireDate(results.getDate("hire_date").toLocalDate());

		return theEmployee;

	}

	private List<Employee> mapRowSetEmployeeList(SqlRowSet results){
		List<Employee> employees = new ArrayList<>();

		while(results != null && results.next()){
			employees.add(mapRowToEmployee(results));
		}

		return employees;
	}

}
