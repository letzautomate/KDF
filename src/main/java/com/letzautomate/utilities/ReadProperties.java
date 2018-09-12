package com.letzautomate.utilities;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.ResourceBundle;

public class ReadProperties {
	static Properties properties;
	public static Properties getProperties() throws IOException {	
		properties = new Properties();
		BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream("C:/KDF/src/test/resources/config/config.properties"));
		properties.load(bufferedInputStream);
		bufferedInputStream.close();
		return properties;
	}

}
