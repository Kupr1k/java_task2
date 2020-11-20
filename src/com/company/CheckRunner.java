package com.company;

import java.io.*;

public class CheckRunner {

    public static void main(String[] args) throws IOException {

        if(args.length < 1) {
            System.out.println("Error, usage: java ClassName inputfile");
            System.exit(1);
        }

       CheckCreator.initData();
       CheckCreator.getCheck(CheckCreator.prodParser(args),CheckCreator.getSale(CheckCreator.cardParser(args)));
    }
}



