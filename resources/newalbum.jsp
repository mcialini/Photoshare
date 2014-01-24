<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.NewAlbumDao" %>
<%@ page import="photoshare.UserDao" %>

<jsp:useBean id="newAlbumBean"
             class="photoshare.NewAlbumBean" />
<jsp:setProperty name="newAlbumBean" property="*"/>

<html>
<head><title>New Album</title></head>

<h2>Create an Album</h2>
<body>
<!-- We want to show the form unless we successfully create a new album -->
<%	UserDao user = new UserDao();
	int user_id = 0;
	String user_email = "";
	boolean showPage = true;
	if (!request.getUserPrincipal().getName().equals("anon")) {
		user_email = request.getUserPrincipal().getName();
		user_id = user.getUserID(user_email);
	} else {
		showPage = false;
	}
%>
<% 	if (!showPage) {
%>
You need to register to create an album! 
<a href="newuser2.jsp">Register Now!</a><br>
<a href="index.jsp">Return to homepage.</a>
<%	} else {
		boolean success = false;
		if (request.getParameter("name") != null) {		    
			NewAlbumDao newAlbumDao = new NewAlbumDao();
			newAlbumDao.create(request.getParameter("name"), user_id);
			success = true;
		}
%>
<% 	if (success) {
%>
<font color="green"><b>Album created successfully.</b></font><br>
<%	} 
%>
<form action="newalbum.jsp" method="post">
  Album name*: <input type="text" name="name"/><br>
  <input type="submit" value="Create"/><br/>
</form>
<br>
<a href="newpicture.jsp">Upload a photo</a>.<br>
<a href="user.jsp?user_id=<%= user_id %>">Go to your profile</a>.<br>
<a href="index.jsp">Return to home page</a>.

<% } %>

</body>
</html>



