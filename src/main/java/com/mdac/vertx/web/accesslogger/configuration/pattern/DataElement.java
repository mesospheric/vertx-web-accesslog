package com.mdac.vertx.web.accesslogger.configuration.pattern;

import io.vertx.ext.web.Cookie;

import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class DataElement implements AccessLogElement{

	private final String identifier;

	public DataElement() {
		this.identifier = null;
	}

	private DataElement(final String identifier) {
		this.identifier = identifier;
	}

	@Override
	public ExtractedPosition findInRawPatternInternal(final String rawPattern) {
		
		final int index = rawPattern.indexOf("%{");
		
		if(index >= 0){
				
				int indexEndConfiguration = rawPattern.indexOf("}d");
			
				if(indexEndConfiguration > index)
				{
					String configurationString = rawPattern.substring(index + 2, indexEndConfiguration);
					
					return new ExtractedPosition(index, configurationString.length() + 4, new DataElement(configurationString));
				}
			
		}
		
		
		return null;
	}
	
	@Override
	public String getFormattedValue(final Map<String, Object> values) {
		
		if(!values.containsKey("data")){
			return "-";
		}
		
		final Map<String, Object> data = (Map<String, Object>) values.get("data");

		final String dataPiece = data.getOrDefault(this.identifier, "-").toString();

		return dataPiece;
		
	}

}
