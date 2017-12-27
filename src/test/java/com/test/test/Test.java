package com.test.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import util.admin.Logger;

import com.main.common.AddressConfig;
import com.main.common.GlobalConfig;
import com.main.common.PropertyConfig;

@SuppressWarnings("deprecation")
@WebAppConfiguration  
//@TransactionConfiguration(transactionManager = "txManager", defaultRollback = false)  
@Transactional  
@RunWith(SpringJUnit4ClassRunner.class)  
@ContextConfiguration(locations = {"classpath:spring.xml","classpath:spring-mvc.xml","classpath:spring-profile.xml"}) // 要包括所有的spring配置文件哦~
//@ActiveProfiles("test")  
public class Test {
	private static final Logger logger = Logger.getLogger(Test.class);
	
	//@Profile("test")
	@org.junit.Test
	public void testa(){
		logger.info("aaaaaaaaa");
		String string = PropertyConfig.getProperty(PropertyConfig.UPLOADFILE_MAXSIZE);
		String string2 = GlobalConfig.getConfig(PropertyConfig.UPLOADFILE_MAXSIZE);
		String string3 = AddressConfig.getConfig(PropertyConfig.UPLOADFILE_MAXSIZE);
		logger.info(string+"======"+string2+"======"+string3);
	}
	@org.junit.Test
	public void testb(){
		List l = new ArrayList();
		 l.add("aa");
		 l.add("aa");
		 l.add("aa");
		/* for (Iterator iter = l.iterator(); iter.hasNext();) {
		  String str = (String)iter.next();
		  System.out.println(str);
		 }*/
		 //迭代器用于while循环
		 Iterator iter = l.iterator();
		 while(iter.hasNext()){
		  String str = (String) iter.next();
		  System.out.println(str);
		 }
		 
	}
}
