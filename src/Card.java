import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

// Represents a card in the game
public class Card implements Flippable {
    // The path to the image of both the card and its back version
    private String imagePath;
    private String backImagePath;
    // The image view of the card, imageView is a JavaFX node that displays an image
    private ImageView imageView;
    // Whether the card has been matched with another card
    private boolean isMatched;
    //The pane holds the image view and allows the joker to stack a label on top of it
    protected StackPane cardPane;

    public Card(String imagePath, String backImagePath) {
        this.imagePath = imagePath;
        this.backImagePath = backImagePath;
        // Load the back image of the card by default. On user click, we load the front image
        this.imageView = new ImageView(new Image(getClass().getResource(backImagePath).toExternalForm()));
        this.imageView.setFitWidth(App.CardWidth);
        this.imageView.setFitHeight(App.CardHeight);
        //I use stack pane so the child class Joker can stack the image view with a laber, the laber obviously being "Joker"
        this.cardPane = new StackPane(imageView);
        this.isMatched = false;
    }
    public StackPane getCardPane() {
        return cardPane;
    }

    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public void flip() {
        // When its time to flip the image
        //if the image is the back image, flip it to the front image
        if (imageView.getImage().getUrl().endsWith(backImagePath)) {
            imageView.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
        } else {
            //if the image is the front image, flip it to the back image
            imageView.setImage(new Image(getClass().getResource(backImagePath).toExternalForm()));
        }
    }

    public boolean isMatched() {
        return isMatched;
    }

    public void setMatched(boolean matched) {
        isMatched = matched;
    }

    public String getImagePath() {
        return imagePath;
    }
    public String getBackImagePath() {
        return backImagePath;
    }
}