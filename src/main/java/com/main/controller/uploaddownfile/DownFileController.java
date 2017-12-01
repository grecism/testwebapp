package com.main.controller.uploaddownfile;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.main.common.BaseController;
import com.main.common.StatusCode;

/**
 * 
 *<p>Title	: ShowPageController</p>
 * @Description	:
 * @author	: admin
 * @date	: 2017年11月30日下午3:22:36
 */
@Controller
@RequestMapping("/downFile/DownFileController")
public class DownFileController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DownFileController.class);
	
	/**
	 * 
	 * @Description :下载列表
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/downlist",method={RequestMethod.POST,RequestMethod.GET})
	public ModelAndView downList(HttpServletRequest request,HttpServletResponse response,Model model){
		logger.info("downList");
		//获取上传文件的目录
		String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/views/uploaddownfile/uploadfile");
		//存储要下载的文件名
		Map<String,String> fileNameMap = new HashMap<String,String>();
		//递归遍历filepath目录下的所有文件和目录，将文件的文件名存储到map集合中
		listFile(new File(filePath),fileNameMap);//File既可以代表一个文件也可以代表一个目录
		//将Map集合发送到showpage.jsp页面进行显示
		model.addAttribute("fileNameMap", fileNameMap);
		return new ModelAndView("/uploaddownfile/downlist");
	}
	
	/**
	 * 
	 * @Description :downFile_stream
	 * @param request
	 * @param response
	 * @param fileName
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/downFile",method={RequestMethod.POST,RequestMethod.GET})
	public String downFile(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("filename") String fileName,Model model){
		try {
			BufferedInputStream bis= null;
			BufferedOutputStream bos = null;
			//response.setContentType("text/html;charset=utf-8"); 
			response.setContentType("application/octet-stream");  
			response.setCharacterEncoding("UTF-8"); 
			//获取上传文件的目录
			File downFile = new File(fileName);
			//String filePath = request.getSession().getServletContext().getRealPath("/WEB-INF/views/uploaddownfile/uploadfile");
			//fileName = fileName.substring(fileName.lastIndexOf("uploadfile")+11);
			//File downFile = new File(filePath+File.separator+fileName);
			String realName = fileName.substring(fileName.lastIndexOf("_")+1);
			response.setHeader("Content-disposition", "attachment; filename="  
			        + new String(realName.getBytes("utf-8"), "ISO8859-1"));  
			response.setHeader("Content-Length", String.valueOf(downFile.length()));
			
			bis = new BufferedInputStream(new FileInputStream(downFile));
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] tempbytes = new byte[2048];
			int len;
			while((len = bis.read(tempbytes)) != -1){
				bos.write(tempbytes, 0, len);
			}
			
			bos.flush();
			bos.close();
			bis.close();
			model.addAttribute("message", getMessage(StatusCode.HTTP_OK));
			model.addAttribute("msgs", StatusCode.HTTP_OK);
		} catch (Exception e) {
			model.addAttribute("message", getMessage(StatusCode.HTTP_FAIL));
			model.addAttribute("msgs", StatusCode.HTTP_FAIL);
			e.printStackTrace();
		}
		//return new ModelAndView("/uploaddownfile/message");
		return null;
	}
	
	/**
	 * 
	 * @Description :downFile2_HttpHeaders
	 * @param request
	 * @param response
	 * @param fileName
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value="/downFile2",method={RequestMethod.POST,RequestMethod.GET})
	public ResponseEntity<byte[]> downFile2(HttpServletRequest request,HttpServletResponse response,
			@RequestParam("filename") String fileName,Model model) throws IOException{
		//获取上传文件的目录
		File downFile = new File(fileName);
		HttpHeaders headers = new HttpHeaders();
		try {
			String realName = fileName.substring(fileName.lastIndexOf("_")+1);
			headers.setContentDispositionFormData("attachment", new String(realName.getBytes("utf-8"),"iso8859-1"));
			headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(downFile),headers,HttpStatus.CREATED);
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
			fileNameMap.put(file.getParent()+file.separator+file.getName(), realFileName);
		}
	}
	
}
