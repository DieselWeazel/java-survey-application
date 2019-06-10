package com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts.questiontype_layout;

import com.considlia.survey.model.answer.Answer;
import com.considlia.survey.model.answer.CheckBoxAnswer;
import com.considlia.survey.model.answer.CheckBoxAnswerChoice;
import com.considlia.survey.model.question.MultiQuestionAlternative;
import com.considlia.survey.model.question.Question;
import com.considlia.survey.ui.custom_component.layout.answercomponents.showanswerlayouts.ShowAnswerLayout;
import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.DataLabelsBuilder;
import com.github.appreciated.apexcharts.config.builder.FillBuilder;
import com.github.appreciated.apexcharts.config.builder.PlotOptionsBuilder;
import com.github.appreciated.apexcharts.config.builder.StrokeBuilder;
import com.github.appreciated.apexcharts.config.builder.TooltipBuilder;
import com.github.appreciated.apexcharts.config.builder.XAxisBuilder;
import com.github.appreciated.apexcharts.config.builder.YAxisBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.chart.selection.builder.YaxisBuilder;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.config.tooltip.builder.YBuilder;
import com.github.appreciated.apexcharts.config.xaxis.builder.TitleBuilder;
import com.github.appreciated.apexcharts.helper.Series;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H5;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ShowMultiChoiceAnswerLayout extends ShowAnswerLayout {

  private List<String> allChosenAnswers = new ArrayList<>();

  private String [] stringList;

  public ShowMultiChoiceAnswerLayout(
      Question question, String [] stringList, List<CheckBoxAnswerChoice> answerList, List<String> categoryList) {
    super(question);
    //todo change?
    this.stringList = stringList;
    for (CheckBoxAnswerChoice answer : answerList) {
      LOGGER.info("Adding answer '{}'", answer.getCheckedAnswer());
      allChosenAnswers.add(answer.getCheckedAnswer());
      add(new H5(answer.getCheckedAnswer() + ", "));
    }

//    double [][] valueList = new double[categoryList.size()][stringList.length];
//
//    for (int i = 0; i < categoryList.size(); i++){
//      for (int j = 0; j < stringList.length; j++){
//        valueList[j][i] += 1.0;
//      }
//      System.out.println(stringList[i]);
//    }

    double [] valuesList = new double[categoryList.size()];


//    for (int i = 0; i < stringList.length; i++){
//      for (int j = 0; j < stringList[i].length(); j++){
//        if (i == j){
//
//          for (int k = 0; k < valuesList.length; k++){
//            valuesList[k] += 1;
//            System.out.println(valuesList[k]);
//          }
//        }
//      }
//    }

    for (int i = 0; i < valuesList.length; i++){
      for (int j = 0; j < stringList.length; j++){
        for (int k = 0; k < stringList[j].length(); k++){
          if (j == k){
            valuesList[i] += 1;
            System.out.println(valuesList[i]);
          }
        }
      }
    }

//    for (int i = 0; i < valuesList.length; i++){


//      for (CheckBoxAnswerChoice checkBoxAnswerChoice : answerList) {
//        for (CheckBoxAnswerChoice checkBoxAnswerChoice1 : answerList) {
//          if (checkBoxAnswerChoice.getCheckedAnswer().equals(checkBoxAnswerChoice1.getCheckedAnswer())){
//            valuesList[i] += 1.0;
//          }
//        }
//      }
//    }

//    for (CheckBoxAnswerChoice checkBoxAnswerChoice : answerList){
//      for (CheckBoxAnswerChoice checkBoxAnswerChoice1 : answerList){
//        if (checkBoxAnswerChoice.getCheckedAnswer().equals(checkBoxAnswerChoice1.getCheckedAnswer())){
//
//        }
//      }
//    }

//    for (CheckBoxAnswerChoice checkBoxAnswerChoice : answerList) {
//      if (checkBoxAnswerChoice.get)
//    }
//    answerList.forEach(e -> {
//      for (int i = 0; i < valuesList.length; i++){
//
//      }
//    })

    for (int i = 0; i < valuesList.length; i++){
      System.out.println(i + " has value: " + valuesList[i]);
    }

//    for (int i = 0; i < valueList.length; i++){
//      for (int j = 0; j < valueList[i].length; j++){
//        System.out.println("valuelist " + i + ", " + j + " " + valueList[i][j]);
//      }
//    }
    ApexCharts barChart = new ApexCharts()
            .withChart(ChartBuilder.get().withType(Type.bar).build())
            .withPlotOptions(
                PlotOptionsBuilder.get()
                    .withBar(BarBuilder.get().withHorizontal(false).withColumnWidth("55%").build())
                    .build())
            .withDataLabels(DataLabelsBuilder.get().withEnabled(false).build())
            .withStroke(
                StrokeBuilder.get().withShow(true).withWidth(5.0).withColors("red").build())
            .withSeries(new Series<>("choices", valuesList))
            .withYaxis(
                YAxisBuilder.get()
                    .withTitle(com.github.appreciated.apexcharts.config.yaxis.builder.TitleBuilder.get().withText("lol").build())
                    .build())
            .withXaxis(XAxisBuilder.get().withCategories(categoryList).build())
            .withFill(FillBuilder.get().withOpacity(1.0).build())
            .withTooltip(
                TooltipBuilder.get()
                    .withY(
                        YBuilder.get()
//                            .withFormatter(
//                                "function (val) {\n"
//                                    + // Formatter currently not yet working
//                                    "return \"$ \" + val + \" tens\"\n"
//                                    + "}")
                            .build())
                    .build());
    add(barChart);
    setWidth("100%");
        ApexCharts mynewCHart = new ApexCharts()
            .withChart(ChartBuilder.get()
                .withType(Type.bar)
                .build())
            .withPlotOptions(PlotOptionsBuilder.get()
                .withBar(BarBuilder.get()
                    .withHorizontal(false)
                    .withColumnWidth("55%")
                    .build())
                .build())
            .withDataLabels(DataLabelsBuilder.get()
                .withEnabled(false).build())
            .withStroke(StrokeBuilder.get()
                .withShow(true)
                .withWidth(2.0)
                .withColors("transparent")
                .build())
            .withSeries(new Series("Net Profit", 44.0, 55.0, 57.0, 56.0, 61.0, 58.0, 63.0, 60.0, 66.0),
                new Series("Revenue", 76.0, 85.0, 101.0, 98.0, 87.0, 105.0, 91.0, 114.0, 94.0),
                new Series("Free Cash Flow", 35.0, 41.0, 36.0, 26.0, 45.0, 48.0, 52.0, 53.0, 41.0))
            .withYaxis(
                YAxisBuilder.get()
                    .withTitle(com.github.appreciated.apexcharts.config.yaxis.builder.TitleBuilder.get().withText("lol").build())
                    .build())
            .withXaxis(XAxisBuilder.get().withCategories("Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct").build())
            .withFill(FillBuilder.get()
                .withOpacity(1.0).build())
            .withTooltip(TooltipBuilder.get()
                .withY(YBuilder.get()
                    .withFormatter("function (val) {\n" + // Formatter currently not yet working
                        "return \"$ \" + val + \" thousands\"\n" +
                        "}").build())
                .build());
        add(mynewCHart);
        setWidth("100%");
      }
    }








