package com.test.readwritefile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.io.Reader;
import jxl.common.Logger;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 *<p>Title	: ReadFromFile</p>
 * @Description	:读文件
 * @author	: admin
 * @date	: 2017年11月24日下午2:33:40
 */
@SuppressWarnings("deprecation")
@WebAppConfiguration  
//@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)  
@Transactional  
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:spring.xml","classpath:spring-mvc.xml"}) // 要包括所有的spring配置文件哦~
public class ReadFromFile {
	private static final Logger logger = Logger.getLogger(ReadFromFile.class);
	
	@org.junit.Test
	public void readFromFile(){
		logger.info("info");
		/**以字节为单位读取文件，常用于读二进制文件，如图片、声音、影像等文件**/
		//以字节为单位读取文件内容，一次读一个字节
		//readFileByBytes("D:/wfile/test/ioreadtest.txt");//本地路径粘贴效果：D:\wfile\test
		//以字节为单位读取文件内容，一次读多个字节
		//readFileByBytesTwo("D:/wfile/test/ioreadtest.txt");
		/**以字符为单位读取文件，常用于读文本，数字等类型的文件**/
		//以字符为单位读取文件内容，一次读一个字节
		//readFileByChars("D:/wfile/test/ioreadtest.txt");
		//以字符为单位读取文件内容，一次读多个字节
		//readFileByCharsTwo("D:/wfile/test/ioreadtest.txt");
		/**以行为单位读取文件，常用于读面向行的格式化文件**/
		//以行为单位读取文件内容，一次读一整行
		//readFileByLines("D:/wfile/test/ioreadtest.txt");
		/**随机读取文件内容**/
		//随机读取一段文件内容
		//readFileByRandomAccess("D:/wfile/test/ioreadtest.txt");
		/**追加文件内容**/
		//使用RandomAccessFile
		//appendContent("D:/wfile/test/ioreadtest.txt","append content!");
		//readFileByLines("D:/wfile/test/ioreadtest.txt");
		//使用FileWriter
		appendContentTwo("D:/wfile/test/ioreadtest.txt","append contentTwo!");
		readFileByLines("D:/wfile/test/ioreadtest.txt");
	}
	
	//以字节为单位读取文件内容，一次读一个字节
	public void readFileByBytes(String fileName){
		File file = new File(fileName);
		InputStream in = null;
		try {
			// 一次读一个字节
			in = new FileInputStream(file);
			int tempbyte;
			while((tempbyte = in.read()) != -1){
				System.out.write(tempbyte);//write()方法是字符流 转换后输出
				//System.out.println(tempbyte);//println()方法是字节流 原样输出
			}
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}
	}
	
	//以字节为单位读取文件内容，一次读多个字节
	public void readFileByBytesTwo(String fileName){
		File file = new File(fileName);
		InputStream in = null;
		try {
			// 一次读多个字节
			byte[] tempbytes = new byte[100];
			in = new FileInputStream(file);
			ReadFromFile.showAvailableBytes(in);//600
			int len;
			// 读入多个字节到字节数组中，len为一次读入的字节数
			while((len = in.read(tempbytes)) != -1){
				//System.out.println(len); //100
				System.out.write(tempbytes, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(in != null){
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	
	//以字符为单位读取文件内容，一次读一个字节
	public void readFileByChars(String fileName){
		File file = new File(fileName);
		Reader reader = null;
		try {
			// 一次读一个字符
			reader = new InputStreamReader(new FileInputStream(file));
			int tempchar;
			while((tempchar = reader.read()) != -1){
				/* if (((char) tempchar) != '\n') {
	                    System.out.print((char) tempchar);
	              }*/
				 System.out.print((char) tempchar);
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//以字符为单位读取文件内容，一次读多个字节
	public void readFileByCharsTwo(String fileName){
		File file = new File(fileName);
		Reader reader = null;
		try {
			// 一次读多个字符
			char[] tempchars = new char[30];
			reader = new InputStreamReader(new FileInputStream(file));
			int len;
			while((len = reader.read(tempchars)) != -1){
				if(len == tempchars.length && tempchars[tempchars.length-1] != '\r'){
					System.out.print(tempchars);
				}else{
					for (int i = 0; i < len; i++) {
						if(tempchars[i] == '\r'){
							continue;
						}else{
							System.out.print(tempchars[i]);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			if(reader != null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	//以行为单位读取文件内容，一次读一整行
	public void readFileByLines(String fileName){
		File file = new File(fileName);
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			int line = 1;
			 // 一次读入一行，直到读入null为文件结束
			while((tempString = reader.readLine()) != null){
				// 显示行号
				System.out.println("line"+line+":"+tempString);
				line++;
			}
			reader.close();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
	}
	
	//随机读取一段文件内容
	public void readFileByRandomAccess(String fileName){
		File file = new File(fileName);
		RandomAccessFile randomFile = null;
		
		try {
			// 打开一个随机访问文件流，按只读方式
			randomFile = new RandomAccessFile(file, "r");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			//确定读文件的起始位置
			int beginIndex = (fileLength > 5) ? 5:0;
			//将读文件的开始位置移到beginIndex位置
			randomFile.seek(beginIndex);
			byte[] tempbytes = new byte[30];
			int len = 0;
			while((len= randomFile.read(tempbytes)) != -1){
				System.out.write(tempbytes,0,len);
			}
			randomFile.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//使用RandomAccessFile
	public void appendContent(String fileName,String content){
		try {
			// 打开一个随机访问文件流，按读写方式
			RandomAccessFile randomFile = new RandomAccessFile(fileName, "rw");
			// 文件长度，字节数
			long fileLength = randomFile.length();
			//将写文件指针移到文件尾
			randomFile.seek(fileLength);
			randomFile.writeBytes(content);
			randomFile.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	//使用FileWriter
	public void appendContentTwo(String fileName,String content){
		try {
			//打开一个写文件器，构造函数中的第二个参数true表示以追加形式写文件
			FileWriter writer = new FileWriter(fileName,true);
			writer.write(content);
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//显示输入流中还剩的字节数
    private static void showAvailableBytes(InputStream in) {
        try {
            System.out.println("当前字节输入流中的字节数为:" + in.available());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
