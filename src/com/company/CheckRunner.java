package com.company;

import java.io.*;

public class CheckRunner {

    public static void main(String[] args) throws IOException {

        if(args.length < 1) {
            System.out.println("Error, usage: java ClassName inputfile");
            System.exit(1);
        }

       CheckCreator.initData();
       System.out.println(CheckCreator.getCheck((CheckCreator.getProds(args)),CheckCreator.getSale(args)));
       CheckCreator.checkInFile(CheckCreator.getCheck((CheckCreator.getProds(args)),CheckCreator.getSale(args)));
    }
}



