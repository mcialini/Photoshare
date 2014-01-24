<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="photoshare.UserDao" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>

<html>
<head><title>User</title></head>

<body>
<%	UserDao user = new UserDao();
	int user_id = Integer.parseInt(request.getParameter("user_id"));
	String user_name = "";
	String user_email = "";
	String curEmail = "";
	int curUser = 0;
	boolean showPage = true;
	boolean showFriendForm = true; 
	boolean showUnfriendForm = false;
	
	
	curEmail = request.getUserPrincipal().getName();
	curUser = user.getUserID(curEmail);

	if (user_id == 0) {		
		if (curUser == user_id) {
			showPage = false;
		}
	} else {
		user_name = user.getFullName(user_id);
		user_email = user.getEmail(user_id);
	}
	
	
%>
<% if (!showPage) {
%>
You need to register to have a profile page! 
<a href="newuser2.jsp">Register Now!</a><br>
<a href="index.jsp">Return to homepage.</a>
<% } else {
%>
<a href="index.jsp">Home</a><br>
<h1>
<%= user_name %>
</h1>
Email: <%= user_email %><br>

<% 	boolean success = false;
	String err = null;
	boolean sameUser = false;
	
	if (request.getParameter("friend") != null) {
                success = user.addFriendship(user_id, curUser);

        }
        if (request.getParameter("unfriend") != null) {
                user.removeFriendship(user_id, curUser);
        }
	
	if (curUser == 0) {
		showFriendForm = false;
	} else if (user_id == curUser) {
                sameUser = true;
                showFriendForm = false;
        } else if (user.alreadyFriends(user_id, curUser)) {
                showUnfriendForm = true;
                showFriendForm = false;
        } else {
                showFriendForm = true;
                showUnfriendForm = false;
        }


%>
<% if (err != null) { %>
<font color=red><b><%= err %></b></font>
<% } %>
<% if (showFriendForm) { %>

<form action="user.jsp?user_id=<%= user_id %>" method="post"> 	
	<input type="submit" value="Add Friend" name="friend"/>
</form>
<br>

<%	} 
	if (success) {
%>
You are now friends with <%= user_name %><br>
<%	}
%>
<%	if (showUnfriendForm) {
%>
<form action="user.jsp?user_id=<%= user_id %>" method="post"> 	
	<input type="submit" value="Unfriend" name="unfriend"/>
</form>
<%	}
%>

<h2><%= user_name %>'s Albums:</h2>
<table>
    <%  List<Integer> albumIds = user.getAlbums(user_id);
		AlbumDao albumDao = new AlbumDao();
		String albumName = "";
	    for (Integer album: albumIds) {
		albumName = albumDao.getAlbumName(album);
	%>
		<tr><td>
		<a href="/photoshare/album.jsp?album_id=<%= album %>"><%= albumName %></a>
		</td></tr>
    <%
       	}
    %>
    </tr>
</table>
	
<h2><%= user_name %>'s Pictures:</h2>
<a href="user.jsp?user_id=<%= user_id %>">Browse all</a><br>
<b>Browse by Tag (separate tags with space)</b>:
<form action="user.jsp?user_id=<%= user_id %>&" method="post">
	<input type="text" name="tags"/>
	<input type="submit"/>
</form>
<% 	PictureDao pictureDao = new PictureDao();
	String tags = request.getParameter("tags");
	String[] allTags;
	List<Integer> pictureIds = new ArrayList<Integer>();
	boolean search = false;	
	if (request.getParameter("tags") != null && !request.getParameter("tags").equals("")) {
		search = true;
		allTags = tags.split(" ");
		pictureIds = pictureDao.userTagSearch(allTags, user_id);
	} else {
		pictureIds = user.getPics(user_id);
	}
%>
<table>
    <tr>
    <%  for (Integer pictureId : pictureIds) {
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

<h2><%= user_name %>'s Likes:</h2>
<table>
    <tr>
    <%  List<Integer> likeIds = user.getLikes(user_id);
	    for (Integer like : likeIds) {
    %>
		<td>
		<a href="/photoshare/image.jsp?picture_id=<%= like %>">
    	<img src="/photoshare/img?t=1&picture_id=<%= like %>"/>
		</a>
		</td>
    <%
       	}
    %>
    </tr>
</table>

<h2><%= user_name %>'s Friends: </h2>
<table>
    <%  List<Integer> friendIds = user.getFriends(user_id);
	String friend_name = "";
        for (Integer friend : friendIds) {
		friend_name = user.getFullName(friend);
    %>
	<tr><td>
	<a href="/photoshare/user.jsp?user_id=<%= friend %>"><%= friend_name %></a>
	</td></tr>
    <%
       	}
    %>
</table>

<% }
%>

</body>
</html>  
