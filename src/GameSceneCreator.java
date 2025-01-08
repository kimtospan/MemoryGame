import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

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
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        layout.getChildren().add(game.createGameBoard(gridSize));

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