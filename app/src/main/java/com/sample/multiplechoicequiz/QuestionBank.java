package com.sample.multiplechoicequiz;

// This file contains questions from QuestionBank

public class QuestionBank {

    // array of questions
    private String textQuestions [] = {
            "1. When did Google acquired Android?",
            "2. What is the name of build toolkit for Android Studio?",
            "3. What widget can replace any use of radio buttons?",
            "4. What is the most recent Android OS ?",
            "5. Which was the first commercially available phone running Android?",
            "6. Which one of the among would be the next version of Android?"
    };

    // array of multiple choices for each question
    private String multipleChoice [][] = {
            {"2003", "2004", "2005", "2006"},
            {"JVM", "Gradle", "Dalvik", "HAXM"},
            {"Toggle", "Spinner", "Switch", "CheckBox"},
            {"Oreo", "Nougat", "Marshmallow", "Octopus"},
            {"Samsung Galaxy S", "Google Nexus", "HTC Dream", "LG V"},
            {"Android Pineapple", "Android Popcorn", "Android P", "Android Plus"}
    };

    // array of correct answers - in the same order as array of questions
    private String mCorrectAnswers[] = {"2005", "Gradle", "Spinner", "Oreo", "HTC Dream", "Android P"};

    // method returns number of questions
    public int getLength(){
        return textQuestions.length;
    }

    // method returns question from array textQuestions[] based on array index
    public String getQuestion(int a) {
        String question = textQuestions[a];
        return question;
    }

    // method return a single multiple choice item for question based on array index,
    // based on number of multiple choice item in the list - 1, 2, 3 or 4 as an argument
    public String getChoice(int index, int num) {
        String choice0 = multipleChoice[index][num-1];
        return choice0;
    }

    //  method returns correct answer for the question based on array index
    public String getCorrectAnswer(int a) {
        String answer = mCorrectAnswers[a];
        return answer;
    }
}