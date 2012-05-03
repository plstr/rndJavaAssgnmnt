package Library;
public class PhysicalMovie extends Movie {
    private int barcode;
    private int qnt;
    private int weight; // grams
    private float[] dimensions; // 3 values


    // default weight and dimension
    public PhysicalMovie(int barcode, String title, String director,
                        String genre, int year, int length,
                        String cast, int qnt, boolean rent, boolean buy){
        super(title, director, genre, year, length, cast, rent, buy);
        this.barcode = barcode;
        this.qnt = qnt;
        this.weight = 20;
        this.dimensions = new float[] {190.5f, 135.0f, 14.6f};
    }

    public void setQnt(String qnt){
        this.qnt = Integer.parseInt(qnt);
    }

    public void setWeight(String weight){
        this.weight = Integer.parseInt(weight);
    }

    public int getBarcode(){
        return this.barcode;
    }

    public void decreaseQuantity(){
        this.qnt -= 1;
    }

    public void increaseQuantity(){
        this.qnt += 1;
    }

    public int getQuantity(){
        return this.qnt;
    }

    public String checkAvailability(){
        if(this.qnt > 0){
            return "\t[in stock]";
        } else {
            return "\t[out of stock]";
        }

    }
}
