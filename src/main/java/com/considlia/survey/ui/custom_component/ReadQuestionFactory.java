package com.considlia.survey.ui.custom_component;

import com.considlia.survey.model.Survey;
import com.considlia.survey.model.question.Question;

public interface ReadQuestionFactory<T> {

  T loadQuestion(Question question);

}
