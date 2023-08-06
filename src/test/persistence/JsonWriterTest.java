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
 * Unit tests for JsonWriter class. Based on JsonSerializationDemo project: WorkroomApp.
 */
public class JsonWriterTest extends JsonTest {
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterNoBricksGame() {
        try {
            BrickBreakGame g = new BrickBreakGame();
            Ball b = new Ball(400, 500, 2, -2);
            Paddle p = new Paddle(500);
            g.addBall(b);
            g.addPaddle(p);

            JsonWriter writer = new JsonWriter("./data/testWriterNoBricksGame.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterNoBricksGame.json");
            g = reader.read();
            assertEquals(400, b.getX());
            assertEquals(500, b.getY());
            assertEquals(2, b.getDx());
            assertEquals(-2, b.getDy());
            assertEquals(500, p.getX());
            assertEquals(0, g.getBricks().size());
            assertEquals(0, g.getScore());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralGame() {
        try {
            BrickBreakGame g = new BrickBreakGame();
            Ball b = new Ball(400, 500, 2, -2);
            Paddle p = new Paddle(500);
            Brick b1 = new Brick(40, 75);
            Brick b2 = new Brick(200, 75);
            Brick b3 = new Brick(280, 75);
            g.addBall(b);
            g.addPaddle(p);
            g.addBrick(b1);
            g.addBrick(b2);
            g.addBrick(b3);
            g.setScore(1);

            JsonWriter writer = new JsonWriter("./data/testWriterGeneralGame.json");
            writer.open();
            writer.write(g);
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterGeneralGame.json");
            g = reader.read();
            assertEquals(400, b.getX());
            assertEquals(500, b.getY());
            assertEquals(2, b.getDx());
            assertEquals(-2, b.getDy());
            assertEquals(500, p.getX());

            ArrayList<Brick> bricks = g.getBricks();
            assertEquals(3, bricks.size());
            checkBrick(40, 75, bricks.get(0));
            checkBrick(200, 75, bricks.get(1));
            checkBrick(280, 75, bricks.get(2));
            assertEquals(1, g.getScore());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
