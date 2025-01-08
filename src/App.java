import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static Stage primaryStage;
    public static Scene mainScene;
    public static Scene gameScene;
    public static String playerName;
    public static int gridSize;
    public static String cardType;
    public static void main(String[] args) throws Exception {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {

        App.primaryStage = primaryStage;
        SceneCreator mainSceneCreator = new MainSceneCreator(500, 500);
        mainScene = mainSceneCreator.createScene();
        



        
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Memory Game");
        primaryStage.show();
    }
}
