import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.List;
import java.math.BigInteger;

public class Group8 {
    public static void main(String[] args) throws InterruptedException, FileNotFoundException,IOException {
        // testing the comparator:
        //Data.test_Data(); // This MUST be commented out for your submission to the competition!

        if (args.length < 2) {
            System.out.println("Please run with two command line arguments: input and output file names");
            System.exit(0);
        }

        String inputFileName = args[0];
        String outFileName = args[1];


        // read as strings
        String [] data = readData(inputFileName);
        String [] toSort = data.clone();
        Data [] sorted = sort(toSort); // Warm up the VM
        toSort = data.clone();
        Thread.sleep(10); //to let other things finish before timing; adds stability of runs

        long start = System.currentTimeMillis(); // Begin the timing
        sorted = sort(toSort);
        long end = System.currentTimeMillis();   // End the timing
        System.out.println(end - start); // Report the results
        writeOutResult(sorted, outFileName);

//        //validate result
//        try {
//            BufferedReader us = new BufferedReader(new FileReader(outFileName));
//            BufferedReader correct = new BufferedReader(new FileReader(validationFileName));
//
//            int i = 0;
//            while(correct.ready()){
//                String ourLine = us.readLine();
//                String correctLine = correct.readLine();
//                if(!ourLine.equals(correctLine)){
//                    System.out.println("error " + i + ": " + ourLine + " vs " + correctLine);
//                }
//                i++;
//            }
//            us.close();
//            correct.close();
//        }catch(Exception e){
//            System.err.println(e);
//        }
    }

    // YOUR SORTING METHOD GOES HERE.
    // You may call other methods and use other classes.
    // You may ALSO modify the methods, inner classes, etc, of Data[]
    // But not in way that transfers information from the warmup sort to the timed sort.
    // Note: you may change the return type of the method.
    // You would need to provide your own function that prints your sorted array to
    // a file in the exact same format that my program outputs
    private static Data[] sort(String[] toSort) {
        Data[] toSortData = new Data[toSort.length];
        for (int i = 0; i < toSort.length; ++i) {
            toSortData[i] = new Data(toSort[i]);
        }
        quicksort(toSortData);
        return toSortData;
    }

    private static void quicksort (Data[] arr){
        quicksort(arr, 0, arr.length - 1);
        insertionSort(arr);
    }

    private static void quicksort (Data[] arr, int low, int high){
        if (high - low > 8){
            int p = partition(arr, low, high);
            quicksort(arr, low, p-1);
            quicksort(arr, p+1, high);
        }
    }
    private static int partition (Data[] arr, int low, int high){
        int pivotPosition = high;
        if(high - low > 8) {
            int middle = (int) Math.floor((high - low) / 2) + low;
            if (arr[low].compareTo(arr[middle]) <= 0) {
                if (arr[high].compareTo(arr[low]) <= 0) {
                    pivotPosition = low;
                } else if (arr[high].compareTo(arr[middle]) >= 0) {
                    pivotPosition = middle;
                } else {
                    pivotPosition = high;
                }
            } else {
                if (arr[high].compareTo(arr[low]) >= 0) {
                    pivotPosition = low;
                } else if (arr[high].compareTo(arr[middle]) <= 0) {
                    pivotPosition = middle;
                } else {
                    pivotPosition = high;
                }
            }
        }
        Data pivot = arr[pivotPosition];
        arr[pivotPosition] = arr[high];
        arr[high] = pivot;
        int swapIndex = low - 1;

        for (int i=low; i<high; i++){
            if(arr[i].compareTo(pivot) <= 0){
                swapIndex ++;
                Data s = arr[swapIndex];
                arr[swapIndex] = arr[i];
                arr[i] = s;
            }
        }
        swapIndex++;
        Data s = arr[swapIndex];
        arr[swapIndex] = arr[high];
        arr[high] = s;
        return swapIndex;
    }
    private static void insertionSort(Data[] arr){
        for(int i=1; i<arr.length; i++){
            Data key = arr[i];
            int j = i - 1;
            while (j >= 0 && arr[j].compareTo(key) > 0){
                arr[j+1] = arr[j];
                j = j - 1;
            }
            arr[j + 1] = key;
        }
    }

    private static void printArray(String[] Arr, int n) {
        for(int i = 0; i < n; i++) {
            System.out.println(Arr[i]);
        }
    }
    private static String[] readData(String inFile) throws FileNotFoundException,IOException {
        List<String> input = Files.readAllLines(Paths.get(inFile));
        // the string array is passed just so that the correct type can be created
        return input.toArray(new String[0]);
    }

    private static void writeOutResult(Data[] sorted, String outputFilename) throws FileNotFoundException {
        PrintWriter out = new PrintWriter(outputFilename);
        for (Data s : sorted) {
            out.println(s.original);
        }
        out.close();
    }

    public static class Data implements Comparable<Data> {
        private double value;
        private String original;

        //In case of ties, type is used first
        //0 = Decimal (goes first in ties)
        //1 = Mixed Fraction (second in ties)
        //2 = Pure Fraction (third in ties)
        private int type;
        //For two Mixed Fractions, the smaller whole number goes first
        private long whole = 0;
        //For two Mixed Fractions with the same whole part
        //or two pure fractions, the smaller numerator goes first.
        private long numerator = 0;


        Data(String o) {
            this.original = o;
            if (o.contains("/")) {
                String[] oa = o.split(" ");
                if (oa.length == 2) {
                    this.type = 1; //Mixed Fraction
                    long whole = Long.parseLong(oa[0]);
                    this.value += whole;
                    this.whole = whole;
                    String[] fraction = oa[1].split("/");
                    this.numerator = Long.parseLong(fraction[0]);
                    this.value += (double) this.numerator / Double.parseDouble(fraction[1]);
                } else {
                    this.type = 2; //Pure Fraction
                    String[] fraction = oa[0].split("/");
                    this.numerator = Long.parseLong(fraction[0]);
                    this.value += (double) this.numerator / Double.parseDouble(fraction[1]);
                }
            } else {
                this.type = 0; //Decimal Expression
                this.value = Double.parseDouble(o);
            }
        }

        @Override
        public String toString() {
            //return Double.toString(this.value);
            return this.original;
        }

        @Override
        public int compareTo(Data other) {
            double valdiff = this.value - other.value;
            if(Math.abs(valdiff) <= 0.0000000000001){
                valdiff = 0;
            }
            int diff = (int) Math.signum(valdiff);
            if (diff == 0) {
                diff = this.type - other.type; //Compare by types
                if (diff == 0 && this.type != 0) { //No further tiebreakers for decimals
                    diff = (int) Math.signum(this.whole - other.whole);
                    if (diff == 0) {
                        diff = (int) Math.signum(this.numerator - other.numerator);
                    }
                }
            }
            return diff;
        }

        static void print_test(String s1,String s2){
            Data testItem,testItem2;
            testItem=new Data(s1); testItem2=new Data(s2);
            System.out.println("Compare: "+s1+" to "+s2+": ");
            System.out.println("Result="+testItem.compareTo(testItem2));
            System.out.println("Compare: "+s2+" to "+s1+": ");
            System.out.println("Result="+testItem2.compareTo(testItem));
            System.out.println("---");
        }
        static void test_Data() {

            String s1,s2;

            print_test("3/4","2/3");      // Two pure fractions in lowest form that are unequal
            print_test("3/4","4/6");      // Two pure fractions, unequal, one in lowest form
            print_test("3/4","6/8");      // Two pure fractions with equal values
            print_test("7/4","1.75");     // One pure fraction and one decimal with equal values
            print_test("7/4","1.74");     // One pure fraction and one decimal with unequal values
            print_test("1.74","1.75");    // Two unequal decimals
            print_test("1 3/4","1.75");   // One mixed fraction, one decimal with equal valeus

            print_test("7/4","1 3/4");    //Pure fraction to mixed fraction with same value
            print_test("1 3/4","1 6/8");  //Mixed fraction to mixed fraction.  Same whole number
            print_test("1 7/4","2 3/4"); // Two mixed factions with equal values
            print_test("1 7/4","13/4");  // Mixed fraction to unequal pure fraction
            System.out.println("---");
        }

    }
}
