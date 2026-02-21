package com.takus.post_it.controller;

import com.takus.post_it.model.dto.question.QuestionRequestDto;
import com.takus.post_it.model.dto.question.QuestionResponseDto;
import com.takus.post_it.model.dto.question.QuestionUpdateDto;
import com.takus.post_it.service.QuestionService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.util.MimeTypeUtils.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/question")
@AllArgsConstructor
public class QuestionController {
	private final QuestionService service;
	
//	-------------------------------------------------------------------------------------
	@PostMapping(path = "/create", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<?> save(@RequestBody QuestionRequestDto dto){
		try {
			QuestionResponseDto responseDto = service.save(dto);
			
			return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
		}catch (IllegalArgumentException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(path = "/get/id/{id}", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findById(@PathVariable Long id){
		try {
			QuestionResponseDto responseDto = service.findById(id);
			
			return new ResponseEntity<>(responseDto, HttpStatus.OK);
		}catch (NoSuchElementException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping(path = "/get/all", produces = APPLICATION_JSON_VALUE)
	public ResponseEntity<?> findAll(){
		List<QuestionResponseDto> list = service.findAll();
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@PutMapping(path = "/update/{id}", consumes = APPLICATION_JSON_VALUE)
	public ResponseEntity<?> update(@PathVariable Long id, @RequestBody QuestionUpdateDto dto){
		try {
			QuestionResponseDto responseDto = service.update(id, dto);
			
			return new ResponseEntity<>(responseDto, HttpStatus.OK);
		}catch (IllegalArgumentException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
	@PutMapping(path = "/answer/{id}")
	public ResponseEntity<?>answer(@PathVariable Long id){
		try {
			QuestionResponseDto responseDto = service.answer(id);
			
			return new ResponseEntity<>(responseDto, HttpStatus.OK);
		}catch (Exception e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping(path = "/delete/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id){
		try {
			boolean deleted = service.delete(id);
			
			return new ResponseEntity<>(deleted, HttpStatus.NO_CONTENT);
		}catch (NoSuchElementException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
	
}
