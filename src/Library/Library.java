package Library;

import java.util.Map;
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
    private Map<String, DigitalMovie> digiMovieLibrary = new HashMap<String, DigitalMovie>();
    private Map<String, PhysicalMovie> phyMovieLibrary = new HashMap<String, PhysicalMovie>();

    public Library() {
        // Repos of all the movies
        for (String each : digiMovieList){
            String[] eachMovie = each.split(";");
            digiMovieLibrary.put(eachMovie[0], new DigitalMovie(eachMovie[0],
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
            this.phyMovieLibrary.put(eachMovie[0], new PhysicalMovie(eachMovie[0], // Barcode
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
        if(filter.equals("rent")){
            for(PhysicalMovie each : phyMovieLibrary.values()){
                if(each.getTitle().matches(".*"+selection+".*") & each.rentable()){
                    results.add(each);
                }
            }
        } else if(filter.equals("buy")){
            for(PhysicalMovie each : phyMovieLibrary.values()){
                if(each.getTitle().matches(".*"+selection+".*") & each.purchasable()){
                    results.add(each);
                }
            }
        } else {
            for(PhysicalMovie each : phyMovieLibrary.values()){
                if(each.getTitle().matches(".*"+selection+".*")){
                    results.add(each);
                }
            }
        }
        return results;
    }

    public ArrayList<Movie> getDigiMovies(String selection, String filter){
        ArrayList<Movie> results = new ArrayList<Movie>();
        if(filter.equals("rent")){
            for(DigitalMovie each : digiMovieLibrary.values()){
                if(each.getTitle().matches(".*"+selection+".*") & each.rentable()){
                    results.add(each);
                }
            }
        } else if(filter.equals("buy")){
            for(DigitalMovie each : digiMovieLibrary.values()){
                if(each.getTitle().matches(".*"+selection+".*") & each.purchasable()){
                    results.add(each);
                }
            }
        } else {
            for(DigitalMovie each : digiMovieLibrary.values()){
                if(each.getTitle().matches(".*"+selection+".*")){
                    results.add(each);
                }
            }
        }
        return results;
    }

    public DigitalMovie getDigiTitle(String title){
        return digiMovieLibrary.get(title);
    }
}
