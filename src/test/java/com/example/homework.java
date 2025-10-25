package com.example;
import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
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

@Test 
void textInput(){
    driver.get("https://uitestingplayground.com/textinput");
    driver.findElement(By.id("newButtonName")).sendKeys("Hello");
    driver.findElement(By.id("updatingButton")).click();

    WebElement btn = driver.findElement(By.id("updatingButton"));
    assertTrue(btn.getText().trim().contains("Hello"));

}

@Test
void scrollBars(){
    driver.get("https://uitestingplayground.com/scrollbars");
    WebElement hb = driver.findElement(By.id("hidingButton"));

    Actions actions = new Actions(driver); //как живое движение по странице
    
    actions.moveToElement(hb).perform();
    hb.click();
}

@Test 
void overlappedElement(){
    driver.get("https://uitestingplayground.com/overlapped");
    WebElement name = driver.findElement(By.id("Name"));

    try {
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", name);
        name.sendKeys("abc");
    } catch (ElementNotInteractableException e) {
        WebElement container = driver.findElement(By.cssSelector("div[style*='overflow-y: scroll']"));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollTop = arguments[0].scrollHeight;", container
        );
        name.sendKeys("abc");
    } 
    //два способа прокрутки, на случай, если можно крутить и страницу и отдельно контейнер

    assertTrue(name.getAttribute("value").contains("abc"));

}

@Test
void visibility() {
    driver.get("https://uitestingplayground.com/visibility");

    driver.findElement(By.id("hideButton")).click();

    
    String[] ids = {
            "removedButton",
            "zeroWidthButton",
            "overlappedButton",
            "transparentButton",
            "invisibleButton",
            "notdisplayedButton",
            "offscreenButton"
    };// Массив Hide/Remove/Zero Height jms.

    for (String id : ids) {
        List<WebElement> elements = driver.findElements(By.id(id));

        if (elements.isEmpty()) {
            System.out.println(id + " ➜ ❌ not in DOM");
        } else {
            boolean displayed = elements.get(0).isDisplayed();
            System.out.println(id + " ➜ isDisplayed() = " + displayed);
        }
    }
}

@Test
void click() {
    driver.get("https://uitestingplayground.com/click");

    WebElement button = driver.findElement(By.className("btn-primary"));
    button.click();

    WebElement success = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.className("btn-success"))
    );

    assertTrue(success.getAttribute("class").contains("btn-success"));
}

@Test
void progressBar() {
    driver.get("https://uitestingplayground.com/progressbar");

    driver.findElement(By.id("startButton")).click();

    waitLong.until(driver -> {
        WebElement progress = driver.findElement(By.id("progressBar"));
        int value = Integer.parseInt(progress.getAttribute("aria-valuenow"));
        return value >= 75;
    });

    driver.findElement(By.id("stopButton")).click();

    WebElement progress = driver.findElement(By.id("progressBar"));
    int finalValue = Integer.parseInt(progress.getAttribute("aria-valuenow"));
    assertTrue(finalValue >= 75);
}

@Test
void mouseOver() {
    driver.get("https://uitestingplayground.com/mouseover");

    Actions actions = new Actions(driver);

    for (int i = 0; i < 2; i++) {
        WebElement clickMe = driver.findElement(By.xpath("//label[text()='Click me']"));
        actions.moveToElement(clickMe).click().perform();
    }
    WebElement counter = driver.findElement(By.id("clickCount"));
    int value = Integer.parseInt(counter.getText());

    
    assertTrue(value == 2);
}

@Test
void shadowDom() {
    driver.get("https://uitestingplayground.com/shadowdom");

    WebElement shadowHost = driver.findElement(By.cssSelector("guid-generator"));
    SearchContext shadowRoot = shadowHost.getShadowRoot();

    shadowRoot.findElement(By.cssSelector("button#buttonGenerate")).click();

    WebElement input = shadowRoot.findElement(By.id("editField"));
    String guid = input.getAttribute("value");
    shadowRoot.findElement(By.cssSelector("button#buttonCopy")).click();
    assertTrue(guid != null && !guid.isEmpty());

 
   
}




}


