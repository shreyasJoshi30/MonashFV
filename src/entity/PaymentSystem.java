package entity;

import java.math.BigDecimal;

public class PaymentSystem {


	/**
	 *  Get the creditcard details and  CVV
	 * @param creditCardNo Credit card number.
	 * @param cvv Security code.
	 * @param charge The amount to be charged to the credit card.
	 * @return true if payment is success or else false.
	 */
	public static boolean payByCreditCard(String creditCardNo , String cvv, BigDecimal charge) {
		if(!charge.equals(BigDecimal.ZERO)) {
			if(creditCardNo.length()!=16 || cvv.length()!=3) {
				return false;
			}
		}
		return true;
	}
}