package persistence;

import model.Ball;
import model.Brick;
import model.BrickBreakGame;
import model.Paddle;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit tests for JsonReader class. Based on JsonSerializationDemo project: WorkroomApp
 * (<a href="https://github.students.cs.ubc.ca/CPSC210/JsonSerializationDemo.git">link</a>).
 */
public class JsonReaderTest extends JsonTest {
    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderNoBricksGame() {
        JsonReader reader = new JsonReader("./data/testReaderNoBricksGame.json");
        try {
            BrickBreakGame g = reader.read();
            Ball b = g.getBall();
            Paddle p = g.getPaddle();

            assertEquals(480, b.getX());
            assertEquals(500, b.getY());
            assertEquals(2, b.getDx());
            assertEquals(2, b.getDy());
            assertEquals(510, p.getX());
            assertEquals(0, g.getBricks().size());
            assertEquals(0, g.getScore());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralGame() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralGame.json");
        try {
            BrickBreakGame g = reader.read();
            Ball b = g.getBall();
            Paddle p = g.getPaddle();

            assertEquals(480, b.getX());
            assertEquals(500, b.getY());
            assertEquals(2, b.getDx());
            assertEquals(2, b.getDy());
            assertEquals(510, p.getX());

            ArrayList<Brick> bricks = g.getBricks();
            assertEquals(3, bricks.size());
            checkBrick(40, 75, bricks.get(0));
            checkBrick(200, 75, bricks.get(1));
            checkBrick(280, 75, bricks.get(2));
            assertEquals(1, g.getScore());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
