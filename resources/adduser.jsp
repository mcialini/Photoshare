<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.NewUserDao" %>

<html>
<head><title>Adding New User</title></head>

<body>

<% 
   String err = null;
   String email  = request.getParameter("email");
   String password1 = request.getParameter("password1");
   String password2 = request.getParameter("password2");
   String first_name = request.getParameter("first_name");
   String last_name =  request.getParameter("last_name");
   int birthdate = Integer.parseInt(request.getParameter("birthdate"));
   String gender =  request.getParameter("gender");
   String home_city = request.getParameter("home_city");
   String home_state = request.getParameter("home_state");
   String home_country = request.getParameter("home_country");
   String cur_city = request.getParameter("cur_city");
   String cur_state = request.getParameter("cur_state");
   String cur_country = request.getParameter("cur_country");
   String university = request.getParameter("university");

if (!email.equals("")) {
     if (!password1.equals(password2)) {
       err = "Both password strings must match";

     }
     else if (password1.length() < 4) {
       err = "Your password must be at least four characters long";
     }
     else {
       // We have valid inputs, try to create the user
       NewUserDao newUserDao = new NewUserDao();
       boolean success = newUserDao.create(email, password1, first_name, last_name, birthdate, gender, home_city, home_state, home_country, cur_city, cur_state, cur_country, university);
       if (!success) {
         err = "Couldn't create user (that email may already be in use)";
       }
     }
   } else {
	 err = "You have to provide an email";

   }
%>

<% if (err != null) { %>
<font color=red><b>Error: <%= err %></b></font>
<p> <a href="newuser2.jsp">Go Back</a>
<% }
   else { %>

<h2>Success!</h2>

<p>A new user has been created with email <%= email %>.
You can now return to the <a href="login.jsp">login page</a>.

<% } %>

</body>
</html>
