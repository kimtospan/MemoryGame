import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

// Represents a deck of cards
public class Deck {
    private List<Card> cards;
    

    public Deck(String[] imagePaths, String backImagePath, Game game) {
        cards = new ArrayList<>();
        List<String> availableImages = new ArrayList<>();
        Collections.addAll(availableImages, imagePaths);
        Collections.shuffle(availableImages, new Random());
        
        

        int numPairs = (App.gridSize * App.gridSize) / 2 - 1; // Subtract a pair for the 2 Jokers

        for (int i = 0; i < numPairs; i++) {
            String imagePath = availableImages.get(i % availableImages.size());
            cards.add(new Card(imagePath, backImagePath));
            cards.add(new Card(imagePath, backImagePath)); // Add pairs of cards
        }

        // Pick 2 pairs to create Jokers for
        // 
        List<Card> jokerPair1 = new ArrayList<>();
        List<Card> jokerPair2 = new ArrayList<>();
        Collections.shuffle(cards, new Random());
        // Deck is shuffled, so we can just pick 
        //index 0 for Jocker 1 
        //index 1 for Jocker 2
        String imagePathJocker1 = cards.get(0).getImagePath();
        String imagePathJocker2 = cards.get(1).getImagePath();
        findMatching(imagePathJocker1, jokerPair1);
        findMatching(imagePathJocker2, jokerPair2);
        // Add Joker cards to the deck
        cards.add(new Joker(imagePathJocker1, backImagePath, jokerPair1, game));
        cards.add(new Joker(imagePathJocker2, backImagePath, jokerPair2, game));

        Collections.shuffle(cards);
    }
    private void findMatching(String imagePath, List<Card> jokerPair) {
        // The jocker now has an image, search the pair that has that image 
        // and add it to the jocker pair
        for (Card card : cards) {
            if (card.getImagePath().equals(imagePath)) {
                jokerPair.add(card);
            }
        }
        
    }

    public List<Card> getCards() {
        return cards;
    }
}