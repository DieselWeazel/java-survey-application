package com.considlia.survey.custom_component;

import com.considlia.survey.model.MultiQuestion;
import com.considlia.survey.model.MultiQuestionAlternative;
import com.considlia.survey.model.Question;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.html.H5;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.radiobutton.RadioButtonGroup;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

public class ReadMultiQuestionLayout extends VerticalLayout {

    private HorizontalLayout horizontalLayout;

    private Question question;

    private Set<MultiQuestionAlternative> alternatives;
    private List<String> stringAlternatives;

    private RadioButtonGroup<String> radioButtons;
    private CheckboxGroup<String> checkBoxButtons;

    private H5 title;

    private int questionType;

    public ReadMultiQuestionLayout(MultiQuestion question){
        this.question = question;
        this.title = new H5(question.getQuestionTitle());
        this.horizontalLayout = new HorizontalLayout();
        this.alternatives = new HashSet<>();

        title.setWidth("90%");

        add(title);




        for (int position = 0; position < stringAlternatives.size(); position++){
            MultiQuestionAlternative alt = new MultiQuestionAlternative();
            alt.setPosition(position);
            alt.setAlternativeTitle(stringAlternatives.get(position));
            alternatives.add(alt);
        }

        if (question.getQuestionType() == 1) {
            radioButtons = new RadioButtonGroup<>();
            radioButtons.setItems(stringAlternatives);
            horizontalLayout.add(radioButtons);

        } else {
            checkBoxButtons = new CheckboxGroup<>();
            checkBoxButtons.setItems(stringAlternatives);
            horizontalLayout.add(checkBoxButtons);
        }
        add(title, horizontalLayout);
    }
}
