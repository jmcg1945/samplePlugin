package com.icfi.logconfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;

import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


import org.apache.commons.compress.utils.IOUtils;


import com.appiancorp.suiteapi.content.ContentConstants;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.expression.annotations.Category;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
//import com.appiancorp.suiteapi.knowledge.Document;






@Category("logConfigCategory")
public class LogConfiguration {
	
	//this LOGGER will print debug code to  "appian.log" path: C:\Appian223\Appian\tomcat\apache-tomcat\bin  
	private static final Logger LOGGER = Logger.getLogger(LogConfiguration.class.getName());
	
	
	//enter Document Id of Document uploaded into Appian
	//function will return Document XML of the file
	  @Function
	  public static  String runFunction(ContentService cs, @Parameter Long arg) throws Exception {
		
		  
		 
			  String filename = cs.getInternalFilename(arg);
			  String line = "";
			  StringBuilder sb = new StringBuilder();
			  File inputDirectory = new File(filename);
			  ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(inputDirectory));
			  
			  
			  ZipEntry zipEntry;
			  
			  //gets xml files in zipped up docx file
			  while((zipEntry = zipInputStream.getNextEntry()) != null) {
				
				  //returns the file in the zipped directory that contains the document details
				  if(zipEntry.getName().equalsIgnoreCase("Word/document.xml")) {
					  LOGGER.info("Found word/document.xml");
					  com.appiancorp.suiteapi.knowledge.Document xmlDoc = new com.appiancorp.suiteapi.knowledge.Document(1225L, "myXMLDocument", "xml");
					  Long docId = cs.create(xmlDoc, ContentConstants.UNIQUE_NONE);
					  String fosFileName = cs.getInternalFilename(docId);					 
					  LOGGER.info("Filename is " + fosFileName);
					  FileOutputStream fos = new FileOutputStream(fosFileName);
					  IOUtils.copy(zipInputStream, fos);
					  fos.close();
					  
					  File readXML = new File(cs.getInternalFilename(docId));
					  BufferedReader reader = new BufferedReader(new FileReader(readXML));
					  
					   line = reader.readLine();
					
					  LOGGER.info("Line value is start of while:" + line);
					  while (line != null) {
						  LOGGER.info("Start of while loop");
						  LOGGER.info("Line value is:" + line);
						 
						  
						  //broken
						  //if (line.contains("<w:t>")) is not reading <w:t> tags contained in the long string in variable "line"
					    if (line.contains("<w:t>")) {
					      int startIndex = line.indexOf("<w:t>") + "<w:t>".length();
					      int endIndex = line.indexOf("</w:t>");
					      String value = line.substring(startIndex, endIndex);
					      LOGGER.info("Value: " + value);
					      LOGGER.info(":" + line);
					    }
					    //broken
					    
					    line = reader.readLine();
					    sb.append(line);
					    LOGGER.info("Line value end of while loop is" + line);
					    LOGGER.info("End of Loop");
					    
					  }
					 
					  reader.close();
					  
		  } 
		  
	  
	  }
			 return sb.toString();
			
	  }
}


