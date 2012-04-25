package Library;
public class PhysicalMovie extends Movie {
    private String barcode;
    private int weight; // grams
    private float[] dimensions; // 3 values

    public PhysicalMovie(String mvID, String title, String director,
                         String genre, int year, int length, String status,
                         String[] cast, int weight, float[] dimensions){
        super(mvID, title, director, genre, year, length, status, cast);
        this.weight = weight;
        this.dimensions = dimensions;
    }
    // default weight and dimension case not defined
    public PhysicalMovie(String mvID, String title, String director,
                        String genre, int year, int length, String status,
                        String[] cast){
        super(mvID, title, director, genre, year, length, status, cast);
        this.weight = 20;
        this.dimensions = new float[] {190.5f, 135.0f, 14.6f};
    }
}
