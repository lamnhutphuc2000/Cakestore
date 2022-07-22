/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.role;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import phucln.utils.DBHelper;

/**
 *
 * @author ASUS
 */
public class RoleDAO implements Serializable {

    public String getIDOfAdmin() throws NamingException, SQLException {
        Connection con = null;
        PreparedStatement stm = null;
        ResultSet rs = null;
        String result = "";
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT roleID "
                        + "FROM tblRole "
                        + "WHERE name = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "Admin");
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = rs.getString("roleID");
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
}
