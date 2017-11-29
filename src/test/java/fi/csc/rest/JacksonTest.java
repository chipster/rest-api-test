package fi.csc.rest;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import fi.csc.rest.model.Thing;

/**
 * Test Java8 Instance json serialization with Jackson
 *
 */
public class JacksonTest {

	private ObjectMapper objectMapper;

	@Before
	public void setUpBeforeClass() throws Exception {
		this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule())
				.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
	}

	@Test
	public void test() throws JsonGenerationException, JsonMappingException, IOException {
		Thing thing = new Thing("pontus");
		String json = objectMapper.writeValueAsString(thing);
		Thing newThing = objectMapper.readValue(json, Thing.class);

		System.out.println("original: " + thing);
		System.out.println("json: " + json);
		System.out.println("new: " + newThing);
		Assert.assertEquals(thing.toString(), newThing.toString());
	}
}
