package com.takus.post_it.util.dtoMapper;

import com.takus.post_it.model.QuestionEntity;
import com.takus.post_it.model.dto.question.QuestionRequestDto;
import com.takus.post_it.model.dto.question.QuestionResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class QuestionMapper {

	public abstract QuestionResponseDto toDto(QuestionEntity entity);
	
	public abstract QuestionEntity toEntity(QuestionRequestDto dto);
	
	public abstract List<QuestionResponseDto> toDtoList(List<QuestionEntity> list);
	
}
