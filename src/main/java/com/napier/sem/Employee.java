package com.napier.sem;

import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.Connection;
import java.util.AbstractList;
import java.util.ArrayList;

public class Employee {

    /**
     * Employee number
     */
    public int emp_no;

    /**
     * Employee's first name
     */
    public String first_name;

    /**
     * Employee's last name
     */
    public String last_name;

    /**
     * Employee's job title
     */
    public String title;

    /**
     * Employee's salary
     */
    public int salary;

    /**
     * Employee's current department
     */
    public String dept_name;

    /**
     * Employee's manager
     */
    public String manager;

    public ArrayList<com.napier.sem.Employee> employees = new ArrayList<>();

    public Employee getEmployee(Connection con, int ID) {
        try {

            // String url = System.getenv("DB_URL");
            // String user = System.getenv("DB_USER");
            // String password = System.getenv("DB_PASSWORD");

            // Connection connection = DriverManager.getConnection(url, user, password);


            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT emp_no, first_name, last_name "
                            + "FROM employees "
                            + "WHERE emp_no = " + ID;
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Return new employee if valid.
            // Check one is returned
            if (rset.next()) {
                Employee emp = new Employee();
                emp.emp_no = rset.getInt("emp_no");
                emp.first_name = rset.getString("first_name");
                emp.last_name = rset.getString("last_name");
                return emp;
            } else
                return null;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get employee details");
            return null;
        }
    }

    public void displayEmployee(Employee emp) {
        if (emp != null) {
            System.out.println(
                    emp.emp_no + " "
                            + emp.first_name + " "
                            + emp.last_name + "\n"
                            + emp.title + "\n"
                            + "Salary:" + emp.salary + "\n"
                            + emp.dept_name + "\n"
                            + "Manager: " + emp.manager + "\n");
        }
    }

    /**
     * Gets all the current employees and salaries.
     *
     * @return A list of all employees and salaries, or null if there is an error.
     */
    public ArrayList<com.napier.sem.Employee> getAllSalaries(Connection con) {
        try {
            // Create an SQL statement
            Statement stmt = con.createStatement();
            // Create string for SQL statement
            String strSelect =
                    "SELECT employees.emp_no, employees.first_name, employees.last_name, salaries.salary "
                            + "FROM employees, salaries "
                            + "WHERE employees.emp_no = salaries.emp_no AND salaries.to_date = '9999-01-01' "
                            + "ORDER BY employees.emp_no ASC";
            // Execute SQL statement
            ResultSet rset = stmt.executeQuery(strSelect);
            // Extract employee information
            while (rset.next()) {
                com.napier.sem.Employee emp = new Employee();
                emp.emp_no = rset.getInt("employees.emp_no");
                emp.first_name = rset.getString("employees.first_name");
                emp.last_name = rset.getString("employees.last_name");
                emp.salary = rset.getInt("salaries.salary");
                employees.add(emp);
            }
            return employees;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Failed to get salary details");
            return null;
        }
    }
}
