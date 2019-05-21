package com.considlia.survey.model.answer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "multi_answer_alt")
public class AnswerAlternative {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "alternative_id")
  private Long id;

  private int position;

}
