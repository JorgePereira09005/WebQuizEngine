package WebQuizEngine.dao;

import WebQuizEngine.entity.Quiz;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface QuizRepository extends PagingAndSortingRepository<Quiz, Integer> {

}
