package com.artechra;

public class DataLoader extends AbstractBaseClient {

    public DataLoader(String clientName, String regionName) {
        super(clientName, regionName) ;
    }
    public void insertItem(int i, String name) {
        getRegion().put(i, name) ;
    }

    public static void main(String args[]) {
        if (args.length != 2) {
            System.err.println("Usage: DataLoader startIdx endIdx") ;
            System.exit(1) ;
        }


        int low = Integer.parseInt(args[0]) ;
        int high = Integer.parseInt(args[1]) ;

        DataLoader l = new DataLoader("DataLoader", "numbersRegion") ;

        System.out.println("Inserting values from " + low + " to " + high) ;
        for (int i=low; i <= high; i++) {
            l.insertItem(i, numberName(i));
        }
        l.close();
    }

    private static String numberName(int i) {
        String numberNames[] = { "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten" } ;
        String ret = "value" + i ;
        if (i >= 0 && i < numberNames.length) {
            ret = numberNames[i] ;
        }
        return ret ;
    }
}
