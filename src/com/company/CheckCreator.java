package com.company;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class CheckCreator {

    private final static List<Product> products = new ArrayList<>();
    private final static List<Card> cards = new ArrayList<>();

    /* Parsing product args */
    public static int[] getProds(String[] args) throws FileNotFoundException {
        Scanner fileReader = new Scanner(new FileInputStream(args[0]));
        String [] split = fileReader.nextLine().split(" ");
        int [] parsedProds = new int[split.length];

        for (int i = 0; i < split.length; i++) {
            if (!split[i].contains("card")) {
                parsedProds[i] = Integer.parseInt(split[i].replaceAll("-", ""));
            }
        }
        return parsedProds;
    }

    /* Parsing card args */
    public static BigDecimal getSale(String[] args) throws FileNotFoundException {
        Scanner fileReader = new Scanner(new FileInputStream(args[0]));
        String  [] split = fileReader.nextLine().split(" ");
        int numCard = 0;
        BigDecimal sale = new BigDecimal(0);

        for (String s : split) {
            if (s.contains("card")) {
                numCard = Integer.parseInt(s.replaceAll("card-", ""));
            }
        }

        for(Card cards: cards){
            if (numCard == cards.getCardNumber()) {
                sale = cards.getDiscount();
            }
        }

        return sale;
    }

    /* Check formation */
    public static String getCheck(int[] parsedProds, BigDecimal sale){
        StringBuilder checkBuilder = new StringBuilder();
        StringBuilder dataBuilder = new StringBuilder();
        BigDecimal prodTotal;
        BigDecimal generalTotal = new BigDecimal(0);
        BigDecimal taxableTotal;
        BigDecimal discountToBigDecimal;
        BigDecimal generalDiscount;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss", Locale.US);
        LocalDate localDate = LocalDate.now();
        LocalTime localTime = LocalTime.now();
        String formattedLocalTime = formatter.format(localTime);

        for (int parsedProd : parsedProds) {
                for (Product products : products) {
                if (products.getId() == parsedProd / 10){
                    BigDecimal prodQtyInOrder = BigDecimal.valueOf(parsedProd % 10);
                    prodTotal = products.getPrice().multiply(prodQtyInOrder);
                    if (products.isOnSales() && parsedProd % 10 > 5) {
                        prodTotal = prodTotal.subtract(prodTotal.multiply(BigDecimal.valueOf(0.1))).setScale(2, RoundingMode.HALF_UP);
                    }
                    dataBuilder.append("\n").append(parsedProd % 10).append("\t\t").append(products.getName()).append("\t\t" +
                            "\t").append(products.getPrice()).append("\t\t").append(prodTotal);
                    generalTotal = generalTotal.add(prodTotal).setScale(2, RoundingMode.HALF_UP);
                }
            }
        }

        discountToBigDecimal = sale.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        taxableTotal = generalTotal.subtract(generalTotal.multiply(discountToBigDecimal).setScale(2, RoundingMode.HALF_UP));
        generalDiscount = generalTotal.subtract(taxableTotal);

        checkBuilder.append("   CASH RECEIPT\t\t\t\n\t\t\t SUPERMARKET  123\t\t\t\n\t\t 12, MILKYWAY Galaxy/ Earth\t\t\t" +
                "\n\t\t\tTel :123-456-7890\t\t\t\n\nCASHIER: 1520\t\t\t\t\t\t\t\tDATE: ");
        checkBuilder.append(localDate);
        checkBuilder.append("\n\t\t\t\t\t\t\t\t\t\t\tTIME: ");
        checkBuilder.append(formattedLocalTime);
        checkBuilder.append("\n_________________________________________________________________________" +
                "\nQTY\t\tDESCRIPTION\t\tPRICE\t\tTOTAL");
        checkBuilder.append(dataBuilder);
        checkBuilder.append("\n_________________________________________________________________________" +
                "\n_________________________________________________________________________" +
                "\nTAXABLE TOT.\t\t\t\t\t\t");
        checkBuilder.append(taxableTotal);
        checkBuilder.append("\nDISCOUNT:").append(sale).append("%\t\t\t\t\t\t").append(generalDiscount);
        checkBuilder.append("\nTOTAL: \t\t\t\t\t\t\t\t").append(generalTotal);

        return checkBuilder.toString();
    }

    public static void checkInFile(String check) throws IOException {
        File outputFile = new File("CheckFile.text");
        FileWriter writer = new FileWriter(outputFile);
        writer.write(check);
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
