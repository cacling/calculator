package com.example.calculator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalculatorSeleniumTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "path/to/chromedriver"); // Replace with the path to your chromedriver
        driver = new ChromeDriver();
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void testCalculatorApp() {
        driver.get("http://localhost:8080"); // Replace with your application's URL

        // Enter expression and click the Calculate button
        WebElement input = driver.findElement(By.id("expression-input")); // Replace with the appropriate element ID
        input.sendKeys("(3.2+0.8)*3+(3-1)");
        WebElement calculateButton = driver.findElement(By.id("calculate-button")); // Replace with the appropriate element ID
        calculateButton.click();

        // Verify result
        WebElement result = driver.findElement(By.id("result")); // Replace with the appropriate element ID
        assertEquals("14", result.getText());

        // Click Undo button
        WebElement undoButton = driver.findElement(By.id("undo-button")); // Replace with the appropriate element ID
        undoButton.click();

        // Verify the last operation is undone
        WebElement lastOperation = driver.findElement(By.id("last-operation")); // Replace with the appropriate element ID
        assertEquals("3-1", lastOperation.getText());
        assertEquals("2", result.getText());

        // Click Redo button
        WebElement redoButton = driver.findElement(By.id("redo-button")); // Replace with the appropriate element ID
        redoButton.click();

        // Verify the last operation is redone
        assertEquals("(3.2+0.8)*3+(3-1)", lastOperation.getText());
        assertEquals("14", result.getText());
    }
}