package exercise;

// BEGIN
public class Circle {
    private Point center;
    private int radius;

    Circle(Point centerPoint, int newRadius) {
        this.center = centerPoint;
        this.radius = newRadius;
    }

    public int getRadius() {
        return this.radius;
    }

    public double getSquare() throws NegativeRadiusException {
        if (radius < 0) throw new NegativeRadiusException("Radius is negative.");
        return Math.PI * radius * radius;
    }
}
// END
