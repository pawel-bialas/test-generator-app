package com.github.pawelbialas.testgeneratorapp.shared;

import com.github.pawelbialas.testgeneratorapp.entity.answer.model.Answer;
import com.github.pawelbialas.testgeneratorapp.entity.answer.repository.AnswerRepository;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.MainTech;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.Question;
import com.github.pawelbialas.testgeneratorapp.entity.question.model.SkillLevel;
import com.github.pawelbialas.testgeneratorapp.entity.question.repository.QuestionRepository;
import com.github.pawelbialas.testgeneratorapp.entity.test.repository.SkillTestRepository;
import com.opencsv.CSVReader;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class BootstrapSomeData implements ApplicationListener<ContextRefreshedEvent> {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final EntityManagerFactory emf;
    private final SkillTestRepository skillTestRepository;

    public BootstrapSomeData(QuestionRepository questionRepository, AnswerRepository answerRepository, EntityManagerFactory emf, SkillTestRepository skillTestRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.emf = emf;
        this.skillTestRepository = skillTestRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        try {
            CSVReader reader = new CSVReader(new FileReader("F:\\Files\\source\\test-generator-app\\test-generator-app\\src\\main\\resources\\tester.csv"), ',');
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
                        question.addAnswer(answer);
                    }
                    questionRepository.save(question);

                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

//        return questions;
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















//        for (int i = 0; i < 10; i++) {
//            Answer answer1 = new Answer();
//            Answer answer2 = new Answer();
//            Answer answer3 = new Answer();
//
//            answer1.setAnswer("aaa "  + (i + 1));
//            answer2.setAnswer("bbb "  + (i + 1));
//            answer3.setAnswer("ccc "  + (i + 1));
//
//            answer1.setCorrect(true);
//            answer2.setCorrect(false);
//            answer3.setCorrect(false);
//
//            Question question = new Question();
//
//            answer1.setQuestion(question);
//            answer2.setQuestion(question);
//            answer3.setQuestion(question);
//
//            question.add(answer1);
//            question.add(answer2);
//            question.add(answer3);
//
//            question.setSpecificTech("Java");
//            question.setSkillLevel(SkillLevel.JUNIOR);
//            question.setMainTech(MainTech.JAVA);
//            question.setContents("Pytanie o numerze " +  + (i + 1));
//            questionRepository.save(question);
//        }

//        List<Question> allByMainTech = questionRepository.findAllByMainTech(MainTech.JAVA);
//
//        Candidate candidate = new Candidate();
//        candidate.setCandidateNumber(12341234L);
//
//        SkillTest skillTest = new SkillTest();
//        skillTest.setQuestions(allByMainTech);
//        skillTest.setCandidate(candidate);
//        skillTestRepository.save(skillTest);


}




