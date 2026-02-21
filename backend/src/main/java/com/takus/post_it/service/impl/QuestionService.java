package com.takus.post_it.service.impl;

import com.takus.post_it.model.QuestionEntity;
import com.takus.post_it.model.dto.question.QuestionRequestDto;
import com.takus.post_it.model.dto.question.QuestionResponseDto;
import com.takus.post_it.model.dto.question.QuestionUpdateDto;
import com.takus.post_it.repository.QuestionRepository;
import com.takus.post_it.util.dtoMapper.QuestionMapper;
import com.takus.post_it.validation.QuestionValidation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@AllArgsConstructor
public class QuestionService implements com.takus.post_it.service.QuestionService {
	private final QuestionRepository repository;
	private final QuestionMapper mapper;
	private final QuestionValidation validation;
	
//	-----------------------------------------------------------------------------------------------------------------------------
	@Override
	public QuestionResponseDto save(QuestionRequestDto dto) {
		List<String> errorMsg = validation.saveUpdate(dto);
		if (!errorMsg.isEmpty()){
			throw new IllegalArgumentException(String.join(", -", errorMsg));
		}
		
		return mapper.toDto(repository.save(mapper.toEntity(dto)));
	}
	
	@Override
	public QuestionResponseDto findById(Long id) {
		QuestionEntity entity = repository.findById(id).orElseThrow(()->new NoSuchElementException("No Question found at the id: "+ id));
		
		return mapper.toDto(entity);
	}
	
	@Override
	public List<QuestionResponseDto> findAll() {
		return mapper.toDtoList(repository.findAll());
	}
	
	@Override
	public QuestionResponseDto update(Long id, QuestionUpdateDto dto) {
		QuestionEntity entity = repository.findById(id).orElseThrow(()->new IllegalArgumentException("No Question found at the id: "+ id));
		
		entity.setContent(dto.content());
		entity.setAnswered(dto.answered());
		
		return mapper.toDto(repository.save(entity));
	}
	
	@Override
	public QuestionResponseDto answer(Long id) {
		QuestionEntity entity = repository.findById(id).orElseThrow(()->new NoSuchElementException("No Question found at the id: "+ id));
		
		entity.setAnswered(!entity.isAnswered());
		
		return mapper.toDto(repository.save(entity));
	}
	
	@Override
	public boolean delete(Long id) {
		QuestionEntity entity = repository.findById(id).orElseThrow(()->new NoSuchElementException("No Question found at the id: "+ id));
		
		repository.delete(entity);
		
		return true;
	}
}
