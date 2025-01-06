import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {
    public static Stage primaryStage;
    public static Scene mainScene;
    public static Scene gameScene;
    public static void main(String[] args) throws Exception {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {

        App.primaryStage = primaryStage;
        SceneCreator mainSceneCreator = new MainSceneCreator(800, 600);
        SceneCreator gameSceneCreator = new GameSceneCreator(800, 600);
        mainScene = mainSceneCreator.createScene();
        gameScene = gameSceneCreator.createScene();



        
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Memory Game");
        primaryStage.show();
    }
}
