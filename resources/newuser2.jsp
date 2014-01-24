<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head><title>Another New User Example</title></head>


<body>

<h2>New user info</h2>

<form action="adduser.jsp" method="post">
  Email*: <input type="text" name="email"/><br>
  Password*: <input type="password" name="password1"/><br>
  Re-enter password*: <input type="password" name="password2"/><br>
  First name*: <input type="text" name="first_name"/><br>
  Last name*:  <input type="text" name="last_name"/><br>
  Birth date (Type in form MMDDYY)*: <input type="text" name="birthdate"/><br>
  Gender (Type either Male or Female): <input type="text" name="gender"/><br>
  Home City: <input type="text" name="home_city"/><br>
  Home State (Two letters): <input type="text" name="home_state"/><br>
  Home Country: <input type="text" name="home_country"/><br>
  Current City: <input type="text" name="cur_city"/><br>
  Current State (Two letters): <input type="text" name="cur_state"/><br>
  Current Country: <input type="text" name="cur_country"/><br>
  University: <input type="text" name="university"/><br>
  <input type="submit" value="Create"/><br/>
</form>

</body>
</html>
