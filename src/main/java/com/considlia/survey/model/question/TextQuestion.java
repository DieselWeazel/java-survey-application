package com.considlia.survey.model.question;

import com.considlia.survey.model.question.Question;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "textquestion")
public class TextQuestion extends Question {}
