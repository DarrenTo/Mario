package physics2d;

import org.joml.Vector2f;
import org.junit.jupiter.api.Test;
import physics2d.rigidbody.IntersectionDetector2D;
import renderer.Line2D;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollisionDetectorTests {
    //if comparing floating point numbers
    private final float EPSILON = 0.000001f;

    @Test
    public void pointOnLineStartPoint() {
        Line2D line = new Line2D(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(0, 0);

        assertTrue(IntersectionDetector2D.pointOnLine(point, line));
    }

    @Test
    public void pointOnLineEndPoint() {
        Line2D line = new Line2D(new Vector2f(0, 0), new Vector2f(12, 4));
        Vector2f point = new Vector2f(12, 4);

        assertTrue(IntersectionDetector2D.pointOnLine(point, line));
    }

    @Test
    public void pointOnLineVertical() {
        Line2D line = new Line2D(new Vector2f(0, 0), new Vector2f(0, 10));
        Vector2f point = new Vector2f(0, 5);

        assertTrue(IntersectionDetector2D.pointOnLine(point, line));
    }

    @Test
    public void pointOnLineHorizontal() {
        Line2D line = new Line2D(new Vector2f(0, 0), new Vector2f(10, 0));
        Vector2f point = new Vector2f(5, 0);

        assertTrue(IntersectionDetector2D.pointOnLine(point, line));
    }
}
