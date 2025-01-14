import javafx.scene.layout.GridPane;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.util.Duration;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
// Game logic

public class Game {
    // A game has a deck
    private Deck deck;
    // We keep track of the first and second selected card
    private Card firstSelectedCard;
    private Card secondSelectedCard;
    // We keep track of the time it takes to finish the game
    // We use an animation timer
    //i t updates once per frame(?) 
    private AnimationTimer timer;
    
    // score logic, we need both a score and a multiplier
    private int multiplier = 1;
    private IntegerProperty score;  


//In order to show the intergers (and one long) in real time, we use properties
    // Property to track the number of hidden cards
    private IntegerProperty hiddenCardsCount;
    // Property to track the number of unsuccessful attempts
    private IntegerProperty unsuccessfulAttemptsCount;
    // Property to track the number of remaining tries
    private IntegerProperty remainingTries;
    // Listener for card pair selections
    private OnCardPairSelectedListener onCardPairSelectedListener;
    // Like interger Property, ill show the time as a long property with animation timer which apparently exists
    private LongProperty elapsedTime = new SimpleLongProperty();

    // Construct a game 
    public Game(String[] imagePaths, String backImagePath) {
        // Create the deck for the round 
        deck = new Deck(imagePaths, backImagePath, this);
        
        
        // Initialize hidden cards count
        hiddenCardsCount = new SimpleIntegerProperty((App.gridSize * App.gridSize) );
        // Initialize unsuccessful attempts count
        unsuccessfulAttemptsCount = new SimpleIntegerProperty(0);
        // Initialize remaining tries based on difficulty level
        int baseTries = 10;
        int additionalTries = (App.difficulty - 1) * 12;
        remainingTries = new SimpleIntegerProperty(baseTries + additionalTries);
        // Initialize score
        score = new SimpleIntegerProperty(0);
    }

    // Function to create the game board
    public GridPane createGameBoard(int gridSize) {
        // Create a grid pane to hold the cards in a grid 
    
        GridPane gridPane = new GridPane();
        // tweak with it till it looks somewhat good
        // Padding betweed the card and the border because it looks aufull when stuck toghether
        gridPane.setPadding(new javafx.geometry.Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        // Obv center
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);
        
        // Loop through the cards and add them to the grid
        // Get the cards from the deck and for each card
        // add an event handler to it and add it to the grid
        // The event handler is the logic of clicking (flipping) the card
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

    // What happens when a card is clicked 
    //If either card is a joker, call handleJokerMatch(), else call checkForMatch()
    private void handleCardClick(Card card) {
        // If the card is already matched, do nothing
        // basically if already turned over dont allow clicks
        if (card.isMatched()) {
            return;
        }
        // If the card is not matched, flip it
        if (firstSelectedCard == null) {
            firstSelectedCard = card;
            System.out.println("Flip the first clicked card");
            firstSelectedCard.flip();
            // If that card was a joker, call handleJokerMatch()
            if (firstSelectedCard instanceof Joker) {
                handleJokerMatch();
            }
        } else if (secondSelectedCard == null && card != firstSelectedCard) {
            // If the second card is not the same as the first card
            // so we dont click the same card twice
            secondSelectedCard = card;
            secondSelectedCard.flip();
            System.out.println("Flip the second clicked card");
            // If the second card is a joker, call handleJokerMatch()
            if (secondSelectedCard instanceof Joker) {
                handleJokerMatch();
            } else {
                // Only check for match when neither card is a joker
                checkForMatch();
            }
        }
    }

    // Compare the two selected cards
    // and if add logic when matched.
    private void checkForMatch() {
        if (firstSelectedCard.getImagePath().equals(secondSelectedCard.getImagePath())) {
            // If matched
            // Increase score by 25 points, multiplied by a multiplier that doubles for each
            //concecutive correct match(or joker)
            // integer property to show the score
            score.set(score.get() + multiplier * 25); 
            multiplier = multiplier * 2 ;
            // If the paths of the two selected cards are the same, they match
            firstSelectedCard.setMatched(true);
            secondSelectedCard.setMatched(true);
            hiddenCardsCount.set(hiddenCardsCount.get() - 2); // Decrease hidden cards count
            // Notify listener
            if (onCardPairSelectedListener != null) {
                onCardPairSelectedListener.onCardPairSelected(hiddenCardsCount.get(), remainingTries.get(), score.get());
            }
            System.out.println("Matched");
            firstSelectedCard = null;
            secondSelectedCard = null;
            isGameOver();
        } else {
            // If the cards do not match, flip them back after a short delay
            // Reset multiplier on unsuccessful attempt
            multiplier = 1;

            // PauseTransition is a JavaFX class that pauses, so we use this 
            // for the delay.
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
                    onCardPairSelectedListener.onCardPairSelected(hiddenCardsCount.get(), remainingTries.get(), score.get());
                }
            });
            pause.play();
            // Decrease remaining tries count
            remainingTries.set(remainingTries.get() - 1);
            
        }
    }

    // Handle the case when a joker is clicked
    private void handleJokerMatch() { 
        // Increase score and multiplier. 
        score.set(score.get() + multiplier * 25);

        multiplier = multiplier * 2;
        // Since the function is called only whe a joker is clicked
        // Decrease the hidden cards count by 3 (joker and its pair)

       
        
        System.out.println("Entered Joker Match");
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
            onCardPairSelectedListener.onCardPairSelected(hiddenCardsCount.get(), remainingTries.get(), score.get());
        }
        
        isGameOver();
    }


    public LongProperty elapsedTimeProperty() {
        return elapsedTime;
    }
    public long getElapsedTime() {
        return elapsedTime.get();
    }
    public void setElapsedTime(long elapsedTime) {
        this.elapsedTime.set(elapsedTime);
    }

    // Start the timer when the game starts
    public void startTimer() {
        App.startTime = System.currentTimeMillis();
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long elapsedTime = (System.currentTimeMillis() - App.startTime) / 1000;
                setElapsedTime(elapsedTime);
            }
        };
        timer.start();
    }
    

    
    

    private void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
        App.endTime = System.currentTimeMillis();
    }

    public int getScore() {
        return score.get();
    }

    // Return the number of hidden cards
    public int getHiddenCardsCount() {
        return hiddenCardsCount.get();
    }

    public void decreaseHiddenCardsCount() {
        hiddenCardsCount.set(hiddenCardsCount.get() - 1);
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

    //  Callback mechanism to notify the listener when a card pair is selected
    public interface OnCardPairSelectedListener {
        void onCardPairSelected(int hiddenCardsCount, int remainingTries, int score);
    }
    
    // Funcion to check if game is over by checking the value of the counter.
    public void isGameOver() {
        System.out.println("Checking if game is over");
        if (hiddenCardsCount.get() <= 0 || remainingTries.get() <= 0) {
            System.out.println("Game over");
            stopTimer();
            
            GameRecordManager.saveRecord(App.playerName, getElapsedTime(), score.get());
        }
        
    }
   
}