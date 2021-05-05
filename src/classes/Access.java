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
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author User
 */
public class Access {

    private int accessId;
    String[][] access;

    public Access() {
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

    public int getAccessId() {
        String sql = "SELECT * FROM access ORDER BY access_id DESC ";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                int id = rs.getInt("access_id");
                this.accessId = id + 1;
            } else {
                this.accessId = 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return this.accessId;
    }

    public void setAccessId(int accessId) {
        this.accessId = accessId;
    }

    public boolean login(String empId) throws QueryException {

        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);

        String sql = "INSERT INTO ACCESS (access_id,emp_id,login_time) VALUES(?,?,?)";
        boolean status = false;
        Department dept = new Department();
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.accessId);
            pstmt.setString(2, empId);
            pstmt.setTimestamp(3, ts);

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

    public void logout(int accessId) {

        Date date = new Date();
        long time = date.getTime();
        Timestamp ts = new Timestamp(time);

        String sql = "UPDATE ACCESS SET logout_time = ? WHERE access_id = " + accessId;

        Department dept = new Department();
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setTimestamp(1, ts);
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public String[][] selectAllAccess() {

        String sql = "SELECT * FROM access WHERE logout_time IS NOT NULL";

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
                this.access = new String[count][4];
                while (rs.next()) {
                    this.access[i][0] = rs.getString("access_id");
                    this.access[i][1] = rs.getString("emp_id");
                    this.access[i][2] = rs.getString("login_time");
                    this.access[i][3] = rs.getString("logout_time");
                    i += 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return this.access;
    }

}
