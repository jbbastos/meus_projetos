package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatoriosService {
	
	private Boolean system = true;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private final FuncionarioRepository funcionarioRepository;
	
	public RelatoriosService(FuncionarioRepository funcRepo) {
		this.funcionarioRepository = funcRepo;
	}
	
	public void inicial(Scanner scanner)
	{
		while (system) {
			System.out.println("Qual relatorio voce quer executar: ");
			System.out.println("0 - Sair");
			System.out.println("1 - Busca funcionario por nome");
			System.out.println("2 - Busca funcionario por Nome, Salario e Data");
			System.out.println("3 - Busca funcionario por Data");
			System.out.println("4 - Busca Funcionario por Salario");
			
			int action = scanner.nextInt();
			switch (action) {
			case 1:
				buscaFuncionarioNome(scanner);
				break;
			case 2:
				buscaFuncionarioNomeSalarioMaiorData(scanner);
				break;
			case 3:
				buscaFuncionarioData(scanner);
				break;
			case 4:
				buscaFuncionarioSalario();
				break;
			default:
				system = false;
				break;
			}
			
		}
	}
	
	// busca utilizando findBy
	private void buscaFuncionarioNome(Scanner scanner) {
		System.out.println("Informe o nome para a busca: ");
		String nome = scanner.next();
		List<Funcionario> list = funcionarioRepository.findByNome(nome);
		list.forEach(System.out::println);
	}
	
	// busca utilizando derived query
	private void buscaFuncionarioNomeSalarioMaiorData(Scanner scanner) {
		System.out.println("Informe o nome: ");
		String nome = scanner.next();
		
		System.out.println("Informe a data: ");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, formatter);
		
		System.out.println("Informe o salario: ");
		Double salario = scanner.nextDouble();
		
		List<Funcionario> list = funcionarioRepository.findNomeSalarioMaiorDataContratacao(nome, salario, localDate);
		list.forEach(System.out::println);
	}
	
	// busca utilizando native query
	private void buscaFuncionarioData(Scanner scanner) {
		System.out.println("Informe a data: ");
		String data = scanner.next();
		LocalDate localDate = LocalDate.parse(data, formatter);
		
		List<Funcionario> list = funcionarioRepository.findDataContratacaoMaior(localDate);
		list.forEach(System.out::println);
	}
	
	// busca usando projeção
	private void buscaFuncionarioSalario() {
		List<FuncionarioProjecao> list = funcionarioRepository.findFuncionarioSalario();
		list.forEach(f -> System.out.println("Funcionario: id" + f.getId()
		+ " | Nome: " + f.getNome() + " | Salario: " + f.getSalario()));
	}

}
