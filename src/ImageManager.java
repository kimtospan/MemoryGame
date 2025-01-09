import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
//Class to manage images


public class ImageManager {
    private static final String[] TICHU_IMAGES = {
        "src/img/tichu/2black.png", "src/img/tichu/2blue.png", "src/img/tichu/2green.png", "src/img/tichu/2red.png",
        "src/img/tichu/3black.png", "src/img/tichu/3blue.png", "src/img/tichu/3green.png", "src/img/tichu/3red.png",
        "src/img/tichu/4black.png", "src/img/tichu/4blue.png", "src/img/tichu/4green.png", "src/img/tichu/4red.png",
        "src/img/tichu/5black.png", "src/img/tichu/5blue.png", "src/img/tichu/5green.png", "src/img/tichu/5red.png",
        "src/img/tichu/6black.png", "src/img/tichu/6blue.png", "src/img/tichu/6green.png", "src/img/tichu/6red.png",
        "src/img/tichu/7black.png", "src/img/tichu/7blue.png", "src/img/tichu/7green.png", "src/img/tichu/7red.png",
        "src/img/tichu/8black.png", "src/img/tichu/8blue.png", "src/img/tichu/8green.png", "src/img/tichu/8red.png",
        "src/img/tichu/9black.png", "src/img/tichu/9blue.png", "src/img/tichu/9green.png", "src/img/tichu/9red.png",
        "src/img/tichu/10black.png", "src/img/tichu/10blue.png", "src/img/tichu/10green.png", "src/img/tichu/10red.png",
        "src/img/tichu/Jblack.png", "src/img/tichu/Jblue.png", "src/img/tichu/Jgreen.png", "src/img/tichu/Jred.png",
        "src/img/tichu/Qblack.png", "src/img/tichu/Qblue.png", "src/img/tichu/Qgreen.png", "src/img/tichu/Qred.png",
        "src/img/tichu/Kblack.png", "src/img/tichu/Kblue.png", "src/img/tichu/Kgreen.png", "src/img/tichu/Kred.png",
        "src/img/tichu/Ablack.png", "src/img/tichu/Ablue.png", "src/img/tichu/Agreen.png", "src/img/tichu/Ared.png",
        "src/img/tichu/Dragon.png", "src/img/tichu/Phoenix.png", "src/img/tichu/Hound.png", "src/img/tichu/MahJong.png",
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