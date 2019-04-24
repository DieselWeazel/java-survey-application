package com.considLia.survey.ui;

import java.util.Set;
import com.considLia.survey.model.MultiQuestion;
import com.considLia.survey.model.MultiQuestionAlternative;
import com.considLia.survey.model.Survey;
import com.considLia.survey.model.TextQuestion;
import com.considLia.survey.repositories.SurveyRepository;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = MainLayout.class)
public class MainView extends VerticalLayout {

  public MainView(SurveyRepository surveyRepository) {


    Survey survey = surveyRepository.getSurveyBySurveyId(1l);

    add(new H1(survey.getSurveyTitle()));
    for (TextQuestion t : survey.getTextQuestionList()) {
      add(new H2(t.getQuestionTitle()));
    }
    for (MultiQuestion m : survey.getMultiQuestionList()) {
      add(new H2(m.getQuestionTitle()));
      Set<MultiQuestionAlternative> set = m.getAlternativeList();
      for (MultiQuestionAlternative mqa : set) {
        add(new H3(mqa.getQuestionTitle()));
      }
    }

    System.out.println("Nådde hit 333");

  }

}
