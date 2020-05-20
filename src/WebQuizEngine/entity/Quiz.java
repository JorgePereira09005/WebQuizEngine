package WebQuizEngine.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @NotBlank(message = "Title is mandatory")
    @Column
    private String title;

    @NotBlank(message = "Text is mandatory")
    @Column
    private String text;

    @NotEmpty
    @Size(min = 2)
    @Column
    @ElementCollection
    private List<String> options;

    @ElementCollection
    @Column
    private List<Integer> answer;

    @ManyToOne
    @JoinColumn(name = "user_email")
    private User creator;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="solvedQuiz", orphanRemoval = true)
    private List<Solved> solved = new ArrayList<>();

    public Quiz() {
    }

    public Quiz(@NotBlank(message = "Title is mandatory") String title, @NotBlank(message = "Text is mandatory") String text, @NotEmpty @Size(min = 2) List<String> options, List<Integer> answer, User creator) {
        this.title = title;
        this.text = text;
        this.options = options;
        this.answer = answer;
        this.creator = creator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<String>  getOptions() {
        return options;
    }

    public void setOptions(List<String> options) {
        this.options = options;
    }

    @JsonIgnore
    public User getCreator() {
        return creator;
    }

    public void setCreator(User creator) {
        this.creator = creator;
    }

    @JsonIgnore
    public List<Integer> getAnswer() {
        return answer;
    }

    @JsonProperty
    public void setAnswer(List<Integer>  answer) {
        this.answer = answer;
    }

    @JsonIgnore
    public List<Solved> getSolved() {
        return solved;
    }

    public void setSolved(List<Solved> solved) {
        this.solved = solved;
    }
}