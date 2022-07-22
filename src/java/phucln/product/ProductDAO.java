/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.product;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
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
public class ProductDAO implements Serializable {

    public int countAllProduct(String txtSearchName, String categoryID, float priceFrom, int searchQuantity, float priceTo, String status) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int result = 0;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT productID "
                        + "FROM tblProduct "
                        + "WHERE name LIKE ? AND categoryID LIKE ? AND status LIKE ? AND quantity > ?  AND expirationDate > ? AND price >= ? AND price <= ? ";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + txtSearchName + "%");
                stm.setString(2, "%" + categoryID + "%");
                stm.setString(3, "%" + status + "%");
                stm.setInt(4, searchQuantity);
                stm.setDate(5, new Date(System.currentTimeMillis()));
                Date a = new Date(System.currentTimeMillis());
                stm.setFloat(6, priceFrom);
                stm.setFloat(7, priceTo);
                rs = stm.executeQuery();
                while (rs.next()) {
                    result++;
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

    List<ProductDTO> listProducts;

    public List<ProductDTO> getListProduct() {
        return listProducts;
    }

    public void searchProducts(String txtSearchName, String categoryID, float priceFrom, int searchQuantity, float priceTo, int currentPage, Date searchExpirationDate, String statusSearch) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT tmp.productID, tmp.name, tmp.image, tmp.description, tmp.price, tmp.createDate, tmp.expirationDate, tmp.quantity, tmp.categoryID, tmp.status "
                        + "FROM ( "
                        + "SELECT ROW_NUMBER() OVER (ORDER BY createDate DESC) AS row, productID, name, image, description, price, createDate, expirationDate, quantity, categoryID, status "
                        + "FROM tblProduct "
                        + "WHERE name LIKE ? AND categoryID LIKE ? AND status LIKE ? AND quantity > ?  AND expirationDate > ? AND price >= ? AND price <= ? "
                        + ")tmp "
                        + "WHERE tmp.row between ? AND ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "%" + txtSearchName + "%");
                stm.setString(2, "%" + categoryID + "%");
                stm.setString(3, "%" + statusSearch + "%");
                stm.setInt(4, searchQuantity);
                stm.setDate(5, searchExpirationDate);
                stm.setFloat(6, priceFrom);
                stm.setFloat(7, priceTo);
                stm.setInt(8, 20 * (currentPage - 1) + 1);
                stm.setInt(9, 20 * (currentPage - 1) + 1 + 19);
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (listProducts == null) {
                        listProducts = new ArrayList<>();
                    }
                    String productID = rs.getString("productID");
                    String name = rs.getString("name");
                    String image = rs.getString("image");
                    image = "./Img/" + image;
                    String description = rs.getString("description");
                    float price = rs.getFloat("price");
                    Date createDate = rs.getDate("createDate");
                    Date expirationDate = rs.getDate("expirationDate");
                    int quantity = rs.getInt("quantity");
                    String status = rs.getString("status");
                    String txtCategoryID = rs.getString("categoryID");
                    ProductDTO dto = new ProductDTO(productID.trim(), name, image, description, price, createDate, expirationDate, quantity, txtCategoryID.trim(), status.trim());
                    listProducts.add(dto);
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

    public boolean updateProduct(String productID, String name, String description, String image, float price, Date createDate, Date expirationDate, int quantity, String category, String status) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "UPDATE tblProduct "
                        + "SET name = ?, description = ?, image = ?, price = ?, createDate = ?, expirationDate = ?, quantity = ?, categoryID = ?, status = ? "
                        + "WHERE productID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, name);
                stm.setString(2, description);
                stm.setString(3, image);
                stm.setFloat(4, price);
                stm.setDate(5, createDate);
                stm.setDate(6, expirationDate);
                stm.setInt(7, quantity);
                stm.setString(8, category);
                stm.setString(9, status);
                stm.setString(10, productID);
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

    public boolean checkProductExisted(String productID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT name "
                        + "FROM tblProduct "
                        + "WHERE productID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, productID);
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

    public boolean createNewProduct(String productID, String name, String image, String description, float price, Date creatDate, Date expirationDate, int quantity, String categoryID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "INSERT INTO tblProduct (productID, name, image, description, price, createDate, expirationDate, quantity, categoryID, status) "
                        + "VALUES (?,?,?,?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, productID);
                stm.setString(2, name);
                stm.setString(3, image);
                stm.setString(4, description);
                stm.setFloat(5, price);
                stm.setDate(6, creatDate);
                stm.setDate(7, expirationDate);
                stm.setInt(8, quantity);
                stm.setString(9, categoryID);
                stm.setString(10, "Active");
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

    public boolean checkProductQuantity(String productID, int quantity) throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        boolean result = false;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT quantity "
                        + "FROM tblProduct "
                        + "WHERE productID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, productID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    int productQuantity = rs.getInt("quantity");
                    int num = productQuantity - quantity;
                    if (num < 0) {
                        return true;
                    }
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

    public boolean sellProduct(String productID, int quantity) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        boolean result = false;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "UPDATE tblProduct "
                        + "SET quantity = ? "
                        + "WHERE productID = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, quantity);
                stm.setString(2, productID);
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

    public int getQuantityByProductID(String productID) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        int quantity = 0;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT quantity "
                        + "FROM tblProduct "
                        + "WHERE productID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, productID);
                rs = stm.executeQuery();
                if (rs.next()) {
                    quantity = rs.getInt("quantity");
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
        return quantity;
    }
}
