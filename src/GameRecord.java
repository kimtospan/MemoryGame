//Represents a game record, cosisting of the player.name, date, timeToCompletion and score
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
// Class defining a game record and what a game record is and can do
// Managing the records, like saving and loading, is done in GameRecordManager
public class GameRecord {
    private String username;
    private LocalDateTime dateTime;
    private long elapsedTime; // in seconds
    private int score;


    // represents a game record
    public GameRecord(String username, long elapsedTime, int score) {
        this.username = username;
        this.dateTime = LocalDateTime.now();
        
        this.elapsedTime = elapsedTime;
        this.score = score;
    }

    // Chose to use CSV because the data is simple
    // function to convert object to CSV
    public String fromGameRecordToCSV() {
        // uhhh not really needed but its in doc so why not
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s,%s,%d,%d", username, dateTime.format(formatter), elapsedTime, score);
    }
    // Change from a CSV line to object
    public GameRecord fromCSVToGameRecord(String csv) {
        // The cvs is split by commas
        String[] data = csv.split(",");
        // Index 0 is the player name
        this.username = data[0];
        // Index 1 is the date by taking the local date time
        this.dateTime = LocalDateTime.parse(data[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        // Index 2 is the time to completion
        this.elapsedTime = Long.parseLong(data[2]);
        // Index 3 is the score
        this.score = Integer.parseInt(data[3]);
        return this;
    }

    public static String getCSVHeader() {
        return "Username,DateTime,TimeTaken,Score";
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("%s, %s, Time: %d seconds, Score: %d",
                username, dateTime.format(formatter), elapsedTime, score);
    }
}
