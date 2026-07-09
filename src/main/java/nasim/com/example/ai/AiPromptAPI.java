package nasim.com.example.ai;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class AiPromptAPI {

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final String requestBody;
    private final String baseUrl;

    public AiPromptAPI(String prompt) {
        this.baseUrl = System.getenv().getOrDefault("AI_BASE_URL", "http://localhost:11434");
        String model = System.getenv().getOrDefault("AI_MODEL", "qwen2.5:3b");
        this.requestBody = """
                {
                  "model": %s,
                  "prompt": %s,
                  "stream": false
                }
                """.formatted(toJsonString(model),toJsonString(prompt));

    }

    public String callPrompt() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl+"/api/generate"))
                    .timeout(Duration.ofSeconds(60))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response =
                    httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            return extractResponse(response.body());


        } catch (IOException e) {
            e.printStackTrace();
            return "Could not connect to the AI model: " + e.getMessage();
        } catch (InterruptedException e) {
            e.printStackTrace();
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