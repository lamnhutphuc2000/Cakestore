/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.tblorder;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class CheckoutError implements Serializable{
    private String discountCodeNoExistError;
    private String discountCodeAlreadyUsedError;
    private String bookIsOutOfStockError;

    public CheckoutError() {
    }

    public CheckoutError(String discountCodeNoExistError, String discountCodeAlreadyUsedError, String bookIsOutOfStockError) {
        this.discountCodeNoExistError = discountCodeNoExistError;
        this.discountCodeAlreadyUsedError = discountCodeAlreadyUsedError;
        this.bookIsOutOfStockError = bookIsOutOfStockError;
    }

    /**
     * @return the discountCodeNoExistError
     */
    public String getDiscountCodeNoExistError() {
        return discountCodeNoExistError;
    }

    /**
     * @param discountCodeNoExistError the discountCodeNoExistError to set
     */
    public void setDiscountCodeNoExistError(String discountCodeNoExistError) {
        this.discountCodeNoExistError = discountCodeNoExistError;
    }

    /**
     * @return the discountCodeAlreadyUsedError
     */
    public String getDiscountCodeAlreadyUsedError() {
        return discountCodeAlreadyUsedError;
    }

    /**
     * @param discountCodeAlreadyUsedError the discountCodeAlreadyUsedError to set
     */
    public void setDiscountCodeAlreadyUsedError(String discountCodeAlreadyUsedError) {
        this.discountCodeAlreadyUsedError = discountCodeAlreadyUsedError;
    }

    /**
     * @return the bookIsOutOfStockError
     */
    public String getBookIsOutOfStockError() {
        return bookIsOutOfStockError;
    }

    /**
     * @param bookIsOutOfStockError the bookIsOutOfStockError to set
     */
    public void setBookIsOutOfStockError(String bookIsOutOfStockError) {
        this.bookIsOutOfStockError = bookIsOutOfStockError;
    }
    
}
