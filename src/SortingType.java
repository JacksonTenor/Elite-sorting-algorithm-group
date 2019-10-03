public class SortingType implements Comparable<SortingType>{
    private double value;
    private String original;

    //In case of ties, type is used first
    //0 = Decimal (goes first in ties)
    //1 = Mixed Fraction (second in ties)
    //2 = Pure Fraction (third in ties)
    private int type;
    //For two Mixed Fractions, the smaller whole number goes first
    private double whole = 0;
    //For two Mixed Fractions with the same whole part
    //or two pure fractions, the smaller numerator goes first.
    private double numerator = 0;


    SortingType(String o){
        this.original = o;
        if(o.contains("/")){
            String[] oa = o.split(" ");
            if(oa.length == 2){
                this.type=1; //Mixed Fraction
                double whole = Double.parseDouble(oa[0]);
                this.value += whole;
                this.whole = whole;
                String[] fraction = oa[1].split("/");
                this.numerator = Double.parseDouble(fraction[0]);
                this.value += this.numerator / Double.parseDouble(fraction[1]);
            }else{
                this.type=2; //Pure Fraction
                String[] fraction = oa[0].split("/");
                this.numerator = Double.parseDouble(fraction[0]);
                this.value += this.numerator / Double.parseDouble(fraction[1]);
            }
        }else{
            this.type = 0; //Decimal Expression
            this.value = Double.parseDouble(o);
        }
    }

    @Override
    public String toString(){
        //return Double.toString(this.value);
        return this.original;
    }

    @Override
    public int compareTo(SortingType other) {
        int diff = (int) Math.signum(this.value - other.value);
        if (diff == 0) {
            diff = this.type - other.type; //Compare by types
            if(diff == 0 && this.type != 0){ //No further tiebreakers for decimals
                diff = (int) Math.signum(this.whole - other.whole);
                if(diff == 0){
                    diff = (int) Math.signum(this.numerator - other.numerator);
                }
            }
        }
        return diff;
    }
}
