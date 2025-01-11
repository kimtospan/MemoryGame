import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;

public class GameSceneCreator extends SceneCreator {

    public GameSceneCreator(double width, double height) {
        super(width, height);
    }

    @Override
    Scene createScene() {
        String[] selectedImages = ImageManager.getImages(App.cardType);
        String backImagePath = ImageManager.getBackImage(App.cardType);
        int gridSize = App.gridSize; // Get the grid size from the App class

        // Generate the required number of card pairs
        String[] selectedImagesSubset = ImageManager.generateCardPairs(selectedImages, (gridSize * gridSize) / 2);

        Game game = new Game(selectedImagesSubset, backImagePath);
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        Label hiddenCardsLabel = new Label("Hidden Cards: " + game.getHiddenCardsCount());
        hiddenCardsLabel.setStyle("-fx-font-size: 16px;");
        Label remainingTriesLabel = new Label("Remaining Tries: " + game.getRemainingTries());
        remainingTriesLabel.setStyle("-fx-font-size: 16px;");

        game.setOnCardPairSelectedListener((hiddenCardsCount, remainingTries) -> {
            hiddenCardsLabel.setText("Hidden Cards: " + hiddenCardsCount);
            remainingTriesLabel.setText("Remaining Tries: " + remainingTries);
        });

        VBox infoBox = new VBox(10);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        infoBox.getChildren().addAll(hiddenCardsLabel, remainingTriesLabel);

        VBox gameBox = new VBox(10);
        gameBox.setAlignment(Pos.CENTER);
        gameBox.getChildren().add(game.createGameBoard(gridSize));

        HBox mainLayout = new HBox(10);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getChildren().addAll(infoBox, gameBox);

        layout.getChildren().add(mainLayout);

        Button goBackButton = new Button("Go Back");
        goBackButton.setOnAction(e -> {
            App.primaryStage.setScene(App.mainScene);
            App.primaryStage.setTitle("Main Scene");
        });

        Button exitButton = new Button("Exit");
        exitButton.setOnAction(e -> {
            App.primaryStage.close();
        });

        layout.getChildren().addAll(goBackButton, exitButton);
        return new Scene(layout, getWidth(), getHeight());
    }
}