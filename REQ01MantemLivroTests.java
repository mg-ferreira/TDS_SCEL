package testeDeSistema;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import java.util.*;

public class REQ01MantemLivroTests {
	private WebDriver driver;
	@SuppressWarnings("unused")
	private Map<String, Object> vars;
	JavascriptExecutor js;

	@BeforeEach
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "browserDriver/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("https://ts-scel-web.herokuapp.com/login");
		driver.manage().window().maximize();
		js = (JavascriptExecutor) driver;
		vars = new HashMap<String, Object>();
	}

	@AfterEach
	public void tearDown() {
		driver.quit();
	}

	@Test
	public void ct01cadastrarlivrocomsucesso() {
		// dado que nao existem livros cadastrados
		// quando o usuario cadastrar um livro
		driver.findElement(By.name("username")).click();
		driver.findElement(By.name("username")).sendKeys("jose");
		driver.findElement(By.name("password")).sendKeys("123");
		driver.findElement(By.cssSelector("button")).click();
		driver.findElement(By.linkText("Livros")).click();
		espera();
		driver.findElement(By.id("isbn")).click();
		driver.findElement(By.id("isbn")).sendKeys("1111");
		driver.findElement(By.id("autor")).click();
		driver.findElement(By.id("autor")).sendKeys("Pressman");
		driver.findElement(By.id("titulo")).click();
		driver.findElement(By.id("titulo")).sendKeys("Engenharia de Software");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		// entao apresenta as seguintes informacoes
		espera();
		assertEquals("1111", driver.findElement(By.cssSelector("td:nth-child(2)")).getText());
		assertEquals("Lista de livros", driver.findElement(By.id("paginaConsulta")).getText());
		assertEquals("https://ts-scel-web.herokuapp.com/sig/livros", driver.getCurrentUrl());
		assertTrue(driver.getPageSource().contains("1111"));
		// ******************************************************************
		// teardown - exclusao do registro
		// ******************************************************************
		driver.findElement(By.linkText("Excluir")).click();
	}

	@Test
	public void ct02cadastrarlivrojacadastrado() {
		// dado que o livro já está cadastrado
		driver.findElement(By.name("username")).click();
		driver.findElement(By.name("username")).sendKeys("jose");
		driver.findElement(By.name("password")).sendKeys("123");
		driver.findElement(By.cssSelector("button")).click();
		driver.findElement(By.linkText("Livros")).click();
		espera();
		driver.findElement(By.id("isbn")).click();
		driver.findElement(By.id("isbn")).sendKeys("1111");
		driver.findElement(By.id("autor")).click();
		driver.findElement(By.id("autor")).sendKeys("Pressman");
		driver.findElement(By.id("titulo")).click();
		driver.findElement(By.id("titulo")).sendKeys("Engenharia de Software");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		// asserts com o objetivo de depuracao
		espera();
		assertEquals("1111", driver.findElement(By.cssSelector("td:nth-child(2)")).getText());
		assertEquals("Lista de livros", driver.findElement(By.id("paginaConsulta")).getText());
		assertEquals("https://ts-scel-web.herokuapp.com/sig/livros", driver.getCurrentUrl());
		// quando o usuario tentar cadastrar um livro já cadastrado
		driver.findElement(By.linkText("Voltar")).click();
		driver.findElement(By.linkText("Livros")).click();
		espera();
		driver.findElement(By.id("isbn")).click();
		driver.findElement(By.id("isbn")).sendKeys("1111");
		driver.findElement(By.id("autor")).click();
		driver.findElement(By.id("autor")).sendKeys("Pressman");
		driver.findElement(By.id("titulo")).click();
		driver.findElement(By.id("titulo")).sendKeys("Engenharia de Software");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		// entao retorna dados invalidos
		assertEquals("Livro ja cadastrado", driver.findElement(By.cssSelector(".text-danger")).getText());
		// ******************************************************************
		// teardown - exclusao do registro
		// ******************************************************************
		driver.findElement(By.cssSelector(".btn:nth-child(2)")).click();
		driver.findElement(By.linkText("Excluir")).click();
	}

	@Test
	public void ct03cadastrarlivrocomisbnnulo() {
		// dado que nao existem livros cadastrados
		// quando o usuario cadastrar um livro com isbn nulo
		driver.findElement(By.name("username")).click();
		driver.findElement(By.name("username")).sendKeys("jose");
		driver.findElement(By.name("password")).sendKeys("123");
		driver.findElement(By.cssSelector("button")).click();
		driver.findElement(By.linkText("Livros")).click();
		espera();
		driver.findElement(By.id("isbn")).click();
		driver.findElement(By.id("isbn")).sendKeys("");
		driver.findElement(By.id("autor")).click();
		driver.findElement(By.id("autor")).sendKeys("Pressman");
		driver.findElement(By.id("titulo")).click();
		driver.findElement(By.id("titulo")).sendKeys("Engenharia de Software");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		// entao retorna dados invalidos
		assertEquals("ISBN deve ter 4 caracteres", driver.findElement(By.cssSelector(".text-danger")).getText());
	}

	@Test
	public void ct04cadastrarlivrocomtitulonulo() {
		// dado que nao existem livros cadastrados
		// quando o usuario cadastrar um livro com titulo nulo
		driver.findElement(By.name("username")).click();
		driver.findElement(By.name("username")).sendKeys("jose");
		driver.findElement(By.name("password")).sendKeys("123");
		driver.findElement(By.cssSelector("button")).click();
		driver.findElement(By.linkText("Livros")).click();
		espera();
		driver.findElement(By.id("isbn")).click();
		driver.findElement(By.id("isbn")).sendKeys("1111");
		driver.findElement(By.id("autor")).click();
		driver.findElement(By.id("autor")).sendKeys("Pressman");
		driver.findElement(By.id("titulo")).click();
		driver.findElement(By.id("titulo")).sendKeys("");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		// entao retorna dados invalidos
		assertEquals("Titulo deve ter entre 1 e 50 caracteres",
				driver.findElement(By.cssSelector(".text-danger")).getText());
	}

	@Test
	public void ct05cadastrarlivrocomtituloinvalido() {
		// dado que nao existem livros cadastrados
		// quando o usuario cadastrar um livro com titulo maior que 50 caracteres
		driver.findElement(By.name("username")).click();
		driver.findElement(By.name("username")).sendKeys("jose");
		driver.findElement(By.name("password")).sendKeys("123");
		driver.findElement(By.cssSelector("button")).click();
		driver.findElement(By.linkText("Livros")).click();
		espera();
		driver.findElement(By.id("isbn")).click();
		driver.findElement(By.id("isbn")).sendKeys("1111");
		driver.findElement(By.id("autor")).click();
		driver.findElement(By.id("autor")).sendKeys("Pressman");
		driver.findElement(By.id("titulo")).click();
		driver.findElement(By.id("titulo")).sendKeys("Engenharia de Software - Uma abordagem profissional");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		// entao retorna dados invalidos
		assertEquals("Titulo deve ter entre 1 e 50 caracteres",
				driver.findElement(By.cssSelector(".text-danger")).getText());
	}

	@Test
	public void ct06cadastrarlivrocomautornulo() {
		// dado que nao existem livros cadastrados
		// quando o usuario cadastrar um livro com autor nulo
		driver.findElement(By.name("username")).click();
		driver.findElement(By.name("username")).sendKeys("jose");
		driver.findElement(By.name("password")).sendKeys("123");
		driver.findElement(By.cssSelector("button")).click();
		driver.findElement(By.linkText("Livros")).click();
		espera();
		driver.findElement(By.id("isbn")).click();
		driver.findElement(By.id("isbn")).sendKeys("1111");
		driver.findElement(By.id("autor")).click();
		driver.findElement(By.id("autor")).sendKeys("");
		driver.findElement(By.id("titulo")).click();
		driver.findElement(By.id("titulo")).sendKeys("Engenharia de Software");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		// entao retorna dados invalidos
		assertEquals("Autor deve ter entre 1 e 50 caracteres",
				driver.findElement(By.cssSelector(".text-danger")).getText());
	}

	@Test
	public void ct07cadastrarlivrocomautorinvalido() {
		// dado que nao existem livros cadastrados
		// quando o usuario cadastrar um livro com autor maior que 50 caracteres
		driver.findElement(By.name("username")).click();
		driver.findElement(By.name("username")).sendKeys("jose");
		driver.findElement(By.name("password")).sendKeys("123");
		driver.findElement(By.cssSelector("button")).click();
		driver.findElement(By.linkText("Livros")).click();
		espera();
		driver.findElement(By.id("isbn")).click();
		driver.findElement(By.id("isbn")).sendKeys("1111");
		driver.findElement(By.id("autor")).click();
		driver.findElement(By.id("autor"))
				.sendKeys("Pressman, Roger S.; Maxim, Bruce R.; Arakaki, Julio; Arakaki, Renato");
		driver.findElement(By.id("titulo")).click();
		driver.findElement(By.id("titulo")).sendKeys("Engenharia de Software");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		// entao retorna dados invalidos
		assertEquals("Autor deve ter entre 1 e 50 caracteres",
				driver.findElement(By.cssSelector(".text-danger")).getText());
	}

	@Test
	public void ct08consultarlivrocomsucesso() {
		// dado que o livro já está cadastrado
		driver.findElement(By.name("username")).click();
		driver.findElement(By.name("username")).sendKeys("jose");
		driver.findElement(By.name("password")).sendKeys("123");
		driver.findElement(By.cssSelector("button")).click();
		driver.findElement(By.linkText("Livros")).click();
		espera();
		driver.findElement(By.id("isbn")).click();
		driver.findElement(By.id("isbn")).sendKeys("1111");
		driver.findElement(By.id("autor")).click();
		driver.findElement(By.id("autor")).sendKeys("Pressman");
		driver.findElement(By.id("titulo")).click();
		driver.findElement(By.id("titulo")).sendKeys("Engenharia de Software");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		// asserts com o objetivo de depuracao
		espera();
		assertEquals("1111", driver.findElement(By.cssSelector("td:nth-child(2)")).getText());
		assertEquals("Lista de livros", driver.findElement(By.id("paginaConsulta")).getText());
		assertEquals("https://ts-scel-web.herokuapp.com/sig/livros", driver.getCurrentUrl());
		// quando o usuario tentar pesquisar o livro
		driver.findElement(By.linkText("Voltar")).click();
		driver.findElement(By.linkText("Livros")).click();
		espera();
		driver.findElement(By.cssSelector(".btn:nth-child(2)")).click();
		// entao retorna as seguintes informacoes
		assertEquals("1111", driver.findElement(By.cssSelector("td:nth-child(2)")).getText());
		assertEquals("Lista de livros", driver.findElement(By.id("paginaConsulta")).getText());
		assertEquals("https://ts-scel-web.herokuapp.com/sig/livros", driver.getCurrentUrl());
		assertTrue(driver.getPageSource().contains("1111"));
		// ******************************************************************
		// teardown - exclusao do registro
		// ******************************************************************
		driver.findElement(By.linkText("Excluir")).click();
	}

	@Test
	public void ct09editarlivrocomsucesso() {
		// dado que o livro já está cadastrado
		driver.findElement(By.name("username")).click();
		driver.findElement(By.name("username")).sendKeys("jose");
		driver.findElement(By.name("password")).sendKeys("123");
		driver.findElement(By.cssSelector("button")).click();
		driver.findElement(By.linkText("Livros")).click();
		espera();
		driver.findElement(By.id("isbn")).click();
		driver.findElement(By.id("isbn")).sendKeys("1111");
		driver.findElement(By.id("autor")).click();
		driver.findElement(By.id("autor")).sendKeys("Pressman");
		driver.findElement(By.id("titulo")).click();
		driver.findElement(By.id("titulo")).sendKeys("Engenharia de Software");
		driver.findElement(By.cssSelector(".btn")).click();
		// asserts com o objetivo de depuracao
		assertEquals("1111", driver.findElement(By.cssSelector("td:nth-child(2)")).getText());
		assertEquals("Lista de livros", driver.findElement(By.id("paginaConsulta")).getText());
		assertEquals("https://ts-scel-web.herokuapp.com/sig/livros", driver.getCurrentUrl());
		// quando o usuario tentar editar o cadastro do livro
		driver.findElement(By.linkText("Editar")).click();
		espera();
		driver.findElement(By.id("isbn")).click();
		driver.findElement(By.id("isbn")).clear();
		driver.findElement(By.id("isbn")).sendKeys("1111");
		driver.findElement(By.id("autor")).click();
		driver.findElement(By.id("autor")).clear();
		driver.findElement(By.id("autor")).sendKeys("Pressman, Roger S.");
		driver.findElement(By.id("titulo")).click();
		driver.findElement(By.id("titulo")).clear();
		driver.findElement(By.id("titulo")).sendKeys("Engenharia");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		// entao retorna as seguintes informacoes
		assertEquals("1111", driver.findElement(By.cssSelector("td:nth-child(2)")).getText());
		assertEquals("Engenharia", driver.findElement(By.cssSelector("td:nth-child(3)")).getText());
		assertEquals("Pressman, Roger S.", driver.findElement(By.cssSelector("td:nth-child(4)")).getText());
		assertEquals("Lista de livros", driver.findElement(By.id("paginaConsulta")).getText());
		assertTrue(driver.getPageSource().contains("1111"));
		// ******************************************************************
		// teardown - exclusao do registro
		// ******************************************************************
		driver.findElement(By.linkText("Excluir")).click();
	}

	@Test
	public void ct10editarlivrocomdadosinvalidos() {
		// dado que o livro já está cadastrado
		driver.findElement(By.name("username")).click();
		driver.findElement(By.name("username")).sendKeys("jose");
		driver.findElement(By.name("password")).sendKeys("123");
		driver.findElement(By.cssSelector("button")).click();
		driver.findElement(By.linkText("Livros")).click();
		espera();
		driver.findElement(By.id("isbn")).click();
		driver.findElement(By.id("isbn")).sendKeys("1111");
		driver.findElement(By.id("autor")).click();
		driver.findElement(By.id("autor")).sendKeys("Pressman");
		driver.findElement(By.id("titulo")).click();
		driver.findElement(By.id("titulo")).sendKeys("Engenharia de Software");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		// asserts com o objetivo de depuracao
		assertEquals("1111", driver.findElement(By.cssSelector("td:nth-child(2)")).getText());
		assertEquals("Lista de livros", driver.findElement(By.id("paginaConsulta")).getText());
		assertEquals("https://ts-scel-web.herokuapp.com/sig/livros", driver.getCurrentUrl());
		// quando o usuario tentar editar o cadastro do livro com dados invalidos
		driver.findElement(By.linkText("Editar")).click();
		espera();
		driver.findElement(By.id("isbn")).click();
		driver.findElement(By.id("isbn")).clear();
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		// asserts com o objetivo de depuracao
		assertEquals("ISBN deve ter 4 caracteres", driver.findElement(By.cssSelector(".text-danger")).getText());
		driver.findElement(By.id("isbn")).click();
		driver.findElement(By.id("isbn")).sendKeys("1111");
		driver.findElement(By.id("autor")).click();
		driver.findElement(By.id("autor")).clear();
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		// asserts com o objetivo de depuracao
		assertEquals("Autor deve ter entre 1 e 50 caracteres", driver.findElement(By.cssSelector(".text-danger")).getText());
		driver.findElement(By.id("autor")).click();
		driver.findElement(By.id("autor")).sendKeys("Pressman");
		driver.findElement(By.id("titulo")).click();
		driver.findElement(By.id("titulo")).clear();
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		// entao retorna o erro
		assertEquals("Titulo deve ter entre 1 e 50 caracteres", driver.findElement(By.cssSelector(".text-danger")).getText());
		// ******************************************************************
		// teardown - exclusao do registro
		// ******************************************************************
		driver.findElement(By.id("titulo")).click();
		driver.findElement(By.id("titulo")).sendKeys("Engenharia de Software");
		driver.findElement(By.cssSelector(".btn:nth-child(1)")).click();
		driver.findElement(By.linkText("Excluir")).click();
	}

	public void espera() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
