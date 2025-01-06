import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.geometry.Pos;

public class MainSceneCreator extends SceneCreator {
    public MainSceneCreator(double width, double height) {
        super(width, height);
    }

    @Override
    Scene createScene() {
        FlowPane rootFlowPane = new FlowPane();
        rootFlowPane.setAlignment(Pos.CENTER);
        rootFlowPane.setVgap(10);

        Button startGameButton = new Button("Start Game");
        startGameButton.setOnAction(e -> {
            App.primaryStage.setScene(App.gameScene);
            App.primaryStage.setTitle("Game Scene");
        });

        rootFlowPane.getChildren().add(startGameButton);
        return new Scene(rootFlowPane, getWidth(), getHeight());
    }
}