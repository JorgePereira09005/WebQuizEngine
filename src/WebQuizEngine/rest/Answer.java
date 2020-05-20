package WebQuizEngine.rest;

public class Answer {

    private int[] answer;

    public Answer(){
    }

    public Answer(int[] value) {

        this.answer = value;
    }

    public int[] getAnswer() {

        return this.answer;
    }

    public void setAnswer(int[] value) {

        this.answer = value;
    }
}