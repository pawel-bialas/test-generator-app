package com.github.pawelbialas.testgeneratorapp.entity.question.service;


import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.shared.MainTech;
import com.github.pawelbialas.testgeneratorapp.shared.SkillLevel;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Value;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.github.pawelbialas.testgeneratorapp.shared.SkillLevel.MID;

public class QuestionCsvConverter {

    @Value("${csv.location}")
    private String fileLocation;

    public List<Question> readQuestionsFromCsv(String fileName) {
        List<Question> questions = new ArrayList<>();
        try {
            CSVReader reader = new CSVReader(new FileReader(fileLocation + fileName), ',');
            try {
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    Question question = new Question();
                    question.setMainTech(convertMainTech(nextLine[1]));
                    question.setSpecificTech(nextLine[2]);
                    question.setSkillLevel(convertSkillLevel(nextLine[3]));
                    question.setContents(nextLine[4]);
                    ArrayList<String> answers = new ArrayList<>();
                    answers.add(nextLine[5]);
                    answers.add(nextLine[6]);
                    answers.add(nextLine[7]);
                    answers.add(nextLine[8]);
                    answers.add(nextLine[9]);
                    question.setAnswers(convertAnswers(answers));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return questions;
    }

    private SkillLevel convertSkillLevel(String someString) {
        SkillLevel result = SkillLevel.ALL;

        switch (someString) {

            case "Entry":
                result = SkillLevel.ENTRY;
                break;
            case "Junior":
                result = SkillLevel.JUNIOR;
                break;
            case "Mid":
                result = SkillLevel.MID;
                break;
            case "Senior":
                result = SkillLevel.SENIOR;
                break;
            case "Expert":
                result = SkillLevel.EXPERT;
                break;
        }

        return result;
    }

    private MainTech convertMainTech(String someString) {
        MainTech result = MainTech.SQL;
        switch (someString) {
            case "Java":
                result = MainTech.JAVA;
                break;
        }
        return result;
    }

    private List<Answer> convertAnswers(ArrayList<String> answers) {
        List<Answer> result = new ArrayList<>();
        List<Integer> correct = new ArrayList<>();
        if (answers.get(4).length() != 1) {
            String[] split = answers.get(4).split(",");
            for (int i = 0; i < split.length; i++) {
                int answerNumber = Integer.parseInt(split[i]);
                correct.add(answerNumber);
            }
        } else {
            correct.add(Integer.parseInt(answers.get(4)));
        }
        for (int i = 0; i < answers.size() -1; i++) {
            Answer answer = new Answer();
            answer.setAnswer(answers.get(i));
            if (correct.contains(i + 1)) {
                answer.setCorrect(true);
            } else answer.setCorrect(false);
            result.add(answer);
        }

        return result;
    }

    public static void main(String[] args) {


        ArrayList<String> answers = new ArrayList<>();
        answers.add("12");
        answers.add("13");
        answers.add("14");
        answers.add("15");
        answers.add("1,2");

        QuestionCsvConverter questionCsvConverter = new QuestionCsvConverter();
        List<Answer> answers1 = questionCsvConverter.convertAnswers(answers);

        System.out.println(answers1);

    }


}



