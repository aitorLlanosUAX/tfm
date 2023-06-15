package com.backend.test.selenium.pageobject;

import org.openqa.selenium.WebDriver;

import com.backend.test.util.SeleniumUtils;

public class PO_HomeView extends PO_NavView {
	static public void checkWelcome(WebDriver driver,String text) {
		SeleniumUtils.EsperaCargaPagina(driver, "text", text, getTimeout());
	}
}
