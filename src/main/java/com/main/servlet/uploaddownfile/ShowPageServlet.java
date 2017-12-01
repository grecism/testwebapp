package com.main.servlet.uploaddownfile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.http.HttpTester.Request;

import util.admin.Logger;

/**
 * 
 *<p>Title	: ShowPageServlet</p>
 * @Description	:展示页面
 * @author	: admin
 * @date	: 2017年11月24日下午2:34:50
 */
public class ShowPageServlet extends HttpServlet{
	private static final Logger logger = Logger.getLogger(ShowPageServlet.class);
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//super.doGet(req, resp);
		logger.info("ShowPageServlet");
		//获取上传文件的目录
		String filePath = this.getServletContext().getRealPath("/WEB-INF/uploaddownfile/upload");
		//存储要下载的文件名
		Map<String,String> fileNameMap = new HashMap<String,String>();
		//递归遍历filepath目录下的所有文件和目录，将文件的文件名存储到map集合中
		listFile(new File(filePath),fileNameMap);//File既可以代表一个文件也可以代表一个目录
		//将Map集合发送到showpage.jsp页面进行显示
		req.setAttribute("fileNameMap", fileNameMap);
		req.getRequestDispatcher("/WEB-INF/uploaddownfile/showpage.jsp").forward(req, resp);
	}
	
	/**
	 * 
	 * @Description : 递归遍历指定目录下的所有文件
	 * @param file  即代表一个文件，也代表一个文件目录
	 * @param fileNameMap 存储文件名的Map集合
	 */
	private void listFile(File file, Map<String, String> fileNameMap) {
		//如果file代表的不是一个文件，而是一个目录
		if(!file.isFile()){
			//列出该目录下的所有文件和目录
			File[] listFiles = file.listFiles();
			//遍历files[]数组
			if(listFiles != null){
				for (File f : listFiles) {
					//递归
					listFile(f,fileNameMap);
				}
			}
			
		}else{
			/**
			 * 处理文件名，上传后的文件是以uuid_文件名的形式去重新命名的，去除文件名的uuid_部分
			 * file.getName().indexOf("_")检索字符串中第一次出现"_"字符的位置，如果文件名类似于：9349249849883438344_aaa.jpg
			 *  那么file.getName().substring(file.getName().indexOf("_")+1)处理之后就可以得到aaa.jpg部分
			 */
			String realFileName = file.getName().substring(file.getName().lastIndexOf("_")+1);
			//file.getName()得到的是文件的原始名称，这个名称是唯一的，因此可以作为key，realName是处理过后的名称，有可能会重复
			fileNameMap.put(file.getName(), realFileName);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//super.doPost(req, resp);
		doGet(req, resp);
	}
}
