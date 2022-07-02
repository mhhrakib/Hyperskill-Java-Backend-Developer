package engine.service;

import engine.model.Quiz;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class QuizService {
    private final QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz saveQuiz(Quiz toSave) {
        return quizRepository.save(toSave);
    }

    public Quiz findQuizById(Long id) {
        return quizRepository.findQuizById(id);
    }

    public void deleteQuizById(Long id) {
        quizRepository.deleteQuizById(id);
    }

    public Page<Quiz> getAllQuiz(Pageable pageable) {
        return quizRepository.findAll(pageable);
    }
}
