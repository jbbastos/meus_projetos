package br.com.alura.spring.data.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import br.com.alura.spring.data.orm.Cargo;
import br.com.alura.spring.data.orm.UnidadeTrabalho;
import br.com.alura.spring.data.repository.CargoRepository;
import br.com.alura.spring.data.repository.UnidadeTrabalhoRepository;

// lógica para inserir valores no banco
@Service
public class CrudUnidadeTrabalhoService {
	
	private Boolean system = true;
	
	private final UnidadeTrabalhoRepository unidadeTrabalhoRepository; // cria uma instância nula
	
	public CrudUnidadeTrabalhoService(UnidadeTrabalhoRepository unidadeTrabRepo) {
		this.unidadeTrabalhoRepository = unidadeTrabRepo;
		
	}
	
	public void inicial(Scanner scanner)
	{
		while (system) {
			System.out.println("Qual acao da UT voce quer executar: ");
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
		System.out.println("Descricao da Unidade: ");
		String descricaoUnidade = scanner.next();
		
		System.out.println("Endereço da Unidade");
		String endereco = scanner.next();
		
		UnidadeTrabalho unidadeTrabalho = new UnidadeTrabalho();
		unidadeTrabalho.setDescricao(descricaoUnidade);
		unidadeTrabalho.setEndereco(endereco);
		
		unidadeTrabalhoRepository.save(unidadeTrabalho);
		
		System.out.println("Unidade de Trabalho salva com sucesso!");
	}
	
	private void atualizar(Scanner scanner)
	{
		System.out.println("Id da Unidade: ");
		Integer idUnidade = scanner.nextInt();
		
		System.out.println("Descricao da Unidade: ");
		String descricaoUnidade = scanner.next();
		
		System.out.println("Endereço da Unidade");
		String endereco = scanner.next();
		
		UnidadeTrabalho unidadeTrabalho = new UnidadeTrabalho();
		unidadeTrabalho.setId(idUnidade);
		unidadeTrabalho.setDescricao(descricaoUnidade);
		unidadeTrabalho.setEndereco(endereco);
		
		unidadeTrabalhoRepository.save(unidadeTrabalho);
		
		System.out.println("Unidade de Trabalho salva com sucesso!");
		
	}
	
	private void visualizar()
	{
		Iterable<UnidadeTrabalho> unidades = unidadeTrabalhoRepository.findAll();
		unidades.forEach(unidade -> System.out.println(unidade));
	}
	
	private void deletar(Scanner scanner)
	{
		System.out.println("Informar o id: ");
		int id = scanner.nextInt();
		unidadeTrabalhoRepository.deleteById(id);
		System.out.println("Registro apagado");
	}

}
