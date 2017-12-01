<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" import="java.util.*"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>showpage</title>
</head>
<body>
<h4>uploadfile01</h4>
<form action="${pageContext.request.contextPath}/servlet/UploanFileServlet01" enctype="multipart/form-data" method="post">
	用户名:<input type="text" name="username"><br/>
	上传文件1:<input type="file" name="file1"><br/>
	上传文件2:<input type="file" name="file2"><br/>
	<input type="submit" value="点击上传">
</form>

<h4>uploadfile02</h4>
<form action="${pageContext.request.contextPath}/servlet/UploanFileServlet02" enctype="multipart/form-data" method="post">
	用户名:<input type="text" name="username"><br/>
	上传文件1:<input type="file" name="file1"><br/>
	上传文件2:<input type="file" name="file2"><br/>
	<input type="submit" value="点击上传">
</form>

<c:forEach var="item" items="${fileNameMap }">
	<c:url var="downurl" value="/servlet/DownLoadServlet">
		<c:param name="filename" value="${item.key }"></c:param>
	</c:url>
	${item.value }<a href="${downurl }">下载</a><br/>
</c:forEach>
</body>
</html>