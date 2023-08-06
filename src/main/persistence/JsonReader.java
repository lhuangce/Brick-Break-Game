package persistence;

import model.Ball;
import model.Brick;
import model.BrickBreakGame;
import model.Paddle;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

/**
 * Represents reader that reads BrickBreakGame from file storing JSON data. Based on JsonSerializationDemo project:
 * WorkroomApp.
 */

public class JsonReader {
    private final String source;

    // EFFECTS: constructs reader to read from source file
    public JsonReader(String source) {
        this.source = source;
    }

    // EFFECTS: reads BrickBreakGame from file and returns it;
    // throws IOException if an error occurs reading data from file
    public BrickBreakGame read() throws IOException {
        String jsonData = readFile(source);
        JSONObject jsonObject = new JSONObject(jsonData);
        return parseGame(jsonObject);
    }

    // EFFECTS: reads source file as string and returns it
    private String readFile(String source) throws IOException {
        StringBuilder contentBuilder = new StringBuilder();

        try (Stream<String> stream = Files.lines(Paths.get(source), StandardCharsets.UTF_8)) {
            stream.forEach(contentBuilder::append);
        }

        return contentBuilder.toString();
    }

    // EFFECTS: parses BrickBreakGame from JSON object and returns it
    private BrickBreakGame parseGame(JSONObject jsonObject) {
        BrickBreakGame g = new BrickBreakGame();
        addBall(g, jsonObject);
        addPaddle(g, jsonObject);
        addBricks(g, jsonObject);
        addScore(g, jsonObject);
        return g;
    }

    // MODIFIES: g
    // EFFECTS: gets ball from JSON object and adds it to BrickBreakGame
    private void addBall(BrickBreakGame g, JSONObject jsonObject) {
        int xcoord = jsonObject.getInt("ballX");
        int ycoord = jsonObject.getInt("ballY");
        int dx = jsonObject.getInt("ballDx");
        int dy = jsonObject.getInt("ballDy");

        Ball b = new Ball(xcoord, ycoord, dx, dy);
        g.addBall(b);
    }

    // MODIFIES: g
    // EFFECTS: gets paddle from JSON object and adds it to BrickBreakGame
    private void addPaddle(BrickBreakGame g, JSONObject jsonObject) {
        int xcoord = jsonObject.getInt("paddleX");

        Paddle p = new Paddle(xcoord);
        g.addPaddle(p);
    }

    // MODIFIES: g
    // EFFECTS: gets bricks from JSON object and adds them to BrickBreakGame
    private void addBricks(BrickBreakGame g, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("bricks");
        for (Object json : jsonArray) {
            JSONObject nextBrick = (JSONObject) json;
            addBrick(g, nextBrick);
        }
    }

    // MODIFIES: g
    // EFFECTS: gets brick from JSON object and adds it to BrickBreakGame
    private void addBrick(BrickBreakGame g, JSONObject jsonObject) {
        int xcoord = jsonObject.getInt("brickX");
        int ycoord = jsonObject.getInt("brickY");

        Brick brick = new Brick(xcoord, ycoord);
        g.addBrick(brick);
    }

    // MODIFIES: g
    // EFFECTS: gets score from JSON object and adds it to BrickBreakGame
    private void addScore(BrickBreakGame g, JSONObject jsonObject) {
        int score = jsonObject.getInt("score");

        g.setScore(score);
    }
}
