import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
// Dimitrios Vasileiadis 321/2022012
// Panagiotis Salamalikis 321/2022182


public class App extends Application {
    // The primary stage of the application
    public static Stage primaryStage;
    // The main scene of the application (menu)
    public static Scene mainScene;
    // The game scene with the board
    public static Scene gameScene;
    //The following variables are known to the whole application at the time of input 
    public static String playerName;
    public static int gridSize;
    public static String cardType;
    // Difficulty level, 1 easy, 2 medium, 3 hard
    public static int difficulty;
    // To make sure its not different, put it here.
    // Default card width and height, later will change depending on the grid size
    public static int CardWidth = 100;
    public static int CardHeight = 125;
    // Start and end time of a game round
    public static long startTime;
    public static long endTime;
    public static void main(String[] args) throws Exception {
        //Load the records
        GameRecordManager.loadRecords();
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        
        // Set the primary stage
        App.primaryStage = primaryStage;
        // Create the main scene
        SceneCreator mainSceneCreator = new MainSceneCreator(550, 550);
    
        mainScene = mainSceneCreator.createScene();
        
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("Memory Game");
        primaryStage.show();
    }
}
