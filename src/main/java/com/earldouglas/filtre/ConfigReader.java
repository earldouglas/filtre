package com.earldouglas.filtre;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ConfigReader {

	private String compositeAllowList;
	private String compositeDenyList;
	private Log log = LogFactory.getLog(getClass());

	public String getCompositeAllowList() {
		return compositeAllowList;
	}

	public String getCompositeDenyList() {
		return compositeDenyList;
	}

	public ConfigReader(String configLocation) {
		try {
			InputStream configInputStream;
			if (configLocation.startsWith("classpath:")) {
				String configFileName = configLocation.substring(10);
				configInputStream = ClassLoader.getSystemClassLoader()
						.getResourceAsStream(configFileName);
			} else {
				configInputStream = new FileInputStream(configLocation);
			}

			Properties properties = new Properties();
			properties.load(configInputStream);

			compositeAllowList = properties.getProperty("allowList");
			compositeDenyList = properties.getProperty("denyList");
		} catch (Exception e) {
			log.error("reading filtre config file '" + configLocation + "'", e);
		}

	}
}
