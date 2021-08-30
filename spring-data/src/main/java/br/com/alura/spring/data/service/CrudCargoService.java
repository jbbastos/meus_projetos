package br.com.alura.spring.data.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.repository.CargoRepository;

// lógica para inserir valores no banco
@Service
public class CrudCargoService {
	
	private Boolean system = true;
	
	// Necessária uma instância da classe CargoRepository
	// Para isso é necessário a injeção de dependência dele
	private final CargoRepository cargoRepository; // cria uma instância nula
	public CrudCargoService(CargoRepository repository) {
		// cria a SpringDataApplication com uma instancia de CargoRepository 
		this.cargoRepository = repository;
		
	}
	
	public void inicial(Scanner scanner)
	{
		while (system) {
			System.out.println("Qual acao de cargo voce quer executar: ");
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
				visualizar();
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
		System.out.println("Descricao do cargo: ");
		String descricao = scanner.next();
		Cargo cargo = new Cargo();
		cargo.setDescricao(descricao);
		cargoRepository.save(cargo);
		System.out.println("Salvo com sucesso!");
	}
	
	private void atualizar(Scanner scanner)
	{
		System.out.println("Informar o id: ");
		Integer idCargo = scanner.nextInt();
		System.out.println("Nova descricao: ");
		String descricao = scanner.next();
		
		Cargo cargo = new Cargo();
		cargo.setId(idCargo);
		cargo.setDescricao(descricao);
		
		cargoRepository.save(cargo);
		System.out.println("Atualização realizada");
		
	}
	
	private void visualizar()
	{
		Iterable<Cargo> cargos = cargoRepository.findAll();
		cargos.forEach(cargo -> System.out.println(cargo));
	}
	
	private void deletar(Scanner scanner)
	{
		System.out.println("Informar o id: ");
		int id = scanner.nextInt();
		cargoRepository.deleteById(id);
		System.out.println("Registro apagado");
	}

}
