package net.sunnycore.intercontent.config;

import java.util.ResourceBundle;

import org.apache.log4j.Logger;

public enum Configuration {
	INSTANCE;
	
	public enum EnviromentType{
		DEV,PROD;
		
		public boolean isDev(){
			return this.equals(DEV);
		}
	}
	
	public EnviromentType envType = EnviromentType.DEV;
	
	private transient Logger LOGGER = Logger.getLogger(Configuration.class);
	
	private Configuration() {
		ResourceBundle bundle = ResourceBundle.getBundle("application");
		try {
			String envTypeS = bundle.getString("enviroment.type");
			envType = EnviromentType.valueOf(envTypeS);
		} catch (Exception e) {
			LOGGER.error("Failed to load enviroment type configuration, using DEV type: "+e.getMessage(), e);
		}
	}
}
