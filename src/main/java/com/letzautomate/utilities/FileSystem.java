package com.letzautomate.utilities;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

public class FileSystem {
	
	public void copyFile(String source, String destination) throws IOException {
		FileUtils.copyFile(new File(source), new File(destination));
	}
	
	public void writeResults(String path, String line) throws IOException {
		File file = new File(path);
		/*if(file.exists()) {
			file.delete();
			//file.createNewFile();
		}*/
		file.createNewFile();
		FileUtils.write(new File(path), line +"\n", true );
		
	}

}
