import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class GameSceneCreator extends SceneCreator {
    private static final String[] TICHU_IMAGES = {
        "/img/tichu/Dragon.png", "/img/tichu/Hound.png", "/img/tichu/MahJong.png", "/img/tichu/Phoenix.png",
        "/img/tichu/Dragon.png", "/img/tichu/Hound.png", "/img/tichu/MahJong.png", "/img/tichu/Phoenix.png",
        "/img/tichu/Dragon.png", "/img/tichu/Hound.png", "/img/tichu/MahJong.png", "/img/tichu/Phoenix.png",
        "/img/tichu/Dragon.png", "/img/tichu/Hound.png", "/img/tichu/MahJong.png", "/img/tichu/Phoenix.png"
    };

    private static final String[] TYPE2_IMAGES = {
        "/img/type2/Apple.png", "/img/type2/Banana.png", "/img/type2/Cherry.png", "/img/type2/Grape.png",
        "/img/type2/Apple.png", "/img/type2/Banana.png", "/img/type2/Cherry.png", "/img/type2/Grape.png",
        "/img/type2/Apple.png", "/img/type2/Banana.png", "/img/type2/Cherry.png", "/img/type2/Grape.png",
        "/img/type2/Apple.png", "/img/type2/Banana.png", "/img/type2/Cherry.png", "/img/type2/Grape.png"
    };

    private static final String[] TYPE3_IMAGES = {
        "/img/type3/Car.png", "/img/type3/Bus.png", "/img/type3/Train.png", "/img/type3/Plane.png",
        "/img/type3/Car.png", "/img/type3/Bus.png", "/img/type3/Train.png", "/img/type3/Plane.png",
        "/img/type3/Car.png", "/img/type3/Bus.png", "/img/type3/Train.png", "/img/type3/Plane.png",
        "/img/type3/Car.png", "/img/type3/Bus.png", "/img/type3/Train.png", "/img/type3/Plane.png"
    };

    private static final String TICHU_BACK_IMAGE = "/img/tichu/back.png";
    private static final String TYPE2_BACK_IMAGE = "/img/type2/back.png";
    private static final String TYPE3_BACK_IMAGE = "/img/type3/back.png";

    public GameSceneCreator(double width, double height) {
        super(width, height);
    }

    @Override
    Scene createScene() {
        String[] selectedImages;
        String backImagePath;
        switch (App.cardType) {
            case "Tichu":
                selectedImages = TICHU_IMAGES;
                backImagePath = TICHU_BACK_IMAGE;
                break;
            case "Type 2":
                selectedImages = TYPE2_IMAGES;
                backImagePath = TYPE2_BACK_IMAGE;
                break;
            case "Type 3":
                selectedImages = TYPE3_IMAGES;
                backImagePath = TYPE3_BACK_IMAGE;
                break;
            default:
                selectedImages = TICHU_IMAGES; // Default to Tichu if no valid selection
                backImagePath = TICHU_BACK_IMAGE;
                break;
        }

        Game game = new Game(selectedImages, backImagePath);
        VBox layout = new VBox(10);
        layout.setAlignment(javafx.geometry.Pos.CENTER);
        layout.getChildren().add(game.createGameBoard(App.gridSize));

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