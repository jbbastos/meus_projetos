package br.com.alura.spring.data.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import br.com.alura.spring.data.orm.Funcionario;
import br.com.alura.spring.data.orm.FuncionarioProjecao;

public interface FuncionarioRepository extends PagingAndSortingRepository<Funcionario, Integer>,
		JpaSpecificationExecutor<Funcionario>{
	
	// passado um nome retorne uma lista de funcionários
	List<Funcionario> findByNome(String nome);
	
	// jpql
	@Query("SELECT f FROM Funcionario f WHERE f.nome = :nome AND f.salario >= :salario AND f.dataContratacao = :data")
	List<Funcionario> findNomeSalarioMaiorDataContratacao(String nome, Double salario, LocalDate data);
	
	// native query
	@Query(value = "SELECT * FROM funcionarios f WHERE f.data_contratacao >= :data", nativeQuery = true)
	List<Funcionario> findDataContratacaoMaior(LocalDate data);
	
	// native query - projeção
	@Query(value = "SELECT f.id, f.nome, f.salario FROM funcionarios f", nativeQuery = true)
	List<FuncionarioProjecao> findFuncionarioSalario();

}
