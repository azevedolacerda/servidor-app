package br.com.test.servidorapp.services;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.test.servidorapp.domains.Servidor;
import br.com.test.servidorapp.models.ServidorComparators;
import br.com.test.servidorapp.models.paging.Column;
import br.com.test.servidorapp.models.paging.Order;
import br.com.test.servidorapp.models.paging.Page;
import br.com.test.servidorapp.models.paging.PagingRequest;
import br.com.test.servidorapp.repositories.ServidorRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ServidorService {

	@Autowired
	private ServidorRepository servidorRepository;

	private static final Comparator<Servidor> EMPTY_COMPARATOR = (e1, e2) -> 0;

	public Page<Servidor> getServidores(PagingRequest pagingRequest) {
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			List<Servidor> servidores = (List<Servidor>) servidorRepository.findAll();

			return getPage(servidores, pagingRequest);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return new Page<>();
	}

	private Page<Servidor> getPage(List<Servidor> servidores, PagingRequest pagingRequest) {
		List<Servidor> filtered = servidores.stream().sorted(sortServidors(pagingRequest))
				.filter(filterServidors(pagingRequest)).skip(pagingRequest.getStart()).limit(pagingRequest.getLength())
				.collect(Collectors.toList());

		long count = servidores.stream().filter(filterServidors(pagingRequest)).count();

		Page<Servidor> page = new Page<>(filtered);
		page.setRecordsFiltered((int) count);
		page.setRecordsTotal((int) count);
		page.setDraw(pagingRequest.getDraw());

		return page;
	}

	private Predicate<Servidor> filterServidors(PagingRequest pagingRequest) {
		if (pagingRequest.getSearch() == null || StringUtils.isEmpty(pagingRequest.getSearch().getValue())) {
			return servidor -> true;
		}

		String value = pagingRequest.getSearch().getValue();

		return servidor -> servidor.getNome().toLowerCase().contains(value)
				|| servidor.getNome().contains(value)
				|| servidor.getMatricula().toLowerCase().contains(value);
	}

	private Comparator<Servidor> sortServidors(PagingRequest pagingRequest) {
		if (pagingRequest.getOrder() == null) {
			return EMPTY_COMPARATOR;
		}

		try {
			Order order = pagingRequest.getOrder().get(0);

			int columnIndex = order.getColumn();
			Column column = pagingRequest.getColumns().get(columnIndex);

			Comparator<Servidor> comparator = ServidorComparators.getComparator(column.getData(), order.getDir());
			if (comparator == null) {
				return EMPTY_COMPARATOR;
			}

			return comparator;

		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}

		return EMPTY_COMPARATOR;
	}
}
