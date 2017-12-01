<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>message</title>
</head>
<body>
<h4>message</h4>
${message }
<br/>
msgs:<c:if test="${msgs!=null || msgs.size()>0}"> <fmt:message key="${msgs}" />  </c:if>
<%-- <%
out.clear();
out = pageContext.pushBody();
%> --%>
</body>
</html>