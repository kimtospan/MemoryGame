import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// Represents a deck of cards
public class Deck {
    private List<Card> cards;

    public Deck(String[] imagePaths, String backImagePath) {
        cards = new ArrayList<>();
        List<String> availableImages = new ArrayList<>();
        Collections.addAll(availableImages, imagePaths);
        Collections.shuffle(availableImages, new Random());

        int numPairs = (App.gridSize * App.gridSize) / 2;

        for (int i = 0; i < numPairs; i++) {
            String imagePath = availableImages.get(i % availableImages.size());
            cards.add(new Card(imagePath, backImagePath));
            cards.add(new Card(imagePath, backImagePath)); // Add pairs of cards
        }
        Collections.shuffle(cards);
    }

    public List<Card> getCards() {
        return cards;
    }
}