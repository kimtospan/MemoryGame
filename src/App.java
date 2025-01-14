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
    // Difficulty level, 1 easy, 2 medium, 3 hard
    public static int difficulty;
    // To make sure its not different, put it here.
    public static int CardWidth = 100;
    public static int CardHeight = 125;
    public static long startTime;
    public static long endTime;
    public static void main(String[] args) throws Exception {
        GameRecordManager.loadRecords();
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {

        App.primaryStage = primaryStage;
        SceneCreator mainSceneCreator = new MainSceneCreator(550, 550);
        mainScene = mainSceneCreator.createScene();
        



        
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Memory Game");
        primaryStage.show();
    }
}
