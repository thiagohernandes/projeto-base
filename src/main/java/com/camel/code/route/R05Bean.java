package com.camel.code.route;

import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.camel.code.bean.GerarObjetoBean;
import com.camel.code.domain.Funcionario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

@Component
public class R05Bean  extends RouteBuilder {

	private final String rotaJetty8089 = "jetty://http://localhost:8089/predicate-teste";
	private final String rotaHttp4 = "http4://localhost:8080/api/funcionarios/todos?bridgeEndpoint=true";
	private final String directHttp = "direct:httpfuncionario3";
	private final String gerarObjeto = "gerarObjeto";
	private final String novoObjeto = "novoObjeto";
	
	@Override
	public void configure() throws Exception {
		from(rotaJetty8089)
			.to(directHttp);
		
		from(directHttp)
			.bean(GerarObjetoBean.class, gerarObjeto)
			.convertBodyTo(String.class)
			.to(rotaHttp4)
			.process(new Processor() {
	            @Override
	            public void process(Exchange exchange) throws Exception {
					Gson gson = new Gson();
					java.lang.reflect.Type listaFuncionariosType = new TypeToken<ArrayList<Funcionario>>(){}.getType(); 
					List<Funcionario> listaFuncionarios= gson.fromJson(exchange.getIn().getBody(String.class), listaFuncionariosType);
	                listaFuncionarios.add((Funcionario)exchange.getProperty(novoObjeto));
					listaFuncionarios.forEach(i -> System.out.println(i.getNome()));
	            }
	        });
				
	}

}
 