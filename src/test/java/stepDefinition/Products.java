package stepDefinition;

//public class Products {
//WebDriverManager.chromedriver().setup();

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class Products {
    public static void main(String[] args) {
        
        //System.setProperty("webdriver.chrome.driver", "C:\\path\\to\\chromedriver.exe");
        WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();

        try {
            // Maximize the browser window
            driver.manage().window().maximize();
            // Open SauceDemo login page
            driver.get("https://www.saucedemo.com/");

            // Login
            driver.findElement(By.id("user-name")).sendKeys("standard_user");
            driver.findElement(By.id("password")).sendKeys("secret_sauce");
            Thread.sleep(3000);

            driver.findElement(By.id("login-button")).click();

            // Get all product elements with prices
            List<WebElement> prices = driver.findElements(By.className("inventory_item_price"));
            List<WebElement> addToCartButtons = driver.findElements(By.cssSelector(".btn_inventory"));

            double maxPrice = 0;
            int maxPriceIndex = -1;

            // Find the highest price product
            for (int i = 0; i < prices.size(); i++) {
                String priceText = prices.get(i).getText().replace("$", "");
                double price = Double.parseDouble(priceText);

                if (price > maxPrice) {
                    maxPrice = price;
                    maxPriceIndex = i;
                }
            }

            if (maxPriceIndex != -1) {
                // Click the "Add to Cart" button for the highest price product
                addToCartButtons.get(maxPriceIndex).click();
                System.out.println("Added highest price product ($" + maxPrice + ") to the cart.");

                // Open the cart page
                driver.findElement(By.className("shopping_cart_link")).click();
                System.out.println("Cart is opened.");

            } else {
                System.out.println("No products found.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Close the browser
            //driver.quit();
        }
    }
}

