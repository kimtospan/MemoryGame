import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
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
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Create the question mark button
        Button infoButton = new Button("?");
        infoButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        infoButton.setOnAction(e -> {
            Stage infoStage = new Stage();
            infoStage.initModality(Modality.APPLICATION_MODAL);
            infoStage.setTitle("How to Play");

            Label infoLabel = new Label(
                "Match pairs of cards by flipping them over. You have limited tries, and are being timed, so try to remember!\n\n" +
                "Score Calculation:\n" +
                "- Each match increases your score by 25 points multiplied by a certain multiplier.\n" +
                "- The multiplier starts at 1 and doubles with each consecutive match.\n" +
                "- On an unsuccesfull attempt, the multiplier resets to 1 again.\n\n" +
                "Joker Card:\n" +
                "- The joker card automatically reveals its associated pair when flipped.\n" +
                "- The joker card and its pair are considered matched and increase your score."
            );
            infoLabel.setWrapText(true);
            

            StackPane infoLayout = new StackPane();
            infoLayout.getChildren().add(infoLabel);

            Scene infoScene = new Scene(infoLayout, 400, 300);
            infoStage.setScene(infoScene);
            infoStage.showAndWait();
        });

        HBox titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(titleLabel, infoButton);

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
        //The about button
        Button aboutButton = new Button("About");
        //On being clicked
        aboutButton.setOnAction(e -> {
            // Create a new window
            Stage aboutStage = new Stage();
            //Modality will block actions on other windows while the about window is open
            aboutStage.initModality(Modality.APPLICATION_MODAL);
            //Set the title of the window to about
            aboutStage.setTitle("About");
            //Add the label talking about us
            Label aboutLabel = new Label("Made by Vasiliadis Dimitrios and Salamalikis Panagiotis as a class project for \"Object-Oriented Programming 2\"");
            aboutLabel.setWrapText(true);
            aboutLabel.setStyle("-fx-padding: 10px;");

            //Add the label to a stack pane to center it
            StackPane aboutLayout = new StackPane();
            aboutLayout.getChildren().add(aboutLabel);

            //Create the scene and show it
            Scene aboutScene = new Scene(aboutLayout, 400, 200);
            aboutStage.setScene(aboutScene);
            aboutStage.showAndWait();
        });

        layout.getChildren().addAll(titleBox, nameLabel, nameField, difficultyLabel, difficultyComboBox, cardTypeLabel, cardTypeComboBox, startGameButton, aboutButton);
        return new Scene(layout, getWidth(), getHeight());
    }
}