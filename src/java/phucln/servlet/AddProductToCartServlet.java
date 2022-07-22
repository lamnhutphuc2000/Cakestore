/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import phucln.cart.Cart;
import phucln.user.UserDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "AddProductToCartServlet", urlPatterns = {"/AddProductToCartServlet"})
public class AddProductToCartServlet extends HttpServlet {

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
        HttpSession session = request.getSession(true);
        ServletContext context = request.getServletContext();
        ResourceBundle resourceBundle = (ResourceBundle) context.getAttribute("SITE_MAP");

        String url = resourceBundle.getString(ERROR_PAGE);
        try {
            UserDTO user = (UserDTO) session.getAttribute("user");
            if (user != null) {
                String roleIDOfAdmin = (String) session.getAttribute("roleIDOfAdmin");
                if (!user.getRoleID().equals(roleIDOfAdmin)) {
                    Cart cart = (Cart) session.getAttribute("CART");
                    if (cart == null) {
                        cart = new Cart();
                    }
                    String productID = request.getParameter("txtProductID");
                    String productName = request.getParameter("txtProductName");
                    String productPrice = request.getParameter("txtProductPrice");
                    float price = Float.parseFloat(productPrice);
                    cart.addItemToCart(productID, productName, price);
                    session.setAttribute("CART", cart);
                    url = resourceBundle.getString(MAIN_PAGE);
                }
            } else {
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart == null) {
                    cart = new Cart();
                }
                String productID = request.getParameter("txtProductID");
                String productName = request.getParameter("txtProductName");
                String productPrice = request.getParameter("txtProductPrice");
                float price = Float.parseFloat(productPrice);
                cart.addItemToCart(productID, productName, price);
                session.setAttribute("CART", cart);
                url = resourceBundle.getString(MAIN_PAGE);
            }
        } catch (NumberFormatException e) {
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
