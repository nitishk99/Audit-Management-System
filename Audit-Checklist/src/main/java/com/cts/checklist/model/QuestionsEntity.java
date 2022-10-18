package com.cts.checklist.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**

 * @version 1.8
 * This class is an entity which we will be storing in the database.
 *  
 */

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="AuditQuestions")
@Schema(description = "Model class for Checklist Questions")
public class QuestionsEntity {
	
	/**
	 * This will be the question id for the questions 
	 */
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="questionId")
	@Schema(description = "Id of the question")
	private Integer questionId;
	/**
	 * This will be the auditType for the questions 
	 */
	@Column(name="auditType")
	@Schema(description = "Audit type of the question")
	private String auditType;
	/**
	 * This will be the questions 
	 */
	@Column(name="questions")
	@Schema(description = "Actual question")
	private String question;
	/**
	 * This will be the response for the question
	 */
	@Column(name="response")
	@Schema(description = "Resoponse for the question")
	private String response;
	
	public QuestionsEntity(String string1, String string2, String string3) {
		this.auditType=string1;
		this.question=string2;
		this.response=string3;
	}
}
