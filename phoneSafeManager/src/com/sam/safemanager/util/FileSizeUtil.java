/**
 * 
 */
package com.sam.safemanager.util;

import java.io.File;

/**
 * @author Sam
 * @date 2013-5-7
 * @weibo:ÂëÅ©Ã÷Ã÷É£
 */
public class FileSizeUtil {

	 
	public static long getFileSize(File f) throws Exception
		 {
		  long size = 0;
		  File flist[] = f.listFiles();
		  for (int i = 0; i < flist.length; i++)
		  {
		   if (flist[i].isDirectory())
		   {
		    size = size + getFileSize(flist[i]);
		   }
		   else
		   {
		    size = size + flist[i].length();
		   }
		  }
		  return size;
		 }


}
