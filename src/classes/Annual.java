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

/**
 *
 * @author User
 */
public class Annual {

    private int AnnualID;
    private String AnnualType;
    private String AnnualDateFrom;
    private String AnnualDateTo;
    private String AnnualReason;
    private String empId;

    public Annual() {
    }

    public int getAnnualID() {
        String sql = "SELECT * FROM annual ORDER BY annual_id DESC ";

        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {

            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                int id = rs.getInt("annual_id");
                this.AnnualID = id + 1;
            } else {
                this.AnnualID = 1;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return AnnualID;
    }

    public void setAnnualID(int AnnualID) {
        this.AnnualID = AnnualID;
    }

    public String getAnnualType() {
        return AnnualType;
    }

    public void setAnnualType(String AnnualType) {
        this.AnnualType = AnnualType;
    }

    public String getAnnualDateFrom() {
        return AnnualDateFrom;
    }

    public void setAnnualDateFrom(String AnnualDateFrom) {
        this.AnnualDateFrom = AnnualDateFrom;
    }

    public String getAnnualDateTo() {
        return AnnualDateTo;
    }

    public void setAnnualDateTo(String AnnualDateTo) {
        this.AnnualDateTo = AnnualDateTo;
    }

    public String getAnnualReason() {
        return AnnualReason;
    }

    public void setAnnualReason(String AnnualReason) {
        this.AnnualReason = AnnualReason;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
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

    public boolean addAnnual() throws QueryException {
        String sql = "INSERT INTO ANNUAL VALUES(?,?,?,?,?,?,?,?)";
        boolean status = false;
        Department dept = new Department();
        try (Connection conn = this.connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, this.empId);
            pstmt.setInt(2, this.AnnualID);
            pstmt.setString(3, this.AnnualType);
            pstmt.setString(4, this.AnnualDateFrom);
            pstmt.setString(5, this.AnnualDateTo);
            pstmt.setString(6, this.AnnualReason);
            pstmt.setString(7, null);
            pstmt.setString(8, null);

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

    public boolean approve(int annualId) throws QueryException {
        String sql = "UPDATE ANNUAL SET approval = 1 WHERE annual_id = " + annualId + "";
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

    public boolean declined(int annualId, String reasonDeclined) throws QueryException {
        String sql = "UPDATE ANNUAL SET approval = 0 , reason_declined = '" + reasonDeclined + "' WHERE annual_id = " + annualId + "";
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

    public String[][] selectAnnualPending() {

        String[][] pendingArray = null;

        String sql = "SELECT * FROM annual WHERE approval is null";

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
                pendingArray = new String[count][6];
                while (rs.next()) {
                    pendingArray[i][0] = rs.getString("emp_id");
                    pendingArray[i][1] = rs.getString("annual_id");
                    pendingArray[i][2] = rs.getString("annual_type");
                    pendingArray[i][3] = rs.getString("leave_from");
                    pendingArray[i][4] = rs.getString("leave_to");
                    pendingArray[i][5] = rs.getString("reason");
                    i += 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return pendingArray;
    }

    public String[][] selectAnnualApproved() {

        String[][] approvedArray = null;

        String sql = "SELECT * FROM annual WHERE approval = 1";

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
                approvedArray = new String[count][6];
                while (rs.next()) {
                    approvedArray[i][0] = rs.getString("emp_id");
                    approvedArray[i][1] = rs.getString("annual_id");
                    approvedArray[i][2] = rs.getString("annual_type");
                    approvedArray[i][3] = rs.getString("leave_from");
                    approvedArray[i][4] = rs.getString("leave_to");
                    approvedArray[i][5] = rs.getString("reason");
                    i += 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return approvedArray;
    }

    public String[][] selectAnnualDeclined() {

        String[][] declinedArray = null;

        String sql = "SELECT * FROM annual WHERE approval = 0";

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
                declinedArray = new String[count][7];
                while (rs.next()) {
                    declinedArray[i][0] = rs.getString("emp_id");
                    declinedArray[i][1] = rs.getString("annual_id");
                    declinedArray[i][2] = rs.getString("annual_type");
                    declinedArray[i][3] = rs.getString("leave_from");
                    declinedArray[i][4] = rs.getString("leave_to");
                    declinedArray[i][5] = rs.getString("reason");
                    declinedArray[i][6] = rs.getString("reason_declined");
                    i += 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return declinedArray;
    }

    public String[][] staffAnnual(String emp_id) {

        String[][] staffAnnualdArray = null;

        String sql = "SELECT * FROM annual WHERE emp_id = '" + emp_id + "'";

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
                staffAnnualdArray = new String[count][6];
                while (rs.next()) {
                    staffAnnualdArray[i][0] = rs.getString("annual_type");
                    staffAnnualdArray[i][1] = rs.getString("leave_from");
                    staffAnnualdArray[i][2] = rs.getString("leave_to");
                    staffAnnualdArray[i][3] = rs.getString("reason");
                    staffAnnualdArray[i][4] = rs.getString("approval");
                    staffAnnualdArray[i][5] = rs.getString("reason_declined");
                    i += 1;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return staffAnnualdArray;
    }
}
