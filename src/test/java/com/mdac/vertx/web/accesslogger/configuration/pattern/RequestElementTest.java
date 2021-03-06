package com.mdac.vertx.web.accesslogger.configuration.pattern;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.mdac.vertx.web.accesslogger.util.VersionUtility;

import io.vertx.core.http.HttpVersion;

/**
 * 
 * Tests {@link RequestElement}
 * 
 * @author Roman Pierson
 *
 */
public class RequestElementTest {
	
	final HttpVersion httpVersion = HttpVersion.HTTP_1_1;
	final Map<String, Object> valuesWithQuery = getAllValues("uri-value", "method-value", "query-value", httpVersion); 
	final Map<String, Object> valuesWithoutQuery = getAllValues("uri-value", "method-value", null, httpVersion); 

	@Test(expected=IllegalArgumentException.class)
	public void testFindInRawPatternInvalidInputNull(){
	
		new RequestElement().findInRawPattern(null);
		
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFindInRawPatternInvalidInputEmpty(){
	
		new RequestElement().findInRawPattern("");
		
	}
	
	@Test
	public void testFindInRawPatternApacheFirstRequestLine(){
		
		final String expectedOutputWithQuery = "method-value uri-value?query-value " + VersionUtility.getFormattedValue(valuesWithQuery);
		final String expectedOutputWithoutQuery = "method-value uri-value " + VersionUtility.getFormattedValue(valuesWithQuery);
		
		ExtractedPosition ep = new RequestElement().findInRawPattern("%r");
		assertNotNull(ep);
		assertEquals(0, ep.getStart());
		assertEquals("%r".length(), ep.getOffset());
		
		// Test if the output of the looked up element is correct
		assertEquals(expectedOutputWithQuery, ep.getElement().getFormattedValue(valuesWithQuery));
		assertEquals(expectedOutputWithoutQuery, ep.getElement().getFormattedValue(valuesWithoutQuery));
	}
	
	@Test
	public void testFindInRawPatternURI(){
		
		final String expectedOutput = "uri-value";
		
		ExtractedPosition ep1 = new RequestElement().findInRawPattern("%U");
		assertNotNull(ep1);
		assertEquals(0, ep1.getStart());
		assertEquals("%U".length(), ep1.getOffset());
		
		// Test if the output of the looked up element is correct
		assertEquals(expectedOutput, ep1.getElement().getFormattedValue(valuesWithQuery));
		
		ExtractedPosition ep2 = new RequestElement().findInRawPattern("cs-uri-stem");
		assertNotNull(ep2);
		assertEquals(0, ep2.getStart());
		assertEquals("cs-uri-stem".length(), ep2.getOffset());
		
		// Test if the output of the looked up element is correct
		assertEquals(expectedOutput, ep2.getElement().getFormattedValue(valuesWithQuery));
		
	}
	
	private Map<String, Object> getAllValues(final String uriValue, final String methodValue, final String queryValue, final HttpVersion versionValue){
		
		Map<String, Object> values = new HashMap<>();
		
		values.put("uri", uriValue);
		values.put("version", versionValue);
		values.put("query", queryValue);
		values.put("method", methodValue);
		
		return values;
	}
}
