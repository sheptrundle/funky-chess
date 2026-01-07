package UI.Images;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class CircleBuilder {
    public static Circle buildCircle(String color) {
        Circle circle = new Circle(33);
        circle.setMouseTransparent(true); // clicks pass through

        switch (color) {
            case "green":
                circle.setFill(Color.rgb(150, 200, 150, 0.6));
                break;
            case "red":
                circle.setFill(Color.rgb(200, 150, 150, 0.6));
                break;
            case "blue":
                circle.setFill(Color.rgb(80, 140, 255, 0.6));
                break;
            default:
                throw new IllegalArgumentException("Invalid color for making Circle: " + color);
        }
        return circle;
    }
}
