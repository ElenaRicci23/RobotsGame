package robots.model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class DefaultAreaTest {

    @Test
    void sizeTest() {
        DefaultArea defaultArea = new DefaultArea();
        assertEquals(1650, defaultArea.getWeight());
        assertEquals(1080, defaultArea.getHeight());
    }

    @Test
    void robotTest() {
        DefaultArea defaultArea = new DefaultArea();
        Robot r1 = defaultArea.getRobot(0);
        assertEquals(0, r1.getId());
        assertEquals(r1, defaultArea.getRobot(0));

        Robot r2 = defaultArea.getRobot(70);
        assertEquals(70, r2.getId());

        CartesianCoordinates c = new CartesianCoordinates(70,45);
        int speed = 5;
        Robot r3 = defaultArea.getRobot(56);
        defaultArea.updateRobotPosition(56, c, speed);
        assertEquals(c, r3.getPosition());
        assertEquals(5, r3.getSpeed());

        CartesianCoordinates c1 = new CartesianCoordinates(45, 67);
        Robot r4 = new Robot(c1);
        defaultArea.addRobot(r4);
        assertEquals(71, r4.getId());

        String l1 = r4.getLabel();
        r3.setLabel(l1);
        assertEquals(r4.getLabel(), r3.getLabel());
        ArrayList<Robot> robotsWithSameLabel = defaultArea.robotsWithSameLabel(l1);
        for (Robot robot : robotsWithSameLabel) {
            String label = robot.getLabel();
            assertEquals(r3.getLabel(), label);
            assertEquals(r4.getLabel(), label);
        }
    }

    @Test
    void shapes() {
        DefaultArea defaultArea = new DefaultArea();
        assertFalse(defaultArea.getRobots().isEmpty());
        assertFalse(defaultArea.getRectangles().isEmpty());
        assertFalse(defaultArea.getCircles().isEmpty());
    }
}
