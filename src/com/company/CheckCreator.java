package com.company;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class CheckCreator {

    private final static List<Product> products = new ArrayList<>();
    private final static List<Card> cards = new ArrayList<>();

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

    public static BigDecimal getSale(int numCard){
        BigDecimal sale = new BigDecimal(0);
        for(Card cards: cards){
            if (numCard == cards.getCardNumber()) {
                sale = cards.getDiscount();
            }
        }
        return sale;
    }

    /* Check formation */
    public static void getCheck(int[] parsedProds, BigDecimal sale) throws IOException {

        File checkFile = new File("CheckFile.text");
        FileWriter writer = new FileWriter(checkFile);

        BigDecimal total;
        BigDecimal generalTotal = new BigDecimal(0);
        BigDecimal percent = new BigDecimal(0.1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US);
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
        for (int parsedProd : parsedProds) {
            for (Product products : products) {
                if (products.getId() == parsedProd / 10) {
                    BigDecimal mod = BigDecimal.valueOf(parsedProd % 10);
                    total = products.getPrice().multiply(mod);
                    if (products.isOnSales() && parsedProd % 10 > 5) {
                        total = total.subtract(total.multiply(percent));
                    }
                    System.out.println(parsedProd % 10 + "\t\t" + products.getName() + "\t\t\t" + products.getPrice() + "\t\t" + total.setScale(2, RoundingMode.HALF_DOWN));
                    writer.write(parsedProd % 10 + "\t" + products.getName() + "\t\t\t" + products.getPrice() + "\t" + total.setScale(2, RoundingMode.HALF_DOWN) + "\n");
                    generalTotal = generalTotal.add(total);
                }
            }
        }
        System.out.println("_____________________________________________");
        writer.write("_________________________________________________\n");
        System.out.println("_____________________________________________");
        writer.write("_________________________________________________\n");
        System.out.println("TAXABLE TOT.\t\t\t\t\t\t" + (generalTotal.subtract(generalTotal.multiply(sale.divide(BigDecimal.valueOf(100))))).setScale(2,RoundingMode.HALF_DOWN));
        writer.write("TAXABLE TOT.\t\t\t\t" + (generalTotal.subtract(generalTotal.multiply(sale.divide(BigDecimal.valueOf(100)))).setScale(2, RoundingMode.HALF_DOWN)) + "\n");
        System.out.println("DISCOUNT:" + sale + "%\t\t\t\t\t\t" + (generalTotal.subtract(generalTotal.subtract(generalTotal.multiply(sale.divide(BigDecimal.valueOf(100)))))).setScale(2, RoundingMode.HALF_DOWN));
        writer.write("DISCOUNT:" + sale + "%\t\t\t\t" + (generalTotal.subtract(generalTotal.subtract(generalTotal.multiply(sale.divide(BigDecimal.valueOf(100))))).setScale(2, RoundingMode.HALF_DOWN)) + "\n");
        System.out.println("TOTAL: \t\t\t\t\t\t\t\t" + generalTotal.setScale(2, RoundingMode.HALF_DOWN));
        writer.write("TOTAL: \t\t\t\t\t" + generalTotal.setScale(2, RoundingMode.HALF_DOWN) + "\n");
        writer.flush();
        writer.close();
    }

    public static void initData(){
        /* Product list */
        products.add(new Product(1, "Bread", BigDecimal.valueOf(1.45), false));
        products.add(new Product(2, "Milk", BigDecimal.valueOf(3.19), true));
        products.add(new Product(3, "Beef", BigDecimal.valueOf(10.23), false));
        products.add(new Product(4, "Onion", BigDecimal.valueOf(4.12), false));
        products.add(new Product(5, "Carrot", BigDecimal.valueOf(6.81), true));
        products.add(new Product(6, "Apple", BigDecimal.valueOf(5.4), true));
        products.add(new Product(7, "Lemon", BigDecimal.valueOf(7.2), true));
        products.add(new Product(8, "Cheese", BigDecimal.valueOf(9.9),false));
        products.add(new Product(9, "Candy bar", BigDecimal.valueOf(1.12), false));
        products.add(new Product(10, "Honey", BigDecimal.valueOf(13.5), false));
        /* Card list */
        cards.add(new Card(1234, BigDecimal.valueOf(20)));
        cards.add(new Card(1864, BigDecimal.valueOf(15)));
        cards.add(new Card(1232, BigDecimal.valueOf(15)));
        cards.add(new Card(1222, BigDecimal.valueOf(5)));
        cards.add(new Card(9531, BigDecimal.valueOf(1)));
    }
}
