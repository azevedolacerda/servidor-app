package br.com.test.servidorapp.models;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import br.com.test.servidorapp.domains.Servidor;
import br.com.test.servidorapp.models.paging.Direction;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

public final class ServidorComparators {
	 @EqualsAndHashCode
	    @AllArgsConstructor
	    @Getter
	    static class Key {
	        String name;
	        Direction dir;
	    }

	    static Map<Key, Comparator<Servidor>> map = new HashMap<>();

	    static {
	        map.put(new Key("nome", Direction.asc), Comparator.comparing(Servidor::getNome));
	        map.put(new Key("nome", Direction.desc), Comparator.comparing(Servidor::getNome)
	                                                           .reversed());

	        map.put(new Key("matricula", Direction.asc), Comparator.comparing(Servidor::getMatricula));
	        map.put(new Key("matricula", Direction.desc), Comparator.comparing(Servidor::getMatricula)
	                                                                 .reversed());

	        map.put(new Key("categoriaFuncional", Direction.asc), Comparator.comparing(Servidor::getCategoriaFuncional));
	        map.put(new Key("categoriaFuncional", Direction.desc), Comparator.comparing(Servidor::getCategoriaFuncional)
	                                                               .reversed());

	        map.put(new Key("lotacao", Direction.asc), Comparator.comparing(Servidor::getLotacao));
	        map.put(new Key("lotacao", Direction.desc), Comparator.comparing(Servidor::getLotacao)
	                                                             .reversed());
	    }

	    public static Comparator<Servidor> getComparator(String name, Direction dir) {
	        return map.get(new Key(name, dir));
	    }

	    private ServidorComparators() {
	    }
}
