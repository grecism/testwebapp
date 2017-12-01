<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" import="java.util.*"%>
<%@ include file="/WEB-INF/views/common/title.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<jsp:include page="../common/header_js.jsp"></jsp:include>
	<script type="text/javascript" src="${ctxp}static/js/upload/upload.js"></script>
	<title>showpage</title>
<style type="text/css">
.none{
	display: none;
}
</style>
</head>
<body>
<h4>uploadSingleFile_${showPage}</h4>
<h4>uploadSingleFile</h4>
<form action="${pageContext.request.contextPath}/UploadFile/UploadFileController/uploadSingleFile" enctype="multipart/form-data" method="post">
	用户名:<input type="text" name="username"><br/>
	上传文件1:<input type="file" name="uploadFile"><br/>
	<input type="submit" value="点击上传">
</form>
<h4>uploadSingleFile2</h4>
<form action="${pageContext.request.contextPath}/UploadFile/UploadFileController/uploadSingleFile2" enctype="multipart/form-data" method="post">
	用户名:<input type="text" name="username"><br/>
	上传文件1:<input type="file" name="uploadFile"><br/>
	<input type="submit" value="点击上传">
</form>
<h4>uploadSingleFile3</h4>
<form action="${pageContext.request.contextPath}/UploadFile/UploadFileController/uploadSingleFile3" enctype="multipart/form-data" method="post">
	用户名:<input type="text" name="username"><br/>
	上传文件1:<input type="file" name="uploadFile"><br/>
	<input type="submit" value="点击上传">
</form>
<h4>uploadMultifile</h4>
<form action="${pageContext.request.contextPath}/UploadFile/UploadFileController/uploadMultifile" enctype="multipart/form-data" method="post">
	用户名:<input type="text" name="username"><br/>
	上传文件1:<input type="file" name="uploadFile"><br/>
	上传文件2:<input type="file" name="uploadFile"><br/>
	上传文件3:<input type="file" name="uploadFile"><br/>
	<input type="submit" value="点击上传">
</form>
<h4>uploadFile</h4>
<form action="${pageContext.request.contextPath}/UploadFile/UploadFileController/uploadFile" enctype="multipart/form-data" method="post">
	用户名:<input type="text" name="username"><br/>
	上传文件1:<input type="file" name="uploadFile"><br/>
	<input type="submit" value="点击上传">
</form>
<h4>uploadFile</h4>
<form action="${pageContext.request.contextPath}/UploadFile/UploadFileController/uploadFile" enctype="multipart/form-data" method="post">
	用户名:<input type="text" name="username"><br/>
	上传文件1:<input type="file" name="uploadFile1"><br/>
	上传文件2:<input type="file" name="uploadFile2"><br/>
	上传文件3:<input type="file" name="uploadFile3"><br/>
	<input type="submit" value="点击上传">
</form>
<h4>uploadFileToZip</h4>
<form action="${pageContext.request.contextPath}/UploadFile/UploadFileController/uploadFileToZip" enctype="multipart/form-data" method="post">
	用户名:<input type="text" name="username"><br/>
	上传文件1:<input type="file" name="uploadFile"><br/>
	<input type="submit" value="点击上传">
</form>
<h4>uploadFileToZip</h4>
<form action="${pageContext.request.contextPath}/UploadFile/UploadFileController/uploadFileToZip" enctype="multipart/form-data" method="post">
	用户名:<input type="text" name="username"><br/>
	上传文件1:<input type="file" name="uploadFile1"><br/>
	上传文件2:<input type="file" name="uploadFile2"><br/>
	上传文件3:<input type="file" name="uploadFile3"><br/>
	<input type="submit" value="点击上传">
</form>
<h4>ajaxUploadFile</h4>
<%-- <form action="${pageContext.request.contextPath}/UploadFile/UploadFileController/uploadSingleFile" enctype="multipart/form-data" method="post"> --%>
	用户名:<input type="text" name="username"><br/>
	<input type="button" onclick="javascript:$('#uploadFile').click();" value="upload"/>
	<input type="file" name="uploadFile" id="uploadFile" class="none"/>
<!-- </form> -->
<h4>ajaxUploadFile</h4>
	用户名:<input type="text" name="username"><br/>
	文件1:<br/>
	<input type="button" onclick="uploadfile(this,'uploadFile1');" value="upload1"/>
	<input type="file" name="uploadFile" id="uploadFile1" class="none"/><br/>
	文件2:<br/>
	<input type="button" onclick="uploadfile(this,'uploadFile2');" value="upload2"/>
	<input type="file" name="uploadFile" id="uploadFile2" class="none"/><br/>
	文件3:<br/>
	<input type="button" onclick="uploadfile(this,'uploadFile3');" value="upload3"/>
	<input type="file" name="uploadFile" id="uploadFile3" class="none"/><br/>
</body>
</html>