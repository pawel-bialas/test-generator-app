package com.github.pawelbialas.testgeneratorapp.entity.question.service;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.MainTech;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.question.repository.QuestionRepository;
import com.opencsv.CSVReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManagerFactory;
import javax.transaction.Transactional;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private EntityManagerFactory emf;
    @Value("${csv.location}")
    private String fileLocation;


    public void saveOrUpdate(Question question) {
        if (question.getId() == null) {
            questionRepository.save(question);
        }
        emf.createEntityManager().merge(question);
    }

    public List<Question> findAllByMainTech(MainTech mainTech) {
        return questionRepository.findAllByMainTech(mainTech);
    }

    public List<Question> findAllByMainTechAndSkillLevel(MainTech mainTech, SkillLevel skillLevel) {
        return questionRepository.findAllByMainTechAndSkillLevel(mainTech, skillLevel);
    }

    public List<Question> findAllByMainTechAndSkillLevelAndSpecificTech (MainTech mainTech, String specificTech, SkillLevel skillLevel) {
        return questionRepository.findAllByMainTechAndSkillLevelAndSpecificTech(mainTech, skillLevel, specificTech);
    }


    public void readQuestionsFromCsv() {
        try {
            CSVReader reader = new CSVReader(new FileReader(fileLocation), ',');
            try {
                String[] nextLine;
                while ((nextLine = reader.readNext()) != null) {
                    Question question = new Question();
                    question.setMainTech(convertMainTech(nextLine[1]));
                    question.setSpecificTech(nextLine[2]);
                    question.setSkillLevel(convertSkillLevel(nextLine[3]));
                    question.setContents(nextLine[4]);
                    ArrayList<String> rawAnswers = new ArrayList<>();
                    rawAnswers.add(nextLine[5]);
                    rawAnswers.add(nextLine[6]);
                    rawAnswers.add(nextLine[7]);
                    rawAnswers.add(nextLine[8]);
                    rawAnswers.add(nextLine[9]);
                    List<Answer> answers = convertAnswers(rawAnswers);
                    for (Answer answer : answers) {
                        answer.setQuestion(question);
                        question.getAnswers().add(answer);
                    }
                    questionRepository.save(question);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private SkillLevel convertSkillLevel(String someString) {
        SkillLevel result = null;
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
        MainTech result = MainTech.UNASSIGNED;
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
        try {
            if (answers.get(4).length() != 1) {
                String[] split = answers.get(4).split(",");
                for (int i = 0; i < split.length; i++) {
                    int answerNumber = Integer.parseInt(split[i]);
                    correct.add(answerNumber);
                }
            } else {
                correct.add(Integer.parseInt(answers.get(4)));
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < answers.size() - 1; i++) {
            Answer answer = new Answer();
            answer.setAnswer(answers.get(i));
            if (correct.contains(i + 1)) {
                answer.setCorrect(true);
            } else answer.setCorrect(false);
            result.add(answer);
        }

        return result;
    }
}
