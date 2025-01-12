import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

// To show when the user played


// Function to handle the game records in the csv
public class GameRecordManager {
    private static final String FILE_PATH = "GameRecords.csv";

    // Save a single game record to the CSV file
    public static void saveRecord(GameRecord record) {
        // Save a record using BufferedWriter
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(record.fromGameRecordToCSV());
            writer.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Load all game records from the CSV file
    public static List<GameRecord> loadRecords() {
        // Create a list of game records to save them 
        List<GameRecord> records = new ArrayList<>();
        //try catch to show we know how
        try {
            // Read all lines from the file and add them to a list
            List<String> lines = Files.readAllLines(Paths.get(FILE_PATH));
            // Every line represents a game record, so
            //for every line
            for (String line : lines) {
                // If the line is not empty
                if (!line.trim().isEmpty()) {
                    GameRecord record = new GameRecord("", 0, 0).fromCSVToGameRecord(line);
                    records.add(record);
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        return records;
    }

    
}
