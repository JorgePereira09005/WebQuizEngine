package WebQuizEngine.dao;

import WebQuizEngine.entity.Solved;
import WebQuizEngine.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface SolvedRepository extends PagingAndSortingRepository<Solved, Integer> {

    Page<Solved> findAllBySolvedBy(User user, Pageable pageable);

}
