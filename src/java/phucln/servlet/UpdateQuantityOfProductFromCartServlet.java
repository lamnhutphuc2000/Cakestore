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
import phucln.cart.Cart;
import phucln.cart.UpdateProductInCartError;
import phucln.user.UserDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "UpdateQuantityOfProductFromCartServlet", urlPatterns = {"/UpdateQuantityOfProductFromCartServlet"})
public class UpdateQuantityOfProductFromCartServlet extends HttpServlet {

    private final String VIEW_CART_PAGE = "showCartPage";
    private final String ERROR_PAGE = "errorPage";

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
                    url = resourceBundle.getString(VIEW_CART_PAGE);

                    Cart cart = (Cart) session.getAttribute("CART");
                    if (cart != null) {
                        String productID = request.getParameter("txtProductID");
                        String productQuantity = request.getParameter("txtQuantity");
                        boolean flag = false;
                        int quantity = 0;
                        UpdateProductInCartError error = new UpdateProductInCartError();
                        try {
                            quantity = Integer.parseInt(productQuantity.trim());
                            if (quantity <= 0) {
                                flag = true;
                                error.setNegativeQuantityError("Quantity must > 0");
                            }
                        } catch (NumberFormatException e) {
                            flag = true;
                            error.setFormatQuantityError("Quantity must be number only");
                        }
                        if (!flag) {
                            cart.updateProductInCart(productID, quantity);
                            session.setAttribute("CART", cart);
                        } else {
                            request.setAttribute("updateQuantityInCartError", error);
                        }
                    }
                }
            } else {
                url = resourceBundle.getString(VIEW_CART_PAGE);
                Cart cart = (Cart) session.getAttribute("CART");
                if (cart != null) {
                    String productID = request.getParameter("txtProductID");
                    String productQuantity = request.getParameter("txtQuantity");
                    boolean flag = false;
                    int quantity = 0;
                    UpdateProductInCartError error = new UpdateProductInCartError();
                    try {
                        quantity = Integer.parseInt(productQuantity.trim());
                        if (quantity <= 0) {
                            flag = true;
                            error.setNegativeQuantityError("Quantity must > 0");
                        }
                    } catch (NumberFormatException e) {
                        flag = true;
                        error.setFormatQuantityError("Quantity must be number only");
                    }
                    if (!flag) {
                        cart.updateProductInCart(productID, quantity);
                        session.setAttribute("CART", cart);
                    } else {
                        request.setAttribute("updateQuantityInCartError", error);
                    }
                }
            }
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
