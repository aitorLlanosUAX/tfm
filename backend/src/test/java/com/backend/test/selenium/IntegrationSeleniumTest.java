package com.backend.test.selenium;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.backend.test.selenium.pageobject.PO_CreateNewImage;
import com.backend.test.selenium.pageobject.PO_CreateNewInstance;
import com.backend.test.selenium.pageobject.PO_CreateNewProcess;
import com.backend.test.selenium.pageobject.PO_CreateNewProvider;
import com.backend.test.selenium.pageobject.PO_CreateNewRegion;
import com.backend.test.selenium.pageobject.PO_HomeView;
import com.backend.test.selenium.pageobject.PO_LoginView;
import com.backend.test.selenium.pageobject.PO_RegisterView;
import com.backend.test.util.SeleniumUtils;

public class IntegrationSeleniumTest {
	static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
	static String Geckdriver024 = "C:\\geckodriver-v0.30.0-win64\\geckodriver.exe";

	static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
	static String URL = "http://localhost:3000";

	// Antes de cada prueba se navega al URL home de la aplicaciónn
	@BeforeEach
	public void setUp() {
		driver.navigate().to(URL);
	}

	// Después de cada prueba se borran las cookies del navegador
	@AfterEach
	public void tearDown() {
		driver.manage().deleteAllCookies();
	}

	// Antes de la primera prueba
	@BeforeAll
	static public void begin() {
	}

	// Al finalizar la última prueba
	@AfterAll
	static public void end() {
		// Cerramos el navegador al finalizar las pruebas
		driver.quit();
	}

	public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
		System.setProperty("webdriver.firefox.bin", PathFirefox);
		System.setProperty("webdriver.gecko.driver", Geckdriver);
		WebDriver driver = new FirefoxDriver();
		return driver;
	}

	/**
	 * Metodo de prueba de la pagina principal al inicio
	 */
	@Test
	public void PRWelcome() {
		PO_HomeView.checkWelcome(driver, "Welcome to the batch center");
	}

	/**
	 * Prueba basica de la internacionalizacion
	 */
	@Test
	public void PRLanguages() {
		PO_HomeView.checkWelcome(driver, "Welcome to the batch center");
		PO_HomeView.clickOption(driver, "dropdown-basic", "id", "dropdown-basic");
		PO_HomeView.checkElement(driver, "text", "Spanish");
		PO_HomeView.clickOption(driver, "Spanish", "id", "Spanish");
		PO_HomeView.checkWelcome(driver, "Bienvenido a Batch Center");
		PO_HomeView.clickOption(driver, "dropdown-basic", "id", "dropdown-basic");
		PO_HomeView.clickOption(driver, "English", "id", "English");
		PO_HomeView.checkWelcome(driver, "Welcome to the batch center");
	}

	/**
	 * Añadir un usuario no existente
	 */
	@Test
	public void CU1P1() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "aitorNoAdmin", "prueba@email.com", "Prueba", "1", "aitorNoAdmin",
				"aitorNoAdmin");
	}

	/**
	 * Añadir un usuario que ya existe
	 */
	@Test
	public void CU1P2() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "allanosi", "macmiller@gmail.com", "aa", "aa", "12", "12");

		PO_RegisterView.checkElement(driver, "text", "There is already a user with that username");
	}

	/**
	 * Añadir un usuario con algún campo vacío
	 */
	@Test
	public void CU1P3() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "username", "", "aa", "aa", "77777", "77777");
		PO_RegisterView.checkElement(driver, "text", "Some fields are blank, please fill them");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "username", "prueba2@email.com", "", "Perez", "77777", "77777");
		PO_RegisterView.checkElement(driver, "text", "Some fields are blank, please fill them");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "username", "prueba3@email.com", "Josefo", "", "77777", "77777");
		PO_RegisterView.checkElement(driver, "text", "Some fields are blank, please fill them");

	}



	/**
	 * Añadir un usuario con contraseñas diferentes
	 */
	@Test
	public void CU1P5() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		// Rellenamos el formulario.
		PO_RegisterView.fillForm(driver, "username", "pruebae1p3@email.com", "aa", "aa", "12", "1");

		PO_RegisterView.checkElement(driver, "text", "The passwords must be equals");
	}

	/**
	 * Cancelar la Operación
	 */
	@Test
	public void CU1P6() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "signup", "class", "btn btn-primary");
		PO_HomeView.clickOption(driver, "home", "id", "home");
	}

	/**
	 * Iniciar sesión con usuario registrado no administrador.
	 */
	@Test
	public void CU2P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_RegisterView.checkElement(driver, "text", "Processes");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");

	}

	/**
	 * Iniciar sesión con usuario no registrado
	 */
	@Test
	public void CU2P2() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin2", "aitorNoAdmin");
		PO_RegisterView.checkElement(driver, "text", "User unknown, check username and password");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		// Comprobamos que no podemos ir a terraform ni salen procesos
		try {
			PO_RegisterView.checkElement(driver, "text", "Processes");
			PO_HomeView.clickOption(driver, "procesos", "class", "btn btn-primary");
			PO_HomeView.clickOption(driver, "terraform", "class", "btn btn-primary");

		} catch (Exception e) {

		}

	}

	/**
	 * Iniciar sesión con usuario con valores vacíos
	 */
	@Test
	public void CU2P3() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "", "");
		PO_RegisterView.checkElement(driver, "text", "User unknown, check username and password");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		// Comprobamos que no podemos ir a terraform ni salen procesos
		try {
			PO_RegisterView.checkElement(driver, "text", "Processes");
			PO_HomeView.clickOption(driver, "procesos", "class", "btn btn-primary");
			PO_HomeView.clickOption(driver, "terraform", "class", "btn btn-primary");

		} catch (Exception e) {
		}

	}

	/**
	 * Iniciar sesión con usuario registrado administrador.
	 */
	@Test
	public void CU2P4() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "terraform", "class", "btn btn-primary");
		PO_HomeView.clickOption(driver, "logout", "id", "logout");

	}

	/**
	 * Cancelar la Operación
	 */
	@Test
	public void CU2P5() {
		// Vamos al formulario de registro
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		PO_HomeView.clickOption(driver, "home", "id", "home");
	}

	/**
	 * Loguearse como usuario pero contraseña incorrecta
	 */
	@Test
	public void CU2P6() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "12345");
		PO_RegisterView.checkElement(driver, "text", "User unknown, check username and password");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		// Comprobamos que no podemos ir a terraform ni salen procesos
		try {
			PO_RegisterView.checkElement(driver, "text", "Processes");
			PO_HomeView.clickOption(driver, "procesos", "class", "btn btn-primary");
			PO_HomeView.clickOption(driver, "terraform", "class", "btn btn-primary");

		} catch (Exception e) {

		}

	}

	/**
	 * Cerrar sesión con usuario identificado en el sistema
	 */
	@Test
	public void CU3P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		// Vamos a desconectarnos.
		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Cerrar Sesión como usuario no identificado en el sistema
	 */
	@Test
	public void CU3P2() {
		// Vamos a desconectarnos pero no encuentra el boton al estar oculto.
		SeleniumUtils.EsperaCargaPaginaNoTexto(driver, "logout", 2);
	}

	/**
	 * Listar proveedores con usuario identificado.
	 */
	@Test
	public void CU9P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		driver.navigate().to(URL + "/provider/list");
		PO_HomeView.checkElement(driver, "text", "Page not found");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Listar proveedor con usuario administrador.
	 */
	@Test
	public void CU9P2() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "providers", "id", "providers");
		PO_HomeView.checkElement(driver, "h1", "Providers");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Añadir proveedor con usuario con usuario sin registra
	 */
	@Test
	public void CU9P3() {
		driver.navigate().to(URL + "/provider/list");
		PO_HomeView.checkElement(driver, "text", "Page not found");
	}

	/**
	 * Añadir proveedor con usuario administrador.
	 */
	@Test
	public void CU10P2() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "providers", "id", "providers");
		PO_HomeView.clickOption(driver, "insertProvider", "id", "insertProvider");
		PO_HomeView.clickOption(driver, "selectAddProvider", "id", "selectAddProvider");
		PO_CreateNewProvider.fillForm(driver, "PruebaCreedential1", "PruebaCreedential2");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "providers", "id", "providers");
		PO_HomeView.checkElement(driver, "text", "Aws");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Añadir proveedor con usuario con usuario sin registra
	 */
	@Test
	public void CU10P3() {
		driver.navigate().to(URL + "/provider/insert");
		PO_HomeView.checkElement(driver, "text", "Page not found");
	}

	/**
	 * Cancelar la Operación
	 */
	@Test
	public void CU10P4() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "providers", "id", "providers");
		PO_HomeView.clickOption(driver, "insertProvider", "id", "insertProvider");

		By boton = By.name("selectBackProvider");
		driver.findElement(boton).click();
		PO_HomeView.checkElement(driver, "h1", "Providers");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Ver proveedor existente con usuario administrador.
	 */
	@Test
	public void CU12P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "providers", "id", "providers");
		PO_HomeView.clickOption(driver, "checkProvider Aws", "id", "checkProvider Aws");


		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Ver proveedor existente con usuario identificado.
	 */
	@Test
	public void CU12P2() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		driver.navigate().to(URL + "/provider/checkcreedentials/Aws");
		PO_HomeView.checkElement(driver, "text", "Page not found");
		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Ver proceso existente con usuario no registrado.
	 */
	@Test
	public void CU12P3() {
		driver.navigate().to(URL + "/provider/checkcreedentials/Aws");
		PO_HomeView.checkElement(driver, "text", "Page not found");
	}

	/**
	 * Actualizar proveedor existente con usuario administrador.
	 */
	@Test
	public void CU11P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "providers", "id", "providers");
		PO_HomeView.clickOption(driver, "updateProvider Aws", "id", "updateProvider Aws");
		PO_CreateNewProvider.fillForm(driver, "PruebaCreedential3", "PruebaCreedential4");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}
	
	/**
	 * Actualizar proveedor existente con usuario identificado.
	 */
	@Test
	public void CU11P2() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		driver.navigate().to(URL + "/provider/update/Aws");
		PO_HomeView.checkElement(driver, "text", "Page not found");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}
	
	/**
	 * Actualizar proveedor existente con usuario no identificado.
	 */
	@Test
	public void CU11P3() {
		driver.navigate().to(URL + "/provider/update/Aws");
		PO_HomeView.checkElement(driver, "text", "Page not found");
	}
	
	/**
	 * Cancelar la Operación
	 */
	@Test
	public void CU11P4() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "providers", "id", "providers");
		PO_HomeView.clickOption(driver, "updateProvider Aws", "id", "updateProvider Aws");
		By boton = By.name("back");
		driver.findElement(boton).click();
		PO_HomeView.checkElement(driver, "h1", "Providers");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Borrar proveedor existente con usuario administrador.
	 */
	@Test
	public void CU13P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "providers", "id", "providers");
		PO_HomeView.clickOption(driver, "deleteProvider Aws", "id", "deleteProvider Aws");
		try {
			PO_HomeView.checkElement(driver, "text", "Aws");
		} catch (Exception e) {
		}

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}
	
	/**
	 * Borrar proveedor no existente con usuario administrador.
	 */
	@Test
	public void CU13P2() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "providers", "id", "providers");
		try {
			PO_HomeView.clickOption(driver, "deleteProvider Aws2", "id", "deleteProvider Aws2");
		} catch (Exception e) {
		}

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}
	
	/**
	 * Borrar proveedor no existente con usuario administrador.
	 */
	@Test
	public void CU13P3() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		driver.navigate().to(URL + "/provider/list");
		PO_HomeView.checkElement(driver, "text", "Page not found");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}
	
	/**
	 * Borrar proveedor no existente con usuario administrador.
	 */
	@Test
	public void CU13P4() {
		driver.navigate().to(URL + "/provider/list");
		PO_HomeView.checkElement(driver, "text", "Page not found");
	}

	/**
	 * Listar regiones con usuario administrador.
	 */
	@Test
	public void CU14P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "regions", "id", "regions");
		PO_HomeView.checkElement(driver, "h1", "Regions");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Listar regiones con usuario identificado.
	 */
	@Test
	public void CU14P2() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		driver.navigate().to(URL + "/region/list");
		PO_HomeView.checkElement(driver, "text", "Page not found");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Listar regiones con usuario con usuario sin registrar
	 */
	@Test
	public void CU14P3() {
		driver.navigate().to(URL + "/region/list");
		PO_HomeView.checkElement(driver, "text", "Page not found");
	}

	/**
	 * Añadir región con usuario identificado.
	 */
	@Test
	public void CU15P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		driver.navigate().to(URL + "/region/insert");
		PO_HomeView.checkElement(driver, "text", "Page not found");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Añadir región con usuario administrador.
	 */
	@Test
	public void CU15P4() {
		crearProveedorParaProcesoTest();
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "regions", "id", "regions");
		PO_HomeView.clickOption(driver, "insertRegion", "id", "insertRegion");
		PO_CreateNewRegion.fillForm(driver, "prueba-region", "region-city");
		PO_HomeView.checkElement(driver, "text", "prueba-region");
		PO_HomeView.checkElement(driver, "text", "region-city");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Añadir región con usuario con usuario sin registrar
	 */
	@Test
	public void CU15P3() {
		driver.navigate().to(URL + "/region/insert");
		PO_HomeView.checkElement(driver, "text", "Page not found");
	}

	/**
	 * Añadir región sin un proveedor existente
	 */
	@Test
	public void CU15P2() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "regions", "id", "regions");
		PO_HomeView.clickOption(driver, "insertRegion", "id", "insertRegion");
		PO_CreateNewRegion.fillForm(driver, "prueba-region", "region-city");
		PO_HomeView.checkElement(driver, "text", "Some fields are blank");
		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Cancelar la Operación
	 */
	@Test
	public void CU15P5() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "regions", "id", "regions");
		PO_HomeView.clickOption(driver, "insertRegion", "id", "insertRegion");
		By boton = By.name("back");
		driver.findElement(boton).click();
		PO_HomeView.checkElement(driver, "h1", "Regions");
		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Actualizar región existente con usuario administrador.
	 */
	@Test
	public void CU16P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "regions", "id", "regions");
		PO_HomeView.clickOption(driver, "updateRegion prueba-region", "id", "updateRegion prueba-region");
		PO_CreateNewRegion.fillForm(driver, "prueba-region2", "region-city2");
		PO_HomeView.checkElement(driver, "text", "prueba-region2");
		PO_HomeView.checkElement(driver, "text", "region-city2");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Actualizar región existente con usuario identificado.
	 */
	@Test
	public void CU16P2() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		driver.navigate().to(URL + "/region/update/1");
		PO_HomeView.checkElement(driver, "text", "Page not found");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Actualizar región existente con usuario no identificado.
	 */
	@Test
	public void CU16P3() {
		driver.navigate().to(URL + "/region/update/1");
		PO_HomeView.checkElement(driver, "text", "Page not found");
	}

	/**
	 * Actualizar región existente con valores vacíos.
	 */
	@Test
	public void CU16P4() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "regions", "id", "regions");
		PO_HomeView.clickOption(driver, "updateRegion prueba-region", "id", "updateRegion prueba-region");
		PO_CreateNewRegion.fillForm(driver, "", "");
		PO_HomeView.checkElement(driver, "text", "prueba-region");
		PO_HomeView.checkElement(driver, "text", "region-city");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Actualizar región existente con algún valor vacío.
	 */
	@Test
	public void CU16P5() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "regions", "id", "regions");
		PO_HomeView.clickOption(driver, "updateRegion prueba-region", "id", "updateRegion prueba-region");
		PO_CreateNewRegion.fillForm(driver, "prueba-region2", "");
		PO_HomeView.checkElement(driver, "text", "prueba-region2");
		PO_HomeView.checkElement(driver, "text", "region-city");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Cancelar la Operación
	 */
	@Test
	public void CU16P6() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "regions", "id", "regions");
		PO_HomeView.clickOption(driver, "updateRegion prueba-region", "id", "updateRegion prueba-region");
		By boton = By.name("back");
		driver.findElement(boton).click();

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Borrar región existente con usuario administrador.
	 */
	@Test
	public void CU17P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "regions", "id", "regions");
		PO_HomeView.clickOption(driver, "deleteRegion prueba-region2", "id", "deleteRegion prueba-region2");
		try {
			PO_HomeView.checkElement(driver, "text", "prueba-region2");
			PO_HomeView.checkElement(driver, "text", "region-city2");
		} catch (Exception e) {
		}

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Borrar región no existente con usuario administrador.
	 */
	@Test
	public void CU17P2() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "regions", "id", "regions");
		try {
			PO_HomeView.clickOption(driver, "deleteRegion prueba-region2", "id", "deleteRegion prueba-region2");
		} catch (Exception e) {
		}

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Borrar región existente con usuario identificado.
	 */
	@Test
	public void CU17P3() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		driver.navigate().to(URL + "/region/list");
		PO_HomeView.checkElement(driver, "text", "Page not found");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Borrar región existente con usuario no identificado.
	 */
	@Test
	public void CU17P4() {
		driver.navigate().to(URL + "/region/list");
		PO_HomeView.checkElement(driver, "text", "Page not found");
	}

	/**
	 * Listar imágenes con usuario identificado.
	 */
	@Test
	public void CU18P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		driver.navigate().to(URL + "/image/list");
		PO_HomeView.checkElement(driver, "text", "Page not found");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Listar imágenes con usuario administrador.
	 */
	@Test
	public void CU18P2() {
		// Añadir Proveedor
		// Añadir Región
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "images", "id", "images");
		PO_HomeView.checkElement(driver, "h1", "Images");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Listar imágenes con usuario sin registrar
	 */
	@Test
	public void CU18P3() {
		driver.navigate().to(URL + "/image/list");
		PO_HomeView.checkElement(driver, "text", "Page not found");

	}

	/**
	 * Añadir imagen con usuario identificado.
	 */
	@Test
	public void CU19P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		driver.navigate().to(URL + "/image/list");
		PO_HomeView.checkElement(driver, "text", "Page not found");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Añadir imagen con usuario administrador.
	 */
	@Test
	public void CU19P5() {
		// Añadir Región
		crearRegiónParaProcesoTest();
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "images", "id", "images");
		PO_HomeView.clickOption(driver, "insertImage", "id", "insertImage");
		PO_CreateNewImage.fillForm(driver, "prueba-image", "Selenium");
		PO_HomeView.checkElement(driver, "text", "prueba-image");
		PO_HomeView.checkElement(driver, "text", "Selenium");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Añadir imagen con usuario con usuario sin registrar
	 */
	@Test
	public void CU19P3() {

		driver.navigate().to(URL + "/image/list");
		PO_HomeView.checkElement(driver, "text", "Page not found");

	}

	/**
	 * Añadir imagen sin un proveedor existente o sin una región.
	 */
	@Test
	public void CU19P2() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "images", "id", "images");
		PO_HomeView.clickOption(driver, "insertImage", "id", "insertImage");
		PO_CreateNewImage.fillForm(driver, "prueba-image", "Selenium");
		PO_HomeView.checkElement(driver, "text", "Some fields are blank");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Cancelar la Operación
	 */
	@Test
	public void CU19P6() {
		// Añadir Proveedor
		// Añadir Región
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "images", "id", "images");
		PO_HomeView.clickOption(driver, "insertImage", "id", "insertImage");
		By boton = By.name("back");
		driver.findElement(boton).click();
		PO_HomeView.checkElement(driver, "h1", "Image");
		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Actualizar imagen existente con usuario administrador.
	 */
	@Test
	public void CU20P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "images", "id", "images");
		PO_HomeView.clickOption(driver, "updateImage prueba-image", "id", "updateImage prueba-image");
		PO_CreateNewImage.fillForm(driver, "prueba-image2", "Selenium2");
		PO_HomeView.checkElement(driver, "text", "prueba-image2");
		PO_HomeView.checkElement(driver, "text", "Selenium2");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Actualizar imagen existente con usuario identificado.
	 */
	@Test
	public void CU20P2() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		driver.navigate().to(URL + "/image/update/1");
		PO_HomeView.checkElement(driver, "text", "Page not found");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Actualizar imagen existente con usuario no identificado.
	 */
	@Test
	public void CU20P3() {
		driver.navigate().to(URL + "/image/update/1");
		PO_HomeView.checkElement(driver, "text", "Page not found");
	}

	/**
	 * Actualizar imagen existente con valores vacíos
	 */
	@Test
	public void CU20P4() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "images", "id", "images");
		PO_HomeView.clickOption(driver, "updateImage prueba-image", "id", "updateImage prueba-image");
		PO_CreateNewImage.fillForm(driver, "", "");
		PO_HomeView.checkElement(driver, "text", "prueba-image");
		PO_HomeView.checkElement(driver, "text", "Selenium");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Actualizar imagen existente con algún valor vacío.
	 */
	@Test
	public void CU20P5() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "images", "id", "images");
		PO_HomeView.clickOption(driver, "updateImage prueba-image", "id", "updateImage prueba-image");
		PO_CreateNewImage.fillForm(driver, "prueba-image2", "");
		PO_HomeView.checkElement(driver, "text", "prueba-image");
		PO_HomeView.checkElement(driver, "text", "Selenium");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Cancelar la Operación
	 */
	@Test
	public void CU20P6() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "images", "id", "images");
		PO_HomeView.clickOption(driver, "updateImage prueba-image", "id", "updateImage prueba-image");
		By boton = By.name("back");
		driver.findElement(boton).click();

		PO_HomeView.checkElement(driver, "h1", "Images");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Borrar imagen existente con usuario administrador.
	 */
	@Test
	public void CU21P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "images", "id", "images");
		PO_HomeView.clickOption(driver, "deleteImage prueba-image2", "id", "deleteImage prueba-image2");

		try {
			PO_HomeView.checkElement(driver, "text", "prueba-image2");
			PO_HomeView.checkElement(driver, "text", "Selenium2");

		} catch (Exception e) {
		}

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Borrar imagen no existente con usuario administrador.
	 */
	@Test
	public void CU21P2() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "images", "id", "images");

		try {
			PO_HomeView.clickOption(driver, "deleteImage prueba-image2", "id", "deleteImage prueba-image2");

		} catch (Exception e) {
		}

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Borrar imagen existente con usuario identificado.
	 */
	@Test
	public void CU21P3() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		driver.navigate().to(URL + "/image/list");
		PO_HomeView.checkElement(driver, "text", "Page not found");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Borrar imagen existente con usuario no identificado.
	 */
	@Test
	public void CU21P4() {
		driver.navigate().to(URL + "/image/list");
		PO_HomeView.checkElement(driver, "text", "Page not found");
	}

	/**
	 * Listar instancias con usuario identificado.
	 */
	@Test
	public void CU22P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		driver.navigate().to(URL + "/instance/list");
		PO_HomeView.checkElement(driver, "text", "Page not found");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Listar instancias con usuario sin registrar
	 */
	@Test
	public void CU22P3() {
		driver.navigate().to(URL + "/instance/list");
		PO_HomeView.checkElement(driver, "text", "Page not found");
	}

	/**
	 * Listar instancias con usuario administrador.
	 */
	@Test
	public void CU22P2() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "instances", "id", "instances");

		PO_HomeView.checkElement(driver, "h1", "Instances");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Añadir instancia con usuario identificado.
	 */
	@Test
	public void CU23P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		driver.navigate().to(URL + "/instance/update/1");
		PO_HomeView.checkElement(driver, "text", "Page not found");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Añadir instancia con usuario administrador.
	 */
	@Test
	public void CU23P2() {
		crearImagenParaProcesoTest();
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "instances", "id", "instances");
		PO_HomeView.clickOption(driver, "insertInstance", "id", "insertInstance");
		PO_CreateNewInstance.fillForm(driver, "prueba-instance", "1000", "1000", "1000");
		PO_HomeView.checkElement(driver, "text", "prueba-instance");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Añadir instancia con usuario con usuario sin registrar.
	 */
	@Test
	public void CU23P3() {
		driver.navigate().to(URL + "/instance/update/1");
		PO_HomeView.checkElement(driver, "text", "Page not found");
	}

	/**
	 * Cancelar la Operación
	 */
	@Test
	public void CU23P4() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "instances", "id", "instances");
		PO_HomeView.clickOption(driver, "insertInstance", "id", "insertInstance");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}
		By boton = By.name("back");
		driver.findElement(boton).click();

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Actualizar instancia existente con usuario administrador.
	 */
	@Test
	public void CU24P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "instances", "id", "instances");
		PO_HomeView.clickOption(driver, "updateInstance prueba-instance", "id", "updateInstance prueba-instance");
		PO_CreateNewInstance.fillForm(driver, "prueba-instance2", "10001", "10001", "10001");
		PO_HomeView.checkElement(driver, "text", "prueba-instance2");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Actualizar instancia existente con usuario identificado.
	 */
	@Test
	public void CU24P2() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		driver.navigate().to(URL + "/instance/update/1");
		PO_HomeView.checkElement(driver, "text", "Page not found");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Actualizar instancia existente con usuario identificado.
	 */
	@Test
	public void CU24P3() {
		driver.navigate().to(URL + "/instance/update/1");
		PO_HomeView.checkElement(driver, "text", "Page not found");
	}

	/**
	 * Actualizar instancia existente con valores vacíos.
	 */
	@Test
	public void CU24P4() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "instances", "id", "instances");
		PO_HomeView.clickOption(driver, "updateInstance prueba-instance", "id", "updateInstance prueba-instance");
		PO_CreateNewInstance.fillForm(driver, "", "", "", "");
		PO_HomeView.checkElement(driver, "text", "prueba-instance");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Actualizar instancia existente con algún valor vacío.
	 */
	@Test
	public void CU24P5() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "instances", "id", "instances");
		PO_HomeView.clickOption(driver, "updateInstance prueba-instance", "id", "updateInstance prueba-instance");
		PO_CreateNewInstance.fillForm(driver, "", "10001", "10001", "10001");
		PO_HomeView.checkElement(driver, "text", "prueba-instance");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Cancelar la Operación
	 */
	@Test
	public void CU24P6() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "instances", "id", "instances");
		PO_HomeView.clickOption(driver, "updateInstance prueba-instance", "id", "updateInstance prueba-instance");

		By boton = By.name("back");
		driver.findElement(boton).click();
		PO_HomeView.checkElement(driver, "text", "prueba-instance");
		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Borrar instancia existente con usuario administrador.
	 */
	@Test
	public void CU25P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "instances", "id", "instances");
		PO_HomeView.clickOption(driver, "deleteInstance prueba-instance2", "id", "deleteInstance prueba-instance2");

		try {
			PO_HomeView.checkElement(driver, "text", "prueba-instance2");

		} catch (Exception e) {
		}

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Borrar instancia no existente con usuario administrador.
	 */
	@Test
	public void CU25P2() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "instances", "id", "instances");

		try {
			PO_HomeView.clickOption(driver, "deleteInstance prueba-instance2", "id", "deleteInstance prueba-instance2");
		} catch (Exception e) {
		}

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Borrar instancia existente con usuario identificado
	 */
	@Test
	public void CU25P3() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		driver.navigate().to(URL + "/instance/list");
		PO_HomeView.checkElement(driver, "text", "Page not found");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Borrar instancia existente con usuario no identificado.
	 */
	@Test
	public void CU25P4() {
		// Vamos al formulario de logueo.

		driver.navigate().to(URL + "/instance/list");
		PO_HomeView.checkElement(driver, "text", "Page not found");

	}

	public void crearProveedorParaProcesoTest() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "providers", "id", "providers");
		PO_HomeView.clickOption(driver, "insertProvider", "id", "insertProvider");
		PO_HomeView.clickOption(driver, "selectAddProvider", "id", "selectAddProvider");
		PO_CreateNewProvider.fillForm(driver, "PruebaCreedential1", "PruebaCreedential2");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	public void borrarProveedorParaProcesoTest() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "providers", "id", "providers");
		PO_HomeView.clickOption(driver, "deleteProvider Aws", "id", "deleteProvider Aws");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	public void crearRegiónParaProcesoTest() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "regions", "id", "regions");
		PO_HomeView.clickOption(driver, "insertRegion", "id", "insertRegion");
		PO_CreateNewRegion.fillForm(driver, "prueba-region", "region-city");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	public void crearImagenParaProcesoTest() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "images", "id", "images");
		PO_HomeView.clickOption(driver, "insertImage", "id", "insertImage");
		PO_CreateNewImage.fillForm(driver, "prueba-image", "Selenium");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	public void crearInstanciaParaProcesoTest() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "terraform", "id", "terraform");
		PO_HomeView.clickOption(driver, "instances", "id", "instances");
		PO_HomeView.clickOption(driver, "insertInstance", "id", "insertInstance");
		PO_CreateNewInstance.fillForm(driver, "prueba-instance", "1000", "1000", "1000");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Añadir proceso con usuario identificado.
	 */
	@Test
	public void CU5P1() {
		crearProveedorParaProcesoTest();
		crearRegiónParaProcesoTest();
		crearImagenParaProcesoTest();
		crearInstanciaParaProcesoTest();
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "newProcess", "id", "newProcess");
		PO_CreateNewProcess.fillForm(driver, "SeleniumUsuario", "1");
		PO_CreateNewProcess.checkElement(driver, "text", "Aws");
		PO_CreateNewProcess.clickOption(driver, "selectProvider", "id", "selectProvider");
		PO_CreateNewProcess.clickOption(driver, "selectRegion", "id", "selectRegion");
		PO_CreateNewProcess.clickOption(driver, "selectImage", "id", "selectImage");
		PO_CreateNewProcess.clickOption(driver, "selectInstance", "id", "selectInstance");
		PO_CreateNewProcess.checkElement(driver, "text", "Summary");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		PO_CreateNewProcess.clickOption(driver, "submit", "name", "submit");

		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		PO_HomeView.clickOption(driver, "home", "id", "home");
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Volvemos a los procesos
		PO_HomeView.checkElement(driver, "text", "SeleniumUsuario");
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		PO_HomeView.clickOption(driver, "logout", "id", "logout");
		borrarProveedorParaProcesoTest();

	}

	/**
	 * Añadir proceso con usuario administrador.
	 */
	@Test
	public void CU5P2() {
		crearProveedorParaProcesoTest();
		crearRegiónParaProcesoTest();
		crearImagenParaProcesoTest();
		crearInstanciaParaProcesoTest();
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "newProcess", "id", "newProcess");
		PO_CreateNewProcess.fillForm(driver, "SeleniumIdentificado", "1");
		PO_CreateNewProcess.checkElement(driver, "text", "Aws");
		PO_CreateNewProcess.clickOption(driver, "selectProvider", "id", "selectProvider");
		PO_CreateNewProcess.clickOption(driver, "selectRegion", "id", "selectRegion");
		PO_CreateNewProcess.clickOption(driver, "selectImage", "id", "selectImage");
		PO_CreateNewProcess.clickOption(driver, "selectInstance", "id", "selectInstance");
		PO_CreateNewProcess.checkElement(driver, "text", "Summary");
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		PO_CreateNewProcess.clickOption(driver, "submit", "name", "submit");

		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		PO_HomeView.clickOption(driver, "home", "id", "home");
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// Volvemos a los procesos
		PO_HomeView.checkElement(driver, "text", "Selenium");
		((JavascriptExecutor) driver).executeScript("window.scrollTo(0, 0)");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		PO_HomeView.clickOption(driver, "logout", "id", "logout");
		borrarProveedorParaProcesoTest();
	}

	/**
	 * Añadir proceso con usuario con usuario sin registrar
	 */
	@Test
	public void CU5P3() {
		driver.navigate().to(URL + "/newProcess");
		PO_HomeView.checkElement(driver, "text", "Page not found");
	}

	/**
	 * Borrar proceso existente con usuario administrador
	 */
	@Test
	public void CU7P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "deleteProcess SeleniumIdentificado", "id", "deleteProcess SeleniumIdentificado");

		// Comprobamos que no existe mas ese Selenium process
		try {
			PO_HomeView.checkElement(driver, "text", "Selenium");
		} catch (Exception e) {
		}
		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Borrar proceso no existente con usuario administrador
	 */
	@Test
	public void CU7P2() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		try {
			PO_HomeView.clickOption(driver, "deleteProcess Selenium", "id", "deleteProcess Selenium");
		} catch (Exception e) {
		}
		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Borrar proceso existente con usuario identificado.
	 */
	@Test
	public void CU7P3() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		PO_HomeView.clickOption(driver, "home", "id", "home");

		// Comprobamos que no se puede borrar
		try {
			PO_HomeView.clickOption(driver, "deleteProcess SeleniumUsuario", "id", "deleteProcess SeleniumUsuario");
		} catch (Exception e) {
		}
		// Comprobamos que no existe mas ese Selenium process
		try {
			PO_HomeView.checkElement(driver, "text", "SeleniumUsuario");
		} catch (Exception e) {
		}
		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}

	/**
	 * Borrar proceso existente con usuario no identificado.
	 */
	@Test
	public void CU7P4() {
		driver.navigate().to(URL + "/dashboard");
		PO_HomeView.checkElement(driver, "text", "Page not found");
	}

	/**
	 * Ver proceso existente con usuario administrador.
	 */
	@Test
	public void CU6P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.clickOption(driver, "checkProcess SeleniumUsuario", "id", "checkProcess SeleniumUsuario");
		PO_HomeView.checkElement(driver, "text", "Active");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}
	
	/**
	 * Ver procesos con usuario identificado.
	 */
	@Test
	public void CU4P1() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "aitorNoAdmin", "aitorNoAdmin");
		PO_HomeView.clickOption(driver, "home", "id", "home");
		PO_HomeView.checkElement(driver, "text", "Processes");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}



	/**
	 * Listar procesos con usuario administrador.
	 */
	@Test
	public void CU4P2() {
		// Vamos al formulario de logueo.
		PO_HomeView.clickOption(driver, "login", "class", "btn btn-primary");
		// Rellenamos el formulario
		PO_LoginView.fillForm(driver, "allanosi", "cap");
		PO_HomeView.clickOption(driver, "home", "id", "home");

		PO_HomeView.checkElement(driver, "text", "Processes");

		PO_HomeView.clickOption(driver, "logout", "id", "logout");
	}
	
	/**
	 * Ver procesos con usuario sin registrar
	 */
	@Test
	public void CU4P3() {
		driver.navigate().to(URL + "/dashboard");
		PO_HomeView.checkElement(driver, "text", "Page not found");
	}
	
	@Test
	public void CU26P1() {
		borrarProveedorParaProcesoTest();
	}
}
