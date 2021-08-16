package utils;

import java.util.HashMap;
import java.util.Map;

import com.javatechie.trx.exceptions.InsufficientAmountException;

public class PaymentUtils {

	private static Map<String, Double> paymentMap = new HashMap<>();
	
	{
		paymentMap.put("acct1", 100.00);
		paymentMap.put("acct2", 200.00);
		paymentMap.put("acct3", 300.00);
		paymentMap.put("acct4", 200.00);
		paymentMap.put("acct5", 300.00);
	}
	
	public static boolean validateCreditLimit(String accountNo, double paidAmount) {
		if(paidAmount>paymentMap.get(accountNo)) {
			throw new InsufficientAmountException("In sufficient amount exception");
		} else {
			return true;
		}
	}
	
}
