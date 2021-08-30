
package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import br.com.alura.specification.SpecificationFuncionario;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.repository.FuncionarioRepository;

@Service
public class RelatorioFuncionarioDinamico {
	
	private final FuncionarioRepository funcionarioRepository;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	public RelatorioFuncionarioDinamico(FuncionarioRepository funcRepo) {
		this.funcionarioRepository = funcRepo;
	}
	
	public void inicial(Scanner scanner) {
		System.out.println("Digite o nome: ");
		String nome = scanner.next();
		
		if(nome.equalsIgnoreCase("NULL")) {
			nome = null;
		}
		
		System.out.println("Digite o cpf: ");
		String cpf = scanner.next();
		
		if(cpf.equalsIgnoreCase("NULL")) {
			cpf = null;
		}
		
		System.out.println("Digite o salario: ");
		Double salario = scanner.nextDouble();
		
		if(salario == 0) {
			salario = null;
		}
		
		System.out.println("Digite a data de contratacao: ");
		String data = scanner.next();
		
		LocalDate dataContratacao;
		if(data.equalsIgnoreCase("NULL")) {
			dataContratacao = null;
		}else {
			dataContratacao = LocalDate.parse(data, formatter);
		}
		
		// busca dinamica: nome, cpf, salário, data de contratação
		List<Funcionario> funcionarios = funcionarioRepository.findAll(Specification.
											where(SpecificationFuncionario.nome(nome))
											.or(SpecificationFuncionario.cpf(cpf))
											.or(SpecificationFuncionario.salario(salario))
											.or(SpecificationFuncionario.dataContratacao(dataContratacao)));
		funcionarios.forEach(System.out::println);
	}

}
