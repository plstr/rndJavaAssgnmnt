package Library;
/**
 * is this even necessary? can't we just push all of
 * this junk as a secondary constructor?
 */
public class DigitalMovie extends Movie {
    // extra variables
    private int mvID;
    private String fileFormat;
    private int fileSize;

    public DigitalMovie(int mvID, String title, String director,
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

    public void setFileFormat(String fileFormat){
        this.fileFormat = fileFormat;
    }

    public void setFileSize(String fileSize){
        this.fileSize = Integer.parseInt(fileSize);
    }
}
