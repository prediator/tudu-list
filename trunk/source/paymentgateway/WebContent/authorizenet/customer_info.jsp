<%@ page import="java.math.BigDecimal" %>
<%@ page import="net.authorize.*" %>
<%@ page import="net.authorize.sim.*" %>
<%@ page import="net.authorize.aim.*" %>
<%@ page import="net.authorize.data.*" %>
<%@ page import="net.authorize.data.creditcard.*" %>
<%@ page import="com.csc.ipos.web.payment.AuthorizeNetService" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.math.RoundingMode"%><html>
  <head>
    <title>Your Store</title>
    <link rel="stylesheet" href="../css/style.css">
  </head>
  <body>
    <h1>Your Store</h1>
    <h2>Checkout</h2>
<%
AuthorizeNetService service = new AuthorizeNetService();

  Order order = service.createOrder(request.getParameterMap());
%>
    <FORM NAME='checkout_form' ID='checkout_form' ACTION='order_process.jsp' METHOD='POST'>
      <fieldset>
      <div><label>CreditCardNumber</label><input type='text'
        class='text' name='x_card_num' size='15'></input></div>
      <div><label>Exp.</label><input type='text' class='text'
        name='x_exp_date' size='4'></input></div>
      <div><label>Card Code</label><input type='text' class='text'
        name='x_card_code' size='4'></input></div>
      </fieldset>
      <fieldset>
      <div><label>FirstName</label><input type='text' class='text'
        name='x_first_name' size='15'></input></div>
      <div><label>LastName</label><input type='text' class='text'
        name='x_last_name' size='14'></input></div>
      </fieldset>
      <fieldset>
      <div><label>Address</label><input type='text' class='text'
        name='x_address' size='26'></input></div>
      <div><label>City</label><input type='text' class='text'
        name='x_city' size='15'></input></div>
      </fieldset>
      <fieldset>
      <div><label>State</label><input type='text' class='text'
        name='x_state' size='4'></input></div>
      <div><label>ZipCode</label><input type='text' class='text'
        name='x_zip' size='9'></input></div>
      </fieldset>
      <fieldset>
      <div><label>Amount</label><input type='text' class='text'
        name='x_amount' readonly size='9' value='<%=service.sanitizeString(order.getTotalAmount().setScale(2, RoundingMode.HALF_UP).toPlainString())%>'></input></div>
      <div><label>Email</label><input type='text' class='text'
        name='x_email' size='20'></input></div>
      </fieldset>
      <INPUT TYPE="HIDDEN" NAME="size" VALUE="<%=service.sanitizeString(((String[])request.getParameterMap().get("size"))[0])%>"/>
      <INPUT TYPE='SUBMIT' NAME='buy_button' VALUE='BUY' CLASS='submit buy'>
    </FORM>
</body>
</html>
