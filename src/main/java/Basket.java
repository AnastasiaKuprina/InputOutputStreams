import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.Arrays;

public class Basket {

    private String[] products;
    private int[] prices;
    private int[] totalProductCount;
    private int sumProducts = 0;

    public Basket() {

    }

    //конструктор, принимающий массив цен и названий продуктов
    public Basket(String[] products, int[] prices) {
        this.products = products;
        this.prices = prices;
        this.totalProductCount = new int[products.length];
    }

    //метод добавления amount штук продукта номер productNum в корзину;
    public void addToCart(int productNum, int amount) {
        totalProductCount[productNum] = totalProductCount[productNum] + amount;
    }

    // метод вывода на экран покупательской корзины
    public void printCart() {
        System.out.println("Ваша корзина");
        for (int i = 0; i < totalProductCount.length; i++) {
            if (totalProductCount[i] > 0) {
                int oneProductAmounts = prices[i] * totalProductCount[i];
                sumProducts = sumProducts + oneProductAmounts;
                System.out.println(products[i] + " " + totalProductCount[i] + " шт " + oneProductAmounts + " руб в сумме");
            }
        }
        System.out.println("Итого " + sumProducts + " руб");
    }

    //метод сохранения корзины в текстовый файл
    public void saveTxt(File textFile) {
        try (PrintWriter out = new PrintWriter(textFile)) {
            for (String product : products)
                out.print(product + " ");
            out.println();
            for (int price : prices)
                out.print(price + " ");
            out.println();
            for (int quantity : totalProductCount) {
                out.print(quantity + " ");
            }

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    // статический(!) метод восстановления объекта корзины из текстового файла
    public static Basket loadFromTxtFile(File textFile) {
        Basket basket = new Basket();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(textFile))) {
            String productsStr = bufferedReader.readLine();
            String pricesStr = bufferedReader.readLine();
            String totalProductCountStr = bufferedReader.readLine();

            basket.products = productsStr.split(" ");
            basket.prices = Arrays.stream(pricesStr.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();

            basket.totalProductCount = Arrays.stream(totalProductCountStr.split(" "))
                    .map(Integer::parseInt)
                    .mapToInt(Integer::intValue)
                    .toArray();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basket;
    }

    public void saveJSON(File file) {
        try (PrintWriter writer = new PrintWriter(file)) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(this);
            writer.print(json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Basket loadFromJSONFile(File file) {
        Basket basket = null;
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            Gson gson = new Gson();
            basket = gson.fromJson(builder.toString(), Basket.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return basket;
        }
    }

