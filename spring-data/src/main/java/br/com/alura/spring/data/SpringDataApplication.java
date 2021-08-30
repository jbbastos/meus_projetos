package br.com.alura.spring.data;

import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.service.CrudCargoService;
import br.com.alura.spring.data.service.CrudFuncionarioService;
import br.com.alura.spring.data.service.CrudUnidadeTrabalhoService;
import br.com.alura.spring.data.service.RelatorioFuncionarioDinamico;
import br.com.alura.spring.data.service.RelatoriosService;

@SpringBootApplication
public class SpringDataApplication implements CommandLineRunner{
	
	// Necessária uma instância da classe CrudCargoService
	// Para isso é necessário a injeção de dependência dele
	private final CrudCargoService cargoService; // cria uma instância nula
	private final CrudUnidadeTrabalhoService unidadeService;
	private final CrudFuncionarioService funcionarioService;
	private final RelatoriosService relatorioService;
	private final RelatorioFuncionarioDinamico relatorioFuncionarioDinamico;
	
	private Boolean system = true;

	public SpringDataApplication(CrudCargoService cargoService, 
			CrudUnidadeTrabalhoService unidadeService, CrudFuncionarioService funcionarioService, 
				RelatoriosService relatorioService, RelatorioFuncionarioDinamico relatorioFuncionarioDinamico) {
		// cria a SpringDataApplication com uma instancia de CrudCargoService 
		this.cargoService = cargoService;
		this.unidadeService = unidadeService;
		this.funcionarioService = funcionarioService;
		this.relatorioService = relatorioService;
		this.relatorioFuncionarioDinamico = relatorioFuncionarioDinamico;
		
	}

	public static void main(String[] args) {
		SpringApplication.run(SpringDataApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	
		Scanner scanner = new Scanner(System.in);
		
		while (system) {
			System.out.println("Qual acao voce quer executar: ");
			System.out.println("0 - Sair");
			System.out.println("1 - Cargo");
			System.out.println("2 - Unidade de Trabalho");
			System.out.println("3 - Funcionario");
			System.out.println("4 - Relatorios");
			System.out.println("5 - Relatorio Dinamico");
			
			int action = scanner.nextInt();
			switch (action) {
			case 1:
				cargoService.inicial(scanner);
				break;
			case 2:
				unidadeService.inicial(scanner);
				break;
			case 3:
				funcionarioService.inicial(scanner);
				break;
			case 4:
				relatorioService.inicial(scanner);
				break;
			case 5:
				relatorioFuncionarioDinamico.inicial(scanner);
				break;
			default:
				system = false;
				break;
			}
		}
		
	}

}
