package com.icfi.logconfig;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.commons.compress.utils.IOUtils;




//import org.w3c.dom.*;
import org.w3c.dom.NodeList;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import com.appiancorp.suiteapi.content.ContentConstants;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.expression.annotations.Category;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
//import com.appiancorp.suiteapi.knowledge.Document;






@Category("logConfigCategory")
public class LogConfiguration {
	
	
	private static final Logger LOGGER = Logger.getLogger(LogConfiguration.class.getName());
	
	  @Function
	  public static  String runFunction(ContentService cs, @Parameter Long arg) throws Exception {
		  FileInputStream fis = null;
		  int len;
		  
		  

			  String filename = cs.getInternalFilename(arg);
			  
			  File inputDirectory = new File(filename);
			  ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(inputDirectory));
			  int numberOfFiles = 0;
			  
			  ZipEntry zipEntry;
			  
			  
			  while((zipEntry = zipInputStream.getNextEntry()) != null) {
				  numberOfFiles++;
				  
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

					  String line = reader.readLine();
					  StringBuilder sb = new StringBuilder();
					 
					  while (line != null) {
						  LOGGER.info("Start of while loop");
					    if (line.contains("<w:t>")) {
					      int startIndex = line.indexOf("<w:t>") + "<w:t>".length();
					      int endIndex = line.indexOf("</w:t>");
					      String value = line.substring(startIndex, endIndex);
					      LOGGER.info("Value: " + value);
					      LOGGER.info("Line value is:" + line);
					    }
					    line = reader.readLine();
					    LOGGER.info("End of Loop");
					  }
					 
					  reader.close();
					  
		  } 
		  
	  
	  }
			return filename;
			
	  }
}


