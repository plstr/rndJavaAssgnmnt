package Library;

public class Movie {
    // define movie variables
    private String mvID;
    private String title;
    private String director;
    private String genre;
    private int year;
    private int length;
    private String status;
    private String[] cast;
    
    public Movie (String mvID, String title, String director, String genre,
           int year, int length, String status, String[] cast) {
        this.mvID = mvID;
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.year = year;
        this.length = length;
        this.status = status;
        this.cast = cast;
    }
    
    public String printAllValues(){
        return title + "\t "
               + director + "\t "
               + genre + "\t "
               + year;
    }

    public String getTitle(){
        return title;
    }

}
