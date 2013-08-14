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

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

import com.csc.ipos.web.payment.service.AuthorizeNet;


public class AuthorizeNetEngine implements PaymentEngine  {
	static String AUTHORIZENET_ENVIRONMENT_PRODUCTION = "live";
	static String SERVICE_URL_PRODUCTION = "https://secure.authorize.net/gateway/transact.dll";
	static String SERVICE_URL_SANDBOX = "https://test.authorize.net/gateway/transact.dll";
	
	static String RESULT_APPROVED = "1";
	static String RESULT_DECLINED = "2";
	static String RESULT_ERROR = "3";
	static String RESULT_HELDFORREVIEW = "4";
	
	
	static String PAYMENT_TYPE = "CC";
	static String CARDACTION_SALE = "AUTH_CAPTURE";
	static String CARDACTION_PREAUTH = "AUTH_ONLY";
	static String CARDACTION_POSTAUTH = "PRIOR_AUTH_CAPTURE";
	static String CARDACTION_CREDIT = "CREDIT";
	static String CARDACTION_FORCED_POSTAUTH = "";
	static String CARDACTION_UNLINKED_CREDIT = "CREDIT";
	static String CARDACTION_VOID = "VOID";
	
	String paymentMessage = "";
	
	String loginId;
	String tranKey;
	String environment;
	
	static Logger logger = Logger.getLogger(AuthorizeNetEngine.class);
	
	public AuthorizeNetEngine() throws Exception {
		AuthorizeNet authorizeNet = new AuthorizeNet();
		loginId = authorizeNet.getLoginId();
		tranKey = authorizeNet.getTranKey();
		environment = authorizeNet.getEnvironment();
	}
	
	public boolean isProvideCustomer() {
		return false;
	}
	
	private boolean isProduction() {
		return AUTHORIZENET_ENVIRONMENT_PRODUCTION.equals(environment);
	}
	
	public boolean isAllowAuthorizeOnly() {
		return true;
	}
	
	
	/* (non-Javadoc)
	 * @see com.csc.ipos.web.payment.gateway.PaymentEngine#authorizeAndCapturePayment(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public void authorizeAndCapturePayment(HttpServletRequest request) throws Exception {
		
		StringBuffer sb = new StringBuffer();
		sb.append("x_login=" + loginId + "&");
		sb.append("x_tran_key=" + tranKey + "&");
		sb.append("x_version=3.1&");
		sb.append("x_method=CC&");
		sb.append("x_type=" + CARDACTION_SALE + "&");
		sb.append("x_delim_data=TRUE&");
		sb.append("x_delim_char=|&");
		sb.append("x_relay_response=FALSE&");
		
	    sb.append("x_card_num=4111111111111111" + "&");
	    sb.append("x_card_code="  + "&");
	    sb.append("x_exp_date=" +  "&");
	    sb.append("x_amount=" + "&");
	    sb.append("x_currency_code=" + "&");

	    String line = sendTransmission(sb);
		
		Vector<?> response = split("|", line);
		String responseCode = (String) response.elementAt(0);
		if (!responseCode.equals(RESULT_APPROVED)) {
			logger.error("request = " + sb.toString());
			logger.error("response = " + line);
			paymentMessage = (String) response.elementAt(3);
			throw new Exception(responseCode);
		}
//		authCode = (String) response.elementAt(4);
//		paymentReference1 = (String) response.elementAt(6);
//		paymentReference2 = (String) response.elementAt(1);
//		paymentReference3 = "";
//		paymentReference4 = "";
//		paymentType = PAYMENT_TYPE;
		
		return;
	}
	
	
	/* (non-Javadoc)
	 * @see com.csc.ipos.web.payment.gateway.PaymentEngine#voidPayment()
	 */
	@Override
	public void voidPayment() throws  Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("x_login=" + loginId + "&");
		sb.append("x_tran_key=" + tranKey + "&");
		sb.append("x_version=3.1&");
		sb.append("x_method=CC&");
		sb.append("x_type=" + CARDACTION_VOID + "&");
		sb.append("x_delim_data=TRUE&");
		sb.append("x_delim_char=|&");
		sb.append("x_relay_response=FALSE&");
		
//		PaymentTran payment = invoiceHeader.getPaymentTran();
//	    sb.append("x_trans_id=" + payment.getPaymentReference1() + "&");

	    String line = sendTransmission(sb);
		
		Vector<?> response = split("|", line);
		String responseCode = (String) response.elementAt(0);
		if (!responseCode.equals(RESULT_APPROVED)) {
			logger.error("request = " + sb.toString());
			logger.error("response = " + line);
			paymentMessage = (String) response.elementAt(3);
			throw new Exception(paymentMessage);
		}
//		authCode = (String) response.elementAt(4);
//		paymentReference1 = (String) response.elementAt(6);
//		paymentReference2 = (String) response.elementAt(1);
//		paymentReference3 = "";
//		paymentReference4 = "";
//		
//		paymentType = PAYMENT_TYPE;
		
		return;
	}
	
	
	public String sendTransmission(StringBuffer sb) throws Exception {
	    URL url = null;
	    if (isProduction()) {
	    	url = new URL(SERVICE_URL_PRODUCTION);
	    }
	    else {
	    	url = new URL(SERVICE_URL_SANDBOX);
	    }
	    logger.error("url > " + url);
	    logger.error("send > " + sb.toString());
	    
	    String line = "";
	    try {
			URLConnection connection = url.openConnection();
			connection.setConnectTimeout(30000);
			connection.setReadTimeout(30000);
			connection.setDoOutput(true);
			connection.setUseCaches(false);
			connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
			DataOutputStream out = new DataOutputStream( connection.getOutputStream() );
			out.write(sb.toString().getBytes());
			out.flush();
			out.close();
			BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			line = in.readLine();
			in.close();
	    }
	    catch (Exception e) {
			logger.error(e);
			logger.error("request = " + sb.toString());
			logger.error("response = " + line);
			paymentMessage = e.getMessage();
			throw new Exception(e.getMessage());
	    }
	    logger.error("receive > " + line);
	    return line;
	}
	

	public static Vector<?> split(String pattern, String in){
		int s1=0, s2=-1;
		Vector<String> out = new Vector<String>(30);
		while(true){
			s2 = in.indexOf(pattern, s1);
			if(s2 != -1){
				out.addElement(in.substring(s1, s2));
			}else{
				String _ = in.substring(s1);
				if(_ != null && !_.equals("")){
					out.addElement(_);
				}
				break;
			}
			s1 = s2;
			s1 += pattern.length();
		}
		return out;
	}

	public boolean isExtendedTransaction() {
		return false;
	}
}
