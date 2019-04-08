package com.techelevator.projects.model.jdbc;

import java.util.ArrayList;
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
		//create sql query
		//SqlRowSet results = jdbcTemplate.queryForRowSet(sqlqueryname)
		//return arraylist
		return new ArrayList<>();
	}

	@Override
	public List<Department> searchDepartmentsByName(String nameSearch) {
		//create an instance of Arraylist
		//create sql query with clause WHERE  name = ?
		//SqlRowSet results = jdbcTemplate.queryForRowSet(sqlqueryname)
		//return arraylist
		return new ArrayList<>();
	}

	@Override
	public void saveDepartment(Department updatedDepartment) {
		//sqlquery that has INSERT INTO and VALUES
		//where(result.next())
		//updatedDepartment.setID
		//jdbcTemplate.update(sqlInsertCity,
	}


	@Override
	public Department createDepartment(Department newDepartment) {
		return null;
	}

	@Override
	public Department getDepartmentById(Long id) {
		//Department theDepartment = null;
		//sql query with clause WHERE id = ?
		//SqlRowSet results = jdbcTemplate.queryForRowSet(sqlqueryname, id)

		//if(results.next()){  theDepartment = mapRowToDepartment

		return null;
	}

	private Department mapRowToDepartment(SqlRowSet results) {
		Department theDepartment = new Department();

		theDepartment.setId(results.getLong("id"));
		theDepartment.setName(results.getString("name"));

		return theDepartment;

	}

}
