<%--
  Author: Giorgos Zervas <cs460tf@cs.bu.edu>
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="photoshare.NewUserDao" %>
<jsp:useBean id="newUserBean"
             class="photoshare.NewUserBean" />
<jsp:setProperty name="newUserBean" property="*"/>

<html>
<head><title>New User</title></head>

<body>
<!-- We want to show the form unless we successfully create a new user -->
<% boolean showForm = true;
   String err = null; %>

<% if (!newUserBean.getEmail().equals("")) {
     if (!newUserBean.getPassword1().equals(newUserBean.getPassword2())) {
       err = "Both password strings must match";
     }
     else if (newUserBean.getPassword1().length() < 4) {
       err = "Your password must be at least four characters long";
     }

     else if (newUserBean.getGender() != 'M' && newUserBean.getGender() != 'F') {
       err = "You must enter your gender (either M or F)";
     } 
     else {
	NewUserDao newUserDao = new NewUserDao();
        boolean success = newUserDao.create(newUserBean.getEmail(),
             newUserBean.getPassword1(), request.getParameter("first_name"), request.getParameter("last_name"), 
			 request.getParameter("birthdate"), request.getParameter("gender"), request.getParameter("home_city"), 
			 request.getParameter("home_state"), request.getParameter("home_country"), request.getParameter("cur_city"), 
			 request.getParameter("cur_state"), request.getParameter("cur_country"), request.getParameter("university")); 
        if (success) {
          showForm = false;
        } else {
          err = "Couldn't create user (that email may already be in use)";
        }
     }
   }
%>  
<% if (err != null) { %>
<font color=red><b>Error: <%= err %></b></font>
<% } %>

<% if (showForm) { %>

<h2>New user info</h2>

<form action="newuser.jsp" method="post">
  Email*: <input type="text" name="email"/><br>
  Password*: <input type="password" name="password1"/><br>
  Re-enter password*: <input type="password" name="password2"/><br>
  First name*: <input type="text" name="first_name"/><br>
  Last name*:  <input type="text" name="last_name"/><br>
  Birth date (Type in form MMDDYY)*: <input type="text" name="birthdate"/><br>
  Gender (Type either M or F)*: <input type="text" name="gender"/><br>
  Home City: <input type="text" name="home_city"/><br>
  Home State (Two letters): <input type="text" name="home_state"/><br>
  Home Country: <input type="text" name="home_country"/><br>
  Current City: <input type="text" name="cur_city"/><br>
  Current State (Two letters): <input type="text" name="cur_state"/><br>
  Current Country: <input type="text" name="cur_country"/><br>
  University: <input type="text" name="university"/><br>
  <input type="submit" value="Create"/><br/>
</form>

<% }
   else { %>

<h2>Success!</h2>

<p>A new user has been created with email <%= newUserBean.getEmail() %>.
You can now return to the <a href="login.jsp">login page</a>.

<% } %>

</body>
</html>                                                                                                       
