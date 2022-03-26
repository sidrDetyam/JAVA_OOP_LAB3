package ru.nsu.ccfit.gemuev.model;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class Server {

    private final String serverUrl;

    public Server(String serverUrl){
        this.serverUrl = serverUrl;
    }

    private synchronized String postJsonRequest(String jsonRequest){

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
            String response = IOUtils.toString(in, StandardCharsets.UTF_8);
            in.close();
            conn.disconnect();
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    public void addEntry(String name, int score){
        postJsonRequest("{ \"type\" : \"add\", \"name\" : \"%s\", \"score\" : \"%d\" }"
                .formatted(name, score));
    }

    public void clearEntries(){
        postJsonRequest("{\"type\" : \"clear\"}");
    }

    public Optional<HighScoreEntry[]> getTopPlayers(int count){
        String response = postJsonRequest("{ \"type\" : \"get\", \"count\" : \"%d\" }".formatted(count));

        if(response.equals("error") || response.equals("")){
            return Optional.empty();
        }
        else{
            String[] raw = response.split("\\|");
            HighScoreEntry[] scores = new HighScoreEntry[raw.length];
            for(int i=0; i<raw.length; ++i){
                String[] tmp = raw[i].split(":");
                scores[i] = new HighScoreEntry(tmp[0], Integer.parseInt(tmp[1]));
            }

            return Optional.of(scores);
        }
    }
}