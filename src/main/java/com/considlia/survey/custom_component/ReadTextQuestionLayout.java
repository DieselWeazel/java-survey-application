package com.considlia.survey.custom_component;

import com.considlia.survey.model.Question;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;

public class ReadTextQuestionLayout extends VerticalLayout {

    private HorizontalLayout horizontalLayout;
    private VerticalLayout verticalLayout;
    private Question question;

    private TextField questionField;

    public ReadTextQuestionLayout(Question question){
        this.verticalLayout = new VerticalLayout();
        this.horizontalLayout = new HorizontalLayout();
        this.question = question;
        questionField = new TextField();
        questionField.setLabel(question.getQuestionTitle());

        horizontalLayout.add(questionField);
        verticalLayout.add(horizontalLayout);
        add(verticalLayout);
    }
}
