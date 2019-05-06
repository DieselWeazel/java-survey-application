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

//    private MultiQuestion question;

    private Set<MultiQuestionAlternative> alternatives;
    private Set<MultiQuestionAlternative> stringAlternatives;

    private RadioButtonGroup<MultiQuestionAlternative> radioButtons;
    private CheckboxGroup<MultiQuestionAlternative> checkBoxButtons;

    private H5 title;

    private int questionType;

    public ReadMultiQuestionLayout(MultiQuestion question){
//        this.question = question;
        this.title = new H5(question.getQuestionTitle());
        this.horizontalLayout = new HorizontalLayout();
        this.alternatives = new HashSet<>();
        this.stringAlternatives = stringAlternatives;
        title.setWidth("90%");

        add(title);


        for (MultiQuestionAlternative mqa : question.getAlternativeList()){
            System.out.println(mqa.toString());
        }


//        for (int position = 0; position < stringAlternatives.size(); position++){
//            MultiQuestionAlternative alt = new MultiQuestionAlternative();
//            alt.setPosition(position);
////            alt.setAlternativeTitle(stringAlternatives.get(position));
//            alternatives.add(alt);
//        }

        VerticalLayout choiceVerticalLayout = new VerticalLayout();
        if (question.getQuestionType() == 1) {
            this.radioButtons = new RadioButtonGroup<>();
            radioButtons.setItems(question.getAlternativeList());
            choiceVerticalLayout.add(radioButtons);

        } else {
            this.checkBoxButtons = new CheckboxGroup<>();
            checkBoxButtons.setItems(stringAlternatives);
            choiceVerticalLayout.add(checkBoxButtons);
        }
        horizontalLayout.add(choiceVerticalLayout);
        add(title, horizontalLayout);
    }
}
