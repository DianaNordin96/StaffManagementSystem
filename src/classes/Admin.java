/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import exception.InvalidUserException;
import exception.QueryException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author User
 */
public class Admin extends Employee {

    private String password = "admin";
    private double price;
    private String[][] admin;

    public Admin() {
    }

    public Admin(String password, String empID) {
        super(empID);
        this.password = password;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public boolean addAdmin() throws QueryException {

        String sql = "INSERT INTO ADMIN VALUES(?,?)";
        boolean status = false;

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.getEmp_id());
            pstmt.setString(2, this.password);

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

    public boolean deleteAdmin(String empID) throws QueryException {

        boolean status = false;
        String sql = "DELETE FROM ADMIN WHERE STAFFID = '" + empID + "'";

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

    public void updatePassword(String empID) {
        String sql = "UPDATE ADMIN SET PASSWORD ='" + this.password + "' WHERE STAFFID = '" + empID + "'";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean checkAdmin(String empID, String password) throws InvalidUserException {

        boolean status = false;

        String sql = "SELECT * FROM admin WHERE staffid = '" + empID + "' AND password = '" + password + "'";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            if (rs.next()) {
                status = true;
            } else {
                status = false;
//                throw new InvalidUserException();
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return status;
    }

    public String[][] selectAllAdmin() {

        String sql = "SELECT * FROM admin , employee WHERE employee.emp_id = admin.staffid";

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
                this.admin = new String[count][3];
                while (rs.next()) {
                    this.admin[i][0] = rs.getString("staffid");
                    this.admin[i][1] = rs.getString("emp_name");
                    this.admin[i][2] = rs.getString("password");
                    i += 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return this.admin;
    }

}
