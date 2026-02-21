package com.takus.post_it.validation;

import com.takus.post_it.model.dto.question.QuestionRequestDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class QuestionValidation {
	
//	------------------------------------------------------------------------------------------------------------
	public List<String >saveUpdate(QuestionRequestDto dto){
		List<String> errors = new ArrayList<>();
		
		if (dto.content().isBlank()){
			errors.add("Enter a right message content.");
		}
		
		return errors;
	}
}
