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

import java.util.List;
  

import javafx.geometry.Pos;

// Responsible for creating the main scene of the application
//Has a menu of options for the user to select from in order to start the game
//Also has info about the game and the creators, along with the last 4 people that played
public class MainSceneCreator extends SceneCreator {
    public MainSceneCreator(double width, double height) {
        super(width, height);
    }

    @Override
    Scene createScene() {
        // The base layout of the main scene is a VBox aligned to the center
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        // Create the title label
        Label titleLabel = new Label("Memory Game");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        // Create the question mark button which will 
        // show info about the game
        Button infoButton = new Button("?");
        infoButton.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        //on being clicked
        infoButton.setOnAction(e -> {
            Stage infoStage = new Stage();
            //Create a new window
            infoStage.initModality(Modality.APPLICATION_MODAL);
            // Using modality obstruct actions on other windows while open
            // title for the window
            infoStage.setTitle("How to Play");
            // The info
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
            // Wrap the text
            

            // Add the label to a stack pane to center it
            StackPane infoLayout = new StackPane();
            infoLayout.getChildren().add(infoLabel);

            // Create the scene and show it
            Scene infoScene = new Scene(infoLayout, 400, 300);
            infoStage.setScene(infoScene);
            infoStage.showAndWait();
        });

        // Put the title and info button in an HBox in order to show together
        HBox titleBox = new HBox(10);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().addAll(titleLabel, infoButton);

        //Under the title we have a text field for the name of the player
        Label nameLabel = new Label("Enter your name:");
        TextField nameField = new TextField();
        nameField.setMaxWidth(200); // Set the maximum width of the TextField
        
        // Add a listener to enforce a maximum of 15 characters and no commas
        // No commas so it doesnt mess with the csv
        // idk if it does but eh
        nameField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 15 || newValue.contains(",") || !newValue.matches("[a-zA-Z]*") ) {
                nameField.setText(oldValue);
            }

        });
          
        

        // Handle the selection of difficulty and card type using a ComboBox for both
        Label difficultyLabel = new Label("Select difficulty:");

    
        ComboBox<String> difficultyComboBox = new ComboBox<>();
        difficultyComboBox.getItems().addAll("4x4 (Easy)", "8x8 (Medium)", "10x10 (Hard)");

        

      
            // The card type selection 
            // Also a combobox
        Label cardTypeLabel = new Label("Select card type:");
        ComboBox<String> cardTypeComboBox = new ComboBox<>();
        cardTypeComboBox.getItems().addAll("Tichu", "Vintage", "Classic");

        Button startGameButton = new Button("Start Game");
        // Upon the click of the start game button, set the globan GridSize.
        // When the game starts, 
        startGameButton.setOnAction(e -> {
            // change the variables depending 
            App.playerName = nameField.getText();
            String difficulty = difficultyComboBox.getValue();
            // Set the variables depending on the difficulty
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
            // Set the card type
            App.cardType = cardTypeComboBox.getValue();

            // Create the game scene
            SceneCreator gameSceneCreator = new GameSceneCreator(1200, 1000);
            App.gameScene = gameSceneCreator.createScene();
            App.primaryStage.setScene(App.gameScene);
            App.primaryStage.setTitle("Game Scene");
            
        });
       // Load the game records
       // I chose the last 4 to not spam 
       // get the records from the cvs file
List<GameRecord> records = GameRecordManager.loadRecords();
// Find out how big it is
int recordCount = records.size();
// Show the last 4
int recordsToShow = Math.min(4, recordCount);
List<GameRecord> lastRecords = records.subList(recordCount - recordsToShow, recordCount);

// Create a VBox to hold the last 4 players
VBox recordsBox = new VBox(10);
recordsBox.setAlignment(Pos.CENTER_RIGHT);
recordsBox.setStyle("-fx-padding: 10px; -fx-border-color: black; -fx-border-width: 1px;");
Label recordsTitle = new Label("Last 4 Players:");
recordsTitle.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");
recordsBox.getChildren().add(recordsTitle);
// for each record in those 4, create a label and add it to the VBox
for (GameRecord record : lastRecords) {
    Label recordLabel = new Label(record.toString());
    recordsBox.getChildren().add(recordLabel);
}

       

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

        // The exit button
        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            App.primaryStage.close();
        });

        layout.getChildren().addAll(titleBox, nameLabel, nameField, difficultyLabel, difficultyComboBox, cardTypeLabel, cardTypeComboBox, startGameButton, aboutButton, exitButton, recordsBox);
        return new Scene(layout, getWidth(), getHeight());
    }
}