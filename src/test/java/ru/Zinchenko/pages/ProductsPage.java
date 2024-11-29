package ru.Zinchenko.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.Zinchenko.items.ProductItem;
import ru.Zinchenko.properties.AppProperties;
import ru.Zinchenko.utils.PropConst;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class ProductsPage {
    private final WebDriver driver;
    private final WebDriverWait wait;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait =  new WebDriverWait(driver, Duration.ofSeconds(100));
    }

    public WebElement getTable(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='table']")));
    }

    public WebElement getButtonAdd() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@class='btn btn-primary']")));
    }

    public WebElement getInputName(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='name']")));
    }

    public WebElement getInputType(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//select[@id='type']")));
    }

    public WebElement getInputExotic(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='exotic']")));
    }

    public WebElement getButtonSave(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@id='save']")));
    }

    public WebElement getDropdown(){
        return driver.findElement(By.xpath("//a[@id='navbarDropdown']"));
    }

    public WebElement getResetButton(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@id='reset']")));
    }

    public List<WebElement> getCellsFromRow(WebElement row) {
        List<WebElement> cells = new ArrayList<>();

        for (int i = 0; i < Integer.parseInt(AppProperties.getProperty(PropConst.RETRY_NUMBER)); i++) {
            try {
                cells = row.findElements(By.tagName("td"));
                break;
            } catch (StaleElementReferenceException ex) {
                System.out.println(PropConst.ERR_MESSAGE_FOR_STALE_ELEMENT + " " + i + ". Get cells");
            }
        }

        return cells;
    }

    public void sendName(String name) {
        WebElement input = getInputName();

        for (int i = 0; i < Integer.parseInt(AppProperties.getProperty(PropConst.RETRY_NUMBER)); i++) {
            try {
                input.click();
                break;
            } catch (StaleElementReferenceException ex) {
                System.out.println(PropConst.ERR_MESSAGE_FOR_STALE_ELEMENT + " " + i + ". Input name");
            }
        }
        input.sendKeys(name);
    }

    public void sendType(String type) {
        WebElement selectType = getInputType();
        Select selectInTypes = new Select(selectType);
        selectInTypes.selectByVisibleText(type);
    }

    public void sendExotic(Boolean isExotic) {
        WebElement checkBoxExotic = getInputExotic();
        if (isExotic != checkBoxExotic.isSelected()) {
            checkBoxExotic.click();
        }
    }

    public void clickAdd() {

        for (int i = 0; i < Integer.parseInt(AppProperties.getProperty(PropConst.RETRY_NUMBER)); i++) {
            try {
                getButtonAdd().click();
                break;
            } catch (StaleElementReferenceException ex) {
                System.out.println(PropConst.ERR_MESSAGE_FOR_STALE_ELEMENT + " " + i + ". add button");
            }
        }

    }
    public void clickSave(){
        getButtonSave().click();
    }

    public void addNewProduct(ProductItem item) {
        clickAdd();

        sendName(item.getName());
        sendType(item.getType());
        sendExotic(item.isExotic());

        clickSave();
    }

    public boolean isAddingValid(List<ProductItem> items) {
        int countFounded = 0;
        List<WebElement> rows = new ArrayList<>();
        WebElement table = getTable();
        for (int i = 0; i < Integer.parseInt(AppProperties.getProperty(PropConst.RETRY_NUMBER)); i++) {
            try {
                rows = table.findElements(By.tagName("tr"));
                break;
            } catch (StaleElementReferenceException ex) {
                System.out.println(PropConst.ERR_MESSAGE_FOR_STALE_ELEMENT + " " + i + ". Get lines");
            }
        }

        for (WebElement row: rows){

            List<WebElement> cells = getCellsFromRow(row);

            for (ProductItem item : items) {
                if (!cells.isEmpty() &&
                        cells.get(0).getText().equals(item.getName()) &&
                        cells.get(1).getText().equals(item.getType()) &&
                        cells.get(2).getText().equals(String.valueOf(item.isExotic()))) {

                    countFounded++;
                    break;
                }
            }
        }

        return countFounded == items.size();
    }

    public void reset() {
        for (int i = 0; i < Integer.parseInt(AppProperties.getProperty(PropConst.RETRY_NUMBER)); i++) {
            try {
                getDropdown().click();
                break;
            } catch (StaleElementReferenceException ex) {
                System.out.println(PropConst.ERR_MESSAGE_FOR_STALE_ELEMENT + " " + i + ". Get dropdown");
            }
        }

        for (int i = 0; i < Integer.parseInt(AppProperties.getProperty(PropConst.RETRY_NUMBER)); i++) {
            try {
                getResetButton().click();
                break;
            } catch (StaleElementReferenceException ex) {
                System.out.println(PropConst.ERR_MESSAGE_FOR_STALE_ELEMENT + " " + i + ". Reset button");
            }
        }

    }

}
