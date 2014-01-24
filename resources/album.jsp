<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="photoshare.UserDao" %>
<%@ page import="java.util.List" %>

<html>
<head><title>Album</title></head>

<body>
<a href="index.jsp">Home</a><br>
<%	int album_id = 0;
	if (request.getParameter("album_id") != null) {
		album_id = Integer.valueOf(request.getParameter("album_id"));
	}
	UserDao user = new UserDao();
	AlbumDao albumDao = new AlbumDao();
	int user_id = albumDao.getUserID(album_id);
	boolean showPage = true;
	String curEmail = request.getUserPrincipal().getName();
	int curUser = user.getUserID(curEmail);		
%>
<%	boolean showDelete = false;
	if (curUser == user_id) {
		showDelete = true;
	}
	if (request.getParameter("delete") != null) {
		albumDao.remove(album_id);
		showPage = false;
	}
%>
<% if (showPage) {
%>
<h1>
<%  String album_name = albumDao.getAlbumName(album_id); 
	String user_name = user.getFullName(user_id);
%>
<%= album_name %> by <a href="/photoshare/user.jsp?user_id=<%= user_id %>"><%= user_name %></a>
</h1>
<% 	if (showDelete) {
%>
<form action="album.jsp?album_id=<%= album_id %>" method="post">
	<input type="submit" value="Delete" name="delete"/>
</form>
<% 	}
%>

<h2>Existing Pictures:</h2>

<table>
    <tr>
    <%  List<Integer> pictureIds = albumDao.albumPictureIds(album_id);
        for (Integer pictureId : pictureIds) {
    %>
   	 <td>
   	 <a href="/photoshare/image.jsp?picture_id=<%= pictureId %>">
    		<img src="/photoshare/img?t=1&picture_id=<%= pictureId %>"/>
   	 </a>
   	 </td>
    <%
       	}
    %>
    </tr>
</table>
<br>
<a href="/photoshare/newpicture.jsp">Upload a picture</a><br>
<% } else {
%>
You have successfully removed this album. 
<a href="index.jsp">Return to home page</a>
<% }
%>
</body>
</html>   	
