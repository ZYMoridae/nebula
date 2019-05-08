package com.jz.nebula.hateoas.event;

import static org.junit.Assert.assertEquals;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.util.UriComponentsBuilder;

public class PaginatedResultsRetrievedEventTest {
	@Test
	public void createPaginatedResultsRetrievedEventTest() {
		Class<String> parameterClazz = String.class;
		UriComponentsBuilder parameterBuilder = UriComponentsBuilder.newInstance();
		HttpServletResponse parameterResponse = new MockHttpServletResponse();
		int parameterPageToSet = 1;
		int parameterTotalPagesToSet = 1;
		int parameterPageSizeToSet = 1;

		PaginatedResultsRetrievedEvent<String> event = new PaginatedResultsRetrievedEvent<String>(parameterClazz, parameterBuilder,
				parameterResponse, parameterPageToSet, parameterTotalPagesToSet, parameterPageSizeToSet);

		assertEquals(parameterClazz, event.getClazz());
		assertEquals(parameterBuilder, event.getUriBuilder());
		assertEquals(parameterResponse, event.getResponse());
		assertEquals(parameterPageToSet, event.getPage());
		assertEquals(parameterTotalPagesToSet, event.getTotalPages());
		assertEquals(parameterPageSizeToSet, event.getPageSize());
	}
}
