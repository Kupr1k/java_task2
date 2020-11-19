package com.company;

import java.io.*;

public class CheckRunner {

    public static void main(String[] args) throws IOException {

       if (args.length == 0) {
            System.err.println("Please select a product!");
            System.exit(1);
        }

       CheckCreator.initData();
       CheckCreator.getCheck(CheckCreator.prodParser(args),CheckCreator.getSale(CheckCreator.cardParser(args)));
    }
}



