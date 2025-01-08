import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class MainSceneCreator extends SceneCreator {
    public MainSceneCreator(double width, double height) {
        super(width, height);
    }

    @Override
    Scene createScene() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Label titleLabel = new Label("Memory Game");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Label nameLabel = new Label("Enter your name:");
        TextField nameField = new TextField();
        nameField.setMaxWidth(200); // Set the maximum width of the TextField

        // Add a listener to enforce a maximum of 15 characters
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 15) {
                nameField.setText(oldValue);
            }
        });

        Label difficultyLabel = new Label("Select difficulty:");
        ComboBox<String> difficultyComboBox = new ComboBox<>();
        difficultyComboBox.getItems().addAll("4x4 (Easy)", "8x8 (Medium)", "10x10 (Hard)");

        Label cardTypeLabel = new Label("Select card type:");
        ComboBox<String> cardTypeComboBox = new ComboBox<>();
        cardTypeComboBox.getItems().addAll("Tichu", "Unavailable", "Unavailable");

        Button startGameButton = new Button("Start Game");
        startGameButton.setOnAction(e -> {
            App.playerName = nameField.getText();
            String difficulty = difficultyComboBox.getValue();
            if (difficulty.equals("4x4 (Easy)")) {
                App.gridSize = 4;
            } else if (difficulty.equals("8x8 (Medium)")) {
                App.gridSize = 8;
            } else if (difficulty.equals("10x10 (Hard)")) {
                App.gridSize = 10;
            }
            App.cardType = cardTypeComboBox.getValue();

            SceneCreator gameSceneCreator = new GameSceneCreator(1200, 1000);
            App.gameScene = gameSceneCreator.createScene();
            App.primaryStage.setScene(App.gameScene);
            App.primaryStage.setTitle("Game Scene");
        });

        layout.getChildren().addAll(titleLabel, nameLabel, nameField, difficultyLabel, difficultyComboBox, cardTypeLabel, cardTypeComboBox, startGameButton);
        return new Scene(layout, getWidth(), getHeight());
    }
}