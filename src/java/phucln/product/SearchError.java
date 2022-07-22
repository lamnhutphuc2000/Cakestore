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
public class SearchError implements Serializable {

    private String negativePriceTo;
    private String negativePriceFrom;
    private String formatPriceToError;
    private String formatPriceFromError;

    public SearchError() {
    }

    /**
     * @return the negativePriceTo
     */
    public String getNegativePriceTo() {
        return negativePriceTo;
    }

    /**
     * @param negativePriceTo the negativePriceTo to set
     */
    public void setNegativePriceTo(String negativePriceTo) {
        this.negativePriceTo = negativePriceTo;
    }

    /**
     * @return the negativePriceFrom
     */
    public String getNegativePriceFrom() {
        return negativePriceFrom;
    }

    /**
     * @param negativePriceFrom the negativePriceFrom to set
     */
    public void setNegativePriceFrom(String negativePriceFrom) {
        this.negativePriceFrom = negativePriceFrom;
    }

    /**
     * @return the formatPriceToError
     */
    public String getFormatPriceToError() {
        return formatPriceToError;
    }

    /**
     * @param formatPriceToError the formatPriceToError to set
     */
    public void setFormatPriceToError(String formatPriceToError) {
        this.formatPriceToError = formatPriceToError;
    }

    /**
     * @return the formatPriceFromError
     */
    public String getFormatPriceFromError() {
        return formatPriceFromError;
    }

    /**
     * @param formatPriceFromError the formatPriceFromError to set
     */
    public void setFormatPriceFromError(String formatPriceFromError) {
        this.formatPriceFromError = formatPriceFromError;
    }

}
