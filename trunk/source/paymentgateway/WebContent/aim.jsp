<!--
This sample code is designed to connect to Authorize.net using the AIM method.
For API documentation or additional sample code, please visit:
http://developer.authorize.net
-->

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" 
  "http://www.w3.org/TR/html4/loose.dtd">
<HTML lang='en'>
<HEAD>
	<TITLE> Sample AIM Implementation </TITLE>
</HEAD>
<BODY>
<P> This sample code is designed to generate a post using Authorize.net's
Advanced Integration Method (AIM) and display the results of this post to
the screen. </P>
<P> For details on how this is accomplished, please review the readme file,
the comments in the sample code, and the Authorize.net AIM API documentation
found at http://developer.authorize.net </P>
<HR />

<%@ page import="java.util.*" %>
<%@ page import="java.net.*" %>
<%@ page import="java.io.*" %>
<%@ page import="javax.net.ssl.*" %>
<%@ page import="java.net.URLEncoder" %>
<%

// By default, this sample code is designed to post to our test server for
// developer accounts: https://test.authorize.net/gateway/transact.dll
// for real accounts (even in test mode), please make sure that you are
// posting to: https://secure.authorize.net/gateway/transact.dll
URL post_url = new URL("https://test.authorize.net/gateway/transact.dll");

Hashtable post_values = new Hashtable();
  
// the API Login ID and Transaction Key must be replaced with valid values
post_values.put ("x_login", "6u9MKp3U");
post_values.put ("x_tran_key", "6865jTSp5cm2Xz9J");
  
post_values.put ("x_version", "3.1");
post_values.put ("x_delim_data", "TRUE");
post_values.put ("x_delim_char", "|");
post_values.put ("x_relay_response", "FALSE");

post_values.put ("x_type", "AUTH_CAPTURE");
post_values.put ("x_method", "CC");
post_values.put ("x_card_num", "4111111111111111");
post_values.put ("x_exp_date", "0115");

post_values.put ("x_amount", "19.99");
post_values.put ("x_description", "Sample Transaction");

post_values.put ("x_first_name", "John");
post_values.put ("x_last_name", "Doe");
post_values.put ("x_address", "1234 Street");
post_values.put ("x_state", "WA");
post_values.put ("x_zip", "98004");
// Additional fields can be added here as outlined in the AIM integration
// guide at: http://developer.authorize.net

// This section takes the input fields and converts them to the proper format
// for an http post.  For example: "x_login=username&x_tran_key=a1B2c3D4"
StringBuffer post_string = new StringBuffer();
Enumeration keys = post_values.keys();
while( keys.hasMoreElements() ) {
  String key = URLEncoder.encode(keys.nextElement().toString(),"UTF-8");
  String value = URLEncoder.encode(post_values.get(key).toString(),"UTF-8");
  post_string.append(key + "=" + value + "&");
}

// The following section provides an example of how to add line item details to
// the post string.  Because line items may consist of multiple values with the
// same key/name, they cannot be simply added into the above array.
//
// This section is commented out by default.
/*
String[] line_items = {
	"item1<|>golf balls<|><|>2<|>18.95<|>Y",
	"item2<|>golf bag<|>Wilson golf carry bag, red<|>1<|>39.99<|>Y",
	"item3<|>book<|>Golf for Dummies<|>1<|>21.99<|>Y"};
	
for (int i = 0; i < line_items.length; i++) {
  String value = line_items[i];
  post_string.append("&x_line_item=" + URLEncoder.encode(value));
}
*/

// Open a URLConnection to the specified post url
URLConnection connection = post_url.openConnection();
connection.setDoOutput(true);
connection.setUseCaches(false);

// this line is not necessarily required but fixes a bug with some servers
connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");

// submit the post_string and close the connection
DataOutputStream requestObject = new DataOutputStream( connection.getOutputStream() );
requestObject.write(post_string.toString().getBytes());
requestObject.flush();
requestObject.close();

// process and read the gateway response
BufferedReader rawResponse = new BufferedReader(new InputStreamReader(connection.getInputStream()));
String line;
String responseData = rawResponse.readLine();
rawResponse.close();	                     // no more data

// split the response into an array
String [] responses = responseData.split("\\|");

// The results are output to the screen in the form of an html numbered list.
out.println("<OL>");
for(Iterator iter=Arrays.asList(responses).iterator(); iter.hasNext();) {
	out.println("<LI>" + iter.next() + "&nbsp;</LI>");
}
out.println("</OL>");
// individual elements of the array could be accessed to read certain response
// fields.  For example, response_array[0] would return the Response Code,
// response_array[2] would return the Response Reason Code.
// for a list of response fields, please review the AIM Implementation Guide
%>
</BODY>
</HTML>