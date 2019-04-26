package com.considLia.survey.ui;

import com.considLia.survey.model.MultiQuestion;
import com.considLia.survey.model.MultiQuestionAlternative;
import com.considLia.survey.model.Question;
import com.considLia.survey.model.Survey;
import com.considLia.survey.repositories.SurveyRepository;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
public class MainView extends VerticalLayout {

  public MainView(SurveyRepository surveyRepository) {

    Survey survey = surveyRepository.getSurveyBySurveyId(20l);

    add(new H1(survey.getSurveyTitle() + " " + survey.getCreator() + " " + survey.getDate()));

    survey.getQuestionList();

    for (Question q : survey.getQuestionList()) {
      add(new H2(q.getQuestionTitle()));
      if (q instanceof MultiQuestion) {
        MultiQuestion mq = (MultiQuestion) q;
        for (MultiQuestionAlternative mqa : mq.getAlternativeList()) {
          add(new H3(mqa.getAlternativeTitle()));
        }
      }
    }

  }

}
