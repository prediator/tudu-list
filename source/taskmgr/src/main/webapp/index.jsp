<%-- <% response.sendRedirect("chinguyen/users"); %> --%>
<html>


	<frameset rows="105,*" framespacing="0" border="0" frameborder="0" >
		<frame name="banner" scrolling="no" noresize src="header.html" />
		<frameset cols="20%,*" >
			<frame name="contents" id="contents" src="navigation.html" /> <!-- target="main" -->
			<frame name="main" id="main" src="${pageContext.request.contextPath}/chinguyen/customers" scrolling="auto" />
		</frameset>
	</frameset>
</html>
