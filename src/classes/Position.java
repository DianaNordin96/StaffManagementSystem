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
import javax.swing.JOptionPane;

/**
 *
 * @author User
 */
public class Position {

    private int positionID;
    private String positionName;
    private String[][] position = null;

    public Position() {
    }

    public int getPositionID() {
        String sql = "SELECT * FROM position ORDER BY position_id DESC ";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                int id = rs.getInt("position_id");
                this.positionID = id + 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return positionID;
    }

    public void setPositionID(int positionID) {
        this.positionID = positionID;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
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
     * @return
     * @throws exception.QueryException
     */
    public boolean addPosition(String empID) throws QueryException {

        boolean status = false;
        String sql = "INSERT INTO POSITION VALUES(?,?,?,?)";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, this.positionID);
            pstmt.setString(2, this.positionName);
            pstmt.setTimestamp(3, timestamp);
            pstmt.setString(4, empID);
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

    public void deletePosition(int posID) {
        String sql = "DELETE FROM POSITION WHERE POSITION_ID = " + posID + "";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public boolean updatePosition(String empID, int postID, String postName) throws QueryException {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String sql = "UPDATE POSITION SET POSITION_NAME ='" + postName + "', UPDATED_AT = '" + timestamp + "', EMPLOYEE_ID = '" + empID + "' WHERE POSITION_ID = " + postID + "";
        boolean status = false;
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.executeUpdate();
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

    public String[][] selectAllPosition() {

        String sql = "SELECT * FROM position";

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
                this.position = new String[count][2];
                while (rs.next()) {
                    position[i][0] = Integer.toString(rs.getInt("position_id"));
                    position[i][1] = rs.getString("position_name");
                    i += 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return position;
    }

}
