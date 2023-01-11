package com.icfi.logconfig;

import com.appiancorp.suiteapi.expression.annotations.Category;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.content.*;


import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;


@Category("logConfigCategory")
public class LogConfiguration {

  @Function
  public static  String runFunction(ContentService cs, @Parameter long arg) throws Exception{
  
  
	  FileInputStream inputStream = null;
	  
	  try {
	      // Open the Word document
		  String filename = cs.getInternalFilename(arg);
	      inputStream = new FileInputStream(filename);
	      XWPFDocument doc = new XWPFDocument(inputStream);

	      // Extract the text
	      XWPFWordExtractor ex = new XWPFWordExtractor(doc);
	      String text = ex.getText();
	      //System.out.println(text);
	      return text;
	     
	  } catch (IOException e) {
	      // Handle the exception
	  } 
	  finally {
	      if (inputStream != null) 
	   
	      {
	          try {
	              inputStream.close();
	          } catch (IOException e) {
	              // Handle the exception
	          }
	         
	      }
	  
	  }
	return text();
	  
  } 
}






