import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
//Class to manage images


public class ImageManager {
    private static final String[] TICHU_IMAGES = {
        "/img/tichu/2black.png", "/img/tichu/2blue.png", "/img/tichu/2green.png", "/img/tichu/2red.png",
        "/img/tichu/3black.png", "/img/tichu/3blue.png", "/img/tichu/3green.png", "/img/tichu/3red.png",
        "/img/tichu/4black.png", "/img/tichu/4blue.png", "/img/tichu/4green.png", "/img/tichu/4red.png",
        "/img/tichu/5black.png", "/img/tichu/5blue.png", "/img/tichu/5green.png", "/img/tichu/5red.png",
        "/img/tichu/6black.png", "/img/tichu/6blue.png", "/img/tichu/6green.png", "/img/tichu/6red.png",
        "/img/tichu/7black.png", "/img/tichu/7blue.png", "/img/tichu/7green.png", "/img/tichu/7red.png",
        "/img/tichu/8black.png", "/img/tichu/8blue.png", "/img/tichu/8green.png", "/img/tichu/8red.png",
        "/img/tichu/9black.png", "/img/tichu/9blue.png", "/img/tichu/9green.png", "/img/tichu/9red.png",
        "/img/tichu/10black.png", "/img/tichu/10blue.png", "/img/tichu/10green.png", "/img/tichu/10red.png",
        "/img/tichu/Jblack.png", "/img/tichu/Jblue.png", "/img/tichu/Jgreen.png", "/img/tichu/Jred.png",
        "/img/tichu/Qblack.png", "/img/tichu/Qblue.png", "/img/tichu/Qgreen.png", "/img/tichu/Qred.png",
        "/img/tichu/Kblack.png", "/img/tichu/Kblue.png", "/img/tichu/Kgreen.png", "/img/tichu/Kred.png",
        "/img/tichu/Ablack.png", "/img/tichu/Ablue.png", "/img/tichu/Agreen.png", "/img/tichu/Ared.png",
        "/img/tichu/Dragon.png", "/img/tichu/Phoenix.png", "/img/tichu/Hound.png", "/img/tichu/MahJong.png",
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