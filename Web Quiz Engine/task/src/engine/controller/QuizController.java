package engine.controller;

import engine.model.Quiz;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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


@RestController
public class QuizController {
    private final List<Quiz> quizzes = new ArrayList<>();
    private int idCount = 0;

    @GetMapping("/api/quizzes")
    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    @PostMapping("/api/quizzes")
    public ResponseEntity<Quiz> addQuiz(@RequestBody Quiz quiz) {
        quiz.setId(++idCount);
        quizzes.add(quiz);
        return ResponseEntity.ok(quiz);
    }

    @GetMapping("/api/quizzes/{id}")
    public ResponseEntity<Quiz> getQuiz(@PathVariable int id) {
        for (Quiz quiz : quizzes) {
            if (quiz.getId() == id) {
                return ResponseEntity.ok(quiz);
            }
        }
        throw new QuizNotFoundException("Oops! The quiz with specified id: " + id + " could not be found.");
    }

    @PostMapping("/api/quizzes/{id}/solve")
    public ResponseEntity<Response> solveQuiz(@PathVariable int id, @RequestParam int answer) {
        for (Quiz quiz : quizzes) {
            if (quiz.getId() == id) {
                if (quiz.getAnswer() == answer) {
                    return ResponseEntity.ok(new Response(true, "Congratulations, you're right!"));
                } else {
                    return ResponseEntity.ok(new Response(false, "Wrong answer! Please, try again."));
                }
            }
        }
        throw new QuizNotFoundException("Oops! The quiz with specified id: " + id + " could not be found.");
    }
}
