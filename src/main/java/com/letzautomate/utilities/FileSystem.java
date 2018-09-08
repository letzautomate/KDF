package com.letzautomate.utilities;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class FileSystem {
	
	public void copyFile(String source, String destination) throws IOException {
		FileUtils.copyFile(new File(source), new File(destination));
	}

}
