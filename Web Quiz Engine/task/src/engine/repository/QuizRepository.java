package engine.repository;

import engine.model.Quiz;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuizRepository extends PagingAndSortingRepository<Quiz, Long> {
    Quiz findQuizById(Long id);

    void deleteQuizById(Long id);

    Page<Quiz> findAll(Pageable pageable);

}
