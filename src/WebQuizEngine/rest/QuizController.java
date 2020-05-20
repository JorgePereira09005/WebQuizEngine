package WebQuizEngine.rest;

import WebQuizEngine.dao.QuizRepository;
import WebQuizEngine.dao.SolvedRepository;
import WebQuizEngine.dao.UserRepository;
import WebQuizEngine.entity.Quiz;
import WebQuizEngine.entity.Solved;
import WebQuizEngine.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.NoSuchElementException;

@RestController
public class QuizController {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SolvedRepository solvedRepository;

    public QuizController(){
    }

    @PostMapping(path = "/api/register")
    public void registerUser (@Valid @RequestBody User newUser) {

        if(this.userRepository.findByEmail(newUser.getEmail()) == null) {

            BCryptPasswordEncoder encoder = registrationEncoder();
            User user = new User(newUser.getEmail(), encoder.encode(newUser.getPassword()));
            this.userRepository.save(user);

        } else {
            throw new ResponseStatusException( HttpStatus.BAD_REQUEST, "User already exists");
        }
    }


    @PostMapping(path = "/api/quizzes")
    public Quiz createNewQuiz(@Valid @RequestBody Quiz newQuiz) {

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
        User creator = this.userRepository.findByEmail(userDetails.getUsername());

        Quiz quiz = new Quiz(newQuiz.getTitle(), newQuiz.getText(), newQuiz.getOptions(), newQuiz.getAnswer(), creator );

        return this.quizRepository.save(quiz);
    }

    @GetMapping(path = "/api/quizzes/{id}")
    public Quiz getQuiz(@PathVariable int id) {

        try {
            return quizRepository.findById(id).get();
        } catch (NoSuchElementException e) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "entity not found");
        }

    }

    @GetMapping(path = "/api/quizzes")
    public Page<Quiz> getQuizzes(@RequestParam(required = false, defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 10);
        return this.quizRepository.findAll(pageable);
    }

    @PostMapping(path ="/api/quizzes/{id}/solve")
    public Feedback solveQuiz(@RequestBody Answer answer, @PathVariable int id) {

        try {
            int[] arrayCorrectAnswers = quizRepository.findById(id).get().getAnswer()
                    .stream().mapToInt(i->i).toArray();

            if (Arrays.equals(answer.getAnswer(),arrayCorrectAnswers )){

                Solved newSolved = new Solved();
                UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                        .getPrincipal();
                newSolved.setCompletedAt(LocalDateTime.now());
                newSolved.setSolvedQuiz(this.quizRepository.findById(id).get());
                newSolved.setSolvedBy(this.userRepository.findByEmail(userDetails.getUsername()));
                this.solvedRepository.save(newSolved);

                return new Feedback(true, "Congratulations, you're right!");
            }

            return new Feedback(false, "Wrong answer! Please, try again.");

        } catch (NoSuchElementException e) {
            throw new ResponseStatusException( HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @GetMapping(path = "/api/quizzes/completed")
    public Page<Solved> getSolved(@RequestParam(required = false, defaultValue = "0") int page) {

        Pageable pageable = PageRequest.of(page, 10, Sort.by("completedAt").descending());
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        return this.solvedRepository.
                findAllBySolvedBy(this.userRepository.findByEmail(userDetails.getUsername()), pageable);
    }

    @PostMapping(path ="/actuator/shutdown")
    public ResponseEntity<String> shutdown() {
        return new ResponseEntity<>("Shutdown", HttpStatus.OK);
    }

    @DeleteMapping(path ="/api/quizzes/{quizId}")
    public ResponseEntity<String> deleteQuiz (@PathVariable int quizId) {

        Quiz quiz;

        try {
            quiz = this.quizRepository.findById(quizId).get();
        } catch (Exception e) {
            return new ResponseEntity<>("Quiz not found", HttpStatus.NOT_FOUND);
        }

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();

        if (userDetails.getUsername() == quiz.getCreator().getEmail()) {
            this.quizRepository.delete(quiz);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>("You're not the author of the quiz!", HttpStatus.FORBIDDEN);
        }

    }

    @Bean
    public BCryptPasswordEncoder registrationEncoder() {
        return new BCryptPasswordEncoder();
    }

}
