<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head><title>Photoshare Login</title></head>

<body>
<h2>New to PhotoShare? Make a new account <a href="/photoshare/newuser2.jsp">here</a>.</h2>
Login below:<br>

<form method="POST" action="j_security_check">
    <table>
        <tr><th>Email</th><td><input type="text" name="j_username"></td></tr>
        <tr><th>Password</th><td><input type="password" name="j_password"></td>
        </tr>
        <tr><td colspan="2" align="right"><input type="submit" value="Login"/>
        </td></tr>
    </table>
</form>
<form method="POST" action="j_security_check">
	<input type="hidden" name="j_username" value="anon"/>
	<input type="hidden" name="j_password" value="anonymous"/>
	<input type="submit" value="Login Anonymously"/>
</form>

</body>
</html>
