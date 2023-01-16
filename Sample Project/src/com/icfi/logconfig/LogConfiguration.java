package com.icfi.logconfig;

import com.appiancorp.suiteapi.expression.annotations.Category;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.content.*;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.Scanner;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;


@Category("logConfigCategory")
public class LogConfiguration {

	  @Function
	  public static  String runFunction(ContentService cs, @Parameter long arg) throws Exception{
	FileInputStream fis = null;
	WordExtractor w = null;
	try {
		  String filename = cs.getInternalFilename(arg);
		  //fs = new FileSystem(filename);
		 fis = new FileInputStream(filename);

		  

		  
		  
		  
		  
		  //POIDocument file   = new POIDocument(OPCPackage.open(fis));
		  //WordExtractor ext = new WordExtractor(fis);
		  
		
	      //Creating a Scanner object	  
	      //Scanner sc = new Scanner(fis);     
	      //StringBuffer sb = new StringBuffer();
	      //while(sc.hasNext()){
	      //   sb.append(sc.nextLine());
	      //}
	      //return(sb.toString());
	      // scanner ends
	      
	      
		  	  
		  //output
		  		//return new String(ext);
		  		//System.out.println(ext.getText());
		  
		  //gets bytes
		  		w = new WordExtractor(fis);
		  		byte[] b = new byte[1024];
		  		fis.read(b);
		  		return new String(b);
		  //bytes
	}
			  finally {
				  //we.close();
				  fis.close();
				  w.close();
		  }
			 
			  
		  }
}




