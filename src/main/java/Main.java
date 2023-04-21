
import org.xml.sax.SAXException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;

public class Main {

    public static String[] products = {"Хлеб", "Яблоки", "Молоко"};
    public static int[] prices = {100, 200, 300};


    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        XMLSettingsReader settings = new XMLSettingsReader(new File("shop.xml"));
        File loadFile = new File(settings.loadFile);
        File saveFile = new File(settings.saveFile);
        File logFile = new File(settings.logFile);


        Basket basket = createBasket(loadFile, settings.isLoad, settings.loadFormat);
        ClientLog log = new ClientLog();

        System.out.println("Список возможных товаров для покупки");
        for (int i = 0; i < products.length; i++) {
            System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " руб/шт ");
        }
        System.out.println("Выберите товар и количество или введите `end`");


        Scanner scanner = new Scanner(System.in);
        while (true) {
            String inputString = scanner.nextLine();
            if ("end".equals(inputString)) {
                if (settings.isLog) {
                    log.exportAsCSV(logFile);
                }
                break;
            }

            int productNumber = 0;
            int productCount = 0;

            String parts[] = inputString.split(" ");
            productNumber = Integer.parseInt(parts[0]) - 1;
            productCount = Integer.parseInt(parts[1]);

            basket.addToCart(productNumber, productCount);

            if (settings.isLog) {
                log.log(productNumber, productCount);
            }

            if (settings.isSave) {
                switch(settings.saveFormat){
                    case "json":
                         basket.saveJSON(saveFile);
                        break;
                    case "txt":
                        basket.saveTxt(saveFile);
                }
            }
        }
        basket.printCart();
    }

    private static Basket createBasket(File loadFile, boolean isLoad, String loadFormat) {
        Basket basket;
        if (isLoad && loadFile.exists()) {
            switch (loadFormat) {
                case "json":
                    basket = Basket.loadFromJSONFile(loadFile);
                    break;
                case "txt":
                    basket = Basket.loadFromTxtFile(loadFile);
                    break;
                default:
                    basket = new Basket(products, prices);
            }
        } else {
            basket = new Basket(products, prices);
        }
        return basket;
    }
}
