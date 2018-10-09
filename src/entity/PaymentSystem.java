package entity;

import java.math.BigDecimal;

public class PaymentSystem {
	
	
	/**
     * Get the creditcard details and  CVV 
	 * @param totalCartCost 
     * @return true if payment is success or else false.
     */
	public boolean payByCreditCard(String creditCardNo , String cvv, BigDecimal totalCartCost) {

		boolean paymentConfirmed = false;
		if(totalCartCost!=BigDecimal.ZERO) {
		if(creditCardNo.length()!=16 || cvv.length()!=3) {
			paymentConfirmed=false;
		}
		}
		return paymentConfirmed;
	}
}