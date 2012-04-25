package Library;
public class PhysicalMovie extends Movie {
    private String barcode;
    private int qnt;
    private int weight; // grams
    private float[] dimensions; // 3 values

    // default weight and dimension
    public PhysicalMovie(String barcode, String title, String director,
                        String genre, int year, int length,
                        String cast, int qnt, boolean rent, boolean buy){
        super(title, director, genre, year, length, cast, rent, buy);
        this.barcode = barcode;
        this.qnt = qnt;
        this.weight = 20;
        this.dimensions = new float[] {190.5f, 135.0f, 14.6f};
    }
}
