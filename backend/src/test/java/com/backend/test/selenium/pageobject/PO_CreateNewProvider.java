package com.backend.test.selenium.pageobject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PO_CreateNewProvider extends PO_NavView {
	static public void fillForm(WebDriver driver, String aws_keyp,String aws_secretKeyp) {
		WebElement aws_key= driver.findElement(By.name("aws_key"));
		aws_key.click();
		aws_key.clear();
		aws_key.sendKeys(aws_keyp);
		WebElement aws_secretKey= driver.findElement(By.name("aws_secretKey"));
		aws_secretKey.click();
		aws_secretKey.clear();
		aws_secretKey.sendKeys(aws_secretKeyp);
		// Pulsar el boton de Alta.
		By boton = By.name("submit");
		driver.findElement(boton).click();
	}
}
