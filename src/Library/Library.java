package Library;

import java.util.HashMap;
import java.util.ArrayList;

public class Library {

    // The list of movies. This is lame. TODO: CSV at least.
    private String[] movieList = new String[]{
        "The Matrix;Wachowski Brothers;Sci-Fi,Adventure;1998;120;available;true;true",
        "Star Trek;J.J. Abrams;Sci-Fi,Drama,Romance;2009;120;available;false;true",
        "Dark City;Alex Proyas;Sci-Fi,Horror;1998;true;120;available;false",
        "Brotherhood of the Wolf;Christophe Gans;Horror;2001;120;available;true;false",
        "City of God;Fernando Meirelles;Sci-Fi;2002;120;available;true;true",
        "Pi;Darren Aronofsky;Thriller;1998;120;available;false;true"
    };

    private Movie[] movieLibrary = new Movie[movieList.length];
    public Movie (String mvID, String title, String director, String genre,
                  int year, int length, String status, String[] cast) {
    public Library() {
        // Repo of all the movies
        int mvid = 0;
        for (int i=0; i <= movieList.length - 1; i++){
            String[] eachMovie = movieList[i].split(";");
            if(eachMovie.length == 6){
                movieLibrary[i] = new Movie(mvid++, eachMovie[0], eachMovie[1],
                    eachMovie[2], Integer.parseInt(eachMovie[3]),
                    Boolean.parseBoolean(eachMovie[4]),
                    Boolean.parseBoolean(eachMovie[5]));
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
