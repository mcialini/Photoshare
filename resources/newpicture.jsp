<%--
	Page for submitting new pictures to the database
--%>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.UserDao" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="org.apache.commons.fileupload.FileUploadException" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<jsp:useBean id="imageUploadBean"
             class="photoshare.ImageUploadBean">
    <jsp:setProperty name="imageUploadBean" property="*"/>
</jsp:useBean>

<html>
<head><title>Create New Picture</title></head>

<body>

<h2>Upload a New Picture</h2>


<%	UserDao user = new UserDao();	
	int user_id = 0;
	String email = "";
	boolean showPage = true;
	String user_name = "";
	if (!request.getUserPrincipal().getName().equals("anon")) {
		email = request.getUserPrincipal().getName();
		user_id = user.getUserID(email);
		user_name = user.getFullName(user_id);
	} else {
		showPage = false;
	}
%>
<% 	if (!showPage) {
%>
You need to register to upload pictures! 
<a href="newuser2.jsp">Register Now!</a><br>
<a href="index.jsp">Return to homepage.</a>
<% } else {
	PictureDao pictureDao = new PictureDao();
	try {
        Picture picture = imageUploadBean.upload(request);
        if (picture != null) {
            pictureDao.save(picture);
%>
<font color="green"><b>Image uploaded successfully.</b></font>
<%
        }
    } catch (FileUploadException e) {
        e.printStackTrace();
    }   
%>



<form action="newpicture.jsp" enctype="multipart/form-data" method="post">
    Filename: <input type="file" name="filename"/><br>
    Caption: <input type="text" name="caption"/><br>
    Album:
	<select name="album">
<%	String album_name = "";
	AlbumDao albumDao = new AlbumDao();	
	List<Integer> album_ids = user.getAlbums(user_id);
	for (Integer album_id: album_ids) {
		album_name = albumDao.getAlbumName(album_id);
%>
		<option value=<%= album_id %>><%= album_name %></option>
<%	}
%>
	</select>
		
    <input type="submit" value="Upload"/><br>
</form>
<a href="photoshare/newalbum.jsp">Create a new album</a>.<br>
<a href="user.jsp?user_id=<%= user_id %>">Go to your profile</a>.<br>
<a href="index.jsp">Home</a><br>


<%	}
%>
</body>
</html>
