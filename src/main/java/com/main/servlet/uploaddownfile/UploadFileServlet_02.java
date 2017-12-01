package com.main.servlet.uploaddownfile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.ProgressListener;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import util.admin.Logger;

/**
 * 
 *<p>Title	: UploadFileServlet_02</p>
 * @Description	:上传文件
 * 上述的代码虽然可以成功将文件上传到服务器上面的指定目录当中，但是文件上传功能有许多需要注意的小细节问题，以下列出的几点需要特别注意的：
 *　1、为保证服务器安全，上传文件应该放在外界无法直接访问的目录下，比如放于WEB-INF目录下。
 *　2、为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名。
 *　3、为防止一个目录下面出现太多文件，要使用hash算法打散存储。
 *　4、要限制上传文件的最大值。
 *　5、要限制上传文件的类型，在收到上传文件名时，判断后缀名是否合法。
 *　针对上述提出的5点细节问题，我们来改进一下，改进后的代码如下：
 * @author	: admin
 * @date	: 2017年11月24日下午2:38:48
 */
public class UploadFileServlet_02 extends HttpServlet{
	private static final Logger logger = Logger.getLogger(UploadFileServlet_02.class);
	
	private static final int UPLOADMAXVALUE= 1024*1024*1;
	private static final int UPLOADMAXSUMVALUE=1024*1024*3;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//super.doGet(req, resp);
		//得到上传文件的保存目录，将上传的文件存放于WEB-INF目录下，不允许外界直接访问，保证上传文件的安全
		String savePath = this.getServletContext().getRealPath("/WEB-INF/uploaddownfile/upload");
		File saveFle = new File(savePath);
		if(!saveFle.exists()){
			saveFle.mkdirs();
		}
		//上传时生成的临时文件保存目录
		String tempPath = this.getServletContext().getRealPath("/WEB-INF/uploaddownfile/tempupload");
		File tempFile = new File(tempPath);
		if(!tempFile.exists()){
			tempFile.mkdirs();
		}
		
		String message = "";
		try {
			//使用Apache文件上传组件处理文件上传步骤：
			//1、创建一个DiskFileItemFactory工厂
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			//设置工厂的缓冲区的大小，当上传的文件大小超过缓冲区的大小时，就会生成一个临时文件存放到指定的临时目录当中
			diskFileItemFactory.setSizeThreshold(1024*100);//设置缓冲区的大小为100KB，如果不指定，那么缓冲区的大小默认是10KB
			diskFileItemFactory.setRepository(tempFile);//设置上传时生成的临时文件的保存目录
			//2、创建一个文件上传解析器
			ServletFileUpload servletFileUpload = new ServletFileUpload(diskFileItemFactory);
			//监听文件上传进度
			servletFileUpload.setProgressListener(new ProgressListener() {
				@Override
				public void update(long pBytesRead, long pContentLength, int pItems) {
					logger.info("当前文件大小为："+pContentLength+"======当前已处理："+pBytesRead);
					// 当前文件大小为：529129======当前已处理：502518
					// 当前文件大小为：529129======当前已处理：505974
					// 当前文件大小为：529129======当前已处理：507904
					// 当前文件大小为：529129======当前已处理：511958
					// 当前文件大小为：529129======当前已处理：514848
					// 当前文件大小为：529129======当前已处理：518902
					// 当前文件大小为：529129======当前已处理：522358
					// 当前文件大小为：529129======当前已处理：524288
					// 当前文件大小为：529129======当前已处理：528342
					// 当前文件大小为：529129======当前已处理：529129
				}
			});
			servletFileUpload.setFileSizeMax(1024*1024*1);//设置上传单个文件的大小的最大值，目前是设置为1024*1024字节，也就是1MB
			servletFileUpload.setSizeMax(1024*1024*10);//设置上传文件总量的最大值，最大值=同时上传的多个文件的大小的最大值的和，目前设置为10MB
			servletFileUpload.setHeaderEncoding("utf-8"); //解决上传文件名的中文乱码
			//3、判断提交上来的数据是否是上传表单的数据
			if(!servletFileUpload.isMultipartContent(req)){
				//按照传统方式获取数据
				PrintWriter printWriter = resp.getWriter();
				printWriter.println("Error: 表单必须包含 enctype=multipart/form-data");
				printWriter.flush();
				printWriter.close();
			}
			//4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
			List<FileItem> list = servletFileUpload.parseRequest(req);
			for (FileItem fileItem : list) {
				if(fileItem.isFormField()){//如果fileitem中封装的是普通输入项的数据
					String name = fileItem.getFieldName();
					String value = fileItem.getString("utf-8");//解决普通输入项的数据的中文乱码问题
					logger.info("name:"+name+"======value:"+value);
				}else{
					String fileName = fileItem.getName();//得到上传的文件名称
					if(fileName == null || "".equals(fileName)){
						continue;
					}
					
					//注意：不同的浏览器提交的文件名是不一样的，有些浏览器提交上来的文件名是带有路径的，如：  c:\a\b\1.txt，而有些只是单纯的文件名，如：1.txt
					//处理获取到的上传文件的文件名的路径部分，只保留文件名部分
					fileName = fileName.substring(fileName.lastIndexOf("\\")+1);
					//得到上传文件的扩展名
					String fileExtName = fileName.substring(fileName.lastIndexOf(".")+1);
					//如果需要限制上传的文件类型，那么可以通过文件的扩展名来判断上传的文件类型是否合法
					logger.info("上传文件的扩展名是："+fileExtName);
					//得到文件保存的名称
					String realSaveFileName = makeFileName(fileName);
					//得到文件的保存目录
					String realSavePath = makePath(realSaveFileName,savePath);
					//获取item中的上传文件的输入流
					InputStream inputStream = fileItem.getInputStream();
					//创建一个文件输出流
					//FileOutputStream fileOutputStream = new FileOutputStream(realSavePath+"\\"+realSaveFileName);
					FileOutputStream fileOutputStream = new FileOutputStream(realSavePath+File.separator+realSaveFileName);
					//创建一个缓冲区
					byte[] tempbytes = new byte[1024];
					//判断输入流中的数据是否已经读完的标识
					int len = 0;
					//循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
					while((len = inputStream.read(tempbytes)) > 0){
						//使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(realSavePath + "\\" + realSaveFileName)当中
						fileOutputStream.write(tempbytes, 0, len);
					}
					fileOutputStream.flush();
					//关闭输出流
					fileOutputStream.close();
					//关闭输入流
					inputStream.close();
					
					//删除处理文件上传时生成的临时文件
					fileItem.delete();
					message="文件上传成功！";
				}
			}
		} catch (FileUploadBase.FileSizeLimitExceededException e) {
			req.setAttribute("message", "单个文件超出最大值！！！");
			req.getRequestDispatcher("/WEB-INF/uploaddownfile/message.jsp").forward(req, resp);
			logger.info("单个文件超出最大值！！！");
		} catch(FileUploadBase.SizeLimitExceededException e) {
			req.setAttribute("message", "上传文件的总的大小超出限制的最大值！！！");
			req.getRequestDispatcher("/WEB-INF/uploaddownfile/message.jsp").forward(req, resp);
			logger.info("上传文件的总的大小超出限制的最大值！！！");
		} catch (Exception e) {
			message="文件上传失败！";
			logger.info("文件上传失败！"+e);
		}
		req.setAttribute("message", message);
		req.getRequestDispatcher("/WEB-INF/uploaddownfile/message.jsp").forward(req, resp);
	}
	/**
	 * 
	 * @Description :为防止一个目录下面出现太多文件，要使用hash算法打散存储
	 * @param realSaveFileName 文件名，要根据文件名生成存储目录
	 * @param savePath 文件存储路径
	 * @return 新的存储目录
	 */
	private String makePath(String realSaveFileName, String savePath) {
		//得到文件名的hashCode的值，得到的就是realSaveFileName这个字符串对象在内存中的地址
		int hashCode = realSaveFileName.hashCode();
		int dir1 = hashCode&0xf;//0--15
		int dir2 = (hashCode&0xf0)>>4; //0-15
		//构造新的保存目录
		//String dir = savePath +"\\"+dir1 +"\\"+dir2;
		String dir = savePath +File.separator+dir1 +File.separator+dir2;
		File file = new File(dir);
		if(!file.exists()){
			file.mkdirs();
		}
		return dir;
	}

	/**
	 * 
	 * @Description :生成上传文件的文件名，文件名以：uuid+"_"+文件的原始名称/时间毫秒值+"_"+文件的原始名称
	 * @param fileName
	 * @return
	 */
	private String makeFileName(String fileName) {
		//为防止文件覆盖的现象发生，要为上传文件产生一个唯一的文件名
		//return UUID.randomUUID().toString() +"_"+fileName;
		return System.currentTimeMillis() +"_"+fileName;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		//super.doPost(req, resp);
		doGet(req, resp);
	}
}
