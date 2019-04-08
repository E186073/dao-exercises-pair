package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.projects.model.Department;
import com.techelevator.projects.model.DepartmentDAO;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCDepartmentDAO implements DepartmentDAO {
	
	private JdbcTemplate jdbcTemplate;

	public JDBCDepartmentDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Department> getAllDepartments() {
		//create an instance of Arraylist
		List<Department> departments = new ArrayList<>();
		//create sql query
		String sqlGetAllDepartments = "SELECT department_id , name "+
				                      "FROM department ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllDepartments);
		departments = mapSqlRowSetDepartmentList(results);

		//return arraylist
		return departments;
	}

	@Override
	public List<Department> searchDepartmentsByName(String nameSearch) {
		//create an instance of Arraylist
		List<Department> departments = new ArrayList<>();

		//create sql query with clause WHERE  name = ?
		String sqlSearchDepartmentsByName = "SELECT department_id, name " +
				                            "FROM department " +
				                            "WHERE name = ?";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlSearchDepartmentsByName, nameSearch);
		departments = mapSqlRowSetDepartmentList(results);
		//return arraylist
		return departments;
	}

	@Override
	public void saveDepartment(Department updatedDepartment) {
		//sqlquery that has UPDATE clause
		String sqlSaveDepartment = "UPDATE department " +
				                   "SET department_id = ? " +
				                   "name = ?";



		jdbcTemplate.update(sqlSaveDepartment, updatedDepartment.getId(), updatedDepartment.getName());
	}

	private long getNextDepartmentId() {
		SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('seq_department_id')");
		if(nextIdResult.next()) {
			return nextIdResult.getLong(1);
		} else {
			throw new RuntimeException("Something went wrong while getting an id for the new department");
		}
	}

	@Override
	public Department createDepartment(Department newDepartment) {
		//sqlquery that has INSERT INTO and VALUES
		String sqlCreateDepartment = "INSERT INTO department(department_id, name) " +
				                   "VALUES(?, ?)";

		newDepartment.setId(getNextDepartmentId());

		jdbcTemplate.update(sqlCreateDepartment, newDepartment.getId(), newDepartment.getName());

		return getDepartmentById(newDepartment.getId());


	}

	@Override
	public Department getDepartmentById(Long id) {
		Department theDepartment = null;
		//sql query with clause WHERE id = ?
		String sqlGetDepartmentById = "SELECT department_id, name " +
				                      "FROM department " +
				                      "WHERE department_id = ?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetDepartmentById, id);

		if(results.next()){
			theDepartment = mapRowToDepartment(results);
		}

		return theDepartment;
	}

	private Department mapRowToDepartment(SqlRowSet results) {
		Department theDepartment = new Department();

		theDepartment.setId(results.getLong("department_id"));
		theDepartment.setName(results.getString("name"));

		return theDepartment;

	}

	private List<Department> mapSqlRowSetDepartmentList(SqlRowSet results){

		List<Department> departments = new ArrayList<>();

		while(results != null && results.next()){
			departments.add(mapRowToDepartment(results));
		}

		return departments;
	}

}
