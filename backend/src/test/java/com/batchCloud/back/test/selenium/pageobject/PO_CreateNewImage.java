package com.batchCloud.back.test.selenium.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_CreateNewImage extends PO_NavView {
	static public void fillForm(WebDriver driver, String imageNamep,String imageOpSystemp) {
		WebElement imageName= driver.findElement(By.name("imageName"));
		imageName.click();
		imageName.clear();
		imageName.sendKeys(imageNamep);
		WebElement imageOpSystem= driver.findElement(By.name("imageOpSystem"));
		imageOpSystem.click();
		imageOpSystem.clear();
		imageOpSystem.sendKeys(imageOpSystemp);
		// Pulsar el boton de Alta.
		By boton = By.name("submit");
		driver.findElement(boton).click();
	}
}
