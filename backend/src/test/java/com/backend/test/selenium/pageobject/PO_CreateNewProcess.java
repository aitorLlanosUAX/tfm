package com.backend.test.selenium.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_CreateNewProcess extends PO_NavView {
	static public void fillForm(WebDriver driver, String processNamep,String instancep) {
		WebElement processName= driver.findElement(By.name("processName"));
		processName.click();
		processName.clear();
		processName.sendKeys(processNamep);
		WebElement instance= driver.findElement(By.name("instanceNumber"));
		instance.click();
		instance.clear();
		instance.sendKeys(instancep);
		// Pulsar el boton de Alta.
		By boton = By.name("submit");
		driver.findElement(boton).click();
	}
}
