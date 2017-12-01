package com.main.servlet.uploaddownfile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.admin.Logger;

/**
 * 
 *<p>Title	: DownLoadServlet</p>
 * @Description	:下载文件
 * @author	: admin
 * @date	: 2017年11月24日下午5:35:57
 */
public class DownLoadServlet extends HttpServlet{
	private static final Logger logger = Logger.getLogger(DownLoadServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//super.doGet(req, resp);
		//上传的文件都是保存在/WEB-INF/upload目录下的子目录当中
		String filePath = this.getServletContext().getRealPath("/WEB-INF/uploaddownfile/upload");
		String fileName = req.getParameter("filename"); ////得到要下载的文件名 9349249849883438344_aaa.jpg
		//fileName = new String(fileName.getBytes("ios8859-1"),"utf-8");
		//通过文件名找出文件的所在目录
		String realFilePath = findRealFilePath(fileName,filePath);
		logger.info("真实的路径地址为："+realFilePath);
		//得到要下载的文件
		File file = new File(realFilePath+File.separator+fileName);
		//如果文件不存在
		if(!file.exists()){
			req.setAttribute("message", "您要下载的资源已被删除！！");
			req.getRequestDispatcher("/message.jsp").forward(req,resp);
			return;
		}
		//处理文件名
		String realName = fileName.substring(fileName.lastIndexOf("_")+1);
		//设置响应头，控制浏览器下载该文件
		//resp.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(realName, "UTF-8"));
		//解决下载文件名乱码问题
		String userAgent = req.getHeader("User-Agent");
		//针对IE或者以IE为内核的浏览器：
		if (userAgent.contains("MSIE")||userAgent.contains("Trident")) {
			realName = java.net.URLEncoder.encode(realName, "UTF-8");
		} else {
		//非IE浏览器的处理：
			realName = new String(realName.getBytes("UTF-8"),"ISO-8859-1");
		}
		resp.setHeader("Content-disposition", String.format("attachment; filename=\"%s\"", realName));
		//resp.setContentType("application/vnd.ms-excel;charset=utf-8");
		resp.setCharacterEncoding("UTF-8"); 
		//读取要下载的文件，保存到文件输入流
		FileInputStream fileInputStream = new FileInputStream(realFilePath+File.separator+fileName); 
		//创建输出流
		OutputStream outputStream = resp.getOutputStream();
		//创建缓冲区
		byte[] tempbytes = new byte[1024];
		int len= 0;
		//循环将输入流中的内容读取到缓冲区当中
		while((len = fileInputStream.read(tempbytes)) != -1){
			//输出缓冲区的内容到浏览器，实现文件下载
			outputStream.write(tempbytes, 0, len);
		}
		outputStream.flush();
		outputStream.close();
		fileInputStream.close();
	}
	
	/**
	 * 
	 * @Description :通过文件名和存储上传文件根目录找出要下载的文件的所在路径
	 * @param fileName 要下载的文件名
	 * @param filePath 上传文件保存的根目录，也就是/WEB-INF/upload目录
	 * @return 要下载的文件的存储目录
	 */
	private String findRealFilePath(String fileName, String filePath) {
		int hashCode = fileName.hashCode();
		int dir1 = hashCode&0xf;
		int dir2 = (hashCode&0xf0)>>4;
		String dir = filePath+File.separator+dir1+File.separator+dir2;
		File file = new File(dir);
		if(!file.exists()){
			file.mkdirs();
		}
		return dir;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//super.doPost(req, resp);
		doGet(req, resp);
	}
}
