package Library;
/**
 * is this even necessary? can't we just push all of
 * this junk as a secondary constructor?
 */
public class DigitalMovie extends Movie {
    // extra variables
    private String mvID;
    private String fileFormat;
    private int fileSize;

    public DigitalMovie(String mvID, String title, String director,
                        String genre, int year, int length, String cast,
                        int fileSize, String fileFormat, boolean rent,
                        boolean buy){
        super(title, director, genre, year, length, cast, rent, buy);
        this.mvID = mvID;
        this.fileFormat = fileFormat;
        this.fileSize = fileSize;
    }

    public String getFileFormat(){
        return fileFormat;
    }

    public int getFileSize(){
        return fileSize;
    }

    public void getStatus(){
        // implement
    }
}
