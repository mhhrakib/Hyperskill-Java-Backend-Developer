package engine.controller;

import engine.model.Quiz;
import engine.model.User;
import engine.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

class Response {
    Boolean success;
    String feedback;

    public Response(Boolean success, String feedback) {
        this.success = success;
        this.feedback = feedback;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }
}

@ResponseStatus(code = HttpStatus.NOT_FOUND)
class QuizNotFoundException extends RuntimeException {
    public QuizNotFoundException(String cause) {
        super(cause);
    }
}

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
class InvalidQuizException extends RuntimeException {
    public InvalidQuizException(String cause) {
        super(cause);
    }
}


@RestController
public class QuizController {
    private final List<Quiz> quizzes = new ArrayList<>();
    @Autowired
    QuizService quizService;

    @GetMapping("/api/quizzes")
    public List<Quiz> getQuizzes() {
        return quizService.getAllQuiz();
    }

    @PostMapping("/api/quizzes")
    public ResponseEntity<Quiz> addQuiz(@Valid @RequestBody Quiz quiz, @AuthenticationPrincipal User user) {
        try {
            quiz.setCreatedBy(user.getEmail());
            return ResponseEntity.ok(quizService.saveQuiz(quiz));
        } catch (ConstraintViolationException e) {
            throw new InvalidQuizException(e.getMessage());
        }
    }

    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable Long id) {
        Quiz quiz = quizService.findQuizById(id);
        if (quiz != null) {
            return ResponseEntity.ok(quiz);
        } else {
            throw new QuizNotFoundException("Oops! The quiz with specified id: " + id + " could not be found.");
        }
    }

    @DeleteMapping("/api/quizzes/{id}")
    public ResponseEntity deleteQuiz(@PathVariable Long id, @AuthenticationPrincipal User user) {
        Quiz quiz = quizService.findQuizById(id);
        if (quiz != null) {
            if (Objects.equals(quiz.getCreatedBy(), user.getUsername())) {
                quizService.deleteQuizById(id);
                return ResponseEntity.status(HttpStatus.NO_CONTENT)
                        .body("Quiz with id: " + id + " has been deleted.");
            } else {
                return ResponseEntity.status(HttpStatus.FORBIDDEN)
                        .body("You don't have permission to delete quiz with id: " + id);
            }
        } else {
            throw new QuizNotFoundException("Oops! The quiz with specified id: " + id + " could not be found.");
        }
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<Response> solveQuiz(@PathVariable Long id, @RequestBody Map<String, List<Integer>> map) {
        List<Integer> answer = map.get("answer");
        Quiz quiz = quizService.findQuizById(id);
        if (quiz != null) {
            List<Integer> res = new ArrayList<>(quiz.getAnswer());
            if (res.equals(answer)) {
                return ResponseEntity.ok(new Response(true, "Congratulations, you're right!"));
            } else {
                return ResponseEntity.ok(new Response(false, "Wrong answer! Please, try again."));
            }
        } else {
            throw new QuizNotFoundException("Oops! The quiz with specified id: " + id + " could not be found.");
        }
    }
}
