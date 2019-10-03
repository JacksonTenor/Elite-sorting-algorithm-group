import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Groupx {
    public static void main(String[] args){
        String[] strings = new String[]{"3/4", "34", "34/1", "3 3/4"};
        ArrayList<SortingType> sorting = new ArrayList<>();
        for(String s: strings){
            sorting.add(new SortingType(s));
        }


        Collections.sort(sorting);
        for(SortingType s: sorting){
            System.out.println(s.toString());
        }
    }



}
