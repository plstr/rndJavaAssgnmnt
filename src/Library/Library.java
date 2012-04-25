package Library;

import java.util.HashMap;
import java.util.ArrayList;

public class Library {

    // The list of movies. This is lame. TODO: sqlite at least.
    private String[] digiMovieList = new String[]{
        // id | title | director | genre | year | length | cast | size | format | digi_rent | digi_buy
        "00000001;The Matrix;Wachowski Brothers;Sci-Fi,Adventure;1998;120;Addy Ze, Kake Face;512;divX;true;true",
        "00000002;Star Trek;J.J. Abrams;Sci-Fi,Drama,Romance;2009;120;Addy Ze, Kake Face;512;divX;true;true"
    };

    private String[] phyMovieList = new String[]{
            // barcode | title | director | genre | year | length | cast | qnt | phy_rent | phy_buy
            "12321323;The Matrix;Wachowski Brothers;Sci-Fi,Adventure;1998;120;Bobby Dee, Andrew Bob;2;true;false",
            "12321321;Star Trek;J.J. Abrams;Sci-Fi,Drama,Romance;2009;120;Bobby Dee, Andrew Bob;2;true;false"
    };

    // init stores
    private HashMap digiMovieLibrary;
    private HashMap phyMovieLibrary;

    public Library() {
        // Repos of all the movies
        for (String each : digiMovieList){
            String[] eachMovie = each.split(";");
            digiMovieLibrary.put(eachMovie[0], new DigitalMovie(eachMovie[0],  // ID
                    eachMovie[1], // Title
                    eachMovie[2], // Director
                    eachMovie[3], // Genre
                    Integer.parseInt(eachMovie[4]), // Year
                    Integer.parseInt(eachMovie[5]), // Length
                    eachMovie[6], // Cast
                    Integer.parseInt(eachMovie[7]), // Size
                    eachMovie[8], // File format
                    Boolean.parseBoolean(eachMovie[9]), // Rent bool
                    Boolean.parseBoolean(eachMovie[10]))  // Buy bool
            );
        }

        for (String each : phyMovieList){
            String[] eachMovie = each.split(";");
            phyMovieLibrary.put(eachMovie[0], new PhysicalMovie(eachMovie[0], // Barcode
                    eachMovie[1], // Title
                    eachMovie[2], // Director
                    eachMovie[3], // Genre
                    Integer.parseInt(eachMovie[4]), // Year
                    Integer.parseInt(eachMovie[5]), // Length
                    eachMovie[6], // Cast
                    Integer.parseInt(eachMovie[7]), // Quantity
                    Boolean.parseBoolean(eachMovie[8]), // Rent bool
                    Boolean.parseBoolean(eachMovie[9]))  // Buy bool
            );
        }
    }

    public ArrayList<Movie> getPhyMovies(String selection, String filter){
        ArrayList<Movie> results = new ArrayList<Movie>();
        return results;
    }

    public ArrayList<Movie> getDigiMovies(String selection, String filter){
        ArrayList<Movie> results = new ArrayList<Movie>();
        return results;
    }

    public void getPhyRented(){
        // implement
    }


}
