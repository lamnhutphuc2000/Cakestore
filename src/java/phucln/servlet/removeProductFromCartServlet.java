/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import phucln.cart.Cart;
import phucln.user.UserDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "removeProductFromCartServlet", urlPatterns = {"/removeProductFromCartServlet"})
public class removeProductFromCartServlet extends HttpServlet {

    private final String ERROR_PAGE = "errorPage";
    private final String SHOW_CART_PAGE = "showCartPage";

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

        String url = ERROR_PAGE;
        try {
            UserDTO user = (UserDTO) session.getAttribute("user");
            if (user != null) {
                String roleIDOfAdmin = (String) session.getAttribute("roleIDOfAdmin");
                if (!user.getRoleID().equals(roleIDOfAdmin)) {
                    url = SHOW_CART_PAGE;
                    Cart cart = (Cart) session.getAttribute("CART");
                    if (cart != null) {
                        String productID = request.getParameter("txtProductID");
                        cart.removeItemFromCart(productID);
                        session.setAttribute("CART", cart);
                    }
                }
            } else {
                url = SHOW_CART_PAGE;
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart != null) {
                    String productID = request.getParameter("txtProductID");
                    cart.removeItemFromCart(productID);
                    session.setAttribute("CART", cart);
                }
            }
        } finally {
            response.sendRedirect(url);
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
