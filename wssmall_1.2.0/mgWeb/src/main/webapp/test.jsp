<%@ page language="java" import="java.util.*,com.ztesoft.inf.*" pageEncoding="UTF-8"%>
<%
String acc_nbr = request.getParameter("acc_nbr");
String lan_code = request.getParameter("lan_code");
if(acc_nbr!=null && !acc_nbr.equals("")){
	String code =InfTest.doService(acc_nbr,lan_code);
	out.println("ok====>"+code);
}
%>
<form action="/test.jsp" method="post">
	号码：<input name="acc_nbr" type="text" value="18970993899" ><br>
	区号：<input name="lan_code" type="text" value="0791"><br>
	<input type="submit" name="提交"/>
</form>
