package com.considlia.survey.ui.custom_component.question_with_button;

import com.considlia.survey.model.question.Question;
import com.considlia.survey.ui.CreateSurveyView;
import com.considlia.survey.ui.custom_component.ConfirmDialog;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import java.util.function.Consumer;

/**
 * Abstract class extending {@link VerticalLayout}. Contains the common {@link Component}s that the
 * subclasses use.
 *
 */
public abstract class QuestionWithButtons extends VerticalLayout {

  private static final int MOVE_UP = -1;
  private static final int MOVE_DOWN = 1;

  private Question question;
  private H5 title;

  private Button upButton;
  private Button downButton;

  private HorizontalLayout content;
  private CreateSurveyView survey;

  /**
   * Constructs a VerticalLayout containing {@link H1} with the value from
   * {@link Question#getTitle()} and four different {@link Button}(move up, move down, edit this
   * question and remove this question)
   * 
   * @param question is {@link Question}
   * @param survey for accessing its class methods
   */
  public QuestionWithButtons(Question question, CreateSurveyView survey, Consumer<Question> deleteQuestionConsumer) {
    setId("custom");
    setWidth("100%");

    this.question = question;
    this.survey = survey;
    this.upButton = new Button(new Icon(VaadinIcon.ARROW_UP));
    this.downButton = new Button(new Icon(VaadinIcon.ARROW_DOWN));

    content = new HorizontalLayout();
    content.setWidthFull();
    content.setClassName("content");

    title = new H5(question.getTitle() + (question.isMandatory() ? "*" : ""));
    title.setWidth("90%");

    initButtonEvent(upButton, MOVE_UP);
    initButtonEvent(downButton, MOVE_DOWN);

    content.add(title, upButton, downButton);
    content.add(
        new Button(new Icon(VaadinIcon.PENCIL), event -> survey.editQuestion(event.getSource())));
    content.add(new Button(new Icon(VaadinIcon.TRASH), onDelete ->
        new ConfirmDialog("Confirm Delete",
            "Are you sure you want to remove question: "
        + question.getTitle() + "?", deleteQuestionConsumer, question)));
    add(content);
  }

  /**
   * Set a {@link ClickEvent} to the passed {@link Button}
   * 
   * @param button witch the {@link ClickEvent} is added to
   * @param move is the direction that the {@link QuestionWithButtons}-component should move
   */
  public void initButtonEvent(Button button, int move) {
    button.addClickListener(onClick -> {
      survey.moveQuestion(getQuestion(), move);
      survey.updateMoveButtonStatus();
    });
  }

  /**
   * Returns the Question
   * 
   * @return {@link Question}
   */
  public Question getQuestion() {
    return question;
  }

  /**
   * Update the H5 title value in the GUI
   */
  public void setTitleInUI() {

    H5 updatedTitle = new H5(question.getTitle() + (question.isMandatory() ? "*" : ""));
    updatedTitle.setWidth("90%");

    content.replace(title, updatedTitle);
    title = updatedTitle;
  }

  /**
   * Returns upButton object
   * 
   * @return {@link Button} upButton
   */
  public Button getUpButton() {
    return upButton;
  }

  /**
   * Set the upButton object
   */
  public void setUpButton(Button upButton) {
    this.upButton = upButton;
  }

  /**
   * Returns downButton object
   * 
   * @return {@link Button} downButton
   */
  public Button getDownButton() {
    return downButton;
  }

  /**
   * Set the downButton object
   */
  public void setDownButton(Button downButton) {
    this.downButton = downButton;
  }

}
