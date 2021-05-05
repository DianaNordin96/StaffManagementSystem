package classes;

import exception.QueryException;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Arrays;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author User
 */
public class Department {

    private int DepartID;
    private String DepartName;
    String[][] department = null;

    public int getDepartID() {

        String sql = "SELECT * FROM department ORDER BY dep_id DESC ";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                int id = rs.getInt("dep_id");
                this.DepartID = id + 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return DepartID;
    }

    public void setDepartID(int DepartID) {
        this.DepartID = DepartID;
    }

    public String getDepartName() {
        return DepartName;
    }

    public void setDepartName(String DepartName) {
        this.DepartName = DepartName;
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
    public boolean addDepartment(String empID) throws QueryException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String sql = "INSERT INTO DEPARTMENT VALUES(?,?,?,?)";
        boolean status = false;
        Department dept = new Department();
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.DepartID);
            pstmt.setString(2, this.DepartName);
            pstmt.setTimestamp(3, timestamp);
            pstmt.setString(4, empID);
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

    public boolean deleteDepartment(int depID) {
        boolean status = false;
        String sql = "DELETE FROM DEPARTMENT WHERE DEP_ID = " + depID + "";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            if (pstmt.executeUpdate() > 0) {
                status = true;
            }else{
                status = false;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return status;

    }

    public boolean updateDepartment(String empID , int depID) {
        boolean status = false;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String sql = "UPDATE DEPARTMENT SET DEP_NAME ='" + this.DepartName + "', UPDATED_AT = '" + timestamp + "' , EMPLOYEE_ID = '" + empID + "' WHERE DEP_ID = " + depID + "";

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

    public String[][] selectAllDepartment() {

        String sql = "SELECT * FROM department";

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
                this.department = new String[count][2];
                while (rs.next()) {
                    department[i][0] = Integer.toString(rs.getInt("dep_id"));
                    department[i][1] = rs.getString("dep_name");
                    i += 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return department;
    }

    public String[] selectDepartment(int depId) {

        String[] departmentArray = new String[2];
        String sql = "SELECT * FROM department WHERE dep_id = " + depId;

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while (rs.next()) {
                departmentArray[0] = Integer.toString(rs.getInt("dep_id"));
                departmentArray[1] = rs.getString("dep_name");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return departmentArray;
    }

}
