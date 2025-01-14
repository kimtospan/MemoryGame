import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;

//The GameSceneCreator class extends SceneCreator and is responsible for creating the game scene 
 // in the application. It overrides the createScene method to set up the game interface. 
 // The method retrieves the selected images and back image path based on the card type from the App class and determines the grid size. 
 // It then generates the required number of card pairs using the ImageManager. A Game object is created with the selected images and back image path. 
 // The layout is set up using a VBox aligned to the center, and several labels are created to display the number of hidden cards, remaining tries, and elapsed time.
 // The elapsedTimeLabel is bound to a StringBinding to dynamically update the elapsed time.
 
public class GameSceneCreator extends SceneCreator {

    public GameSceneCreator(double width, double height) {
        super(width, height);
    }
   @Override
    Scene createScene() {
        // Get the selected images and back image path based on the card type
        String[] selectedImages = ImageManager.getImages(App.cardType);
        String backImagePath = ImageManager.getBackImage(App.cardType);
        // get the grid size
        int gridSize = App.gridSize; // Get the grid size from the App class


        // Generate the required number of card pairs
        // and put it into a new array
    
        String[] selectedImagesSubset = ImageManager.generateCardPairs(selectedImages, (gridSize * gridSize) / 2);

        // Reference the game.
        Game game = new Game(selectedImagesSubset, backImagePath);
        // The base layout of the game scene is a VBox aligned to the center
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        //Start creating the scene. 
        //From the left of the game board i want to display the hidden cards, remaining tries, elapsed time and score

        Label hiddenCardsLabel = new Label("Hidden Cards: " + game.getHiddenCardsCount());
        hiddenCardsLabel.setStyle("-fx-font-size: 16px;");
       
        Label remainingTriesLabel = new Label("Remaining Tries: " + game.getRemainingTries());
        remainingTriesLabel.setStyle("-fx-font-size: 16px;");

        Label elapsedTimeLabel = new Label();
        elapsedTimeLabel.setStyle("-fx-font-size: 16px;");
        StringBinding elapsedTimeBinding = Bindings.createStringBinding(
            () -> "Elapsed Time: " + game.getElapsedTime() + "s",
            game.elapsedTimeProperty()
        );
        elapsedTimeLabel.textProperty().bind(elapsedTimeBinding);

        Label scoreLabel = new Label("Score: " + game.getScore());
        scoreLabel.setStyle("-fx-font-size: 16px;");

        //When scene begins, start the global startTimer
        game.startTimer();

        

        // A listener to update the labels when a card pair is selected
        game.setOnCardPairSelectedListener((hiddenCardsCount, remainingTries, score) -> {
            hiddenCardsLabel.setText("Hidden Cards: " + hiddenCardsCount);
            remainingTriesLabel.setText("Remaining Tries: " + remainingTries);
            scoreLabel.setText("Score: " + score);
            //
            

        });
        // We devide the vbox to the onfo prt and the game part

        VBox infoBox = new VBox(10);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        infoBox.getChildren().addAll(hiddenCardsLabel, remainingTriesLabel, elapsedTimeLabel, scoreLabel);

        VBox gameBox = new VBox(10);
        gameBox.setAlignment(Pos.CENTER);
        gameBox.getChildren().add(game.createGameBoard(gridSize));

        // add the 2 parts together in an hbox
        HBox mainLayout = new HBox(10);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(infoBox, gameBox);

        // Add the hbox to the layout
        layout.getChildren().add(mainLayout);

        //under the game board we have the go back and exit buttons
        Button goBackButton = new Button("Go Back");
        goBackButton.setOnAction(e -> {
            App.primaryStage.setScene(App.mainScene);
            App.primaryStage.setTitle("Main Scene");
        });

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            App.primaryStage.close();
        });

        //add to the total layout the buttons
        layout.getChildren().addAll(goBackButton, exitButton);
        //and the scene is complete
        return new Scene(layout, getWidth(), getHeight());
    }
   
}