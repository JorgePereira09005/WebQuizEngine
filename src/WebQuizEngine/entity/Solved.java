package WebQuizEngine.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@JsonPropertyOrder({ "id", "completedAt" })
public class Solved {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Integer id;

    @Column(name="quiz_id", updatable=false, insertable=false)
    private int solvedQuiz_fk;

    @ManyToOne
    @JoinColumn(name="user_id", nullable = false)
    private User solvedBy;

    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
    @Column
    private LocalDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz solvedQuiz;

    public Solved(){

    }

    @JsonProperty("id")
    public int getSolvedQuiz_fk() {
        return solvedQuiz_fk;
    }

    public void setSolvedQuiz_fk(int solvedQuiz_fk) {
        this.solvedQuiz_fk = solvedQuiz_fk;
    }

    @JsonIgnore
    public Quiz getSolvedQuiz() {
        return solvedQuiz;
    }

    public void setSolvedQuiz(Quiz solvedQuiz) {
        this.solvedQuiz = solvedQuiz;
    }

    @JsonIgnore
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }

    @JsonIgnore
    public User getSolvedBy() {
        return solvedBy;
    }

    public void setSolvedBy(User solvedBy) {
        this.solvedBy = solvedBy;
    }

}
