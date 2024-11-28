package ru.Zinchenko;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.Zinchenko.items.ProductItem;

import java.time.Duration;
import java.util.List;

import static java.lang.Thread.sleep;

public class ProductsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait =  new WebDriverWait(driver, Duration.ofSeconds(100));
    }

    public List<WebElement> getRowsInTable(){
        return driver.findElements(By.xpath("//table[@class='table']//tr"));
    }

    public WebElement getTable(){
        return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//table[@class='table']")));
    }

    public WebElement getButtonAdd(){
        try {
            sleep(500);
        } catch (InterruptedException ex){
            System.out.println(ex.getMessage());
        }
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

    public List<WebElement> getCellsFromRow(WebElement row){
        try {
            sleep(1000);
        } catch (InterruptedException ex){
            System.out.println(ex.getMessage());
        }
        return row.findElements(By.tagName("td"));
    }

    public void sendName(String name){
        WebElement input = getInputName();
        input.click();
        input.sendKeys(name);
    }

    public void sendType(String type){
        WebElement selectType = getInputType();
        Select selectInTypes = new Select(selectType);
        selectInTypes.selectByVisibleText(type);
    }

    public void sendExotic(Boolean isExotic){
        WebElement checkBoxExotic = driver.findElement(By.xpath("//input[@id='exotic']"));
        if (isExotic != checkBoxExotic.isSelected()){
            checkBoxExotic.click();
        }
    }

    public void clickAdd(){
        getButtonAdd().click();
    }
    public void clickSave(){
        getButtonSave().click();
    }

    public void addNewProduct(ProductItem item){
        clickAdd();

        sendName(item.getName());
        sendType(item.getType());
        sendExotic(item.isExotic());

        clickSave();
    }

    public boolean isAddingValid(List<ProductItem> items){
        int countFounded = 0;
        WebElement table = getTable();
        List<WebElement> rows = table.findElements(By.tagName("tr"));
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

    public void reset(){
        WebElement dropdown = getDropdown();
        dropdown.click();

        WebElement reset =
                getResetButton();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e){
            System.out.println(e.getMessage());
        }
        reset.click();
    }

}
