package tests;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import engine.WebQuizEngine;
import org.hyperskill.hstest.dynamic.input.DynamicTesting;
import org.hyperskill.hstest.dynamic.input.DynamicTestingMethod;
import org.hyperskill.hstest.exception.outcomes.WrongAnswer;
import org.hyperskill.hstest.mocks.web.request.HttpRequest;
import org.hyperskill.hstest.mocks.web.response.HttpResponse;
import org.hyperskill.hstest.stage.SpringTest;
import org.hyperskill.hstest.testcase.CheckResult;

import java.util.Map;

import static org.hyperskill.hstest.common.JsonUtils.getJson;
import static org.hyperskill.hstest.common.JsonUtils.getPrettyJson;
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

    private static int quizId1 = 0;
    private static int quizId2 = 0;

    private static String quiz1 =
        "{\n" +
            "  \"title\": \"The Java Logo\",\n" +
            "  \"text\": \"What is depicted on the Java logo?\",\n" +
            "  \"options\": [\"Robot\",\"Tea leaf\",\"Cup of coffee\",\"Bug\"],\n" +
            "  \"answer\": 2\n" +
            "}";

    private static String quiz2 =
        "{\n" +
            "  \"title\": \"The Ultimate Question\",\n" +
            "  \"text\": \"What is the answer to the Ultimate Question of Life, the Universe and Everything?\",\n" +
            "  \"options\": [\"Everything goes right\",\"42\",\"2+2=4\",\"11011100\"],\n" +
            "  \"answer\": 1\n" +
            "}";

    @DynamicTestingMethod
    public DynamicTesting[] dt = new DynamicTesting[]{
        () -> testAllQuizzes(0),

        () -> testCreateQuiz(1),
        () -> testQuizExists(1),
        () -> testQuizNotExists(1),

        () -> testAllQuizzes(1),

        () -> testCreateQuiz(2),
        () -> testQuizExists(2),
        () -> testQuizNotExists(2),

        () -> testAllQuizzes(2),

        () -> checkQuizSuccess(quizId1, "0", false),
        () -> checkQuizSuccess(quizId1, "1", false),
        () -> checkQuizSuccess(quizId1, "2", true),
        () -> checkQuizSuccess(quizId1, "3", false),

        () -> checkQuizSuccess(quizId2, "0", false),
        () -> checkQuizSuccess(quizId2, "1", true),
        () -> checkQuizSuccess(quizId2, "2", false),
        () -> checkQuizSuccess(quizId2, "3", false),
    };

    private CheckResult testCreateQuiz(int quizNum) {
        String url = "/api/quizzes";
        HttpResponse resp = post(url, quizNum == 1 ? quiz1 : quiz2).send();

        checkStatusCode(resp, 200);

        expect(resp.getContent()).asJson().check(
            isObject()
                .value("id", isInteger(i -> {
                    if (quizNum == 1) {
                        quizId1 = i;
                    } else {
                        quizId2 = i;
                    }
                    return true;
                }))
                .anyOtherValues()
        );

        return CheckResult.correct();
    }

    private CheckResult testQuizExists(int quizNum) {
        int quizId = quizNum == 1 ? quizId1 : quizId2;
        String quiz = quizNum == 1 ? quiz1 : quiz2;

        String url = "/api/quizzes/" + quizId;

        HttpResponse resp = get(url).send();
        checkStatusCode(resp, 200);

        JsonObject rightQuiz = getJson(quiz).getAsJsonObject();
        rightQuiz.remove("answer");
        rightQuiz.addProperty("id", quizId);

        expect(getPrettyJson(rightQuiz)).asJson().check(
            isObject()
                .value("id", quizId)
                .value("title", isString())
                .value("text", isString())
                .value("options", isArray(any()))
        );

        JsonElement json = resp.getJson();

        CheckResult wrongResponse = CheckResult.wrong(
            "The quiz sent to the program looked like this:\n" +
                getPrettyJson(rightQuiz) + "\n\n" +
                "But the received quiz looks like that:\n" +
                getPrettyJson(json)
        );

        if (!json.isJsonObject()) {
            return wrongResponse;
        }

        JsonObject obj = json.getAsJsonObject();

        if (!rightQuiz.equals(obj)) {
            return wrongResponse;
        }

        return CheckResult.correct();
    }

    private CheckResult testQuizNotExists(int quizNum) {
        int quizId = quizNum == 1 ? quizId1 : quizId2;

        String url = "/api/quizzes/" + (quizId + 125);

        HttpResponse resp = get(url).send();
        checkStatusCode(resp, 404);

        return CheckResult.correct();
    }

    private CheckResult testAllQuizzes(int count) {
        String url = "/api/quizzes";
        HttpResponse resp = get(url).send();

        checkStatusCode(resp, 200);

        expect(resp.getContent()).asJson().check(
            isArray(count, isObject().anyOtherValues())
        );

        return CheckResult.correct();
    }

    private CheckResult checkQuizSuccess(int quizNum, String answerSent, boolean shouldResponse) {
        String url = "/api/quizzes/" + quizNum + "/solve";

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
