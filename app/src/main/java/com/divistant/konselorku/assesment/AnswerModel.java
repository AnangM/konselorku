package com.divistant.konselorku.assesment;

public class AnswerModel {
    private String question_id;
    private String user_id;
    private String answer;
    private final String created_at = "NOW()";
    private final String updated_at = "NOW()";

    public AnswerModel(String question_id, String user_id, String answer) {
        this.question_id = question_id;
        this.user_id = user_id;
        this.answer = answer;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
