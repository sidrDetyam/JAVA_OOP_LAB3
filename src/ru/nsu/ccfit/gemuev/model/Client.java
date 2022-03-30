package ru.nsu.ccfit.gemuev.model;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;
import java.util.Scanner;

public record Client(String serverUrl) {

    private synchronized @NotNull String postJsonRequest(String jsonRequest) {

        try {
            URL url = new URL(serverUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(5000);
            conn.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            OutputStream os = conn.getOutputStream();
            os.write(jsonRequest.getBytes(StandardCharsets.UTF_8));
            os.close();

            InputStream in = new BufferedInputStream(conn.getInputStream());
            //String response = IOUtils.toString(in, StandardCharsets.UTF_8);
            StringBuilder response = new StringBuilder();
            Scanner scanner = new Scanner(in);
            while(scanner.hasNext()){
                response.append(scanner.next());
            }

            in.close();
            conn.disconnect();
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public void addEntry(@NotNull String name, int score, int level) {
        postJsonRequest("{ \"type\" : \"add\", \"name\" : \"%s\", \"score\" : \"%d\", \"levelID\":\"%d\" }"
                .formatted(name, score, level));
    }

    public void clearEntries() {
        postJsonRequest("{\"type\" : \"clear\"}");
    }

    public Optional<HighScoreEntry[]> getTopPlayers(int count) {
        String response = postJsonRequest("{ \"type\" : \"get\", \"count\" : \"%d\" }".formatted(count));

        if (response.equals("error")) {
            return Optional.empty();
        } else {
            if (response.equals("")) {
                return Optional.of(new HighScoreEntry[0]);
            }

            String[] raw = response.split("\\|");
            HighScoreEntry[] scores = new HighScoreEntry[raw.length];
            for (int i = 0; i < raw.length; ++i) {
                String[] tmp = raw[i].split(":");
                scores[i] = new HighScoreEntry(tmp[0], Integer.parseInt(tmp[1]), Integer.parseInt(tmp[2]));
            }

            return Optional.of(scores);
        }
    }
}
