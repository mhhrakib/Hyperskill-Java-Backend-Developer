package engine.controller;

import engine.model.Quiz;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

@RestController
public class QuizController {
    List<String> options = List.of("Robot", "Tea leaf", "Cup of coffee", "Bug");
    Quiz quiz = new Quiz("The Java Logo", "What is depicted on the Java logo?", options);

    @GetMapping("/api/quiz")
    public Quiz getQuiz() {
        return quiz;
    }

    @PostMapping("/api/quiz")
    public ResponseEntity<Response> answer(@RequestParam int answer) {
        if (answer == 2) {
            return ResponseEntity.ok(new Response(true, "Congratulations, you're right!"));
        } else {
            return ResponseEntity.ok(new Response(false, "Wrong answer! Please, try again."));
        }
    }
}
