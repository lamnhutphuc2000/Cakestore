/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.servlet;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javax.imageio.ImageIO;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import phucln.category.CategoryDAO;
import phucln.product.ProductDAO;
import phucln.product.UpdateError;
import phucln.updaterecord.UpdateRecordDAO;
import phucln.user.UserDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "UpdateProductServlet", urlPatterns = {"/UpdateProductServlet"})
public class UpdateProductServlet extends HttpServlet {

    private Logger log = null;
    private final String MAIN_PAGE = "searchProduct";
    private final String ERROR_PAGE = "errorPage";

    @Override
    public void init() throws ServletException {
        log = Logger.getLogger(this.getClass().getName());
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        ServletContext context = request.getServletContext();
        ResourceBundle resourceBundle = (ResourceBundle) context.getAttribute("SITE_MAP");
        String url = resourceBundle.getString(ERROR_PAGE);

        try {
            if (session != null) {
                UserDTO userDto = (UserDTO) session.getAttribute("user");
                if (userDto != null) {
                    String roleIDOfAdmin = (String) session.getAttribute("roleIDOfAdmin");
                    if (roleIDOfAdmin != null && roleIDOfAdmin.equals(userDto.getRoleID())) {

                        //get Param
                        String txtProductID = request.getParameter("txtProductID");
                        txtProductID = txtProductID.trim();

                        String txtUpdateName = request.getParameter("txtUpdateName");
                        txtUpdateName = txtUpdateName.trim();

                        String txtUpdateDescription = request.getParameter("txtUpdateDescription");
                        txtUpdateDescription = txtUpdateDescription.trim();

                        String txtUpdatePrice = request.getParameter("txtUpdatePrice");
                        txtUpdatePrice = txtUpdatePrice.trim();

                        String txtUpdateCreateDate = request.getParameter("txtUpdateCreateDate");
                        txtUpdateCreateDate = txtUpdateCreateDate.trim();

                        String txtUpdateExpirationDate = request.getParameter("txtUpdateExpirationDate");
                        txtUpdateExpirationDate = txtUpdateExpirationDate.trim();

                        String txtUpdateQuantity = request.getParameter("txtUpdateQuantity");
                        txtUpdateQuantity = txtUpdateQuantity.trim();

                        String txtUpdateCategory = request.getParameter("txtUpdateCategory");
                        txtUpdateCategory = txtUpdateCategory.trim();

                        String txtUpdateStatus = request.getParameter("txtUpdateStatus");
                        txtUpdateStatus = txtUpdateStatus.trim();

                        String image = request.getParameter("txtUpdateImage");
                        image = image.trim();

                        url = resourceBundle.getString(MAIN_PAGE);
                        //check Param
                        boolean flag = false;
                        boolean flagImage = false; 
                        UpdateError error = new UpdateError();
                        if (txtProductID == null || txtProductID.equals("")) {
                            flag = true;
                        }
                        if (image == null || image.equals("")) { 
                            flagImage = true;
                        } 
                        if (txtUpdateName.equals("") || txtUpdateName == null) {
                            flag = true;
                            error.setEmptyName("Name can not be empty");
                        }
                        if (txtUpdateDescription.equals("") || txtUpdateDescription == null) {
                            flag = true;
                            error.setEmptyDescription("Description can not be empty");
                        }
                        float updatePrice = -1;
                        if (txtUpdatePrice.equals("") || txtUpdatePrice == null) {
                            flag = true;
                            error.setEmptyPrice("Price can not be empty");
                        } else {
                            try {
                                updatePrice = Float.parseFloat(txtUpdatePrice);
                                if (updatePrice <= 0) {
                                    flag = true;
                                    error.setNegativePrice("Price can not be negative");
                                }
                            } catch (NumberFormatException e) {
                                flag = true;
                                error.setFormatPriceError("Price must be number ");
                            }
                        }
                        Date createDate = new Date(System.currentTimeMillis());
                        try {
                            createDate = Date.valueOf(txtUpdateCreateDate);
                        } catch (IllegalArgumentException e) {
                            flag = true;
                            error.setFormatCreateDateError("Create date must have format yyyy-mm-dd");
                        }
                        Date expirationDate = new Date(System.currentTimeMillis());
                        try {
                            expirationDate = Date.valueOf(txtUpdateExpirationDate);
                            if (expirationDate.before(createDate)) {
                                flag = true;
                                error.setExpirationDateBeforeCreateDateError("Expiration date must after create date");
                            } else {
                                if (expirationDate.before(new Date(System.currentTimeMillis()))) {
                                    flag = true;
                                    error.setExpirationDateBeforeNowError("Expiration date must after today");
                                }
                            }
                        } catch (IllegalArgumentException e) {
                            flag = true;
                            error.setFormatExpirationDateError("Expiration date must have format yyyy-mm-dd");
                        }
                        int quantity = 0;
                        try {
                            if (txtUpdateQuantity != null) {
                                quantity = Integer.parseInt(txtUpdateQuantity.trim());
                                if (quantity < 0) {
                                    flag = true;
                                    error.setNegativeQuantity("Quantity must not be negative");
                                }
                            }
                        } catch (NumberFormatException e) {
                            flag = true;
                            error.setFormatQuantityError("Quantity must be number only");
                        }

                        if (flag) {
                            error.setProductID(txtProductID.trim());
                            request.setAttribute("updateError", error);
                        } else { 
                            File f ;
                            File newFile ;
                            try {
                                if (!flagImage) {
                                    f = new File(image);
                                    String extension = "";
                                    int i = image.lastIndexOf(".");
                                    if (i > 0) {
                                        extension = image.substring(i + 1);
                                    }
                                    String realPath = this.getClass().getClassLoader().getResource("").getPath();
                                    String[] pathSplit = realPath.split("build");
                                    String path = pathSplit[0] + "web" + "/Img/" + f.getName();
                                    newFile = new File(path);
                                    BufferedImage bi = ImageIO.read(f);
                                    ImageIO.write(bi, extension, newFile);
                                    newFile.createNewFile();
                                    image = newFile.getName();
                                }else {
                                    image = request.getParameter("txtProductImage");
                                    String[] imageSplit = image.split("/");
                                    image = imageSplit[2];
                                }   
                                ProductDAO dao = new ProductDAO();
                                CategoryDAO categoryDao = new CategoryDAO();
                                String categoryID = categoryDao.getID(txtUpdateCategory);
                                if (dao.updateProduct(txtProductID, txtUpdateName, txtUpdateDescription,image, updatePrice, createDate, expirationDate, quantity, categoryID.trim(), txtUpdateStatus)) {
                                    Timestamp date = new Timestamp(System.currentTimeMillis());
                                    String userID = userDto.getUserID();
                                    String id = userID + date + txtProductID;
                                    UpdateRecordDAO updateRecordDao = new UpdateRecordDAO();
                                    if (updateRecordDao.insertUpdateRecord(id, userID, date, txtProductID));
                                }
                            } catch (IOException e) {
                                log.error(e);
                            } 
                        }
                    }
                }
            }
        } catch (NamingException e) {
            log.error(e);
        } catch (SQLException e) {
            log.error(e);
        } finally {
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
