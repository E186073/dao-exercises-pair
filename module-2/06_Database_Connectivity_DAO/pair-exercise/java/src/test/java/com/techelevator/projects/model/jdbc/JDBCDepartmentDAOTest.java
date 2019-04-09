package com.techelevator.projects.model.jdbc;

import com.techelevator.projects.model.Department;
import org.junit.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import javax.print.DocFlavor;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class JDBCDepartmentDAOTest {


    //private static final  int DEPARTMENTID = 1;
    private static SingleConnectionDataSource dataSource;
    private JDBCDepartmentDAO dao;

    @BeforeClass
    public static void setupDataSource() {
        dataSource = new SingleConnectionDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/projects");
        dataSource.setUsername("postgres");
        dataSource.setPassword("postgres1");

        dataSource.setAutoCommit(false);
    }

    @AfterClass
    public static void closeDataSource() throws SQLException {
        dataSource.destroy();
    }


    @Before
    public void setup(){
        dao = new JDBCDepartmentDAO(dataSource);
    }

    @After
    public void rollback() throws SQLException {
        dataSource.getConnection().rollback();
    }
    @Test
    public void getAllDepartmentsTest(){
        //Act
        List<Department> thedepartment = dao.getAllDepartments();

        //Assert
        //compare department size
        assertEquals(5, thedepartment.size());
        //compare department is not null
        assertNotNull(thedepartment);

    }

    @Test
    public void searchDepartmentByNameTest() {
        //dao.createdepartment input department with name something weird
        List<Department> departmentsWithTrain  = dao.searchDepartmentsByName("train");


        assertNotNull(departmentsWithTrain);
        assertEquals(1, departmentsWithTrain.size());

    }

    @Test
    public void saveDepartmentTest(){


    }

    @Test
    public void getNextDepartmentById(){

    }

    @Test
    public void createDepartment(){


    }

    @Test
    public void getDepartmentById(){
        //Arrange

        Department theDepartment = getDepartment("departmentName");
        theDepartment = dao.createDepartment(theDepartment);

        //Act
        Department createDepartment = dao.getDepartmentById(theDepartment.getId());

        //Assert
        assertNotEquals(null, createDepartment);
        assertDepartmentAreEqual(theDepartment, createDepartment);

    }



    private Department getDepartment(String name) {
        Department theDepartment = new Department();
        theDepartment.setName(name);
        return theDepartment;
    }

    private void assertDepartmentAreEqual(Department expected, Department actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

}
