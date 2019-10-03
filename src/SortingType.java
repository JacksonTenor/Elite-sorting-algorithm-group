public class SortingType implements Comparable<SortingType>{
    private double value;
    private String original;

    SortingType(String o){
        this.original = o;
        if(o.contains("/")){
            String[] oa = o.split(" ");
            if(oa.length == 2){
                this.value += Double.parseDouble(oa[0]);
                String[] fraction = oa[1].split("/");
                this.value += Double.parseDouble(fraction[0]) / Double.parseDouble(fraction[1]);
            }else{
                String[] fraction = oa[0].split("/");
                this.value += Double.parseDouble(fraction[0]) / Double.parseDouble(fraction[1]);
            }
        }else{
            this.value = Double.parseDouble(o);
        }
    }

    @Override
    public String toString(){
        return Double.toString(this.value);
    }

    @Override
    public int compareTo(SortingType other) {
        int diff = (int) Math.signum(this.value - other.value);
        if (diff == 0){
            return diff;
            //This will not work long term.
        }else{
            return diff;
        }
    }
}
