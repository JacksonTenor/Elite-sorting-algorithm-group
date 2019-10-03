import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Groupx {
    public static void main(String[] args){
        File input = new File("data/data1.txt");
        ArrayList<String> strings = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(input));
            while(br.ready()){
                strings.add(br.readLine());
            }
            br.close();
        }catch(Exception e){
            System.err.println(e);
        }

        ArrayList<SortingType> sorting = new ArrayList<>();
        for(String s: strings){
            sorting.add(new SortingType(s));
        }
        Collections.sort(sorting);

        File output = new File("data/out1.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader(output));
            int i = 0;
            while(br.ready()){
                String line = br.readLine();
                if(!line.equals(sorting.get(i).toString())){
                    System.out.println("error");
                }
                i++;
            }
            br.close();
        }catch(Exception e){
            System.err.println(e);
        }
    }



}
