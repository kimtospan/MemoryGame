import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<Card> cards;

    public Deck(String[] imagePaths, String backImagePath) {
        cards = new ArrayList<>();
        for (String imagePath : imagePaths) {
            cards.add(new Card(imagePath, backImagePath));
            cards.add(new Card(imagePath, backImagePath)); // Add pairs of cards
        }
        Collections.shuffle(cards);
    }

    public List<Card> getCards() {
        return cards;
    }
}