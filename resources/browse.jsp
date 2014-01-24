
<%@ page import="photoshare.UserDao" %>
<%@ page import="photoshare.AlbumDao" %>
<%@ page import="photoshare.PictureDao" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
<title>Browse Pictures</title>

<style>

.cell {
    float: left;
    width: 124px;
    border: 2px solid #000000
}

</style>

</head>

<body>
<a href="index.jsp">Home</a><br>
<h1>Browse all Pictures</h1>
<h3>Most popular tags</h3>
<% 	PictureDao pictureDao = new PictureDao();
	List<String> popularTags = new ArrayList<String>();
	popularTags = pictureDao.getPopularTags();	
%>
<table>
    <tr>
        <%	for (String tag : popularTags) {
        %>
        <td><a href="browse.jsp?tags=<%= tag %>">#<%= tag %></a>
        </td>
        <%
            }
        %>
    </tr>
</table>


<h3>You may also like:</h3>


<b>Browse by Tag (separate tags with space)</b>:
<form action="browse.jsp">
	<input type="text" name="tags"/>
	<input type="submit"/>
</form>
<a href="browse.jsp">Browse all</a><br>

<h2>All Pictures</h2>
<%	String tags = request.getParameter("tags");
	String[] allTags;
	List<Integer> pictureIds = new ArrayList<Integer>();
	boolean search = false;	
	if (request.getParameter("tags") != null && !request.getParameter("tags").equals("")) {
		search = true;
		allTags = tags.split(" ");
		pictureIds = pictureDao.tagSearch(allTags);
	} else {
		pictureIds = pictureDao.allPicturesIds();
	}
%>
<%	for (Integer pictureId : pictureIds) {
%>
<div id="cell">
<a href="/photoshare/image.jsp?picture_id=<%= pictureId %>">
<img src="/photoshare/img?t=1&picture_id=<%= pictureId %>"/>
</a>
</div>
<%
    }
%>

</body>
</html>
