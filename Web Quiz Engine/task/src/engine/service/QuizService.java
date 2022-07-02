package engine.service;

import engine.model.Quiz;
import engine.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
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

    public List<Quiz> getAllQuiz() {
        return quizRepository.findAll();
    }
}
