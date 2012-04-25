package Library;

import java.util.HashMap;
import java.util.ArrayList;

public class Library {

    // The list of movies. This is lame. TODO: sqlite at least.
    private String[] digiMovieList = new String[]{
        // title | director | genre | year | length | cast | size | format | digi_rent | digi_buy
        "The Matrix;Wachowski Brothers;Sci-Fi,Adventure;1998;120;Addy Ze, Kake Face;512;divX;true;true",
        "Star Trek;J.J. Abrams;Sci-Fi,Drama,Romance;2009;120;Addy Ze, Kake Face;512;divX;true;true"
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
        int mvid = 0;
        for (String each : digiMovieList){
            String[] eachMovie = each.split(";");
            digiMovieLibrary.put(eachMovie[0], Movie(eachMovie[0], eachMovie[1],
                    eachMovie[2], Integer.parseInt(eachMovie[3]), eachMovie[4],
                    Boolean.parseBoolean(eachMovie[4]),
                    Boolean.parseBoolean(eachMovie[5])));
            }
        }
    }

    public ArrayList<Movie> getMovies(String selection, String filter){
        ArrayList<Movie> results = new ArrayList<Movie>();

        if(filter.matches("all")){
            for(Movie each : movieLibrary){
                if(each.getTitle().toLowerCase().matches(".*" + selection +
                        ".*")){
                    results.add(each);
                }
            }
        }
        else if(filter.matches("buy")){
            for(Movie each : movieLibrary){
                if(each.getTitle().toLowerCase().matches(".*" + selection +
                        ".*") && each.checkBuy()){
                    results.add(each);
                }
            }
        }

        else if(filter.matches("rent")){
            for(Movie each : movieLibrary){
                if(each.getTitle().toLowerCase().matches(".*" + selection +
                        ".*") && each.checkRent()){
                    results.add(each);
                }
            }
        }
        return results;
    }
}
