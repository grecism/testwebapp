
$(function(){
	/*ajaxfileupload不支持响应头ContentType为application/json的设置，并且IE也不支持这种格式，
	而当我们用SpringMVC的@ResponseBody注解的时候会自动将响应类型设置为application/json，
	所以解决办法只有手动设置响应类型*/
	$("#uploadFile").on("change", function(){
		$.ajaxFileUpload({  
	        url : getUri()+'UploadFile/UploadFileController/ajaxUploadFile',//用于文件上传的服务器端请求地址  
	        async: false,
	        fileElementId : 'uploadFile', //文件上传空间的id属性   <input type="file" id="file1" name="file" />
	        type : 'post',  
	        dataType : 'text',  //兼容IE将datatype改为 text
	        //dataType: 'application/json', //返回值类型 一般设置为json
	        success : function(data){//服务器成功响应处理函数
	        	data = $.parseJSON(data); 
	        	if(data.code == "001"){
	        		alert("upload success");
	        	}else{
	        		alert("upload error");
	        	}
	        },  
			error: function (data, status, e){//服务器响应失败处理函数
		        alert(e);
		    }
	    });  
	});
});

function uploadfile(obj,fileid){
	$("#"+fileid).click();
	$("#"+fileid).on("change", function(){
		$.ajaxFileUpload({  
	        url : getUri()+'UploadFile/UploadFileController/ajaxUploadFile',//用于文件上传的服务器端请求地址  
	        async: false,
	        fileElementId : fileid, //文件上传空间的id属性   <input type="file" id="file1" name="file" />
	        type : 'post',  
	        dataType : 'text',  //兼容IE将datatype改为 text
	        //dataType: 'application/json', //返回值类型 一般设置为json
	        success : function(data){//服务器成功响应处理函数
	        	//alert(data);
	        	data = $.parseJSON(data); 
	        	if(data.code == "001"){
	        		alert("upload success"+data.message);
	        	}else{
	        		alert("upload error"+data.message);
	        	}
	        },  
			error: function (data, status, e){//服务器响应失败处理函数
		        alert(e);
		    }
	    });  
	});
	//window.location.reload();
}

/*function myrefresh()
{
   window.location.reload();
}
setTimeout('myrefresh()',1000); //指定1秒刷新一次*/