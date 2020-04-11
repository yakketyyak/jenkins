package ci.pabeu.test;


import static junit.framework.TestCase.assertTrue;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ci.pabeu.test.repository.TypeOfAccountRepository;
import ci.pabeu.test.rest.AccountController;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ContextConfiguration
@WebAppConfiguration
@EnableWebMvc
public class RestControllerTests {

	@Autowired
	private MockMvc mvc;

	@Mock
	private TypeOfAccountRepository typeOfAccountRepository;

	@InjectMocks
	private AccountController accountController;

	@Autowired
	private WebApplicationContext ctx;

	@Before
	public void setUp() {
		this.mvc = MockMvcBuilders.webAppContextSetup(ctx).build();
	}

	@After
	public void setDown() {
		System.out.println("After all tests");

	}

	@Test
	@DisplayName("Check repository")
	public void contexLoads() throws Exception {
		assertThat(typeOfAccountRepository).isNotNull();
	}

	@Test
	@DisplayName("Check controller")
	public void contexLoadsOne() throws Exception {
		assertThat(accountController).isNotNull();
	}

	@Test
	@DisplayName("Method /account/type/get")
	public void getTypes() throws Exception {
		// Given
		final int expectedSize = 4;
		System.out.println("expectedSize " + expectedSize);

		// When
		MvcResult mvcResult = mvc.perform(
				get("/account/type/get")
				.accept(MimeTypeUtils.APPLICATION_JSON_VALUE))
				.andExpect(status().isOk())
				// .andDo(print())
				.andReturn();

		// Then
		ObjectMapper objectMapper = new ObjectMapper();
		JsonNode node = objectMapper.readTree(mvcResult.getResponse().getContentAsString());
		assertNotNull(node);
		assertTrue(node.isArray());
		assertEquals(expectedSize, node.size());
	}

}
