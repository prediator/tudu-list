<%@page import="java.io.UnsupportedEncodingException"%>
<%@page import="java.net.URLEncoder"%>
<%@page import="org.apache.http.client.utils.URLEncodedUtils"%>

<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.LinkedHashMap" %>
<%@ page import="net.authorize.Environment" %>
<%@ page import="net.authorize.Merchant" %>
<%@ page import="net.authorize.TransactionType" %>
<%@ page import="net.authorize.aim.Result" %>
<%@ page import="net.authorize.aim.Transaction" %>
<%@ page import="net.authorize.data.*" %>
<%@ page import="net.authorize.data.creditcard.*" %>
<%@ page import="com.csc.ipos.web.payment.AuthorizeNetService" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <title>Your Store</title>
    <link rel="stylesheet" href="../css/style.css">
  </head>
  <body>
 <h1>Your Store</h1>

<%
	AuthorizeNetService service = new AuthorizeNetService();

    Result<Transaction> result = service.processTransactionAIM2(request);

    String transactionId = result.getTarget().getTransactionId();

    // 1 means we have a successful transaction
    if(result.isApproved()) {
      // auth_capture transaction
      if("auth_capture".equalsIgnoreCase(result.getTarget().getTransactionType().getValue())) {
%>
    <h2>Thank You</h2>
    <h3>Your transaction ID:</h3>
    <div class="id"><%=service.sanitizeString(transactionId)%></div>
    <h3>Coffee not hot enough?</h3>
    Request a
    <FORM NAME='refund_form' ID='refund_form_id' ACTION='order_process.jsp' METHOD='POST'>
      <INPUT TYPE="HIDDEN" NAME='refund' VALUE='TRUE'>
      <INPUT TYPE='HIDDEN' NAME='x_trans_id' VALUE='<%=service.sanitizeString(transactionId)%>'>
      <INPUT TYPE='SUBMIT' NAME='refund_button' VALUE='REFUND' CLASS='submit refund'>
    </FORM>
<%
      // refund transaction
      } else if ("void".equalsIgnoreCase(result.getTarget().getTransactionType().getValue())) {
%>
    <h2>Refund Processed</h2>
    <h3>Your transaction ID:</h3>
    <div class="id"><%=service.sanitizeString(transactionId)%></div>
<%
      }
    }
    // there's a problem processing the transaction request
    else if(result.isDeclined() || result.isError()) {
      String txnType = "order";
      if("refund".equalsIgnoreCase(result.getTarget().getTransactionType().getValue())) {
       txnType = "refund";
      }
%>
    <h2>Error!</h2>
    <div class='error'>
    <h3>We're sorry, but we can't process your <%=service.sanitizeString(txnType)%> at this time due to the following error:</h3>
    <%=service.sanitizeString(result.getResponseText()) %>
      <table>
        <tr>
          <td>response code</td>
          <td><%=result.getResponseCode().getCode()%></td>
        </tr>
            <tr>
          <td>response reason code</td>
          <td><%=result.getReasonResponseCode().getResponseReasonCode()%></td>
        </tr>
    </table>
    </div>
<%
    }
%>
    <a STYLE="text-decoration:none" href="/paymentgateway/authorizenet/"><input type="submit" class="submit" value="Start Over"></a>
  </body>
</html>
