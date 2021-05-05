/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import exception.QueryException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author User
 */
public class Salary extends Employee {

    private double Salary;
    private String month;
    private String empId;
    String[][] salaries;

    public Salary() {
    }

    public Salary(double Salary, String month, String empID) {
        super(empID);
        this.Salary = Salary;
        this.month = month;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public double getSalary() {
        return Salary;
    }

    public void setSalary(double Salary) {
        this.Salary = Salary;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    private Connection connect() {
        // SQLite connection string
        String url = "jdbc:derby://localhost:1527/Administrator";
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public boolean addSalary() throws QueryException {
        String sql = "INSERT INTO SALARY VALUES(?,?,?,?)";
        boolean status = false;

        Date date = new Date();
        int year = date.getYear();
        year += 1900;
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.getEmp_id());
            pstmt.setDouble(2, this.Salary);
            pstmt.setString(3, this.month);
            pstmt.setString(4, Integer.toString(year));
            if (pstmt.executeUpdate() > 0) {
                status = true;
            } else {
                status = false;
                throw new QueryException();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return status;
    }

    public boolean deleteSalary() throws QueryException {
        String sql = "DELETE from salary where emp_id = '" + this.empId + "' AND emp_salary = " + this.Salary + " AND emp_salary_month = '" + this.month + "'";
        boolean status = false;

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (pstmt.executeUpdate() > 0) {
                status = true;
            } else {
                status = false;
                throw new QueryException();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return status;
    }

    public String[][] findSalary(String userId) {

        String sql = "SELECT * FROM salary WHERE emp_id ='" + userId + "'";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            Statement st = conn.createStatement();
            Statement sts = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSet rss = sts.executeQuery(sql);
            int i = 0;
            int count = 0;

            while (rss.next()) {
                count += 1;
            }

            if (count != 0) {
                this.salaries = new String[count][3];
                while (rs.next()) {
                    this.salaries[i][0] = rs.getString("emp_salary");
                    this.salaries[i][1] = rs.getString("emp_salary_month");
                    this.salaries[i][2] = rs.getString("emp_salary_year");
                    i += 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return this.salaries;
    }

    public String[][] selectAllSalary() {

        String sql = "SELECT * FROM salary ";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            Statement st = conn.createStatement();
            Statement sts = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSet rss = sts.executeQuery(sql);
            int i = 0;
            int count = 0;

            while (rss.next()) {
                count += 1;
            }

            if (count != 0) {
                this.salaries = new String[count][4];
                while (rs.next()) {
                    this.salaries[i][0] = rs.getString("emp_id");
                    this.salaries[i][1] = rs.getString("emp_salary");
                    this.salaries[i][2] = rs.getString("emp_salary_month");
                    this.salaries[i][3] = rs.getString("emp_salary_year");
                    i += 1;
                }
            } else {
                this.salaries = null;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return this.salaries;
    }

    public boolean updateSalary() throws QueryException {
        String sql = "UPDATE SALARY SET emp_salary = " + this.Salary + " WHERE emp_id = '" + this.empId + "' AND emp_salary_month = '" + this.month + "'";
        boolean status = false;
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (pstmt.executeUpdate() > 0) {
                status = true;
            } else {
                status = false;
                throw new QueryException();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return status;
    }
}
