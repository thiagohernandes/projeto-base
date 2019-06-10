package com.camel.code.bean;

import org.apache.camel.Exchange;
import org.springframework.stereotype.Controller;

import com.camel.code.domain.Funcionario;

@Controller
public class GerarObjetoBean {

	private final String novoObjeto = "novoObjeto";
	
	public void gerarObjeto(Exchange exchange) {
		exchange.setProperty(novoObjeto, new Funcionario(100,"Alexandro Chivas", 12000.90));
	}
}
