package com.takus.post_it.service;

import com.takus.post_it.model.dto.question.QuestionRequestDto;
import com.takus.post_it.model.dto.question.QuestionResponseDto;
import com.takus.post_it.model.dto.question.QuestionUpdateDto;

import java.util.List;

public interface QuestionService {

	QuestionResponseDto save(QuestionRequestDto dto);
	
	QuestionResponseDto findById(Long id);
	
	List<QuestionResponseDto> findAll();
	
	QuestionResponseDto update(Long id, QuestionUpdateDto dto);
	
	QuestionResponseDto answer(Long id);
	
	boolean delete(Long id);
	
}
