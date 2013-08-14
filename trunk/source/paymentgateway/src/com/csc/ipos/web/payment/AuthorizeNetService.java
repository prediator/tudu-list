package com.csc.ipos.web.payment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;

import net.authorize.Environment;
import net.authorize.Merchant;
import net.authorize.TransactionType;
import net.authorize.aim.Result;
import net.authorize.aim.Transaction;
import net.authorize.data.Customer;
import net.authorize.data.Order;
import net.authorize.data.OrderItem;
import net.authorize.data.ShippingCharges;
import net.authorize.data.creditcard.CreditCard;

public class AuthorizeNetService extends HttpServlet {

	/* EDIT THE FOLLOWING FOR YOUR SPECIFIC ENVIRONMENT */

	// API login ID
	static String apiLoginID = "6u9MKp3U";
	// API transaction key
	static String transactionKey = "6865jTSp5cm2Xz9J";
	// SIM/DPM relay response URL
	static String relayResponseUrl = "http://localhost:8080/paymentgateway/relay_response.jsp";
	// SIM/DPM order receipt URL
	static String orderReceiptUrl = "http://localhost:8080/paymentgateway/dpm/order_receipt.jsp";
	// merchant defined MD5 Hash key
	static String merchantMD5Key = "201308140948";

	/* NO NEED TO EDIT BELOW HERE */

	// create the merchant
	public static Merchant sandboxMerchant = Merchant.createMerchant(Environment.SANDBOX, apiLoginID, transactionKey);

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.service(req, res);
	}

	public String[] processTransactionAIM1() throws IOException {
		URL post_url = new URL("https://test.authorize.net/gateway/transact.dll");

		Hashtable<String, String> post_values = new Hashtable<String, String>();

		// the API Login ID and Transaction Key must be replaced with valid
		// values
		post_values.put("x_login", "6u9MKp3U");
		post_values.put("x_tran_key", "6865jTSp5cm2Xz9J");

		post_values.put("x_version", "3.1");
		post_values.put("x_delim_data", "TRUE");
		post_values.put("x_delim_char", "|");
		post_values.put("x_relay_response", "FALSE");

		post_values.put("x_type", "AUTH_CAPTURE");
		post_values.put("x_method", "CC");
		post_values.put("x_card_num", "4111111111111111");
		post_values.put("x_exp_date", "0115");

		post_values.put("x_amount", "19.99");
		post_values.put("x_description", "Sample Transaction");

		post_values.put("x_first_name", "John");
		post_values.put("x_last_name", "Doe");
		post_values.put("x_address", "1234 Street");
		post_values.put("x_state", "WA");
		post_values.put("x_zip", "98004");
		// Additional fields can be added here as outlined in the AIM
		// integration
		// guide at: http://developer.authorize.net

		// This section takes the input fields and converts them to the proper
		// format
		// for an http post. For example: "x_login=username&x_tran_key=a1B2c3D4"
		StringBuffer post_string = new StringBuffer();
		Enumeration keys = post_values.keys();
		while (keys.hasMoreElements()) {
			String key = URLEncoder.encode(keys.nextElement().toString(), "UTF-8");
			String value = URLEncoder.encode(post_values.get(key).toString(), "UTF-8");
			post_string.append(key + "=" + value + "&");
		}

		// The following section provides an example of how to add line item
		// details to
		// the post string. Because line items may consist of multiple values
		// with the
		// same key/name, they cannot be simply added into the above array.
		//
		// This section is commented out by default.
		/*
		 * String[] line_items = { "item1<|>golf balls<|><|>2<|>18.95<|>Y",
		 * "item2<|>golf bag<|>Wilson golf carry bag, red<|>1<|>39.99<|>Y",
		 * "item3<|>book<|>Golf for Dummies<|>1<|>21.99<|>Y"};
		 * 
		 * for (int i = 0; i < line_items.length; i++) { String value =
		 * line_items[i]; post_string.append("&x_line_item=" +
		 * URLEncoder.encode(value)); }
		 */

		// Open a URLConnection to the specified post url
		URLConnection connection = post_url.openConnection();
		connection.setDoOutput(true);
		connection.setUseCaches(false);

		// this line is not necessarily required but fixes a bug with some
		// servers
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

		// submit the post_string and close the connection
		DataOutputStream requestObject = new DataOutputStream(connection.getOutputStream());
		requestObject.write(post_string.toString().getBytes());
		requestObject.flush();
		requestObject.close();

		// process and read the gateway response
		BufferedReader rawResponse = new BufferedReader(new InputStreamReader(connection.getInputStream()));
		String line;
		String responseData = rawResponse.readLine();
		rawResponse.close(); // no more data

		// split the response into an array
		String[] responses = responseData.split("\\|");

		return responses;

	}

	public Result<Transaction> processTransactionAIM2(ServletRequest req) {
		Map<String, String[]> requestParameterMap = req.getParameterMap();

		String transactionId = requestParameterMap.containsKey("x_trans_id") ? requestParameterMap.get("x_trans_id")[0]
				: "";

		if (requestParameterMap != null) {
			// create the order
			Order order = createOrder(requestParameterMap);

			// create transaction
			Transaction transaction = sandboxMerchant.createAIMTransaction(
					requestParameterMap.containsKey("refund") ? TransactionType.VOID : TransactionType.AUTH_CAPTURE,
					order.getTotalAmount());
			if (requestParameterMap.containsKey("refund")) {
				transaction.setTransactionId(transactionId);
			} else {
				// create customer
				Customer customer = Customer.createCustomer();
				customer.setFirstName(requestParameterMap.get("x_first_name")[0]);
				customer.setLastName(requestParameterMap.get("x_last_name")[0]);
				customer.setAddress(requestParameterMap.get("x_address")[0]);
				customer.setCity(requestParameterMap.get("x_city")[0]);
				customer.setState(requestParameterMap.get("x_state")[0]);
				customer.setZipPostalCode(requestParameterMap.get("x_zip")[0]);
				// create credit card
				CreditCard creditCard = CreditCard.createCreditCard();
				creditCard.setCreditCardNumber(requestParameterMap.get("x_card_num")[0]);
				String expiration = requestParameterMap.get("x_exp_date")[0].replaceAll("/", "");
				if (expiration.length() >= 4) {
					creditCard.setExpirationMonth(expiration.substring(0, 2));
					creditCard.setExpirationYear(expiration.substring(2));
				}

				transaction.setCustomer(customer);
				transaction.setOrder(order);
				transaction.setCreditCard(creditCard);
			}

			Result<Transaction> result = (Result<Transaction>) sandboxMerchant.postTransaction(transaction);
			return result;
		}
		else
			return null;
	}

	/**
	 * Creates an order from form data passed in.
	 */
	public Order createOrder(Map<String, String[]> requestMap) {
		Order order = Order.createOrder();
		order.setDescription("Coffee");

		try {
			order.setInvoiceNumber(Long.toString(System.currentTimeMillis()));
			OrderItem coffeeOrderItem = OrderItem.createOrderItem();
			coffeeOrderItem.setItemQuantity(new BigDecimal(1.00));

			if (requestMap.containsKey("size")) {

				int coffeeSize = Integer.parseInt(requestMap.get("size")[0]);
				switch (coffeeSize) {
				case 1:
					coffeeOrderItem.setItemName("Small Coffee");
					coffeeOrderItem.setItemTaxable(true);
					coffeeOrderItem.setItemId("s");
					coffeeOrderItem.setItemPrice(new BigDecimal("1.99"));
					break;
				case 2:
					coffeeOrderItem.setItemName("Medium Coffee");
					coffeeOrderItem.setItemTaxable(true);
					coffeeOrderItem.setItemId("m");
					coffeeOrderItem.setItemPrice(new BigDecimal("2.99"));
					break;
				case 3:
					coffeeOrderItem.setItemName("Large Coffee");
					coffeeOrderItem.setItemTaxable(true);
					coffeeOrderItem.setItemId("l");
					coffeeOrderItem.setItemPrice(new BigDecimal("3.99"));
					break;
				default:
					break;
				}
				ShippingCharges shippingCharges = ShippingCharges.createShippingCharges();
				shippingCharges.setTaxExempt(false);
				shippingCharges.setTaxItemName("California Tax (9.5%)");
				shippingCharges.setTaxAmount(coffeeOrderItem.getItemPrice().multiply(new BigDecimal(0.095)));

				order.addOrderItem(coffeeOrderItem);
				order.setShippingCharges(shippingCharges);
				// add CA sales tax (9.5%)
				order.setTotalAmount(coffeeOrderItem.getItemPrice().add(shippingCharges.getTaxAmount()));
			}
		} catch (Exception e) {
			order = null;
		}

		return order;
	}

	/**
	 * sanitize strings for output
	 */
	public String sanitizeString(String string) {

		java.lang.StringBuilder retval = new java.lang.StringBuilder();
		java.text.StringCharacterIterator iterator = new java.text.StringCharacterIterator(string);
		char character = iterator.current();

		while (character != java.text.CharacterIterator.DONE) {
			if (character == '<') {
				retval.append("&lt;");
			} else if (character == '>') {
				retval.append("&gt;");
			} else if (character == '&') {
				retval.append("&amp;");
			} else if (character == '\"') {
				retval.append("&quot;");
			} else if (character == '\t') {
				addCharEntity(9, retval);
			} else if (character == '!') {
				addCharEntity(33, retval);
			} else if (character == '#') {
				addCharEntity(35, retval);
			} else if (character == '$') {
				addCharEntity(36, retval);
			} else if (character == '%') {
				addCharEntity(37, retval);
			} else if (character == '\'') {
				addCharEntity(39, retval);
			} else if (character == '(') {
				addCharEntity(40, retval);
			} else if (character == ')') {
				addCharEntity(41, retval);
			} else if (character == '*') {
				addCharEntity(42, retval);
			} else if (character == '+') {
				addCharEntity(43, retval);
			} else if (character == ',') {
				addCharEntity(44, retval);
			} else if (character == '-') {
				addCharEntity(45, retval);
			} else if (character == '.') {
				addCharEntity(46, retval);
			} else if (character == '/') {
				addCharEntity(47, retval);
			} else if (character == ':') {
				addCharEntity(58, retval);
			} else if (character == ';') {
				addCharEntity(59, retval);
			} else if (character == '=') {
				addCharEntity(61, retval);
			} else if (character == '?') {
				addCharEntity(63, retval);
			} else if (character == '@') {
				addCharEntity(64, retval);
			} else if (character == '[') {
				addCharEntity(91, retval);
			} else if (character == '\\') {
				addCharEntity(92, retval);
			} else if (character == ']') {
				addCharEntity(93, retval);
			} else if (character == '^') {
				addCharEntity(94, retval);
			} else if (character == '_') {
				addCharEntity(95, retval);
			} else if (character == '`') {
				addCharEntity(96, retval);
			} else if (character == '{') {
				addCharEntity(123, retval);
			} else if (character == '|') {
				addCharEntity(124, retval);
			} else if (character == '}') {
				addCharEntity(125, retval);
			} else if (character == '~') {
				addCharEntity(126, retval);
			} else {
				retval.append(character);
			}
			character = iterator.next();
		}
		return retval.toString();
	}

	/**
	 * Convert integer to char entity
	 */
	public void addCharEntity(int i, StringBuilder sb) {

		String padding = "";
		if (i <= 9) {
			padding = "00";
		} else if (i <= 99) {
			padding = "0";
		}
		String number = padding + i;
		sb.append("&#" + number + ";");
	}
}
