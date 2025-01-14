import javafx.scene.Scene;
// SceneCreator is an abstract class that creates a Scene object

public abstract class SceneCreator {
    private double width;
    private double height;

    public SceneCreator(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    abstract Scene createScene();
}