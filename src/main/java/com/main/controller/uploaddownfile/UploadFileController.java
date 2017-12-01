package com.main.controller.uploaddownfile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;
import com.main.common.BaseBackInfo;
import com.main.common.BaseController;
import com.main.common.PropertyConfig;
import com.main.common.StatusCode;
import com.main.common.utils.UploadDownUtil;
import util.admin.Logger;

/**
 * 
 *<p>Title	: UploadFileController</p>
 * @Description	: 上传文件
 * @author	: admin
 * @date	: 2017年11月27日下午1:22:58
 */
@Controller
@RequestMapping("/UploadFile/UploadFileController")
public class UploadFileController extends BaseController{
	private static final Logger logger = Logger.getLogger(UploadFileController.class);
	
	/**
	 * 
	 * @Description :首页
	 * @param request
	 * @param reqResponse
	 * @return
	 */
	@RequestMapping(value="/list",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView showPage(HttpServletRequest request,HttpServletResponse reqResponse){
		
		request.setAttribute("showPage", "UploadFileController");
		return new ModelAndView("/uploaddownfile/uploaddownlist");
	}
	
	/**
	 * 
	 * @Description :uploadSingleFile_multipartFile.transferTo
	 * @param request
	 * @param reqResponse
	 * @param uploadFile
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/uploadSingleFile",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView uploadSingleFile(HttpServletRequest request,HttpServletResponse reqResponse,
			MultipartFile uploadFile,Model model){
		String username = request.getParameter("username");
		String message ="username:"+username+"";
		
		if(uploadFile == null || uploadFile.isEmpty()){
			message = getMessage(StatusCode.UPLOADFILE_NULL);
			model.addAttribute("message", message);
			model.addAttribute("msgs", "1000001");
			return new ModelAndView("/uploaddownfile/message");
		}
		logger.info("uploadFile.getSize():"+uploadFile.getSize());
		if(uploadFile.getSize() > Long.valueOf(PropertyConfig.getProperty(PropertyConfig.UPLOADFILE_MAXSIZE))){
			message = getMessage(StatusCode.UPLOADFILE_GTSIZE);
			model.addAttribute("message", message);
			model.addAttribute("msgs", "1000002");
			return new ModelAndView("/uploaddownfile/message");
		}
		String fileName = uploadFile.getOriginalFilename();
		if(!UploadDownUtil.isFiles(fileName)){
			message = getMessage(StatusCode.UPLOADFILE_UNSUFFIXS);
			model.addAttribute("message", message);
			model.addAttribute("msgs", "1000003");
			return new ModelAndView("/uploaddownfile/message");
		}
		
		String savePath = getAbsolutePath(request,"/WEB-INF/views/uploaddownfile/uploadfile",fileName);
		File targetFile = new File(savePath);
		try {
			logger.info("上传username："+username);
			uploadFile.transferTo(targetFile);//上传文件到指定的文件夹中
			message +="上传文件成功！";
			model.addAttribute("msgs","001");
		}catch (Exception e) {
			message +="上传文件失败！";
			model.addAttribute("msgs","上传文件失败");
			e.printStackTrace();
		}
		
		model.addAttribute("message", message);
		return new ModelAndView("/uploaddownfile/message");
	}
	
	/**
	 * 
	 * @Description :uploadSingleFile_MultipartHttpServletRequest_multipartFile.transferTo
	 * @param request
	 * @param reqResponse
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/uploadSingleFile2",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView uploadSingleFile2(HttpServletRequest request,HttpServletResponse reqResponse,
			Model model){
		//将request转成MultipartHttpServletRequest
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) (request);
		//页面控件的文件流，对应页面控件 input uploadFile
		MultipartFile uploadFile = mRequest.getFile("uploadFile");
		String username = request.getParameter("username");
		String message ="username:"+username+"";
		
		if(uploadFile == null || uploadFile.isEmpty()){
			message = getMessage(StatusCode.UPLOADFILE_NULL);
			model.addAttribute("message", message);
			model.addAttribute("msgs", "1000001");
			return new ModelAndView("/uploaddownfile/message");
		}
		if(uploadFile.getSize() > Long.valueOf(PropertyConfig.getProperty(PropertyConfig.UPLOADFILE_MAXSIZE))){
			message = getMessage(StatusCode.UPLOADFILE_GTSIZE);
			model.addAttribute("message", message);
			model.addAttribute("msgs", "1000002");
			return new ModelAndView("/uploaddownfile/message");
		}
		String fileName = uploadFile.getOriginalFilename();
		if(!UploadDownUtil.isFiles(fileName)){
			message = getMessage(StatusCode.UPLOADFILE_UNSUFFIXS);
			model.addAttribute("message", message);
			model.addAttribute("msgs", "1000003");
			return new ModelAndView("/uploaddownfile/message");
		}
		
		String savePath = getAbsolutePath(request,"/WEB-INF/views/uploaddownfile/uploadfile",fileName);
		File targetFile = new File(savePath);
		try {
			logger.info("上传username："+username);
			uploadFile.transferTo(targetFile);//上传文件到指定的文件夹中
			message +="上传文件成功！";
			model.addAttribute("msgs","001");
		}catch (Exception e) {
			message +="上传文件失败！";
			model.addAttribute("msgs","002");
			e.printStackTrace();
		}
		
		model.addAttribute("message", message);
		return new ModelAndView("/uploaddownfile/message");
	}
	
	/**
	 * 
	 * @Description :uploadSingleFile_multipartFile.stream
	 * @param request
	 * @param reqResponse
	 * @param uploadFile
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/uploadSingleFile3",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView uploadSingleFile3(HttpServletRequest request,HttpServletResponse reqResponse,
			MultipartFile uploadFile,Model model){
		String username = request.getParameter("username");
		String message ="username:"+username+"";
		
		if(uploadFile == null || uploadFile.isEmpty()){
			message = getMessage(StatusCode.UPLOADFILE_NULL);
			model.addAttribute("message", message);
			model.addAttribute("msgs", "1000001");
			return new ModelAndView("/uploaddownfile/message");
		}
		if(uploadFile.getSize() > Long.valueOf(PropertyConfig.getProperty(PropertyConfig.UPLOADFILE_MAXSIZE))){
			message = getMessage(StatusCode.UPLOADFILE_GTSIZE);
			model.addAttribute("message", message);
			model.addAttribute("msgs", "1000002");
			return new ModelAndView("/uploaddownfile/message");
		}
		String fileName = uploadFile.getOriginalFilename();
		if(!UploadDownUtil.isFiles(fileName)){
			message = getMessage(StatusCode.UPLOADFILE_UNSUFFIXS);
			model.addAttribute("message", message);
			model.addAttribute("msgs", "1000003");
			return new ModelAndView("/uploaddownfile/message");
		}
		
		String savePath = getAbsolutePath(request,"/WEB-INF/views/uploaddownfile/uploadfile",fileName);
		File targetFile = new File(savePath);
		try {
			logger.info("上传username："+username);
			InputStream inputStream = uploadFile.getInputStream();
			FileOutputStream fileOutputStream = new FileOutputStream(targetFile);
			byte[] tempbytes = new byte[4096];
			long fileSize = uploadFile.getSize();
			if(fileSize <= tempbytes.length){
				tempbytes = new byte[(int) fileSize];
			}
			int len = 0;
			while((len = inputStream.read(tempbytes)) != -1){
				fileOutputStream.write(tempbytes, 0, len);
			}
			fileOutputStream.flush();
			fileOutputStream.close();
			inputStream.close();
			message +="上传文件成功！";
			model.addAttribute("msgs","001");
		}catch (Exception e) {
			message +="上传文件失败！";
			model.addAttribute("msgs","002");
			e.printStackTrace();
		}
		
		model.addAttribute("message", message);
		return new ModelAndView("/uploaddownfile/message");
	}
	
	/**
	 * 
	 * @Description :uploadMultifile_multipartFile[].transferTo
	 * @param request
	 * @param response
	 * @param multipartFiles
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/uploadMultifile",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView uploadMultifile(HttpServletRequest request,HttpServletResponse response,
			MultipartFile[] uploadFile,Model model){
		String username = request.getParameter("username");
		String message ="username:"+username+"";
		
		logger.info("uploadFile："+uploadFile.length);
		for (int i = 0; i < uploadFile.length; i++) {
			MultipartFile multipartFile = uploadFile[i];
			if(multipartFile == null || multipartFile.isEmpty()){
				message = getMessage(StatusCode.UPLOADFILE_NULL);
				model.addAttribute("message", message);
				model.addAttribute("msgs", "1000001");
				return new ModelAndView("/uploaddownfile/message");
			}
			if(multipartFile.getSize() > Long.valueOf(PropertyConfig.getProperty(PropertyConfig.UPLOADFILE_MAXSIZE))){
				message = getMessage(StatusCode.UPLOADFILE_GTSIZE);
				model.addAttribute("message", message);
				model.addAttribute("msgs", "1000002");
				return new ModelAndView("/uploaddownfile/message");
			}
			if(!UploadDownUtil.isFiles(multipartFile.getOriginalFilename())){
				message = getMessage(StatusCode.UPLOADFILE_UNSUFFIXS);
				model.addAttribute("message", message);
				model.addAttribute("msgs", "1000003");
				return new ModelAndView("/uploaddownfile/message");
			}
			
		}
		for (int i = 0; i < uploadFile.length; i++) {
			MultipartFile multipartFile = uploadFile[i];
			String savePath = getAbsolutePath(request,"/WEB-INF/views/uploaddownfile/uploadfile",multipartFile.getOriginalFilename());
			File targetFile = new File(savePath);
			try {
				logger.info("上传username："+username);
				multipartFile.transferTo(targetFile);//上传文件到指定的文件夹中
				if(i==(uploadFile.length-1)){
					message +="上传文件成功！";
				}
				model.addAttribute("msgs","001");
			}catch (Exception e) {
				message +="第"+i+"个文件上传失败！";
				model.addAttribute("msgs","002");
				e.printStackTrace();
			}
			
		}
		
		model.addAttribute("message", message);
		return null;
		
	}

	/**
	 * 
	 * @Description :uploadFile_CommonsMultipartResolver_multipartFile.transferTo
	 * @param request
	 * @param reqResponse
	 * @param uploadFile
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/uploadFile",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView uploadFile(HttpServletRequest request,HttpServletResponse reqResponse,
			Model model){
		String username = request.getParameter("username");
		String message ="username:"+username+"";
		//将当前上下文初始化给  CommonsMultipartResolver （多部分解析器）
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		//检查form中是否有enctype="multipart/form-data"
		if (multipartResolver.isMultipart(request)) {
			 //将request变成多部分request
			 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request ;
			 //获取multiRequest 中所有的文件名
			 Iterator<String> iter = multipartRequest.getFileNames();
			 List<MultipartFile> list = new ArrayList<MultipartFile>();
		try {
			 while (iter.hasNext()) {
			      //一次遍历所有文件
				  String name = iter.next().toString();
			      MultipartFile uploadFile = multipartRequest.getFile(name);
			      if(uploadFile == null || uploadFile.isEmpty()){
						message = getMessage(StatusCode.UPLOADFILE_NULL);
						model.addAttribute("message", message);
						model.addAttribute("msgs", "1000001");
						return new ModelAndView("/uploaddownfile/message");
					}
					if(uploadFile.getSize() > Long.valueOf(PropertyConfig.getProperty(PropertyConfig.UPLOADFILE_MAXSIZE))){
						message = getMessage(StatusCode.UPLOADFILE_GTSIZE);
						model.addAttribute("message", message);
						model.addAttribute("msgs", "1000002");
						return new ModelAndView("/uploaddownfile/message");
					}
					if(!UploadDownUtil.isFiles(uploadFile.getOriginalFilename())){
						message = getMessage(StatusCode.UPLOADFILE_UNSUFFIXS);
						model.addAttribute("message", message);
						model.addAttribute("msgs", "1000003");
						return new ModelAndView("/uploaddownfile/message");
					}
					list.add(uploadFile);
			 }
			
			 for (MultipartFile multipartFile : list) {
				 //保存文件
				 String savePath = getAbsolutePath(request,"/WEB-INF/views/uploaddownfile/uploadfile",multipartFile.getOriginalFilename());
				 File targetFile = new File(savePath);
				
				 logger.info("上传username："+username);
				 multipartFile.transferTo(targetFile);//上传文件到指定的文件夹中
			}
			message +="上传文件成功！";
			model.addAttribute("msgs","001");
		}catch (Exception e) {
			message +="上传文件失败！";
			model.addAttribute("msgs","002");
			e.printStackTrace();
		}
		}
		model.addAttribute("message", message);
		return new ModelAndView("/uploaddownfile/message");
	}
	
	/**
	 * 
	 * @Description :uploadFileToZip
	 * @param request
	 * @param reqResponse
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/uploadFileToZip",method={RequestMethod.GET,RequestMethod.POST})
	public ModelAndView uploadFileToZip(HttpServletRequest request,HttpServletResponse reqResponse,
			Model model){
		String username = request.getParameter("username");
		String message ="username:"+username+"";
		MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) request;  
        Map<String, MultipartFile> fileMap = mRequest.getFileMap();  
        List<MultipartFile> list = new ArrayList<MultipartFile>();
        try {
	        int i = 0;
	        for (Iterator<Map.Entry<String, MultipartFile>> it = fileMap.entrySet()  
	                .iterator(); it.hasNext(); i++) {  
	            Map.Entry<String, MultipartFile> entry = it.next();  
	            MultipartFile mFile = entry.getValue();  
	            if(mFile == null || mFile.isEmpty()){
	    			message = getMessage(StatusCode.UPLOADFILE_NULL);
	    			model.addAttribute("message", message);
	    			model.addAttribute("msgs", "1000001");
	    			return new ModelAndView("/uploaddownfile/message");
	    		}
	    		if(mFile.getSize() > Long.valueOf(PropertyConfig.getProperty(PropertyConfig.UPLOADFILE_MAXSIZE))){
	    			message = getMessage(StatusCode.UPLOADFILE_GTSIZE);
	    			model.addAttribute("message", message);
	    			model.addAttribute("msgs", "1000002");
	    			return new ModelAndView("/uploaddownfile/message");
	    		}
	    		String fileName = mFile.getOriginalFilename();
	    		if(!UploadDownUtil.isFiles(fileName)){
	    			message = getMessage(StatusCode.UPLOADFILE_UNSUFFIXS);
	    			model.addAttribute("message", message);
	    			model.addAttribute("msgs", "1000003");
	    			return new ModelAndView("/uploaddownfile/message");
	    		}
	    		logger.info("第======"+i+"======个文件正在上传");
	    		list.add(mFile);
	        }  
	        
	        for (MultipartFile multipartFile : list) {
	        	String savePath = getAbsolutePath(request,"/WEB-INF/views/uploaddownfile/uploadfile",multipartFile.getOriginalFilename());
				String zipNamePath = zipName(savePath);  
				// 上传成为压缩文件  
				ZipOutputStream outputStream = new ZipOutputStream(  
				        new BufferedOutputStream(new FileOutputStream(zipNamePath)));  
				outputStream.putNextEntry(new ZipEntry(multipartFile.getOriginalFilename()));  
				outputStream.setEncoding("GBK");  
				logger.info("上传username："+username);
				FileCopyUtils.copy(multipartFile.getInputStream(), outputStream); //上传文件到指定的文件夹中 
			}
	        message +="上传文件成功！";
	        model.addAttribute("msgs","001");
        }catch (IOException e) {
			message +="上传文件失败！";
			model.addAttribute("msgs","002");
			e.printStackTrace();
		}
		model.addAttribute("message", message);
		return new ModelAndView("/uploaddownfile/message");
	}
	
	/**
	 * 
	 * @Description :ajaxUploadFile
	 * @param request
	 * @param reqResponse
	 * @param uploadFile
	 * @param model
	 * @return
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonGenerationException 
	 */
	@RequestMapping(value="/ajaxUploadFile",method={RequestMethod.GET,RequestMethod.POST})
	//@ResponseBody
	public BaseBackInfo<String> ajaxUploadFile(HttpServletRequest request,HttpServletResponse response,
			MultipartFile uploadFile,Model model) throws JsonGenerationException, JsonMappingException, IOException{
		BaseBackInfo<String> baseBackInfo = new BaseBackInfo<String>();
		ObjectMapper mapper = new ObjectMapper();
		response.setContentType("text/html;charset=utf-8");
		//response.getWriter().write(JsonMapper.nonEmptyMapper().toJson(baseBackInfo));
		String username = request.getParameter("username");
		String message ="username:"+username+"";
		
		if(uploadFile == null || uploadFile.isEmpty()){
			baseBackInfo.setCode(StatusCode.UPLOADFILE_NULL);
			baseBackInfo.setMessage(getMessage(StatusCode.UPLOADFILE_NULL));
			String writeValueAsString = mapper.writeValueAsString(baseBackInfo);
			response.getWriter().write(mapper.writeValueAsString(baseBackInfo));
			return null;
		}
		logger.info("uploadFile.getSize():"+uploadFile.getSize());
		if(uploadFile.getSize() > Long.valueOf(PropertyConfig.getProperty(PropertyConfig.UPLOADFILE_MAXSIZE))){
			baseBackInfo.setCode(StatusCode.UPLOADFILE_GTSIZE);
			baseBackInfo.setMessage(getMessage(StatusCode.UPLOADFILE_GTSIZE));
			String writeValueAsString = mapper.writeValueAsString(baseBackInfo);
			response.getWriter().write(mapper.writeValueAsString(baseBackInfo));
			return null;
		}
		String fileName = uploadFile.getOriginalFilename();
		if(!UploadDownUtil.isFiles(fileName)){
			baseBackInfo.setCode(StatusCode.UPLOADFILE_UNSUFFIXS);
			baseBackInfo.setMessage(getMessage(StatusCode.UPLOADFILE_UNSUFFIXS));
			String writeValueAsString = mapper.writeValueAsString(baseBackInfo);
			response.getWriter().write(mapper.writeValueAsString(baseBackInfo));
			return null;
		}
		
		String savePath = getAbsolutePath(request,"/WEB-INF/views/uploaddownfile/uploadfile",fileName);
		File targetFile = new File(savePath);
		try {
			logger.info("上传username："+username);
			//uploadFile.transferTo(targetFile);//上传文件到指定的文件夹中
			FileCopyUtils.copy(uploadFile.getBytes(), targetFile);   
			baseBackInfo.setCode(StatusCode.HTTP_OK);
			baseBackInfo.setMessage(getMessage(StatusCode.HTTP_OK));
		}catch (Exception e) {
			baseBackInfo.setCode(StatusCode.HTTP_FAIL);
			baseBackInfo.setMessage(getMessage(StatusCode.HTTP_FAIL));
			e.printStackTrace();
		}
		
		String writeValueAsString = mapper.writeValueAsString(baseBackInfo);
		response.getWriter().write(mapper.writeValueAsString(baseBackInfo));
		return null;
	}
	
	/**
	 * 
	 * @Description :获得文件的绝对路径
	 * @param request
	 * @param filePath 文件路径
	 * @param fileName 文件名称
	 * @return
	 */
	private static String getAbsolutePath(HttpServletRequest request, String filePath, String fileName) {
		  String savePath = request.getSession().getServletContext().getRealPath(filePath)
				  		+ File.separator + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		  File saveFile = new File(savePath);
		  if (!saveFile.exists() || !saveFile.isDirectory()) {
			   saveFile.mkdirs();
          }
		  savePath  = savePath + File.separator + UUID.randomUUID().toString()+"_"+fileName;
		  return savePath;
	}
	
	/**
	 * 
	 * @Description :压缩后的文件名 
	 * @param name
	 * @return
	 */
	private static String zipName(String name) {  
        String prefix = "";  
        if (name.lastIndexOf(".") != -1) {  
            prefix = name.substring(0, name.lastIndexOf("."));  
        } else {  
            prefix = name;  
        }  
        return prefix + ".zip";  
	 }  
}
