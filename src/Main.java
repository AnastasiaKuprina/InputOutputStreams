import java.util.Scanner;
import java.io.File;
import java.io.IOException;

public class Main {

    public static String[] products = {"Хлеб", "Яблоки", "Молоко"};
    public static int[] prices = {100, 200, 300};
    public static File basketFile = new File("basket.bin");

    public static void main(String[] args) {

        Basket basket;
        if (basketFile.exists()) {
              basket = Basket.loadFromBinFile(basketFile);
        } else {
              basket = new Basket(products, prices);
        }

        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " +  products[i] + " " + prices[i] + " руб/шт ");
        }
        System.out.println("Выберите товар и количество или введите `end`");


        Scanner scanner = new Scanner(System.in);
        while (true){
            String inputString = scanner.nextLine();
            if ("end".equals(inputString)) {
                break;
            }

            int productNumber = 0;
            int productCount = 0;

            String parts[] = inputString.split(" ");
            productNumber = Integer.parseInt(parts[0])-1;
            productCount = Integer.parseInt(parts[1]);

            basket.addToCart(productNumber, productCount);
            basket.saveBin(basketFile);
        }

        basket.printCart();

    }
}
