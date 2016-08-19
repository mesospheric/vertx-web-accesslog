package com.mdac.vertx.web.accesslogger.configuration.pattern;

import java.util.Map;

public class StatusElement implements AccessLogElement{

	
	
	@Override
	public ExtractedPosition findInRawPattern(final String rawPattern, final int start) {
		
		if(true){
			return null;
		}
		
		String pattern1 = "cs-uri";
		
		int index = rawPattern.indexOf(pattern1);
		
		if(index >= 0){
			return new ExtractedPosition(0, pattern1.length());
		}
		
		String pattern2 = "%r";
		
		index = rawPattern.indexOf(pattern2);
		
		if(index >= 0){
			return new ExtractedPosition(0, pattern2.length());
		}
		
		return null;
	}

	@Override
	public String getFormattedValue(final Map<String, Object> values) {
		
		return "" + (int) values.get("status");
		
	}

}