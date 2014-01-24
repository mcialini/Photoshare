<%--
  Author: Giorgos Zervas <cs460tf@cs.bu.edu>
--%>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.UserDao" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="imageUploadBean"
             class="photoshare.ImageUploadBean">
    <jsp:setProperty name="imageUploadBean" property="*"/>
</jsp:useBean>

<html>
<head><title>Photo Sharing</title></head>

<body>
<h1>A photo sharing application by Matt Cialini</h1>

<%  UserDao user = new UserDao(); 
	int userID = 0;
	String email = "";
	String user_name = "";
	
	if (request.getUserPrincipal() == null) {
		user_name = "Anonymous";
	} else {
		email = request.getUserPrincipal().getName();
		userID = user.getUserID(email);
		user_name = user.getFullName(userID);
	}	
%>
	 

<h2>Hello <%= user_name %>!</h2>
<a href="/photoshare/logout.jsp">Log out</a><br>
<a href="/photoshare/user.jsp?user_id=<%= userID %>">Your profile page</a><br>
<a href="/photoshare/newpicture.jsp">Upload a picture</a><br>
<a href="/photoshare/newalbum.jsp">Create new album</a><br>
<a href="/photoshare/search.jsp">Search for Users</a><br>
<a href="/photoshare/browse.jsp">Browse all Pictures</a>

<h2>Notable Users</h2>
<table>
	<%	List<Integer> topUsers = user.getTopUsers();
		for (Integer u : topUsers) {
    %>
    <tr><td>
    <a href="/photoshare/user.jsp?user_id=<%= u %>"><%= user.getFullName(u) %></a>
	</td></tr>
    <%
        }
    %>
</table>
</body>
</html>
