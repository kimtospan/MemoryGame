import javafx.scene.layout.GridPane;

import java.util.List;

import javafx.animation.PauseTransition;
import javafx.util.Duration;
// Game logic
public class Game {
    private Deck deck;
    private Card firstSelectedCard;
    private Card secondSelectedCard;
    private long startTime;
    private int matchCounter;
    private int matchCounterMax;

    // Construct a game 
    public Game(String[] imagePaths, String backImagePath) {
        deck = new Deck(imagePaths, backImagePath);
        startTime = System.currentTimeMillis();
        matchCounter = 0;
        matchCounterMax = (App.gridSize * App.gridSize) / 2;
    }

    // Function to create the game board
    public GridPane createGameBoard(int gridSize) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new javafx.geometry.Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);
        
        // Loop through the cards and add them to the grid
        // Add an event handler to each card to handle clicks
        List<Card> cards = deck.getCards();
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                Card card = cards.remove(0);
                card.getImageView().setOnMouseClicked(e -> handleCardClick(card));
                gridPane.add(card.getImageView(), j, i);
            }
        }

        return gridPane;
    }

    // Logic of turning the card. 
    private void handleCardClick(Card card) {
        if (firstSelectedCard == null) {
            firstSelectedCard = card;
            firstSelectedCard.flip();
        } else if (secondSelectedCard == null && card != firstSelectedCard) {
            secondSelectedCard = card;
            secondSelectedCard.flip();
            checkForMatch();
        }
    }

    private void checkForMatch() {
        // If the paths of the two selected cards are the same, they match
        // if they match, set the cards to matched and reset the selected cards
        if (firstSelectedCard.getImagePath().equals(secondSelectedCard.getImagePath())) {
            firstSelectedCard.setMatched(true);
            secondSelectedCard.setMatched(true);
            firstSelectedCard = null;
            secondSelectedCard = null;
        } else {
            // If the cards do not match, flip them back after a short delay
            //  PauseTransition is a JavaFX class that pauses the transition for a specified duration
            // 1 sec seems alright
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                firstSelectedCard.flip();
                secondSelectedCard.flip();
                // Reset the selected cards
                firstSelectedCard = null;
                secondSelectedCard = null;
            });
            pause.play();
        }
    }
    // Funcion to check if game is over by checking the value of the counter.
    public void isGameOver() {
        
    }
   /*  private void saveGameRecord() {
        long endTime = System.currentTimeMillis();
        long timeTaken = (endTime - startTime) / 1000; // in seconds
        GameRecord record = new GameRecord(App.playerName, timeTaken, score);
        GameRecordManager.saveRecord(record);
    }
    */
}