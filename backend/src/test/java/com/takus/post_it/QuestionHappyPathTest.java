package com.takus.post_it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.takus.post_it.model.dto.question.QuestionRequestDto;
import com.takus.post_it.repository.QuestionRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(properties = {
	"spring.datasource.driver-class-name=org.h2.Driver",
	"spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
	"spring.datasource.username=sa",
	"spring.datasource.password=",
	"spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
	"spring.jpa.hibernate.ddl-auto=create-drop"
})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class QuestionHappyPathTest {
	
	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	@Autowired private QuestionRepository questionRepository;
	
	private Long questionId;
	
	@BeforeAll
	void init() {
		questionRepository.deleteAll();
	}
	
	@Test
	@Order(1)
	@DisplayName("1. CREATE - Devrait créer une question avec succès")
	void shouldCreateQuestion() throws Exception {
		QuestionRequestDto request = new QuestionRequestDto("C'est quoi Spring Boot ?");
		
		MvcResult result = mockMvc.perform(post("/question/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.id", notNullValue()))
			.andExpect(jsonPath("$.content", is("C'est quoi Spring Boot ?")))
			.andExpect(jsonPath("$.answered", is(false)))
			.andReturn();
		
		// On récupère l'ID pour les étapes suivantes
		String response = result.getResponse().getContentAsString();
		questionId = Long.valueOf(com.jayway.jsonpath.JsonPath.read(response, "$.id").toString());
	}
	
	@Test
	@Order(2)
	@DisplayName("2. READ - Devrait récupérer la question créée")
	void shouldFindQuestionById() throws Exception {
		mockMvc.perform(get("/question/get/id/" + questionId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.id", is(questionId.intValue())))
			.andExpect(jsonPath("$.content", is("C'est quoi Spring Boot ?")));
	}
	
	@Test
	@Order(3)
	@DisplayName("3. UPDATE - Devrait modifier le contenu de la question")
	void shouldUpdateQuestion() throws Exception {
		QuestionRequestDto updateRequest = new QuestionRequestDto("Contenu modifié");
		
		mockMvc.perform(put("/question/update/" + questionId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(updateRequest)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content", is("Contenu modifié")));
	}
	
	@Test
	@Order(4)
	@DisplayName("4. ANSWER - Devrait marquer la question comme répondue")
	void shouldToggleAnswerStatus() throws Exception {
		mockMvc.perform(put("/question/answer/" + questionId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.answered", is(true)));
	}
	
	@Test
	@Order(5)
	@DisplayName("5. FIND ALL - Devrait lister toutes les questions")
	void shouldFindAllQuestions() throws Exception {
		mockMvc.perform(get("/question/get/all"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)));
	}
	
	@Test
	@Order(6)
	@DisplayName("6. DELETE - Devrait supprimer la question")
	void shouldDeleteQuestion() throws Exception {
		mockMvc.perform(delete("/question/delete/" + questionId))
			.andExpect(status().isNoContent());
		
		// Vérification pragmatique : elle ne doit plus exister
		mockMvc.perform(get("/question/get/id/" + questionId))
			.andExpect(status().isNotFound());
	}
}
