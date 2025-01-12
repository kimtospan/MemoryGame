import javafx.scene.layout.GridPane;
import java.util.List;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
// Game logic
public class Game {
    private Deck deck;
    private Card firstSelectedCard;
    private Card secondSelectedCard;
    private long startTime;
    private int matchCounter;
    private int matchCounterMax;
    // Property to track the number of hidden cards
    private IntegerProperty hiddenCardsCount;
    // Property to track the number of unsuccessful attempts
    private IntegerProperty unsuccessfulAttemptsCount;
    // Property to track the number of remaining tries
    private IntegerProperty remainingTries;
    // Listener for card pair selections
    private OnCardPairSelectedListener onCardPairSelectedListener;

    // Construct a game 
    public Game(String[] imagePaths, String backImagePath) {
        deck = new Deck(imagePaths, backImagePath);
        startTime = System.currentTimeMillis();
        matchCounter = 0;
        matchCounterMax = (App.gridSize * App.gridSize) / 2;
        // Initialize hidden cards count
        hiddenCardsCount = new SimpleIntegerProperty((App.gridSize * App.gridSize) );
        // Initialize unsuccessful attempts count
        unsuccessfulAttemptsCount = new SimpleIntegerProperty(0);
        // Initialize remaining tries based on difficulty level
        int baseTries = 10;
        int additionalTries = (App.difficulty - 1) * 12;
        remainingTries = new SimpleIntegerProperty(baseTries + additionalTries);
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
    //If either card is a joker, call handleJokerMatch(), else call checkForMatch()
    private void handleCardClick(Card card) {
        // If the card is already matched, do nothing
        if (card.isMatched()) {
            return;
        }
        if (firstSelectedCard == null) {
            firstSelectedCard = card;
            System.out.println("Flip the first clicked card");
            firstSelectedCard.flip();
            if (firstSelectedCard instanceof Joker) {
                handleJokerMatch();
            }
        } else if (secondSelectedCard == null && card != firstSelectedCard) {
            secondSelectedCard = card;
            secondSelectedCard.flip();
            System.out.println("Flip the second clicked card");
            if (secondSelectedCard instanceof Joker) {
                handleJokerMatch();
            } else {
                checkForMatch();
            }
        }
    }

    private void checkForMatch() {
        if (firstSelectedCard.getImagePath().equals(secondSelectedCard.getImagePath())) {
            // If the paths of the two selected cards are the same, they match
            firstSelectedCard.setMatched(true);
            secondSelectedCard.setMatched(true);
            hiddenCardsCount.set(hiddenCardsCount.get() - 2); // Decrease hidden cards count
            // Notify listener
            if (onCardPairSelectedListener != null) {
                onCardPairSelectedListener.onCardPairSelected(hiddenCardsCount.get(), remainingTries.get());
            }
            System.out.println("Matched");
            firstSelectedCard = null;
            secondSelectedCard = null;
        } else {
            // If the cards do not match, flip them back after a short delay
            // Decrease remaining tries count
            remainingTries.set(remainingTries.get() - 1);
            if (remainingTries.get() <= 0) {
                System.out.println("Game Over");
                // Display "Game Over" and end the game
                // You can add more logic here to handle the end of the game, such as disabling further clicks
                // and showing a "Game Over" message to the user.
                return;
            }
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                firstSelectedCard.flip();
                System.out.println("Flip back after delay 1");
                secondSelectedCard.flip();
                System.out.println("Flip back after delay 2");
                firstSelectedCard = null;
                secondSelectedCard = null;
                // Notify listener
                if (onCardPairSelectedListener != null) {
                    onCardPairSelectedListener.onCardPairSelected(hiddenCardsCount.get(), remainingTries.get());
                }
            });
            pause.play();
        }
    }

    private void handleJokerMatch() {
        if (firstSelectedCard instanceof Joker) {
            System.out.println("First card is a joker, reset first selection");
            // Reset the clicks
            firstSelectedCard = null; 
            
        } else if (secondSelectedCard instanceof Joker) {
            Joker jokerCard = (Joker) secondSelectedCard;
            if (firstSelectedCard.getImagePath().equals(jokerCard.getImagePath())) {
                // If the second selected card is a Joker and the first selected card has the same image as the Joker
                firstSelectedCard.setMatched(true);
                secondSelectedCard.setMatched(true);
                
                 
                System.out.println("Match found with a joker");
            } else {
                // If the second selected card is a Joker but the first selected card does not have the same image
                firstSelectedCard.flip(); // Flip the first card back
                
                System.out.println("Second card is a joker, no match found");
            }

            firstSelectedCard = null;
            secondSelectedCard = null;
        }

        // Notify listener
        if (onCardPairSelectedListener != null) {
            onCardPairSelectedListener.onCardPairSelected(hiddenCardsCount.get(), remainingTries.get());
        }
        // Since the function is called only whe a joker is clicked
        // Decrease the hidden cards count by 3 (joker and its pair)
        hiddenCardsCount.set(hiddenCardsCount.get() - 3);

    }

    // Return the number of hidden cards
    public int getHiddenCardsCount() {
        return hiddenCardsCount.get();
    }
    private int decreaseHiddenCardsCount() {
        return hiddenCardsCount.get() - 1;
    }

    // Return the number of remaining tries
    public int getRemainingTries() {
        return remainingTries.get();
    }

    // Return the number of unsuccessful attempts
    public int getUnsuccessfulAttemptsCount() {
        return unsuccessfulAttemptsCount.get();
    }

    // Set the listener for card pair selections
    public void setOnCardPairSelectedListener(OnCardPairSelectedListener listener) {
        this.onCardPairSelectedListener = listener;
    }

    // Interface for the listener
    public interface OnCardPairSelectedListener {
        void onCardPairSelected(int hiddenCardsCount, int remainingTries);
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