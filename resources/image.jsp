<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="photoshare.UserDao" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="photoshare.Picture" %>
<%@ page import="photoshare.CommentDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<html>
<head><title>Image</title></head>

<body>
<%	PictureDao pictureDao = new PictureDao(); 
	UserDao user = new UserDao();
	AlbumDao albumDao = new AlbumDao();
	int picture_id = Integer.parseInt(request.getParameter("picture_id"));
	Picture picture = pictureDao.load(picture_id);
	int album_id = picture.getAlbumID();
	String caption = picture.getCaption();
	String album_name = albumDao.getAlbumName(album_id); 
	int user_id = albumDao.getUserID(album_id);
	String user_name = user.getFullName(user_id);
	String curEmail = "";
	int curUser = 0;
	boolean showPage = true;
	boolean showForm = true;

	
	if (request.getUserPrincipal().getName().equals("anon")) {
		curUser = 0;
		showForm = false;
	} else {
		curEmail = request.getUserPrincipal().getName();
		curUser = user.getUserID(curEmail);
	}
%>
<%	boolean showDelete = false;
	if (curUser == user_id) {
		showDelete = true;
	}
	if (request.getParameter("delete") != null) {
		pictureDao.remove(picture_id);
		showPage = false;
	}
%>
<% if (showPage) {
%>
<a href="index.jsp">Home</a><br>
<img src="/photoshare/img?picture_id=<%= picture_id %>"/><br>
<% 	if (showDelete) {
%>
<form action="image.jsp?picture_id=<%= picture_id %>" method="post">
	<input type="submit" value="Delete" name="delete"/>
</form>
<% 	}
%>
<b>Caption:</b>: <i><%= caption %></i><br>

<b>Album</b>: <a href="album.jsp?album_id=<%= album_id %>"><%= album_name %></a><br>

<b>User</b>: <a href="user.jsp?user_id=<%= user_id %>"><%= user_name %></a><br>

<% 	if (request.getParameter("tags") != null) {
		String all = request.getParameter("tags");
		pictureDao.addTags(all.split(" "), picture_id);
	}	
%>

<b>Tags</b>: 
<table>
    <tr>
    <%  List<String> tags = pictureDao.getTags(picture_id);
        for (String tag : tags) {
    %>
		<td>
		#<%= tag %>
		</td>
    <%
       	}
    %>
    </tr>
</table>
<%	boolean showSuggestions = false;
	List<String> suggested = new ArrayList<String>();
	if (request.getParameter("tags2") != null) {
		String all2 = request.getParameter("tags2");
		suggested = pictureDao.getSuggested(all2.split(" "));
		showSuggestions = true;
	}
%>

<form action="image.jsp?picture_id=<%= picture_id %>" method="post">
	<input type="text" name="tags"/>
	<input type="submit" value="Set Tags" name="addTags"/>
</form>
<b>Not sure what to tag this picture? Type some tags you have in mind separated with a space and we'll recommend some for you!</b>
<%	if (showSuggestions) {
%>
<table>
    <tr>
    <%  for (String suggestion : suggested) {
    %>
		<td>
		#<%= suggestion %>
		</td>
    <%
       	}
    %>
    </tr>
</table>
<% }
%>
<form action="image.jsp?picture_id=<%= picture_id %>" method="post">
	<input type="text" name="tags2"/>
	<input type="submit" value="Get Suggestions" name="suggestTags"/>
</form>

<b>Likes</b>: 
<% 	boolean sameUser = false;
	boolean success = false;
	boolean showLink = false;

	if (curUser==0) {
		showLink = true;
		showForm = false;
	} else if (user_id == curUser) {
		sameUser = true;
		showForm = false;
	} else if (user.alreadyLikes(curUser, picture_id)) {
		showForm = false;
		success = true;
	} else if (request.getParameter("like") != null) {
		user.addLike(curUser, picture_id);   
		showForm = false;
		success = true;
	}
	
%>
<% if (showForm) { %>

<form action="image.jsp?picture_id=<%= picture_id %>" method="post">
	<input type="submit" value="Like this Picture" name="like"/>
</form>

<%	}
	if (success) {
%>
You like this photo.
<%	}
%>	

<table>
    <tr>
    <%  List<Integer> likeIds = pictureDao.getLikes(picture_id);
		String likeName = "";
	    for (Integer like : likeIds) {
			likeName = user.getFullName(like);
    %>
		<td>
		<a href="/photoshare/user.jsp?user_id=<%= like %>"><%= likeName %></a>
		</a>
		</td>
    <%
       	}
    %>
    </tr>
</table>
<b>Comments</b>:<br>
<% 	showForm = true; 
	sameUser = false;
	
	if (user_id == curUser) {
		sameUser = true;
		showForm = false;
	}
	
	else if (request.getParameter("commentText") != null) {
		user.addComment(curUser, picture_id, request.getParameter("commentText"));   
	}
	
%>
<% if (showForm) { %>

<form action="image.jsp?picture_id=<%= picture_id %>" method="post">
	<input type="text" name="commentText"/>
	<input type="submit" value="Comment" name="postComment"/>
</form>

<%	} 
%>
<table>
<%  CommentDao commentDao = new CommentDao();
	List<Integer> commentIds = commentDao.getComments(picture_id);
	String commentUserName = "";
	int commentUserID = 0;
	String text = "";
	for (Integer comment : commentIds) {
		commentUserID = commentDao.getUserID(comment);
		commentUserName = user.getFullName(commentUserID);
		text = commentDao.getText(comment);
%>	
    <tr>
		<td><a href="/photoshare/user.jsp?user_id=<%= commentUserID %>"><%= commentUserName %></a>: </td>
		<td><%= text %></td>
	</tr>
<%
    }
%>
</table>

<% } else {
%>
You have successfully removed this picture. 
<a href="index.jsp">Return to home page</a>
<% }
%>
</body>
</html>   	
