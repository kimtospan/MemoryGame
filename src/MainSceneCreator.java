import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.layout.StackPane;
import javafx.geometry.Pos;

public class MainSceneCreator extends SceneCreator {
    public MainSceneCreator(double width, double height) {
        super(width, height);
    }

    @Override
    Scene createScene() {
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        // Create the title label
        Label titleLabel = new Label("Memory Game");
        // Set the style of the title label to bold and font size 24 
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

        // Handle the selection of difficulty and card type using a ComboBox for both
        Label difficultyLabel = new Label("Select difficulty:");
        ComboBox<String> difficultyComboBox = new ComboBox<>();
        difficultyComboBox.getItems().addAll("4x4 (Easy)", "8x8 (Medium)", "10x10 (Hard)");

        Label cardTypeLabel = new Label("Select card type:");
        ComboBox<String> cardTypeComboBox = new ComboBox<>();
        cardTypeComboBox.getItems().addAll("Tichu", "Vintage", "Classic");

        Button startGameButton = new Button("Start Game");
        // Upon the click of the start game button, set the globan GridSize.
        startGameButton.setOnAction(e -> {
            App.playerName = nameField.getText();
            String difficulty = difficultyComboBox.getValue();
            if (difficulty.equals("4x4 (Easy)")) {
                App.gridSize = 4;
                App.CardWidth = 100;
                App.CardHeight = 125;
                App.difficulty = 1;
            } else if (difficulty.equals("8x8 (Medium)")) {
                App.gridSize = 8;
                App.CardWidth = 80;
                App.CardHeight = 90;
                App.difficulty = 2;
            } else if (difficulty.equals("10x10 (Hard)")) {
                App.gridSize = 10;
                App.CardWidth = 70;
                App.CardHeight = 80;
                App.difficulty = 3;
            }
            App.cardType = cardTypeComboBox.getValue();

            SceneCreator gameSceneCreator = new GameSceneCreator(1200, 1000);
            App.gameScene = gameSceneCreator.createScene();
            App.primaryStage.setScene(App.gameScene);
            App.primaryStage.setTitle("Game Scene");
            
        });

        Button aboutButton = new Button("About");
        aboutButton.setOnAction(e -> {
            Stage aboutStage = new Stage();
            //Modality will block actions on other windows while the about window is open
            aboutStage.initModality(Modality.APPLICATION_MODAL);
            aboutStage.setTitle("About");

            Label aboutLabel = new Label("Made by Vasiliadis Dimitrios and Salamalikis Panagiotis as a class project for \"Object-Oriented Programming 2\"");
            aboutLabel.setWrapText(true);
            aboutLabel.setStyle("-fx-padding: 10px;");

            StackPane aboutLayout = new StackPane();
            aboutLayout.getChildren().add(aboutLabel);

            Scene aboutScene = new Scene(aboutLayout, 400, 200);
            aboutStage.setScene(aboutScene);
            aboutStage.showAndWait();
        });

        layout.getChildren().addAll(titleLabel, nameLabel, nameField, difficultyLabel, difficultyComboBox, cardTypeLabel, cardTypeComboBox, startGameButton, aboutButton);
        return new Scene(layout, getWidth(), getHeight());
    }
}