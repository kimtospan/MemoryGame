import javafx.scene.layout.GridPane;

import java.util.List;

import javafx.animation.PauseTransition;
import javafx.util.Duration;

public class Game {
    private Deck deck;
    private Card firstSelectedCard;
    private Card secondSelectedCard;

    public Game(String[] imagePaths, String backImagePath) {
        deck = new Deck(imagePaths, backImagePath);
    }

    public GridPane createGameBoard(int gridSize) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new javafx.geometry.Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(javafx.geometry.Pos.CENTER);

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
        if (firstSelectedCard.getImagePath().equals(secondSelectedCard.getImagePath())) {
            firstSelectedCard.setMatched(true);
            secondSelectedCard.setMatched(true);
            firstSelectedCard = null;
            secondSelectedCard = null;
        } else {
            // Flip the cards back after a short delay
            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
                firstSelectedCard.flip();
                secondSelectedCard.flip();
                firstSelectedCard = null;
                secondSelectedCard = null;
            });
            pause.play();
        }
    }
}