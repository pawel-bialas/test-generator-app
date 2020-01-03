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
                    ArrayList<String> answers = new ArrayList<>();
                    question.setMainTech(convertMainTech(nextLine[1]));
                    question.setSpecificTech(nextLine[2]);
                    question.setSkillLevel(convertSkillLevel(nextLine[3]));
                    question.setContents(nextLine[4]);
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
        
    }


}
