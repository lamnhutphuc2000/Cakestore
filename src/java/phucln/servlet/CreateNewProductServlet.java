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
import java.util.List;
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
import phucln.category.CategoryDTO;
import phucln.product.CreateNewProductError;
import phucln.product.ProductDAO;
import phucln.user.UserDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "CreateNewProductServlet", urlPatterns = {"/CreateNewProductServlet"})
public class CreateNewProductServlet extends HttpServlet {

    private Logger log = null;
    private final String MAIN_PAGE = "searchProduct";
    private final String CREATE_NEW_ERROR_PAGE = "createNewProductPage";
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
                UserDTO user = (UserDTO) session.getAttribute("user");
                if (user != null) {
                    String roleIDOfAdmin = (String) session.getAttribute("roleIDOfAdmin");
                    if (user.getRoleID().equals(roleIDOfAdmin)) {
                        url = resourceBundle.getString(CREATE_NEW_ERROR_PAGE);
                        CategoryDAO categoryDao = new CategoryDAO();
                        categoryDao.loadCategories();
                        List<CategoryDTO> listCategory = categoryDao.getCategories();
                        request.setAttribute("listCategory", listCategory);
                        String txtCategoryName = request.getParameter("txtCreateNewCategory");
                        if (txtCategoryName != null) {
                            String productID = request.getParameter("txtCreateNewProductID");
                            productID = productID.trim();
                            String name = request.getParameter("txtCreateNewName");
                            name = name.trim();
                            String image = request.getParameter("txtCreateNewImage");
                            image = image.trim();
                            String description = request.getParameter("txtCreateNewDescription");
                            description = description.trim();
                            String txtPrice = request.getParameter("txtCreateNewPrice");
                            txtPrice = txtPrice.trim();
                            String txtCreateDate = request.getParameter("txtCreateNewCreateDate");
                            txtCreateDate = txtCreateDate.trim();
                            String txtExpirationDate = request.getParameter("txtCreateNewExpirationDate");
                            txtExpirationDate = txtExpirationDate.trim();
                            String txtQuantity = request.getParameter("txtCreateNewQuantity");
                            txtQuantity = txtQuantity.trim();

                            boolean flag = false;
                            CreateNewProductError error = new CreateNewProductError();

                            ProductDAO productDao = new ProductDAO();
                            if (productID != null && !productID.isEmpty()) {
                                if (productDao.checkProductExisted(productID)) {
                                    flag = true;
                                    error.setDuplicateProductID("Product ID is existed");
                                }
                            }
                            if (image == null || image.equals("")) {
                                error.setEmptyImage("Image can not be null");
                                flag = true;
                            }
                            if (name.equals("") || name == null) {
                                flag = true;
                                error.setEmptyName("Name can not be empty");
                            }
                            if (description.equals("") || description == null) {
                                flag = true;
                                error.setEmptyDescription("Description can not be empty");
                            }
                            float createPrice = -1;
                            if (txtPrice.equals("") || txtPrice == null) {
                                flag = true;
                                error.setEmptyPrice("Price can not be empty");
                            } else {
                                try {
                                    createPrice = Float.parseFloat(txtPrice);
                                    if (createPrice <= 0) {
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
                                createDate = Date.valueOf(txtCreateDate);
                            } catch (IllegalArgumentException e) {
                                flag = true;
                                error.setFormatCreateDateError("Create date must have format yyyy-mm-dd");
                            }
                            Date expirationDate = new Date(System.currentTimeMillis());
                            try {
                                expirationDate = Date.valueOf(txtExpirationDate);
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
                                if (txtQuantity != null) {
                                    quantity = Integer.parseInt(txtQuantity.trim());
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
                                request.setAttribute("createNewProductError", error);
                            } else {
                                File f ;
                                File newFile ;
                                try {
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
                                    String categoryID = categoryDao.getID(txtCategoryName);
                                    boolean result = productDao.createNewProduct(productID, name, newFile.getName(), description, createPrice, createDate, expirationDate, quantity, categoryID);
                                    if (result) {
                                        url = resourceBundle.getString("searchProduct");
                                    }
                                } catch (IOException e) {
                                    log.error(e);
                                } catch (SQLException e) {
                                    log.error(e);
                                } 
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
