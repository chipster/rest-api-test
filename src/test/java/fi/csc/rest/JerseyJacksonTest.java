package fi.csc.rest;

import java.net.URI;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

import fi.csc.rest.model.Thing;
import fi.csc.rest.resource.ThingResource;

/**
 * Test Java8 Instance json serialization with Jackson and Jersey
 *
 */
public class JerseyJacksonTest {

	private HttpServer server;

	@Test
	public void test() {
		ResourceConfig rc = new ResourceConfig().register(JacksonJaxbJsonProvider.class)
				.register(CustomObjectMapperProvider.class);

		Thing thing = new Thing("pontus");
		rc.register(new ThingResource(thing));

		this.server = GrizzlyHttpServerFactory.createHttpServer(URI.create("http://localhost:8080"), rc);

		String json = ClientBuilder.newClient().register(JacksonJaxbJsonProvider.class)
				.register(CustomObjectMapperProvider.class).target("http://localhost:8080")
				.request(MediaType.APPLICATION_JSON_TYPE).get(String.class);

		Thing responseThing = ClientBuilder.newClient().register(JacksonJaxbJsonProvider.class)
				.register(CustomObjectMapperProvider.class).target("http://localhost:8080")
				.request(MediaType.APPLICATION_JSON_TYPE).get(Thing.class);

		System.out.println("original: " + thing);
		System.out.println("json: " + json);
		System.out.println("response: " + responseThing);
		Assert.assertEquals(thing.toString(), responseThing.toString());
	}

	@Provider
	public static class CustomObjectMapperProvider implements ContextResolver<ObjectMapper> {

		@Override
		public ObjectMapper getContext(Class<?> type) {
			return new ObjectMapper().registerModule(new JavaTimeModule())
					.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
		}
	}

	@After
	public void tearDownAfter() throws InterruptedException {
		server.shutdown();
	}
}
