import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card implements Flippable {
    private String imagePath;
    private String backImagePath;
    private ImageView imageView;
    private boolean isMatched;

    public Card(String imagePath, String backImagePath) {
        this.imagePath = imagePath;
        this.backImagePath = backImagePath;
        this.imageView = new ImageView(new Image(getClass().getResource(backImagePath).toExternalForm()));
        this.imageView.setFitWidth(100);
        this.imageView.setFitHeight(150);
        this.isMatched = false;
    }

    public ImageView getImageView() {
        return imageView;
    }

    @Override
    public void flip() {
        if (imageView.getImage().getUrl().endsWith(backImagePath)) {
            imageView.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
        } else {
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
}