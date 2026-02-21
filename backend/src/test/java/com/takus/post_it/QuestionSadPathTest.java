package com.takus.post_it;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.takus.post_it.model.dto.question.QuestionRequestDto;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource(properties = {
	"spring.datasource.driver-class-name=org.h2.Driver",
	"spring.datasource.url=jdbc:h2:mem:sad_testdb;DB_CLOSE_DELAY=-1;MODE=PostgreSQL",
	"spring.datasource.username=sa",
	"spring.datasource.password=",
	"spring.jpa.database-platform=org.hibernate.dialect.H2Dialect",
	"spring.jpa.hibernate.ddl-auto=create-drop"
})
public class QuestionSadPathTest {
	
	@Autowired private MockMvc mockMvc;
	@Autowired private ObjectMapper objectMapper;
	
	@Test
	@DisplayName("SAD PATH: Validation - Contenu vide lors de la création")
	void shouldFailWhenContentIsInvalid() throws Exception {
		// On simule un DTO qui échouerait à ta validation (saveUpdate)
		QuestionRequestDto invalidRequest = new QuestionRequestDto("");
		
		mockMvc.perform(post("/question/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(invalidRequest)))
			.andExpect(status().isBadRequest());
		// Ton contrôleur catch IllegalArgumentException -> 400
	}
	
	@Test
	@DisplayName("SAD PATH: Recherche - ID inexistant")
	void shouldReturn404WhenQuestionNotFound() throws Exception {
		Long nonExistentId = 999L;
		
		mockMvc.perform(get("/question/get/id/" + nonExistentId))
			.andExpect(status().isNotFound());
		// Ton contrôleur catch NoSuchElementException -> 404
	}
	
	@Test
	@DisplayName("SAD PATH: Update - ID inexistant")
	void shouldReturn400WhenUpdatingNonExistentQuestion() throws Exception {
		Long nonExistentId = 999L;
		QuestionRequestDto dto = new QuestionRequestDto("New Content");
		
		mockMvc.perform(put("/question/update/" + nonExistentId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto)))
			.andExpect(status().isBadRequest());
		// Ton service jette IllegalArgumentException si findById est vide lors de l'update
	}
	
	@Test
	@DisplayName("SAD PATH: Delete - ID inexistant")
	void shouldReturn404WhenDeletingNonExistentQuestion() throws Exception {
		Long nonExistentId = 999L;
		
		mockMvc.perform(delete("/question/delete/" + nonExistentId))
			.andExpect(status().isNotFound());
		// Ton contrôleur catch NoSuchElementException -> 404
	}
}