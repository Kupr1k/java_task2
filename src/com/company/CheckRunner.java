package com.company;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Locale;

public class CheckRunner {

    public static List<Products> products = new ArrayList<>();
    public static List<Cards> cards = new ArrayList<>();

    public static void main(String[] args) throws IOException {

       if (args.length == 0) {
            System.err.println("Please select a product!");
            System.exit(1);
        }

       initData();
       getCheck(prodParser(args),getSale(cardParser(args)));

    }

    /* Парсинг аргументов продуктов */
    public static int[] prodParser(String[] args){

        int [] parsedProds = new int[args.length];

        for(int i = 0; i < args.length; i++) {
            if (!args[i].contains("card")) {
                parsedProds[i] = Integer.parseInt(args[i].replaceAll("-", ""));
            }
        }
        return parsedProds;
    }

    /* Парсинг аргументов карт */
    public static int cardParser(String[] args){

        int numCard = 0;

        for (int i = 0; i < args.length; i++){
            if (args[i].contains("card")) {
                numCard = Integer.parseInt(args[i].replaceAll("card-", ""));
            }
        }
        return numCard;
    }

    public static int getSale(int numCard){
        int sale = 0;
        for(Cards cards: cards){
          if (numCard == cards.getCardNumber()){
               sale = cards.getDiscount();
           }
       }
        return sale;
}

    /* Формирование чека */
    public static void getCheck(int[] parsedProds, int sale) throws IOException {

        File checkFile = new File("CheckFile.text");
        checkFile.createNewFile();

        FileWriter writer = new FileWriter(checkFile);


        float total = 0;
        float generalTotal = 0;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US); // Убираем миллисекунды в LocalTime
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        String f = formatter.format(localTime);

        System.out.println("\t\t\t   CASH RECEIPT\t\t\t");
        writer.write("\t\t   CASH RECEIPT\t\t\t\n");
        System.out.println("\t\t\t SUPERMARKET  123\t\t\t");
        writer.write("\t\t SUPERMARKET  123\t\t\t\n");
        System.out.println("\t\t 12, MILKYWAY Galaxy/ Earth\t\t\t");
        writer.write("\t 12, MILKYWAY Galaxy/ Earth\t\t\t\n");
        System.out.println("\t\t\tTel :123-456-7890\t\t\t");
        writer.write("\t\tTel :123-456-7890\t\t\t\n");
        System.out.println();
        writer.write("\n");
        System.out.println("CASHIER: 1520\t\t\t\t" + "DATE: " + localDate);
        writer.write("CASHIER: 1520\t\t\t" + "DATE: " + localDate + "\n");
        System.out.println("\t\t\t\t\t\t\tTIME: " + f);
        writer.write("\t\t\t\tTIME: " + f + "\n");
        System.out.println("_____________________________________________");
        writer.write("_________________________________________________\n");
        System.out.println("QTY\t\tDESCRIPTION\t\tPRICE\t\tTOTAL");
        writer.write("QTY\tDESCRIPTION\t\tPRICE\tTOTAL\n");
        for(int i = 0; i < parsedProds.length; i++){
            for(Products products:products){
                if (products.getId() == parsedProds[i]/10){
                    total = products.getPrice() * (parsedProds[i]%10);
                    if(products.isOnSales() && parsedProds[i]%10 > 5){
                        total = total - (total * 0.1f);
                    }
                    System.out.println(parsedProds[i]%10 + "\t\t" + products.getName() + "\t\t\t" + products.getPrice() + "\t\t" + total);
                    writer.write(parsedProds[i]%10 + "\t" + products.getName() + "\t\t\t" + products.getPrice() + "\t" + total + "\n");
                    generalTotal += total;
                }
            }
        }
        System.out.println("_____________________________________________");
        writer.write("_________________________________________________\n");
        System.out.println("_____________________________________________");
        writer.write("_________________________________________________\n");
        System.out.println("TAXABLE TOT.\t\t\t\t\t\t" + (generalTotal - (generalTotal * sale/100)));
        writer.write("TAXABLE TOT.\t\t\t\t" + (generalTotal - (generalTotal * sale/100)) + "\n");
        System.out.println("DISCOUNT:" + sale + "%\t\t\t\t\t\t" + (generalTotal - (generalTotal -(generalTotal * sale/100))));
        writer.write("DISCOUNT:" + sale + "%\t\t\t\t" + (generalTotal - (generalTotal -(generalTotal * sale/100))) + "\n");
        System.out.println("TOTAL: \t\t\t\t\t\t\t\t" + generalTotal);
        writer.write("TOTAL: \t\t\t\t\t" + generalTotal + "\n");
        writer.flush();
        writer.close();
    }

    public static void initData(){

        /* Продукты */
        products.add(new Products(1, "Bread", 1.45f, false));
        products.add(new Products(2, "Milk", 3.19f, true));
        products.add(new Products(3, "Beef", 10.23f, false));
        products.add(new Products(4, "Onion", 4.12f, false));
        products.add(new Products(5, "Carrot", 6.81f, true));
        products.add(new Products(6, "Apple", 5.4f, true));
        products.add(new Products(7, "Lemon", 7.2f, true));
        products.add(new Products(8, "Cheese", 9.9f,false));
        products.add(new Products(9, "Candy bar", 1.12f, false));
        products.add(new Products(10, "Honey", 13.5f, false));

        /* Карточки */
        cards.add(new Cards(1234, 20));
        cards.add(new Cards(1864, 15));
        cards.add(new Cards(1232, 15));
        cards.add(new Cards(1222, 5));
        cards.add(new Cards(9531, 1));
    }

}



