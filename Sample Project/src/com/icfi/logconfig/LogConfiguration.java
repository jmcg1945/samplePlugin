package com.icfi.logconfig;

import com.appiancorp.suiteapi.expression.annotations.Category;
import com.appiancorp.suiteapi.expression.annotations.Function;
import com.appiancorp.suiteapi.expression.annotations.Parameter;
import com.appiancorp.suiteapi.content.*;
import com.appiancorp.suiteapi.content.exceptions.InvalidContentException;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


@Category("logConfigCategory")
public class LogConfiguration {

	  @Function
	  public static  String runFunction(ContentService cs, @Parameter Long arg) throws IOException, InvalidContentException {
		  	String filename = cs.getInternalFilename(arg);
	        byte[] buffer = new byte[1024];
	        FileInputStream fis = new FileInputStream(filename);
	        ZipInputStream zis = new ZipInputStream(new BufferedInputStream(fis));
	        ZipEntry ze;
	        String xmlContent = "";
	        
	        while ((ze = zis.getNextEntry()) != null) {
	            File newFile = new File(filename.substring(0, filename.lastIndexOf(".")), ze.getName());
	            if (ze.isDirectory()) {
	                newFile.mkdirs();
	                continue;
	            } else {
	                newFile.getParentFile().mkdirs();
	            }
	            FileOutputStream fos = new FileOutputStream(newFile);
	            int len;
	            while ((len = zis.read(buffer)) > 0) {
	                fos.write(buffer, 0, len);
	            }
	            fos.close();
	            if(ze.getName().equals(filename)){
	              InputStream is = new FileInputStream(newFile);
	              ByteArrayOutputStream result = new ByteArrayOutputStream();
	              byte[] buffer1 = new byte[1024];
	              int length;
	              while ((length = is.read(buffer1)) != -1) {
	                  result.write(buffer1, 0, length);
	              }
	              xmlContent = result.toString("UTF-8");
	              is.close();
	            }
	        }
	        zis.close();
	        fis.close();
	        return xmlContent;
	    }
	}





