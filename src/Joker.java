import javafx.scene.control.Label;
import javafx.scene.image.Image;
import java.util.List;

// A Joker Is-A Card
public class Joker extends Card {
    private List<Card> pair;
    private Label jokerLabel;

    public Joker(String imagePath, String backImagePath, List<Card> associatedPair) {
        super(imagePath, backImagePath);
        this.pair = associatedPair;

        // Initialize the label to indicate this is a Joker card
        jokerLabel = new Label("JOKER");
        // Hardly visible so make it RED
        // Red bold text FOR THE JOOOOKEEERRR ok 
        jokerLabel.setStyle("-fx-text-fill: red; -fx-font-size: 20px; -fx-font-weight: bold;");
        
        jokerLabel.setVisible(false); // Initially hide the label
        getCardPane().getChildren().add(jokerLabel); // Add the label to the card pane
    }

      @Override
    public void flip() {
        // If the card is on its back, it means the flip function was called to reveal the card
        // since the card is a joker, upon flipping, the associated pair will also be flipped
        if (getImageView().getImage().getUrl().endsWith(getBackImagePath())) {
            // Reveal the associated pair
            flipPair();
            // Set the image to the front image
            getImageView().setImage(new Image(getClass().getResource(getImagePath()).toExternalForm()));
            jokerLabel.setVisible(true); // Show the label when the card is flipped to the front
        } else {
            getImageView().setImage(new Image(getClass().getResource(getBackImagePath()).toExternalForm()));
            jokerLabel.setVisible(false); // Hide the label when the card is flipped to the back
        }
    }
    
    // If the joker is picked, this function will also flip the pair associated with it.
    public void flipPair() {
        // For each card in the pair
        for (Card card : pair) {
            // If they haven't been matched yet and are currently showing their back, flip them
            if (!card.isMatched() && card.getImageView().getImage().getUrl().endsWith(card.getBackImagePath())) {
                card.flip();
                
                
                System.out.println("Flip Pair in Joker");
                card.setMatched(true);
            }
        }
        setMatched(true);
    }
}