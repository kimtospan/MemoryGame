import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class GameSceneCreator extends SceneCreator {
    public GameSceneCreator(double width, double height) {
        super(width, height);
    }

    @Override
    Scene createScene() {
        VBox layout = new VBox(10); // 10 is the spacing between elements
        Button goBackButton = new Button("Go Back");
        goBackButton.setOnAction(e -> {
            App.primaryStage.setScene(App.mainScene);
            App.primaryStage.setTitle("Main Scene");
        });
        layout.getChildren().add(goBackButton);
        return new Scene(layout, getWidth(), getHeight());
    }
}