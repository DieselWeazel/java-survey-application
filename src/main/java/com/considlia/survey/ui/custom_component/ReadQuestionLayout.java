package com.considlia.survey.ui.custom_component;

import com.considlia.survey.model.answer.Answers;
import com.considlia.survey.model.question.Question;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.binder.Binder;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//TODO what method do I want them all to share that is useful?
public class ReadQuestionLayout extends VerticalLayout {

  private H5 title;
  private Question question;
  private static final Logger LOGGER = LoggerFactory.getLogger(ReadQuestionLayout.class);
  //TODO
//  private Binder <Answers> binder;
//  private Answers answers;

  public ReadQuestionLayout(Question question) {
    this.title = new H5(question.getTitle() + (question.isMandatory() ? "*" : ""));
    this.question = question;
//    this.binder = new Binder();
    add(title);
  }

//  public Binder getBinder() {
//    return binder;
//  }
//
//  public Answers getAnswers() {
//    return answers;
//  }

  public Question getQuestion() {
    return question;
  }

  public static Logger getLOGGER() {
    return LOGGER;
  }
}
