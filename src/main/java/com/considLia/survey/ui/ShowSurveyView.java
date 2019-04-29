package com.considLia.survey.ui;

import com.considLia.survey.model.MultiQuestion;
import com.considLia.survey.model.MultiQuestionAlternative;
import com.considLia.survey.model.Question;
import com.considLia.survey.model.Survey;
import com.considLia.survey.repositories.SurveyRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;

import javax.validation.constraints.Null;

@Route(value = "showsurvey", layout = MainLayout.class)
public class ShowSurveyView extends VerticalLayout implements HasUrlParameter<Long> {

  // -- Private Variables --
  private SurveyRepository surveyRepository;

  // -- TextContainers --
  private H1 h1;

  // -- Buttons --
  private Button saveButton = new Button();

  // -- Serializement --
  private Binder<Question> binder = new Binder<>(Question.class);

  // -- Layout --
  public ShowSurveyView(SurveyRepository surveyRepository) {
    this.surveyRepository = surveyRepository;
    h1 = new H1("PlaceHolder // Survey Not Actually Found, Text not Updated");
    saveButton.setText("Send");
    saveButton.addClickListener(e-> saveResponse());

    add(h1);
  }

  // -- Parameter Method, finding Survey --
  @Override
  public void setParameter(BeforeEvent event, Long parameter) {
    try {
      if (parameter == null) {
        // Null Pointer Exception? (Survey Null Pointer Exception)
        h1.setText("ERROR, no survey ID parameter given.");

        goHome();
      }
      if (surveyRepository.findById(parameter).isPresent()) {
        Survey survey = surveyRepository.getSurveyBySurveyId(parameter);
        h1.setText(survey.getSurveyTitle());

        loadSurvey(survey);
      } else {
        // Or Create SurveyNotFoundException
        h1.setText("ERROR, no Survey by the ID of: " + parameter + " exists.");

        goHome();
      }
    } catch (NullPointerException e){
      h1.setText(e.getMessage());
    }
  }

  // -- Loading Survey to Layout
  public void loadSurvey(Survey survey) {
    // Should work outside of constructor as well, then methods outside with save etc should work perfectly fine.
    Binder<MultiQuestionAlternative> binderAlternatives = new Binder<>(MultiQuestionAlternative.class);

    for (Question q: survey.getQuestionList()){

      if (q instanceof MultiQuestion) {
        add(new H2(q.getQuestionTitle()));
        HorizontalLayout horLayout = new HorizontalLayout();
//                CheckboxGroup questionCheckBoxGroup = new CheckboxGroup();
        MultiQuestion mq = (MultiQuestion) q;
        if (mq.getQuestionType() == 1) {
          CheckboxGroup<MultiQuestionAlternative> multiQuestionCheckboxes = new CheckboxGroup<>();
          multiQuestionCheckboxes.setItems(mq.getAlternativeList());
          VerticalLayout checkBoxVertContainer = new VerticalLayout();
          checkBoxVertContainer.add(multiQuestionCheckboxes);


          horLayout.add(checkBoxVertContainer);

          // else, if we want radiobutton
        } else if (mq.getQuestionType() == 0){

          RadioButtonGroup<MultiQuestionAlternative> multiQuestionRadioButtons = new RadioButtonGroup<>();
          multiQuestionRadioButtons.setItems(mq.getAlternativeList());
          VerticalLayout checkBoxVertContainer = new VerticalLayout();
          checkBoxVertContainer.add(multiQuestionRadioButtons);


          horLayout.add(checkBoxVertContainer);

        }
        add(horLayout);
      } else {
        add(new H2(q.getQuestionTitle()));
        TextField textField2 = new TextField();

        binder.forField(textField2).bind(Question::getQuestionTitle, Question::setQuestionTitle);

        binder.readBean(q);
        add(textField2);
      }
    }

    add(saveButton);
  }

  // -- Public Button Methods --
  public void saveResponse(){
    saveButton.setText("Survey Response cannot be saved yet!");
  }

  public void goHome(){
    saveButton.setText("Go To Mainview");
    saveButton.addClickListener(e-> getUI().ifPresent(ui -> ui.navigate("")));
    add(saveButton);
  }
}

