package engine.repository;

import engine.model.Quiz;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends CrudRepository<Quiz, Long> {
    Quiz findQuizById(Long id);
    void deleteQuizById(Long id);
    List<Quiz> findAll();

}
