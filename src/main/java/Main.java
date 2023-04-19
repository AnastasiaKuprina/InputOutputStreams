//package main.java;

import java.io.IOException;
import java.util.Scanner;
import java.io.File;

public class Main {

    public static String[] products = {"Хлеб", "Яблоки", "Молоко"};
    public static int[] prices = {100, 200, 300};
    public static File basketFile = new File("basket.json");

    public static void main(String[] args) throws IOException {

        Basket basket;
        if (basketFile.exists()) {
            basket = Basket.loadFromJSONFile(basketFile);
        } else {
            basket = new Basket(products, prices);
        }

        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб/шт ");
        }
        System.out.println("Выберите товар и количество или введите `end`");


        ClientLog log = new ClientLog();
        Scanner scanner = new Scanner(System.in);
        while (true) {
            String inputString = scanner.nextLine();
            if ("end".equals(inputString)) {
                log.exportAsCSV(new File("log.csv"));
                break;
            }

            int productNumber = 0;
            int productCount = 0;

            String parts[] = inputString.split(" ");
            productNumber = Integer.parseInt(parts[0]) - 1;
            productCount = Integer.parseInt(parts[1]);

            basket.addToCart(productNumber, productCount);
            log.log(productNumber, productCount);
            basket.saveJSON(basketFile);
        }

        basket.printCart();

    }
}
