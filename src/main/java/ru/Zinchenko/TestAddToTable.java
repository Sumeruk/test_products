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

public class TestAddToTable implements TestProducts {

    private final WebDriver driver = new ChromeDriver();
    private final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    private final String name1;
    private final String name2;

    private final String type;

    private final boolean isExotic1;
    private final boolean isExotic2;

    public TestAddToTable(String name1, String name2, String type, boolean isExotic1, boolean isExotic2) {
        this.name1 = name1;
        this.name2 = name2;
        this.type = type;
        this.isExotic1 = isExotic1;
        this.isExotic2 = isExotic2;
    }

    @Override
    public boolean runTest() {

        try {
            driver.get("http://localhost:8080/food");

            List<WebElement> rowsInTable = driver.findElements(By.xpath("//table[@class='table']//tr"));
            int startTableSize = rowsInTable.size();

            addProductToTable(name1, type, isExotic1);
            addProductToTable(name2, type, isExotic2);

            WebElement table =
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='table']")));

            boolean res = checkToExpectedResultsInTable(table, startTableSize);
                    resetData();
            return res;

        } finally {

            driver.quit();
        }
    }

    private void addProductToTable(String name, String type, boolean isExotic){

        try {
            Thread.sleep(500);
        } catch (InterruptedException e){
            System.out.println(e.getMessage());
        }

        WebElement buttonAdd =
                wait.until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//button[@class='btn btn-primary']")));
        buttonAdd.click();

        WebElement inputName =
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='name']")));
        inputName.click();
        inputName.sendKeys(name);

        WebElement selectType = driver.findElement(By.xpath("//select[@id='type']"));
        Select selectInTypes = new Select(selectType);
        selectInTypes.selectByVisibleText(type);

        WebElement checkBoxExotic = driver.findElement(By.xpath("//input[@id='exotic']"));
        if (isExotic != checkBoxExotic.isSelected()){
            checkBoxExotic.click();
        }

        WebElement buttonSave = driver.findElement(By.xpath("//button[@id='save']"));
        buttonSave.click();
    }

    private boolean checkToExpectedResultsInTable(WebElement table, int startTableSize){
        boolean firstAdd = false, secondAdd = false;
        List<WebElement> rows = table.findElements(By.tagName("tr"));

        for (WebElement row : rows) {
            List<WebElement> lines = row.findElements(By.tagName("td"));

            if (!lines.isEmpty() &&
                    lines.get(0).getText().equals(name1) &&
                    lines.get(1).getText().equals(type) &&
                    lines.get(2).getText().equals(String.valueOf(isExotic1))) {

                firstAdd = true;
            }

            if (!lines.isEmpty() &&
                    lines.get(0).getText().equals(name2) &&
                    lines.get(1).getText().equals(type) &&
                    lines.get(2).getText().equals(String.valueOf(isExotic2))) {

                secondAdd = true;
            }

        }

        return firstAdd && secondAdd && (rows.size() - startTableSize == 2);
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
