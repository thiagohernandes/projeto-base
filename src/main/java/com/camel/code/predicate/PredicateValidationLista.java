package com.camel.code.predicate;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;

import com.camel.code.domain.Funcionario;
import com.google.gson.Gson;

public class PredicateValidationLista implements Predicate {

	private final String propListFuncionarios = "propListFuncionarios";
	private final String msgListaVazia = "A lista está vazia!";
	private final String propListaVazia = "propListaVazia";
	
	@Override
	public boolean matches(Exchange exchange) {
		Gson g = new Gson();
		Funcionario[] lista = g.fromJson(exchange.getProperty(propListFuncionarios).toString(), Funcionario[].class);
		if(lista.length == 0 || lista == null) {
			exchange.setProperty(propListaVazia, msgListaVazia);
			return false;
		}	
		return true;
	}
}
