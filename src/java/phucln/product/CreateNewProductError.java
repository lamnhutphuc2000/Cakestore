/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phucln.product;

import java.io.Serializable;

/**
 *
 * @author ASUS
 */
public class CreateNewProductError implements Serializable {

    private String duplicateProductID;
    private String emptyName;
    private String emptyDescription;
    private String emptyImage;
    private String negativePrice;
    private String emptyPrice;
    private String formatPriceError;
    private String formatCreateDateError;
    private String formatExpirationDateError;
    private String expirationDateBeforeCreateDateError;
    private String expirationDateBeforeNowError;
    private String negativeQuantity;
    private String formatQuantityError;

    public CreateNewProductError() {
    }

    public CreateNewProductError(String duplicateProductID, String emptyName, String emptyDescription, String emptyImage, String negativePrice, String emptyPrice, String formatPriceError, String formatCreateDateError, String formatExpirationDateError, String expirationDateBeforeCreateDateError, String expirationDateBeforeNowError, String negativeQuantity, String formatQuantityError) {
        this.duplicateProductID = duplicateProductID;
        this.emptyName = emptyName;
        this.emptyDescription = emptyDescription;
        this.emptyImage = emptyImage;
        this.negativePrice = negativePrice;
        this.emptyPrice = emptyPrice;
        this.formatPriceError = formatPriceError;
        this.formatCreateDateError = formatCreateDateError;
        this.formatExpirationDateError = formatExpirationDateError;
        this.expirationDateBeforeCreateDateError = expirationDateBeforeCreateDateError;
        this.expirationDateBeforeNowError = expirationDateBeforeNowError;
        this.negativeQuantity = negativeQuantity;
        this.formatQuantityError = formatQuantityError;
    }

    /**
     * @return the emptyName
     */
    public String getEmptyName() {
        return emptyName;
    }

    /**
     * @param emptyName the emptyName to set
     */
    public void setEmptyName(String emptyName) {
        this.emptyName = emptyName;
    }

    /**
     * @return the emptyDescription
     */
    public String getEmptyDescription() {
        return emptyDescription;
    }

    /**
     * @param emptyDescription the emptyDescription to set
     */
    public void setEmptyDescription(String emptyDescription) {
        this.emptyDescription = emptyDescription;
    }

    /**
     * @return the emptyImage
     */
    public String getEmptyImage() {
        return emptyImage;
    }

    /**
     * @param emptyImage the emptyImage to set
     */
    public void setEmptyImage(String emptyImage) {
        this.emptyImage = emptyImage;
    }

    /**
     * @return the negativePrice
     */
    public String getNegativePrice() {
        return negativePrice;
    }

    /**
     * @param negativePrice the negativePrice to set
     */
    public void setNegativePrice(String negativePrice) {
        this.negativePrice = negativePrice;
    }

    /**
     * @return the emptyPrice
     */
    public String getEmptyPrice() {
        return emptyPrice;
    }

    /**
     * @param emptyPrice the emptyPrice to set
     */
    public void setEmptyPrice(String emptyPrice) {
        this.emptyPrice = emptyPrice;
    }

    /**
     * @return the formatPriceError
     */
    public String getFormatPriceError() {
        return formatPriceError;
    }

    /**
     * @param formatPriceError the formatPriceError to set
     */
    public void setFormatPriceError(String formatPriceError) {
        this.formatPriceError = formatPriceError;
    }

    /**
     * @return the formatCreateDateError
     */
    public String getFormatCreateDateError() {
        return formatCreateDateError;
    }

    /**
     * @param formatCreateDateError the formatCreateDateError to set
     */
    public void setFormatCreateDateError(String formatCreateDateError) {
        this.formatCreateDateError = formatCreateDateError;
    }

    /**
     * @return the formatExpirationDateError
     */
    public String getFormatExpirationDateError() {
        return formatExpirationDateError;
    }

    /**
     * @param formatExpirationDateError the formatExpirationDateError to set
     */
    public void setFormatExpirationDateError(String formatExpirationDateError) {
        this.formatExpirationDateError = formatExpirationDateError;
    }

    /**
     * @return the expirationDateBeforeCreateDateError
     */
    public String getExpirationDateBeforeCreateDateError() {
        return expirationDateBeforeCreateDateError;
    }

    /**
     * @param expirationDateBeforeCreateDateError the
     * expirationDateBeforeCreateDateError to set
     */
    public void setExpirationDateBeforeCreateDateError(String expirationDateBeforeCreateDateError) {
        this.expirationDateBeforeCreateDateError = expirationDateBeforeCreateDateError;
    }

    /**
     * @return the expirationDateBeforeNowError
     */
    public String getExpirationDateBeforeNowError() {
        return expirationDateBeforeNowError;
    }

    /**
     * @param expirationDateBeforeNowError the expirationDateBeforeNowError to
     * set
     */
    public void setExpirationDateBeforeNowError(String expirationDateBeforeNowError) {
        this.expirationDateBeforeNowError = expirationDateBeforeNowError;
    }

    /**
     * @return the negativeQuantity
     */
    public String getNegativeQuantity() {
        return negativeQuantity;
    }

    /**
     * @param negativeQuantity the negativeQuantity to set
     */
    public void setNegativeQuantity(String negativeQuantity) {
        this.negativeQuantity = negativeQuantity;
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
     * @return the duplicateProductID
     */
    public String getDuplicateProductID() {
        return duplicateProductID;
    }

    /**
     * @param duplicateProductID the duplicateProductID to set
     */
    public void setDuplicateProductID(String duplicateProductID) {
        this.duplicateProductID = duplicateProductID;
    }

}
