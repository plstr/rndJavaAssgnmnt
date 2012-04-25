package Library;
/**
 * is this even necessary? can't we just push all of
 * this junk as a secondary constructor?
 */
public class DigitalMovie extends Movie {
    // extra variables
    private boolean rentable;
    private boolean purchasable;
    private String fileFormat;
    private int fileSize;

    public DigitalMovie(String mvID, String title, String director,
                        String genre, int year, int length, String status,
                        String[] cast, boolean rentable, boolean purchasable,
                        String fileFormat, int fileSize){
        super(mvID, title, director, genre, year, length, status, cast);
        this.rentable = rentable;
        this.purchasable = purchasable;
        this.fileFormat = fileFormat;
        this.fileSize = fileSize;
    }

    public String getFileFormat(){
        return fileFormat;
    }

    public int getFileSize(){
        return fileSize;
    }

    public String getStatus(){
        if(this.checkBuy() && this.checkRent()){
            return " (buy | rent)";
        } else if (this.checkBuy() && !(this.checkRent())){
            return " (buy only)";
        } else if (this.checkRent() && !(this.checkBuy())){
            return " (rent only)";
        } else {
            return " (Not available)";
        }
    }

    public boolean checkRent(){
        return rentable;
    }

    public boolean checkBuy(){
        return purchasable;
    }
}
