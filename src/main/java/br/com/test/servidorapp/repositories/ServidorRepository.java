package br.com.test.servidorapp.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.test.servidorapp.domains.Servidor;

@Repository
public interface ServidorRepository extends CrudRepository<Servidor, String> {

	
	
}
