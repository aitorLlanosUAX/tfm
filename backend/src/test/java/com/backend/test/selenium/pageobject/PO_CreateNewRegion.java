package com.backend.test.selenium.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_CreateNewRegion extends PO_NavView {
	static public void fillForm(WebDriver driver, String regionNamep,String regionZonep) {
		WebElement regionName= driver.findElement(By.name("regionName"));
		regionName.click();
		regionName.clear();
		regionName.sendKeys(regionNamep);
		WebElement regionZone= driver.findElement(By.name("regionZone"));
		regionZone.click();
		regionZone.clear();
		regionZone.sendKeys(regionZonep);
		// Pulsar el boton de Alta.
		By boton = By.name("submit");
		driver.findElement(boton).click();
	}
}
