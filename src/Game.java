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
                // Remove card from the deck
                Card card = cards.remove(0);
                // Add an event handler to the card
                card.getImageView().setOnMouseClicked(e -> handleCardClick(card));
                // Add the card to the grid
                gridPane.add(card.getCardPane(), j, i);
                
            }
        }

        return gridPane;
    }

    // Logic of turning the card. 
    private void handleCardClick(Card card) {
        // If the card is already matched, do nothing
        if (card.isMatched()) {
            return;
        }
        if (firstSelectedCard == null) {
            firstSelectedCard = card;
            System.out.println("Flip the first clicked card");
            firstSelectedCard.flip();
            
        } else if (secondSelectedCard == null && card != firstSelectedCard) {
            secondSelectedCard = card;
            secondSelectedCard.flip();
            System.out.println("Flip the second clicked card");
            checkForMatch();
        }
    }

    private void checkForMatch() {
        if (firstSelectedCard instanceof Joker) {
            // If the first selected card is a Joker
            // Reset the first selected card
          firstSelectedCard = null; 
        System.out.println("First card was joker, reset first selection");
        } else if (secondSelectedCard instanceof Joker) {
            Joker jokerCard = (Joker) secondSelectedCard;
            if (firstSelectedCard.getImagePath().equals(jokerCard.getImagePath())) {
                // If the second selected card is a Joker and the first selected card has the same image as the Joker
                firstSelectedCard.setMatched(true);
                secondSelectedCard.setMatched(true);
                jokerCard.flipPair();
                
                firstSelectedCard = null;
                secondSelectedCard = null;
            }else {
                // If the second selected card is a Joker, flip the associated pair and flip back the first card only if it's not the same image
                if (!firstSelectedCard.getImagePath().equals(jokerCard.getImagePath())) {
                    firstSelectedCard.flip();
                    System.out.println("Second card is a joker, flip back first");
                }
                jokerCard.flipPair();
                firstSelectedCard = null;
                secondSelectedCard = null;
            }
        } else if (firstSelectedCard.getImagePath().equals(secondSelectedCard.getImagePath())) {
            // If the paths of the two selected cards are the same, they match
            firstSelectedCard.setMatched(true);
            secondSelectedCard.setMatched(true);
            System.out.println("Matched");
            firstSelectedCard = null;
            secondSelectedCard = null;
        } else {
            // If the cards do not match, flip them back after a short delay
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                firstSelectedCard.flip();
                System.out.println("Flip back after delay 1");
                secondSelectedCard.flip();
                System.out.println("Flip back after delay 2");
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