/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.cart;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class UpdateProductInCartError implements Serializable {

    private String formatQuantityError;
    private String negativeQuantityError;

    public UpdateProductInCartError() {
    }

    public UpdateProductInCartError(String formatQuantityError, String negativeQuantityError) {
        this.formatQuantityError = formatQuantityError;
        this.negativeQuantityError = negativeQuantityError;
    }

    /**
     * @return the formatQuantityError
     */
    public String getFormatQuantityError() {
        return formatQuantityError;
    }

    /**
     * @param formatQuantityError the formatQuantityError to set
     */
    public void setFormatQuantityError(String formatQuantityError) {
        this.formatQuantityError = formatQuantityError;
    }

    /**
     * @return the negativeQuantityError
     */
    public String getNegativeQuantityError() {
        return negativeQuantityError;
    }

    /**
     * @param negativeQuantityError the negativeQuantityError to set
     */
    public void setNegativeQuantityError(String negativeQuantityError) {
        this.negativeQuantityError = negativeQuantityError;
    }

}
