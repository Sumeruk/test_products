package ru.Zinchenko;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class TestFruit implements TestProducts {

    private final WebDriver driver = new ChromeDriver();
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    @Override
    public boolean startTest() {

        try {
            driver.get("http://localhost:8080/food");

            List<WebElement> rowsInTable = driver.findElements(By.xpath("//tr"));
            int startTableSize = rowsInTable.size();

            addProductToTable("банан", false);
            addProductToTable("Дуриан", true);

            WebElement tmpElement =
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr")));
            List<WebElement> rowsInTableEnd = driver.findElements(By.xpath("//tr"));

//            List<WebElement> rows = driver.findElements(By.xpath("//table//tr"));


            int endTableSize = rowsInTableEnd.size();

            resetData();

            return endTableSize - startTableSize == 2;

        } finally {
            driver.quit();
        }
    }

    private void addProductToTable(String name, boolean isExotic){

        WebElement buttonAdd =
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button[@class='btn btn-primary']")));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
        buttonAdd.click();

        WebElement inputName =
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='name']")));
        inputName.click();
        inputName.sendKeys(name);

        WebElement selectType = driver.findElement(By.xpath("//select[@id='type']"));
        Select selectInTypes = new Select(selectType);
        selectInTypes.selectByVisibleText("Фрукт");

        WebElement checkBoxExotic = driver.findElement(By.xpath("//input[@id='exotic']"));
        if (isExotic != checkBoxExotic.isSelected()){
            checkBoxExotic.click();
        }

        WebElement buttonSave = driver.findElement(By.xpath("//button[@id='save']"));
        buttonSave.click();
    }

    private void resetData(){
        WebElement dropdown = driver.findElement(By.xpath("//a[@id='navbarDropdown']"));
        dropdown.click();

        WebElement reset =
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='reset']")));
        try {
            Thread.sleep(500);
        } catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
        reset.click();

    }
}
