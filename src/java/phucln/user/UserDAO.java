/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.user;

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
public class UserDAO implements Serializable {

    public UserDTO checkLogin(String username, String password) throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        UserDTO user = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT name, phone, address, status, roleID "
                        + "FROM tblUser "
                        + "WHERE userID = ? AND password = ? AND status = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, username);
                stm.setString(2, password);
                stm.setString(3, "Active");
                rs = stm.executeQuery();
                if (rs.next()) {
                    String name = rs.getString("name");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    String status = rs.getString("status");
                    String roleID = rs.getString("roleID");
                    user = new UserDTO(username, password, name, phone, address, status, roleID);
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
        return user;
    }
    
    List<UserDTO> listUsers;

    public List<UserDTO> getListUsers() {
        return listUsers;
    }

    public void getActiveUser() throws SQLException, NamingException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT userID, name, phone, address, roleID "
                        + "FROM tblUser "
                        + "WHERE status = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "Active");
                rs = stm.executeQuery();
                while (rs.next()) {
                    if (listUsers == null) {
                        listUsers = new ArrayList<>();
                    }
                    String userID = rs.getString("userID");
                    String name = rs.getString("name");
                    String phone = rs.getString("phone");
                    String address = rs.getString("address");
                    String roleID = rs.getString("roleID");
                    UserDTO dto = new UserDTO(userID, "", name, phone, address, "Active", roleID);
                    listUsers.add(dto);
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
