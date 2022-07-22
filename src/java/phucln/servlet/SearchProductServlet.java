/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
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
import phucln.product.ProductDAO;
import phucln.product.ProductDTO;
import phucln.product.SearchError; 
import phucln.user.UserDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "SearchProductServlet", urlPatterns = {"/SearchProductServlet"})
public class SearchProductServlet extends HttpServlet {

    private Logger log = null;
    private final String ERROR_PAGE = "errorPage";
    private final String MAIN_PAGE = "mainPage";

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
        ServletContext context = request.getServletContext();
        ResourceBundle resourceBundle = (ResourceBundle) context.getAttribute("SITE_MAP");
        String url = resourceBundle.getString(ERROR_PAGE);
        HttpSession session = request.getSession(false);

        String currentPage = null;
        String txtSearchName = request.getParameter("txtSearchName");
        if (txtSearchName == null) {
            txtSearchName = "";
        }
        String curPage = request.getParameter("curPage");
        if (curPage == null) {
            curPage = "1";
        }
        String txtCategoryName = request.getParameter("txtCategoryName");
        String txtPriceFrom = request.getParameter("txtPriceFrom");
        String txtPriceTo = request.getParameter("txtPriceTo");
        String status = "Active";
        status = status.trim();
        Date expirationDate = new Date(System.currentTimeMillis());
        int quantity = 0;
        try {

            //check Admin
            if (session != null) {
                UserDTO user = (UserDTO) session.getAttribute("user");
                if (user != null) {
                    String roleIDOfAdmin = (String) session.getAttribute("roleIDOfAdmin");
                    if (user.getRoleID().equals(roleIDOfAdmin)) {
                        status = "";
                        quantity = -1;
                        expirationDate = Date.valueOf("1000-01-01");
                    }
                }
            } 
            //load Categories
            CategoryDAO categoryDao = new CategoryDAO();
            categoryDao.loadCategories();
            List<CategoryDTO> listCategory = categoryDao.getCategories();
            request.setAttribute("listCategory", listCategory);

            String txtCategoryID = "";
            if (txtCategoryName != null) {
                if (!txtCategoryName.equals("All")) {
                    txtCategoryID = categoryDao.getID(txtCategoryName).trim();
                }
            }
            boolean flag = false;
            SearchError error = new SearchError();
            float priceFrom = 0;
            float priceTo = 0;
            try {
                if (txtPriceFrom != null)  {
                    if (!txtPriceFrom.equals("")) {
                        priceFrom = Float.parseFloat(txtPriceFrom);
                    }

                    if (priceFrom < 0) {
                        flag = true;
                        error.setNegativePriceFrom("Price from must not be negative");
                    }
                }

            } catch (NumberFormatException e) {
                flag = true;
                error.setFormatPriceFromError("Price from must be number only");
            }
            try {
                if (txtPriceTo == null) { 
                    priceTo = 0;
                } else {
                    if (!txtPriceTo.equals("")) {
                        priceTo = Float.parseFloat(txtPriceTo);
                    }

                    if (priceTo < 0) {
                        flag = true;
                        error.setNegativePriceTo("Price to must not be negative");
                    }
                }
            } catch (NumberFormatException e) {
                flag = true;
                error.setFormatPriceToError("Price to must be number only");
            }

            if (!flag) {
                //search Products
                ProductDAO dao = new ProductDAO();
                dao.searchProducts(txtSearchName, txtCategoryID, priceFrom, quantity, priceTo, Integer.parseInt(curPage),expirationDate, status.trim());
                List<ProductDTO> lists = dao.getListProduct(); 
                request.setAttribute("listProduct", lists);

                //Paging
                int numberOfPage = dao.countAllProduct(txtSearchName, txtCategoryID, priceFrom, quantity, priceTo, status) / 20;
                if (dao.countAllProduct(txtSearchName, txtCategoryID, priceFrom, quantity, priceTo, status) % 20 != 0) {
                    numberOfPage++;
                }
                if (numberOfPage == 0) {
                    numberOfPage = 1;
                }
                request.setAttribute("numberOfPage", numberOfPage);
            } else {
                request.setAttribute("searchError", error);
            }

            url = resourceBundle.getString(MAIN_PAGE);

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
