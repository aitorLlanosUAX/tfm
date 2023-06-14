package com.batchCloud.back.test.selenium.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_CreateNewInstance extends PO_NavView {
	static public void fillForm(WebDriver driver, String instanceNamep, String instanceCPUp, String instanceMemoryp,
			String instanceCostp) {
		WebElement instanceName = driver.findElement(By.name("instanceName"));
		instanceName.click();
		instanceName.clear();
		instanceName.sendKeys(instanceNamep);
		WebElement instanceCPU = driver.findElement(By.name("instanceCPU"));
		instanceCPU.click();
		instanceCPU.clear();
		instanceCPU.sendKeys(instanceCPUp);
		WebElement instanceMemory = driver.findElement(By.name("instanceMemory"));
		instanceMemory.click();
		instanceMemory.clear();
		instanceMemory.sendKeys(instanceMemoryp);
		WebElement instanceCost = driver.findElement(By.name("instanceCost"));
		instanceCost.click();
		instanceCost.clear();
		instanceCost.sendKeys(instanceCostp);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		// Pulsar el boton de Alta.
		By boton = By.name("submit");
		driver.findElement(boton).click();
	}
}
