package Library;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class Library {

    // The list of movies. This is lame. TODO: sqlite at least.
    private String[] digiMovieList = new String[]{
        // id | title | director | genre | year | length | cast | size | format | digi_rent | digi_buy
        "00000001;The Matrix;Wachowski Brothers;Sci-Fi,Adventure;1998;120;Addy Ze, Kake Face;512;divX;true;true",
        "00000002;Star Trek;J.J. Abrams;Sci-Fi,Drama,Romance;2009;120;Addy Ze, Kake Face;512;divX;true;true",
        "00000003;The Avengers;Joss Whedon;Action,Fantasy;2012;142;Robert Downey Jr.;832;xvid;true;true"
    };

    private String[] phyMovieList = new String[]{
            // barcode | title | director | genre | year | length | cast | qnt | phy_rent | phy_buy
            "12321323;The Matrix Reloaded;Wachowski Brothers;Sci-Fi,Adventure;1998;120;Bobby Dee, Andrew Bob;2;true;false",
            "12321321;Star Trek Generations;J.J. Abrams;Sci-Fi,Drama,Romance;2009;120;Tony Lau, Andrew Bob;2;true;true",
            "12312333;The Hunger Games;Gary Ross;Action;2012;130;Jennifer Lawrence;1;true;true",
            "12312381;The Descendants;Alexander Payne;Action;2011;152;George Clooney;1;true;true",
            "13331111;Shame;Steve McQueen;Drama;2011;110;Michael Fassbender;3;true;true",
            "10100101;Hugo;Martin Scorsese;Comedy;2011;130;Asa Butterfield;2;true;true"
    };

    // init stores
    private Map<Integer, DigitalMovie> digiMovieLibrary = new HashMap<Integer, DigitalMovie>();
    private Map<Integer, PhysicalMovie> phyMovieLibrary = new HashMap<Integer, PhysicalMovie>();

    private Map currentDigiRentals = new HashMap();
    private Map currentDVDRentals = new HashMap();

    public Library() {
        // Repos of all the movies
        for (String each : digiMovieList){
            String[] eachMovie = each.split(";");
            digiMovieLibrary.put(Integer.parseInt(eachMovie[0]),
                    new DigitalMovie(Integer.parseInt(eachMovie[0]),
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
            this.phyMovieLibrary.put(Integer.parseInt(eachMovie[0]),
                    new PhysicalMovie(Integer.parseInt(eachMovie[0]), // Barcode
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

    public ArrayList<PhysicalMovie> getPhyMovies(String selection, String filter){
        ArrayList<PhysicalMovie> results = new ArrayList<PhysicalMovie>();
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

    public boolean deleteDigiMovie(int id){
        try{
            this.digiMovieLibrary.remove(id);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean deleteDVDMovie(int barcode){
        try{
            this.phyMovieLibrary.remove(barcode);
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public void addDigiMovie(String title, String director, String genre,
                             String year, String length, String cast,
                             String fileSize, String fileFormat, Boolean bRent,
                             Boolean bBuy){
        int newID = this.getDigiID();
        this.digiMovieLibrary.put(newID, new DigitalMovie(newID, title,
                director, genre, Integer.parseInt(year),Integer.parseInt(length),
                cast,Integer.parseInt(fileSize),fileFormat,bRent,bBuy));

    }

    public int getDigiID(){
        int top = 0;
        for(int each : digiMovieLibrary.keySet()){
            if(each > top){
                top = each;
            }
        }
        return top + 1;
    }

    public void addDVDMovie(String title, String director, String genre,
                             String year, String length, String cast,
                             int quantity, Boolean bRent,
                             Boolean bBuy){
        int newBarcode = this.getDVDBarcode();
        this.phyMovieLibrary.put(newBarcode, new PhysicalMovie(newBarcode, title,
                director, genre, Integer.parseInt(year),Integer.parseInt(length),
                cast,quantity,bRent,bBuy));

    }

    public  int getDVDBarcode(){
        int Min = 10000000;
        int Max = 99999999;
        return Min + (int)(Math.random() * ((Max - Min) + 1));
    }

    public boolean checkDigiMovie(int id){
        return this.digiMovieLibrary.containsKey(id);
    }

    public boolean checkDVDMovie(int barcode){
        return this.phyMovieLibrary.containsKey(barcode);
    }

    public DigitalMovie getDigiMovie(int id){
        return this.digiMovieLibrary.get(id);
    }

    public PhysicalMovie getDVDMovie(int barcode){
        return this.phyMovieLibrary.get(barcode);
    }

    public boolean addDigiRental(int id, String user){
        if(this.checkDigiMovie(id)){
            this.currentDigiRentals.put("currentdate" + id, user);
            return true;
        } else {
            return false;
        }
    }

    public Map getDigiRentals(){
        return this.currentDigiRentals;
    }

    public Map getDVDRentals(){
        return this.currentDVDRentals;
    }

    public void returnDigiRental(int id, String user){
        if(this.currentDigiRentals.containsKey(id) &&
                this.currentDigiRentals.get(id).equals(user)){
            // do stuff
        }
    }

    public void returnDVDRental(int barcode, int day){
        if(this.currentDVDRentals.containsKey(barcode)){
            // do stuff
        }
    }

}
