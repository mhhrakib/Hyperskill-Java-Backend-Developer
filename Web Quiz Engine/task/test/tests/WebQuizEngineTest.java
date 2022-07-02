package tests;

import engine.WebQuizEngine;
import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.mocks.web.request.HttpRequest;
import org.hyperskill.hstest.mocks.web.response.HttpResponse;
import org.hyperskill.hstest.stage.SpringTest;
import org.hyperskill.hstest.testcase.CheckResult;

import java.util.Map;

import static org.hyperskill.hstest.testing.expect.Expectation.expect;
import static org.hyperskill.hstest.testing.expect.json.JsonChecker.*;

public class WebQuizEngineTest extends SpringTest {
    public WebQuizEngineTest() {
        super(WebQuizEngine.class);
    }

    static void checkStatusCode(HttpResponse resp, int status) {
        if (resp.getStatusCode() != status) {
            throw new WrongAnswer(
                resp.getRequest().getMethod() + " " +
                    resp.getRequest().getLocalUri() +
                    " should respond with status code " + status + ", " +
                    "responded: " + resp.getStatusCode() + "\n\n" +
                    "Response body:\n\n" + resp.getContent()
            );
        }
    }

    @DynamicTestingMethod
    public DynamicTesting[] dt = new DynamicTesting[] {
        this::checkQuizReceived,
        () -> checkQuizSuccess("2", true),
        () -> checkQuizSuccess("1", false)
    };

    private CheckResult checkQuizReceived() {
        String url = "/api/quiz";

        HttpResponse resp = get(url).send();
        checkStatusCode(resp, 200);

        expect(resp.getContent()).asJson().check(
            isObject()
                .value("title", isString())
                .value("text", isString())
                .value("options", isArray(4, isString()))
        );

        return CheckResult.correct();
    }

    private CheckResult checkQuizSuccess(String answerSent, boolean shouldResponse) {
        String url = "/api/quiz";

        HttpRequest req = post(url, Map.of("answer", answerSent));
        HttpResponse resp = req.send();

        checkStatusCode(resp, 200);

        expect(resp.getContent()).asJson().check(
            isObject()
                .value("success", shouldResponse)
                .value("feedback", isString())
        );

        return CheckResult.correct();
    }
}
