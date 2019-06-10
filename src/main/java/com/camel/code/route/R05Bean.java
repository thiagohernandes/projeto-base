package com.camel.code.route;

import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.camel.code.bean.GerarObjetoBean;
import com.camel.code.domain.Funcionario;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

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
		            	String jsonList = exchange.getIn().getBody(String.class);
		                log.info("---> Lista antes " + jsonList);
		                ObjectMapper mapper = new ObjectMapper();
		                List<Funcionario> lista = mapper
		                		.readValue(jsonList, new TypeReference<List<Funcionario>>(){});
		                lista.add((Funcionario)exchange.getProperty(novoObjeto));
		                log.info("---> Lista depois ");
		                lista.stream().forEach(i -> {
		                	System.out.println(i.getNome());
		                });
		            }
	        });
				
	}

}
