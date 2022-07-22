/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.order;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import phucln.utils.DBHelper;

/**
 *
 * @author ASUS
 */
public class OrderDAO implements Serializable {

    public boolean checkoutCart(String orderID, String userID, String shippingAddress, float totalPrice, String paymentID, String phoneNumber) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "INSERT INTO tblOrder (orderID,userID,orderDate,shippingAddress,totalPrice,paymentID,phoneNumber,paymentStatus) "
                        + "VALUES (?,?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, orderID);
                stm.setString(2, userID);
                Timestamp date = new Timestamp(System.currentTimeMillis());
                stm.setTimestamp(3, date);
                stm.setString(4, shippingAddress);
                stm.setFloat(5, totalPrice);
                stm.setString(6, paymentID);
                stm.setString(7, phoneNumber);
                stm.setString(8, "Not Paid");
                int rowsOfEffected = stm.executeUpdate();
                if (rowsOfEffected == 1) {
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public boolean checkExistedID(String orderID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT userID "
                        + "FROM tblOrder "
                        + "WHERE orderID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, orderID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = true;
                }
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public OrderDTO getOrder(String orderID, String userID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        OrderDTO dto = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT  orderDate, shippingAddress, totalprice, paymentID, phoneNumber, paymentStatus "
                        + "FROM tblOrder "
                        + "WHERE orderID = ? AND userID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, orderID);
                stm.setString(2, userID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    Timestamp date = rs.getTimestamp("orderDate");
                    String shippingAddress = rs.getString("shippingAddress");
                    float totalPrice = rs.getFloat("totalPrice");
                    String paymentID = rs.getString("paymentID");
                    String phoneNumber = rs.getString("phoneNumber");
                    String paymentStatus = rs.getString("paymentStatus");
                    dto = new OrderDTO(orderID, userID, date, shippingAddress, totalPrice, paymentID, phoneNumber, paymentStatus);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(OrderDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return dto;
    }
}
