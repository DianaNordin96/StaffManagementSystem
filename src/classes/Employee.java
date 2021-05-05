package classes;

import exception.InvalidUserException;
import exception.QueryException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;
import java.sql.Timestamp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User
 */
public class Employee {

    private String emp_id, emp_name, emp_contact, emp_email, emp_ic, emp_address, emp_gender, emp_pasword;
    private Date emp_dob;
    private int emp_position, emp_department;
    private String[][] employee = null;
    private String[] employeeOne = null;

    public Employee() {
    }

    public Employee(String empID) {
        this.emp_id = empID;
    }

    public String getEmp_pasword() {
        return emp_pasword;
    }

    public void setEmp_pasword(String emp_pasword) {
        this.emp_pasword = emp_pasword;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public String getEmp_contact() {
        return emp_contact;
    }

    public void setEmp_contact(String emp_contact) {
        this.emp_contact = emp_contact;
    }

    public String getEmp_email() {
        return emp_email;
    }

    public void setEmp_email(String emp_email) {
        this.emp_email = emp_email;
    }

    public String getEmp_ic() {
        return emp_ic;
    }

    public void setEmp_ic(String emp_ic) {
        this.emp_ic = emp_ic;
    }

    public Date getEmp_dob() {
        return emp_dob;
    }

    public void setEmp_dob(Date emp_dob) {
        this.emp_dob = emp_dob;
    }

    public int getEmp_position() {
        return emp_position;
    }

    public void setEmp_position(int emp_position) {
        this.emp_position = emp_position;
    }

    public int getEmp_department() {
        return emp_department;
    }

    public void setEmp_department(int emp_department) {
        this.emp_department = emp_department;
    }

    public String getEmp_address() {
        return emp_address;
    }

    public void setEmp_address(String emp_address) {
        this.emp_address = emp_address;
    }

    public String getEmp_gender() {
        return emp_gender;
    }

    public void setEmp_gender(String emp_gender) {
        this.emp_gender = emp_gender;
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

    /**
     * Insert a new row into the warehouses table
     *
     * @param name
     * @param capacity
     */
    public boolean addEmployee() throws QueryException {
        String sql = "INSERT INTO employee(emp_id,emp_name,emp_phonenumber,emp_email,emp_ic,emp_dob,emp_positionid,emp_departmentid,emp_gender,emp_address,emp_password, updated_at) VALUES(?,?,?,?,?,?,?,?,?,?,?,?)";
        Employee emp = new Employee();
        boolean status = false;

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.emp_id);
            pstmt.setString(2, this.emp_name);
            pstmt.setString(3, this.emp_contact);
            pstmt.setString(4, this.emp_email);
            pstmt.setString(5, this.emp_ic);
            pstmt.setDate(6, (java.sql.Date) this.emp_dob);
            pstmt.setInt(7, this.emp_position);
            pstmt.setInt(8, this.emp_department);
            pstmt.setString(9, this.emp_gender);
            pstmt.setString(10, this.emp_address);
            pstmt.setString(11, "staff" + this.emp_id);
            pstmt.setTimestamp(12, timestamp);

            if (pstmt.executeUpdate() > 0) {
                status = true;
            } else {
                status = false;
                throw new QueryException();
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return status;
    }

    public String[] getEmployeeID(String empEmail) {

        String sql = "SELECT * FROM employee WHERE emp_email = '" + empEmail + "'";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            Statement st = conn.createStatement();
            Statement sts = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSet rss = sts.executeQuery(sql);

            this.employeeOne = new String[9];
            while (rs.next()) {
                employeeOne[0] = rs.getString("emp_id");
                employeeOne[1] = rs.getString("emp_name");
                employeeOne[2] = rs.getString("emp_phonenumber");
                employeeOne[3] = rs.getString("emp_email");
                employeeOne[4] = rs.getString("emp_ic");
                employeeOne[5] = rs.getString("emp_dob");
                employeeOne[6] = rs.getString("emp_phonenumber");
                employeeOne[7] = rs.getString("emp_gender");
                employeeOne[8] = rs.getString("emp_address");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employeeOne;
    }

    public void deleteEmployee(String empID) {
        String sql = "DELETE FROM EMPLOYEE WHERE emp_id = '" + empID + "'";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    public boolean updateEmployee(String empID) throws QueryException {
        boolean status = false;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        String sql = "UPDATE EMPLOYEE "
                + "SET emp_name='" + this.emp_name + "' , emp_phonenumber='"
                + this.emp_contact + "',emp_email='" + this.emp_email + "'"
                + ",emp_ic='" + this.emp_ic + "' , emp_dob='" + this.emp_dob
                + "' , emp_positionid=" + this.emp_position + " , emp_departmentid="
                + this.emp_department + ""
                + ", emp_gender='" + this.emp_gender + "' ,emp_address='"
                + this.emp_address + "' , updated_at = '" + timestamp + "'"
                + "WHERE emp_id ='" + empID + "'";

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

    public boolean updateEmployeeForStaff(String empID) {

        boolean status = false;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        String sql = "UPDATE EMPLOYEE SET "
                + "emp_name='" + this.emp_name + "' ,"
                + "emp_phonenumber='" + this.emp_contact + "',"
                + "emp_email='" + this.emp_email + "',"
                + "emp_dob='" + this.emp_dob + "' ,"
                + "emp_address='" + this.emp_address + "', updated_at = '" + timestamp + "'"
                + "WHERE emp_id ='" + empID + "'";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            if (pstmt.executeUpdate() > 0) {
                status = true;
            } else {
                status = false;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return status;
    }

    public String[][] selectAllEmployee() {

        String sql = "SELECT * FROM employee, department , position WHERE employee.emp_positionid = position.position_id AND employee.emp_departmentid = department.dep_id";

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
                this.employee = new String[count][10];
                while (rs.next()) {
                    employee[i][0] = rs.getString("emp_id");
                    employee[i][1] = rs.getString("emp_name");
                    employee[i][2] = rs.getString("emp_ic");
                    employee[i][3] = rs.getString("emp_gender");
                    employee[i][4] = rs.getString("emp_phonenumber");
                    employee[i][5] = rs.getString("emp_dob");
                    employee[i][6] = rs.getString("emp_email");
                    employee[i][7] = rs.getString("emp_address");
                    employee[i][8] = rs.getString("dep_name");
                    employee[i][9] = rs.getString("position_name");

                    i += 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employee;
    }

    public String[] selectEmployee(String empId) {

        String sql = "SELECT * FROM employee WHERE emp_id = '" + empId + "'";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            Statement st = conn.createStatement();
            Statement sts = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            ResultSet rss = sts.executeQuery(sql);

            this.employeeOne = new String[9];
            while (rs.next()) {
                employeeOne[0] = rs.getString("emp_id");
                employeeOne[1] = rs.getString("emp_name");
                employeeOne[2] = rs.getString("emp_phonenumber");
                employeeOne[3] = rs.getString("emp_email");
                employeeOne[4] = rs.getString("emp_ic");
                employeeOne[5] = rs.getString("emp_dob");
                employeeOne[6] = rs.getString("emp_phonenumber");
                employeeOne[7] = rs.getString("emp_gender");
                employeeOne[8] = rs.getString("emp_address");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employeeOne;
    }

    public String[][] selectAllEmployeeNotAdmin() {

        String sql = "SELECT * FROM employee,department,position WHERE department.dep_id = employee.emp_departmentid AND position.position_id = employee.emp_positionid AND employee.emp_id NOT IN (SELECT staffid FROM admin)";

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
                this.employee = new String[count][10];
                while (rs.next()) {
                    employee[i][0] = rs.getString("emp_id");
                    employee[i][1] = rs.getString("emp_name");
                    employee[i][2] = rs.getString("emp_ic");
                    employee[i][3] = rs.getString("emp_gender");
                    employee[i][4] = rs.getString("emp_phonenumber");
                    employee[i][5] = rs.getString("emp_dob");
                    employee[i][6] = rs.getString("emp_email");
                    employee[i][7] = rs.getString("emp_address");
                    employee[i][8] = rs.getString("dep_name");
                    employee[i][9] = rs.getString("position_name");

                    i += 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return employee;
    }

    public boolean checkEmployee(String empEmail, String password) throws InvalidUserException {

        boolean status = false;

        String sql = "SELECT * FROM employee WHERE emp_email = '" + empEmail + "' AND emp_password = '" + password + "'";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                status = true;
            } else {
                status = false;
                throw new InvalidUserException();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return status;
    }

    public boolean updatePassword(String password, String empID, boolean status) throws QueryException {

        boolean statusQuery = false;
        if (status == true) {
            String sql = "UPDATE admin SET password = '" + password + "' WHERE staffid = '" + empID + "'";

            try (Connection conn = this.connect();
                    PreparedStatement pstmt = conn.prepareStatement(sql)) {

                if (pstmt.executeUpdate() > 0) {
                    statusQuery = true;
                } else {
                    statusQuery = false;
                    throw new QueryException();
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        } else {
            String sql = "UPDATE employee SET emp_password = '" + password + "' WHERE emp_id = '" + empID + "'";

            try (Connection conn = this.connect();
                    PreparedStatement pstmt = conn.prepareStatement(sql)) {

                if (pstmt.executeUpdate() > 0) {
                    statusQuery = true;
                } else {
                    statusQuery = false;
                    throw new QueryException();
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return statusQuery;
    }

}
