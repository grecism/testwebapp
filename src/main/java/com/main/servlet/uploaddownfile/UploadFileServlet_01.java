package com.main.servlet.uploaddownfile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import util.admin.Logger;

/**
 * 
 *<p>Title	: UploanFileServlet</p>
 * @Description	:上传文件
 * @author	: admin
 * @date	: 2017年11月24日下午2:35:13
 */
public class UploadFileServlet_01 extends HttpServlet{
	private static final Logger logger = Logger.getLogger(UploadFileServlet_01.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//super.doGet(req, resp);
		logger.info("UploanFileServlet");
		//得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
		String savePath = this.getServletContext().getRealPath("/WEB-INF/uploaddownfile/upload");
		File file = new File(savePath);
		//判断上传文件的保存目录是否存在
		if(!file.exists() && !file.isDirectory()){
			file.mkdirs();
		}
		//消息提示
		String message = "";
		try {
			//使用Apache文件上传组件处理文件上传步骤:
			//1、创建一个DiskFileItemFactory工厂
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			//2、创建一个文件上传解析器
			ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
			//解决上传文件名的中文乱码
			servletFileUpload.setHeaderEncoding("utf-8");
			//3、判断提交上来的数据是否是上传表单的数据
			if(!servletFileUpload.isMultipartContent(req)){
				//按照传统方式获取数据
				PrintWriter printWriter = resp.getWriter();
				printWriter.println("Error: 表单必须包含 enctype=multipart/form-data");
				printWriter.flush();
				printWriter.close();
				return;
			}
			//4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			List<FileItem> list = servletFileUpload.parseRequest(req);
			for (FileItem fileItem : list) {
				if(fileItem.isFormField()){//如果fileitem中封装的是普通输入项的数据
					String name = fileItem.getFieldName();
					String value = fileItem.getString("utf-8");//解决普通输入项的数据的中文乱码问题
					//String value = fileItem.getString();
					//value = new String(value.getBytes("iso8859-1"),"UTF-8");
					logger.info("name:"+name+"======value:"+value);
				}else{//如果fileitem中封装的是上传文件
					String fileName = fileItem.getName();//得到上传的文件名称
					if(fileName == null || "".equals(fileName.trim())){
						continue;
					}
					//注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					//处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					int lastIndexOf = fileName.lastIndexOf("\\");
					fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
					//获取item中的上传文件的输入流
					InputStream inputStream = fileItem.getInputStream();
					//创建一个文件输出流
					FileOutputStream fileOutputStream = new FileOutputStream(savePath+"/"+fileName);
					//创建一个缓冲区
					byte[] tempbytes = new byte[1024];
					//判断输入流中的数据是否已经读完的标识
					int len = 0;
					//循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					/*while((len = inputStream.read(tempbytes)) != -1){
						fileOutputStream.write(tempbytes, 0, len);
					}*/
					while((len = inputStream.read(tempbytes)) > 0){
						//使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
						fileOutputStream.write(tempbytes, 0, len);
					}
					fileOutputStream.flush();
					fileOutputStream.close();
					inputStream.close();
					//删除处理文件上传时生成的临时文件
					fileItem.delete();
					message = "文件上传成功！";
				}
			}
		} catch (Exception e) {
			message = "文件上传失败！";
			e.printStackTrace();
		}
		req.setAttribute("message", message);
		req.getRequestDispatcher("/WEB-INF/uploaddownfile/message.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//super.doPost(req, resp);
		doGet(req, resp);
	}
}
