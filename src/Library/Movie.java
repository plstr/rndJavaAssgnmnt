package Library;

public abstract class Movie {
    // define movie variables
    private String title;
    private String director;
    private String genre;
    private int year;
    private int length;
    private String cast;
    private Boolean rent;
    private Boolean buy;

    private int rentCount = 0;
    private int purchaseCount = 0;
    
    public Movie (String title, String director, String genre,
           int year, int length, String cast, Boolean rent, Boolean buy) {
        this.title = title;
        this.director = director;
        this.genre = genre;
        this.year = year;
        this.length = length;
        this.cast = cast;
        this.rent = rent;
        this.buy = buy;
    }
    
    public String printAllValues(){
        return title + "\t "
               + director + "\t "
               + genre + "\t "
               + year;
    }


    public boolean rentable(){
        return this.rent;
    }

    public boolean purchasable(){
        return this.buy;
    }

    public String getTitle(){
        return title;
    }

    public String toString(){
        return this.printAllValues();
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDirector(String director){
        this.director = director;
    }

    public void setGenre(String genre){
        this.genre = genre;
    }

    public void setCast(String cast){
        this.cast = cast;
    }

    public void increaseRentCount(){
        this.rentCount += 1;
    }

    public void increasePurchaseCount() {
        this.purchaseCount += 1;
    }

    public String getStats(){
        return "Rental count: " + this.rentCount +
                "Purchase count: " + this.purchaseCount;
    }




}
