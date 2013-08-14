package com.csc.ipos.web.payment.gateway;

import javax.servlet.http.HttpServletRequest;

public interface PaymentEngine {

	public abstract void authorizeAndCapturePayment(HttpServletRequest request) throws Exception;

	public abstract void voidPayment() throws Exception;

}