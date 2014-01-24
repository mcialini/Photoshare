
<%@ page import="photoshare.UserDao" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>Find Users</title></head>

<body>
<a href="index.jsp">Home</a><br>
<h3>Find users</h3>
<form action="search.jsp">
	Email: <input type="text" name="friend"/>
	<input type="submit" value="Search"/>
</form>
<%	UserDao user = new UserDao();
	String friend_email = "";
	int friend_id = 0;

	if (request.getParameter("friend") != null) {
		friend_email = request.getParameter("friend");
		friend_id = user.getUserID(friend_email);
		if (friend_id == 0) {
%>
<font color=red>There are no users registered with the email <%= friend_email %>.</font><br>
<%		} else {
%>
<a href="user.jsp?user_id=<%= friend_id %>"><%= user.getFullName(friend_id) %></a><br>
<%
		}
	}
%>

<h2>Registered Users</h2>
<table>
     <%	List<Integer> userIds = user.allUserIds();
        for (Integer userID : userIds) {
     %>
       <tr> <td><a href="user.jsp?user_id=<%= userID %>"><%= user.getFullName(userID) %></a>
       </td></tr>
    <%
        }
    %>

</table>
</body>
</html>
