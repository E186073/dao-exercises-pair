package com.techelevator.projects.model.jdbc;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.rmi.PortableRemoteObject;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.techelevator.projects.model.Project;
import com.techelevator.projects.model.ProjectDAO;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JDBCProjectDAO implements ProjectDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCProjectDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Project> getAllActiveProjects() {
		List<Project> projects;

		String sqlGetAllActiveProjects = "SELECT * FROM project " +
				 			              "WHERE from_date IS NOT NULL AND from_date < ? AND to_date > ? ";
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllActiveProjects, LocalDate.now(), LocalDate.now());

		projects = mapRowSetProjectList(results);

		return projects;
	}

	@Override
	public void removeEmployeeFromProject(Long projectId, Long employeeId) {

		String sqlRemoveEmployeeFromProject = "DELETE FROM project_employee " +
										      "WHERE project_id = ? AND employee_id = ?";

		jdbcTemplate.update(sqlRemoveEmployeeFromProject, projectId, employeeId);



		
	}

	@Override
	public void addEmployeeToProject(Long projectId, Long employeeId) {

		String sqlAddEmployeeToProject = "UPDATE project_employee " +
										 "SET project_id = ?, employee_id = ? " +
										 "WHERE project_id = ? AND employee_id = ?";

		jdbcTemplate.update(sqlAddEmployeeToProject, projectId, employeeId, projectId, employeeId);
		
	}
	private Project mapRowToProject(SqlRowSet results) {
		Project theProject = new Project();

		theProject.setId(results.getLong("project_id"));
		theProject.setName(results.getString("name"));
		theProject.setStartDate(results.getDate("from_date").toLocalDate());
		theProject.setEndDate(results.getDate("to_date").toLocalDate());

		return theProject;

	}

	private List<Project> mapRowSetProjectList(SqlRowSet results) {
		List<Project> projects = new ArrayList<>();

		while (results != null && results.next()) {
			projects.add(mapRowToProject(results));
		}

		return projects;
	}

}
