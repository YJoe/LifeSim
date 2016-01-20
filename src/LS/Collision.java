package LS;

import javafx.scene.shape.Circle;

/**
 * Collision is a class consisting of exclusively static functions used to determine collisions between Circles.
 * Typically an efficient check would be made to eliminate circles that are clearly not colliding followed by the
 * more accurate check to ensure the collision check is valid
 */
public class Collision {
    /**
     * Accurately checks if a circle is colliding with another
     * @param c1 First Circle to check
     * @param c2 Second Circle to check
     * @return If the two Circles are overlapping
     */
    public static boolean overlapsAccurate(Circle c1, Circle c2){
        int x1pos = (int) (c1.getCenterX() + c1.getTranslateX()), y1pos = (int) (c1.getCenterY() + c1.getTranslateY());
        int x2pos = (int) (c2.getCenterX() + c2.getTranslateX()), y2pos = (int) (c2.getCenterY() + c2.getTranslateY());

        double a = Math.pow(x1pos - x2pos, 2);
        double b = Math.pow(y1pos - y2pos, 2);
        double c = Math.pow(c1.getRadius() + c2.getRadius(), 2);

        // (x2-x1)^2 + (y1-y2)^2 <= (r1+r2)^2
        return (a + b <= c);
    }

    /**
     * Efficiently check if a Circle is colliding with another
     * @param c1 First Circle to check
     * @param c2 Second Circle to check
     * @return If the two Circles are overlapping
     */
    public static boolean overlapsEfficient(Circle c1, Circle c2){
        int x1 = (int) (c1.getCenterX() + c1.getTranslateX()), y1 = (int) (c1.getCenterY() + c1.getTranslateY());
        int x2 = (int) (c2.getCenterX() + c2.getTranslateX()), y2 = (int) (c2.getCenterY() + c2.getTranslateY());
        int r1 = (int) c1.getRadius();
        int r2 = (int) c2.getRadius();

        return (x1 - (r2 + r1) < x2 && x2 < x1 + (r2 + r1) &&
                y1 - (r2 + r1) < y2 && y2 < y1 + (r2 + r1));
    }
}
