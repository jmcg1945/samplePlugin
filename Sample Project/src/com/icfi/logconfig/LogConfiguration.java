package com.icfi.logconfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.compress.utils.IOUtils;
import org.w3c.dom.NodeList;


import com.appiancorp.suiteapi.content.ContentConstants;
import com.appiancorp.suiteapi.content.ContentService;
import com.appiancorp.suiteapi.expression.annotations.Category;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.knowledge.Document;





@Category("logConfigCategory")
public class LogConfiguration {

	private static final Logger LOGGER = Logger.getLogger(LogConfiguration.class.getName());
	
	  @Function
	  public static  String runFunction(ContentService cs, @Parameter Long arg) throws Exception {
		  FileInputStream fis = null;
		  int len;
		  
		  
			  byte[] buffer = new byte[1024];
			  StringBuffer sb = new StringBuffer();
			  String filename = cs.getInternalFilename(arg);
			  
			  File inputDirectory = new File(filename);
			  ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(inputDirectory));
			  int numberOfFiles = 0;
			  
			  ZipEntry zipEntry;
			  
			  
			  while((zipEntry = zipInputStream.getNextEntry()) != null) {
				  numberOfFiles++;
				  
				  if(zipEntry.getName().equalsIgnoreCase("Word/document.xml")) {
					  LOGGER.info("Found word/document.xml");
					  Document xmlDoc = new Document(1225L, "myXMLDocument", "xml");
					  Long docId = cs.create(xmlDoc, ContentConstants.UNIQUE_NONE);
					  String fosFileName = cs.getInternalFilename(docId);					 
					  LOGGER.info("Filename is " + fosFileName);
					  FileOutputStream fos = new FileOutputStream(fosFileName);
					  IOUtils.copy(zipInputStream, fos);
					  fos.close();
					  File readXML = new File(cs.getInternalFilename(docId));
					  DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
					  DocumentBuilder builder = factory.newDocumentBuilder();	
					  
					// Load the input XML document, parse it and return an instance of the
				        // Document class.
				        //org.w3c.dom.Document document = builder.parse(readXML);
				        //document.getDocumentElement().normalize();
				       // NodeList nodeList = document.getElementsByTagName("w:t");
				        
				       // for (int itr = 0; itr < nodeList.getLength(); itr++) {
				       //     LOGGER.info(nodeList.item(itr).getNodeValue());				            
				       //     if (nodeList.item(itr).hasChildNodes()) {
				       //       NodeList nodeList2 = nodeList.item(itr).getChildNodes();
				       //       for (int itr2 = 0; itr2 < nodeList2.getLength(); itr2++) {
				       //         LOGGER.info(" Child node: " + nodeList2.item(itr2).getNodeName() + " value: " + nodeList2.item(itr2).getNodeValue());
				        
				 // }
			  	//}
			 // }
		 
				     //   LOGGER.info("Length of nodes was: " + nodeList.getLength());
	
	    }
	  zipInputStream.closeEntry();
	}
			return filename;
			  
		  } 
		  
	  
	  }

			

//



