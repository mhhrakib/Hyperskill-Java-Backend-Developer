package engine.repository;

import engine.model.Quiz;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuizRepository extends CrudRepository<Quiz, Long> {
    Quiz findQuizById(Long id);

    List<Quiz> findAll();

}
