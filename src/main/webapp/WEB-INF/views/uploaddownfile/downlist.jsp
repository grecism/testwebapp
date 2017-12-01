<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" import="java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>downlist</title>
</head>
<body>
<h4>downFile</h4>
<c:forEach var="item" items="${fileNameMap }">
	<c:url var="downurl" value="/downFile/DownFileController/downFile">
		<c:param name="filename" value="${item.key }"></c:param>
	</c:url>
	${item.value }<a href="${downurl }">下载</a><br/>
</c:forEach>

<h4>downFile2</h4>
<c:forEach var="item" items="${fileNameMap }">
	<c:url var="downurl" value="/downFile/DownFileController/downFile2">
		<c:param name="filename" value="${item.key }"></c:param>
	</c:url>
	${item.value }<a href="${downurl }">下载</a><br/>
</c:forEach>
</body>
</html>