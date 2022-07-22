/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.orderdetail;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import phucln.utils.DBHelper;

/**
 *
 * @author ASUS
 */
public class OrderDetailDAO implements Serializable {

    public boolean insertOrderDetail(String orderID, String productID, int quantity, float price, String productName) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "INSERT INTO tblOrderDetail (orderID,productID,quantity,price,productName) "
                        + "VALUES (?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, orderID);
                stm.setString(2, productID);
                stm.setInt(3, quantity);
                stm.setFloat(4, price);
                stm.setString(5, productName);
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
    List<OrderDetailDTO> listsOrderDetails;

    public List<OrderDetailDTO> getListsOrderDetails() {
        return listsOrderDetails;
    }

    public void getOrderDetail(String orderID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        OrderDetailDTO dto;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT productID, quantity, price, productName "
                        + "FROM tblOrderDetail "
                        + "WHERE orderID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, orderID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (listsOrderDetails == null) {
                        listsOrderDetails = new ArrayList<>();
                    }
                    String productID = rs.getString("productID");
                    int quantity = rs.getInt("quantity");
                    float price = rs.getFloat("price");
                    String productName = rs.getString("productName");
                    dto = new OrderDetailDTO(orderID, productID, quantity, price, productName);
                    listsOrderDetails.add(dto);
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
    }
}
