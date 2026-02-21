package com.takus.post_it.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "questions")
@Builder
@AllArgsConstructor  @NoArgsConstructor
@Getter @Setter
public class QuestionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "content", nullable = false)
	private String content;
	
	@Column(name = "status", nullable = false)
	private boolean answered;
}
