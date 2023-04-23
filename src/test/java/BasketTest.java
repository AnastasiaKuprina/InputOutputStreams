import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.netology.testing.Basket;

import java.io.File;

class BasketTest {


    @Test
    public void testAddToBasket() {
        String[] products = {"Хлеб", "Яблоки", "Молоко"};
        int[] prices = {100, 200, 300};
        Basket basket = new Basket(products, prices);

        basket.addToCart(0, 2);
        basket.addToCart(1, 4);

        int[] actual = basket.getTotalProductCount();
        int[] expected = {2, 4, 0};

        Assertions.assertArrayEquals(expected, actual);
    }

    @Test
    public void testLoadFromTXT() {
        Basket basket = Basket.loadFromTxtFile(new File("src/test/resources/test_basket.txt"));

        String[] actualProducts = basket.getProducts();
        String[] expectedProducts = {"Хлеб", "Яблоки", "Молоко"};
        Assertions.assertArrayEquals(expectedProducts, actualProducts);
    }

    @Test
    public void testLoadFromTxtWhenFileNotExist() {
        Assertions.assertThrows(RuntimeException.class,
                () -> Basket.loadFromTxtFile(new File("src/test/resources/test_bask.txt"))
        );
    }

}