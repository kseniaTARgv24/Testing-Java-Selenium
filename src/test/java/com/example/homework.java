package com.example;
import java.time.Duration;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class homework {
    WebDriver driver = new ChromeDriver();
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(9));
    WebDriverWait waitLong = new WebDriverWait(driver, Duration.ofSeconds(30));

    @AfterEach
    public void finish() {
        driver.close();
        driver.quit();
    }


@Test 
void sampleApp(){
    driver.get("http://uitestingplayground.com/sampleapp");
    driver.findElement(By.name("UserName")).sendKeys("IlusNimi");
    driver.findElement(By.name("Password")).sendKeys("pwd");
    driver.findElement(By.id("login")).click();

    WebElement msg = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.id("loginstatus"))
    );

    assertTrue(msg.getText().contains("Welcome, IlusNimi!"));
}

@Test
void sampleAppLogout(){
    driver.get("http://uitestingplayground.com/sampleapp");
    driver.findElement(By.name("UserName")).sendKeys("IlusNimi");
    driver.findElement(By.name("Password")).sendKeys("pwd");
    driver.findElement(By.id("login")).click();

    driver.findElement(By.id("login")).click();

    WebElement msg = wait.until(
        ExpectedConditions.visibilityOfElementLocated(By.id("loginstatus"))
    );

    assertTrue(msg.getText().contains("User logged out."));
}

@Test 
void dynamicId(){
    driver.get("https://uitestingplayground.com/dynamicid");
    driver.findElement(By.className("btn btn-primary")).click();
    assertTrue(true);
}

@Test 
void classAttribute(){
    driver.get("https://uitestingplayground.com/classattr");
    driver.findElement(By.className("btn class1 btn-primary btn-test")).click();

    Alert alert = driver.switchTo().alert();
    assertTrue(alert.getText().contains("Primary"));
    alert.accept();
}

@Test 
void hiddenLayers(){
    driver.get("https://uitestingplayground.com/hiddenlayers");
    driver.findElement(By.id("greenButton")).click();

    assertThrows(ElementClickInterceptedException.class, ()-> {
        driver.findElement(By.id("greenButton")).click();
    });

    // 	static <T extends Throwable> T assertThrows(Class<T> expectedType, Executable executable,
	// 		@Nullable String message) {
	// 	return assertThrows(expectedType, executable, (Object) message);
	// }
}

@Test
void loadDelay(){
    driver.get("https://uitestingplayground.com/loaddelay");
    WebElement msg = waitLong.until(
        ExpectedConditions.visibilityOfElementLocated(By.className("btn btn-primary")));

        assertTrue(msg.isDisplayed());
    
}

@Test 
void ajaxData(){
    driver.get("https://uitestingplayground.com/ajax");
    driver.findElement(By.id("ajaxButton")).click();

    WebElement msg = waitLong.until(
        ExpectedConditions.visibilityOfElementLocated(By.className("bg-success"))
    );

    assertTrue(msg.getText().trim().contains("Data loaded with AJAX get request."));

}



}


