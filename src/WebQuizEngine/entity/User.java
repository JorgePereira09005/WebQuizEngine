package WebQuizEngine.entity;

import WebQuizEngine.validation.ValidEmail;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User {

    @Id
    @ValidEmail
    @NotNull(message = "is required")
    @Size(min = 1, message = "is required")
    @Column(unique=true)
    private String email;

    @Size(min=5)
    @NotNull
    @Column
    private String password;

    @OneToMany(cascade =  CascadeType.ALL, mappedBy = "creator")
    private Set<Quiz> quizzes = new HashSet<>();

    @OneToMany(cascade =  CascadeType.ALL, mappedBy = "solvedBy")
    private List<Solved> solvedQuizzes = new ArrayList<>();

    public User(){

    }

    public User(@Email @NotNull String email, @Size(min = 5) @NotNull String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(Set<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public List<Solved> getSolvedQuizzes() {
        return solvedQuizzes;
    }

    public void setSolvedQuizzes(List<Solved> solvedQuizzes) {
        this.solvedQuizzes = solvedQuizzes;
    }
}
