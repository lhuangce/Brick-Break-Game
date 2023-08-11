package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Event class. Based on CPSC 210 AlarmSystem project
 * (<a href="https://github.students.cs.ubc.ca/CPSC210/AlarmSystem.git">link</a>).
 */
public class EventTest {
    private Event e;
    private Date d;

    @BeforeEach
    public void runBefore() {
        e = new Event("Brick added to list");   // (1)
        d = Calendar.getInstance().getTime();   // (2)
    }

    @Test
    public void testEvent() {
        assertEquals("Brick added to list", e.getDescription());
        assertTrue(d.getTime() >= e.getDate().getTime() - 50
        && d.getTime() <= e.getDate().getTime() + 50);
    }

    @Test
    public void testEqualsHashCode() {
        Event other = new Event("test");
        Event another = new Event("test");

        assertNotEquals(null, other);
        assertNotEquals("object", other);

        assertNotEquals(e, other);
        assertEquals(other, another);

        assertNotEquals(e.hashCode(), other.hashCode());
        assertEquals(other.hashCode(), another.hashCode());


    }

    @Test
    public void testToString() {
        assertEquals(d.toString() + "\n" + "Brick added to list", e.toString());
    }
}
