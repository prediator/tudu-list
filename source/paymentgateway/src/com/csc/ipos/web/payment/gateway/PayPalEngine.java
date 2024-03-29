/*
 * Copyright 2007-2010 JadaSite.

 * This file is part of JadaSite.
 
 * JadaSite is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.

 * JadaSite is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.

 * You should have received a copy of the GNU General Public License
 * along with JadaSite.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.csc.ipos.web.payment.gateway;

import javax.servlet.http.HttpServletRequest;


public class PayPalEngine implements PaymentEngine {
	static String PAYPAL_ENVIRONMENT_PRODUCTION = "live";
	static public String PAYMENT_TYPE = "PayPal";
	
	String secureURLPrefix;
	String apiUserName;
	String apiPassword;
	String signature;
	String environment;
	String siteContext;
	float extraVerificationPercent;
	float extraVerificationAmount;
	
	String token = null;
	String payerId = null;
	
	
	
	public void authorizeAndCapturePayment( HttpServletRequest request) throws Exception {
		
	}

	@Override
	public void voidPayment() throws Exception {
		// TODO Auto-generated method stub
		
	}

}
