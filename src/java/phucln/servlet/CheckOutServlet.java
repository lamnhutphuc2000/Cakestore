/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
import phucln.cart.Cart;
import phucln.cart.ProductInCart;
import phucln.order.OrderDAO;
import phucln.orderdetail.OrderDetailDAO;
import phucln.product.ProductDAO;
import phucln.user.UserDTO;

/**
 *
 * @author ASUS
 */
@WebServlet(name = "CheckOutServlet", urlPatterns = {"/CheckOutServlet"})
public class CheckOutServlet extends HttpServlet {

    private Logger log = null;
    private final String ERROR_PAGE = "errorPage";
    private final String FAILED_PAGE = "checkOutPage";
    private final String SUCCESS_PAGE = "checkOutSuccessPage";

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
        HttpSession session = request.getSession();
        ServletContext context = request.getServletContext();
        ResourceBundle resourceBundle = (ResourceBundle) context.getAttribute("SITE_MAP");

        String url = resourceBundle.getString(ERROR_PAGE);

        try {
            Cart cart = (Cart) session.getAttribute("CART");
            if (cart != null) {
                url = resourceBundle.getString(FAILED_PAGE);
                UserDTO user = (UserDTO) session.getAttribute("user");
                if (user != null) {
                    String roleIDOfAdmin = (String) session.getAttribute("roleIDOfAdmin");
                    if (!user.getRoleID().equals(roleIDOfAdmin)) {
                        boolean flag = false;
                        String txtUserID = request.getParameter("txtUserID");
                        String txtShippingAddress = request.getParameter("txtShippingAddress");
                        String txtTotalPrice = request.getParameter("txtTotalPrice");
                        String txtPhoneNumber = request.getParameter("txtPhoneNumber");
                        if (txtShippingAddress.trim().isEmpty() || txtShippingAddress == null) {
                            request.setAttribute("shippingAddressError", "Shipping adddress can not be null");
                            flag = true;
                        }
                        if (txtPhoneNumber.trim().isEmpty() || txtPhoneNumber.length() != 10) {
                            request.setAttribute("phoneNumberError", "Phone number must be 10 number character");
                            flag = true;
                        } else {
                            try {
                                long num = Long.parseLong(txtPhoneNumber);
                            } catch (NumberFormatException e) {
                                flag = true;
                                request.setAttribute("phoneNumberError", "Phone number must be 10 number character");
                            }
                        }
                        if (!flag) {
                            OrderDAO orderDao = new OrderDAO();
                            String orderID = "";
                            do {
                                int ranNum = (int) (Math.random() * ((9999999 - 1000000) + 1));
                                orderID = String.valueOf(ranNum);
                            } while (orderDao.checkExistedID(orderID));
                            List<ProductInCart> lists = cart.getCart();
                            OrderDetailDAO orderDetailDao = new OrderDetailDAO();
                            float totalPrice = Float.parseFloat(txtTotalPrice);
                            boolean checkFlag = false;
                            String name = "";
                            ProductDAO productDao = new ProductDAO();
                            for (int i = 0; i < lists.size(); i++) {
                                String productID = lists.get(i).getProductID();
                                int quantity = lists.get(i).getQuantity();
                                if (productDao.checkProductQuantity(productID, quantity)) {
                                    checkFlag = true;
                                    name = lists.get(i).getName();
                                }
                            }
                            if (!checkFlag) {
                                if (orderDao.checkoutCart(orderID, txtUserID, txtShippingAddress, totalPrice, "PUD", txtPhoneNumber)) {

                                    for (int i = 0; i < lists.size(); i++) {
                                        name = lists.get(i).getName();
                                        String productID = lists.get(i).getProductID();
                                        int quantity = lists.get(i).getQuantity();
                                        float price = quantity * lists.get(i).getPrice();
                                        orderDetailDao.insertOrderDetail(orderID, productID, quantity, price, name);
                                        int productQuantity = productDao.getQuantityByProductID(productID);
                                        productDao.sellProduct(productID, productQuantity - quantity);
                                    }
                                    session.removeAttribute("CART");
                                    url = resourceBundle.getString(SUCCESS_PAGE);
                                    request.setAttribute("yourCode", orderID);
                                }
                            } else {
                                String error = "Product " + name + " is out of stock";
                                request.setAttribute("productOutOfOrder", error);
                            }

                        }
                    }
                } else {
                    boolean flag = false;
                    String txtShippingAddress = request.getParameter("txtShippingAddress");
                    String txtTotalPrice = request.getParameter("txtTotalPrice");
                    String txtPhoneNumber = request.getParameter("txtPhoneNumber");
                    if (txtShippingAddress.trim().isEmpty() || txtShippingAddress == null) {
                        request.setAttribute("shippingAddressError", "Shipping adddress can not be null");
                        flag = true;
                    }
                    if (txtPhoneNumber.trim().isEmpty() || txtPhoneNumber.length() != 10) {
                        request.setAttribute("phoneNumberError", "Phone number must be 10 number character");
                        flag = true;
                    } else {
                        try {
                            long num = Long.parseLong(txtPhoneNumber);
                        } catch (NumberFormatException e) {
                            flag = true;
                            request.setAttribute("phoneNumberError", "Phone number must be 10 number character");
                        }
                    }
                    if (!flag) {
                        OrderDAO orderDao = new OrderDAO();
                        String orderID = "";
                        do {
                            int ranNum = (int) (Math.random() * ((9999999 - 1000000) + 1));
                            orderID = String.valueOf(ranNum);
                        } while (orderDao.checkExistedID(orderID));
                        float totalPrice = Float.parseFloat(txtTotalPrice);
                        List<ProductInCart> lists = cart.getCart();
                        OrderDetailDAO orderDetailDao = new OrderDetailDAO();
                        boolean checkFlag = false;
                        ProductDAO productDao = new ProductDAO();
                        String name = "";
                        for (int i = 0; i < lists.size(); i++) {
                            String productID = lists.get(i).getProductID();
                            int quantity = lists.get(i).getQuantity();
                            if (productDao.checkProductQuantity(productID, quantity)) {
                                checkFlag = true;
                                name = lists.get(i).getName();
                            }
                        }
                        if (!checkFlag) {
                            if (orderDao.checkoutCart(orderID, "", txtShippingAddress, totalPrice, "PUD", txtPhoneNumber)) {

                                for (int i = 0; i < lists.size(); i++) {
                                    name = lists.get(i).getName();
                                    String productID = lists.get(i).getProductID();
                                    int quantity = lists.get(i).getQuantity();
                                    float price = quantity * lists.get(i).getPrice();
                                    orderDetailDao.insertOrderDetail(orderID, productID, quantity, price, name);
                                    int productQuantity = productDao.getQuantityByProductID(productID);
                                    productDao.sellProduct(productID, productQuantity - quantity);
                                }
                                session.removeAttribute("CART");
                                url = resourceBundle.getString(SUCCESS_PAGE);
                                request.setAttribute("yourCode", orderID);
                            }
                        } else {
                            String error = "Product " + name + " is out of stock";
                            request.setAttribute("productOutOfOrder", error);
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
