import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

public class GameSceneCreator extends SceneCreator {
    private static final int GRID_SIZE = 4;
    private static final String CARD_BACK_IMAGE = "/img/back.png"; 
    private static final String[] CARD_IMAGES = {
         "/img/Dragon.png", "/img/Hound.png", "/img/MahJong.png", "/img/Phoenix.png",
         "/img/Dragon.png", "/img/Hound.png", "/img/MahJong.png", "/img/Phoenix.png",
         "/img/Dragon.png", "/img/Hound.png", "/img/MahJong.png", "/img/Phoenix.png",
         "/img/Dragon.png", "/img/Hound.png", "/img/MahJong.png", "/img/Phoenix.png"};

    public GameSceneCreator(double width, double height) {
        super(width, height);
    }

    @Override
    Scene createScene() {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);

        // Shuffle and place cards
        List<String> cardImages = new ArrayList<>(Arrays.asList(CARD_IMAGES));
        Collections.shuffle(cardImages);

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                ImageView card = createCard(cardImages.remove(0));
                gridPane.add(card, j, i);
            }
        }

        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);
        Button goBackButton = new Button("Go Back");
        goBackButton.setOnAction(e -> {
            App.primaryStage.setScene(App.mainScene);
            App.primaryStage.setTitle("Main Scene");
        });

        layout.getChildren().addAll(gridPane, goBackButton);
        return new Scene(layout, getWidth(), getHeight());
    }

    private ImageView createCard(String imagePath) {
        ImageView card = new ImageView(new Image(getClass().getResource(CARD_BACK_IMAGE).toExternalForm()));
        card.setFitWidth(100);
        card.setFitHeight(150);
        card.setOnMouseClicked(e -> {
            card.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
            // Add game logic here to handle card matching
        });
        return card;
    }
}