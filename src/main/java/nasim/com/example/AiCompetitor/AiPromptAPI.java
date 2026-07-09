package nasim.com.example.AiCompetitor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class AiPromptAPI {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String requestBody;

    public AiPromptAPI(String prompt) {
        this.requestBody = """
                {
                  "model": "llama3.2",
                  "prompt": %s,
                  "stream": false
                }
                """.formatted(toJsonString(prompt));
    }

    public String callPrompt() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://ollama:11434/api/generate"))
                    .timeout(Duration.ofSeconds(60))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return extractResponse(response.body());

        } catch (IOException e) {
            return "Could not connect to the AI model.";
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "The AI request was interrupted.";
        }
    }

    private String extractResponse(String json) {
        String key = "\"response\":\"";
        int start = json.indexOf(key);

        if (start == -1) {
            return "No response generated.";
        }

        start += key.length();
        int end = json.indexOf("\"", start);

        if (end == -1) {
            return "No response generated.";
        }

        return json.substring(start, end)
                .replace("\\n", " ")
                .replace("\\\"", "\"")
                .trim();
    }

    private String toJsonString(String text) {
        return "\"" + text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"")
                .replace("\n", "\\n")
                .replace("\r", "\\r") + "\"";
    }
}