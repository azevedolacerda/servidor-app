package br.com.test.servidorapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.test.servidorapp.domains.Servidor;
import br.com.test.servidorapp.models.paging.Page;
import br.com.test.servidorapp.models.paging.PagingRequest;
import br.com.test.servidorapp.services.ServidorService;

@RestController
@RequestMapping("servidor")
public class ServidorRestController {
	
	@Autowired
	private ServidorService servidorService;

	//teste
    @PostMapping("/list")
    public Page<Servidor> list(@RequestBody PagingRequest pagingRequest) {
        return servidorService.getServidores(pagingRequest);
    }
}