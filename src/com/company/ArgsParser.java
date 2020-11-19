package com.company;

public class ArgsParser {

    /* Parsing product args */
    public static int[] prodParser(String[] args){

        int [] parsedProds = new int[args.length];

        for(int i = 0; i < args.length; i++) {
            if (!args[i].contains("card")) {
                parsedProds[i] = Integer.parseInt(args[i].replaceAll("-", ""));
            }
        }
        return parsedProds;
    }

    /* Parsing card args */
    public static int cardParser(String[] args){

        int numCard = 0;

        for (int i = 0; i < args.length; i++){
            if (args[i].contains("card")) {
                numCard = Integer.parseInt(args[i].replaceAll("card-", ""));
            }
        }
        return numCard;
    }
}


