import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
//Class to manage images


public class ImageManager {
    private static final String[] TICHU_IMAGES = {
        "/img/tichu/Dragon.png", "/img/tichu/Hound.png", "/img/tichu/MahJong.png", "/img/tichu/Phoenix.png",
        "/img/tichu/Dragon.png", "/img/tichu/Hound.png", "/img/tichu/MahJong.png", "/img/tichu/Phoenix.png",
        "/img/tichu/Dragon.png", "/img/tichu/Hound.png", "/img/tichu/MahJong.png", "/img/tichu/Phoenix.png",
        "/img/tichu/Dragon.png", "/img/tichu/Hound.png", "/img/tichu/MahJong.png", "/img/tichu/Phoenix.png"
    };

    private static final String[] TYPE2_IMAGES = {
        
    };

    private static final String[] TYPE3_IMAGES = {
       
    };

    private static final String TICHU_BACK_IMAGE = "/img/tichu/back.png";
    private static final String TYPE2_BACK_IMAGE = "/img/type2/back.png";
    private static final String TYPE3_BACK_IMAGE = "/img/type3/back.png";



    public static String[] getImages(String cardType) {
        switch (cardType) {
            case "Tichu":
                return TICHU_IMAGES;
            case "Type 2":
                return TYPE2_IMAGES;
            case "Type 3":
                return TYPE3_IMAGES;
            default:
                return TICHU_IMAGES; // Default to Tichu if no valid selection
        }
    }

    public static String getBackImage(String cardType) {
        switch (cardType) {
            case "Tichu":
                return TICHU_BACK_IMAGE;
            case "Type 2":
                return TYPE2_BACK_IMAGE;
            case "Type 3":
                return TYPE3_BACK_IMAGE;
            default:
                return TICHU_BACK_IMAGE; // Default to Tichu if no valid selection
        }
    }

    public static String[] generateCardPairs(String[] availableImages, int numPairs) {
        List<String> availableImagesList = new ArrayList<>();
        Collections.addAll(availableImagesList, availableImages);
        Collections.shuffle(availableImagesList, new Random());

        String[] cardPairs = new String[numPairs];
        for (int i = 0; i < numPairs; i++) {
            cardPairs[i] = availableImagesList.get(i % availableImagesList.size());
        }
        return cardPairs;
    }
}