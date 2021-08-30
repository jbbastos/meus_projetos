package br.com.alura.spring.data.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.FuncionarioRepository;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

// lógica para inserir valores no banco
@Service
public class CrudFuncionarioService {
	
	private Boolean system = true;
	private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
	
	private final FuncionarioRepository funcionarioRepository;
	private final CargoRepository cargoRepository;
	private final UnidadeTrabalhoRepository unidadeTrabalhoRepository;
	
	public CrudFuncionarioService(FuncionarioRepository funcRepo, CargoRepository cargoRepo, 
			UnidadeTrabalhoRepository unidRepo) {
		this.funcionarioRepository = funcRepo;
		this.cargoRepository = cargoRepo;
		this.unidadeTrabalhoRepository = unidRepo;
	}

	
	public void inicial(Scanner scanner)
	{
		while (system) {
			System.out.println("Qual acao de funcionario voce quer executar: ");
			System.out.println("0 - Sair");
			System.out.println("1 - Salvar");
			System.out.println("2 - Atualizar");
			System.out.println("3 - Visualizar");
			System.out.println("4 - Deletar");
			
			int action = scanner.nextInt();
			switch (action) {
			case 1:
				salvar(scanner);
				break;
			case 2:
				atualizar(scanner);
				break;
			case 3:
				visualizar(scanner);
				break;
			case 4:
				deletar(scanner);
				break;
			default:
				system = false;
				break;
			}
			
		}
	}
	
	private void salvar(Scanner scanner)
	{
		System.out.println("Nome: ");
		String nome = scanner.next();
		
		System.out.println("CPF: ");
		String cpf = scanner.next();
		
		System.out.println("Salário: ");
		Double salario = scanner.nextDouble();
		
		System.out.println("Data da contratação: ");
		String dataContratacao = scanner.next();
		
		System.out.println("Digite o id do cargo: ");
		Integer cargoId = scanner.nextInt();
		
		List<UnidadeTrabalho> unidades = unidade(scanner);
		
		Funcionario funcionario = new Funcionario();
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
		
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		funcionario.setCargo(cargo.get());
		
		funcionario.setUnidadeTrabalho(unidades);
		
		funcionarioRepository.save(funcionario);
		
		System.out.println("Funcionário cadastrado com sucesso!");
	}
	
	private List<UnidadeTrabalho> unidade(Scanner scanner) {
		
		Boolean isTrue = true;
		List<UnidadeTrabalho> unidades = new ArrayList<>();
		
		while(isTrue) {
			System.out.println("Digite o id da unidade(Para sair digite 0)");
			Integer unidadeId = scanner.nextInt();
			
			if(unidadeId != 0) {
				Optional<UnidadeTrabalho> unidade = unidadeTrabalhoRepository.findById(unidadeId);
				unidades.add(unidade.get());
			}else {
				isTrue = false;
			}
		}
		
		return unidades;
	}


	private void atualizar(Scanner scanner)
	{
		System.out.println("Digite o id: ");
		Integer id = scanner.nextInt();
		
		System.out.println("Nome: ");
		String nome = scanner.next();
		
		System.out.println("CPF: ");
		String cpf = scanner.next();
		
		System.out.println("Salário: ");
		Double salario = scanner.nextDouble();
		
		System.out.println("Data da contratação: ");
		String dataContratacao = scanner.next();
		
		System.out.println("Digite o id do cargo: ");
		Integer cargoId = scanner.nextInt();
		
		Funcionario funcionario = new Funcionario();
		funcionario.setId(id);
		funcionario.setNome(nome);
		funcionario.setCpf(cpf);
		funcionario.setSalario(salario);
		funcionario.setDataContratacao(LocalDate.parse(dataContratacao, formatter));
		
		Optional<Cargo> cargo = cargoRepository.findById(cargoId);
		funcionario.setCargo(cargo.get());
		
		funcionarioRepository.save(funcionario);
		
		System.out.println("Funcionário atualizado com sucesso!");
	}
	
	private void visualizar(Scanner scanner)
	{
		System.out.println("Qual pagina voce deseja visualizar: ");
		Integer page = scanner.nextInt();
		
		// cria uma request para que retorne a página que o cliente selecionou
		Pageable pageable = PageRequest.of(page, 5, Sort.Direction.ASC, "nome");
		
		Page<Funcionario> funcionarios = funcionarioRepository.findAll(pageable);
		
		System.out.println(funcionarios);
		System.out.println("Pagina atual " + funcionarios.getNumber());
		System.out.println("Total elementos " + funcionarios.getTotalElements());
		funcionarios.forEach(funcionario -> System.out.println(funcionario));

	}
	
	private void deletar(Scanner scanner)
	{
		System.out.println("Informe o id para excluir: ");
		int id = scanner.nextInt();
		funcionarioRepository.deleteById(id);
		System.out.println("Funcionário excluído");
	}

}