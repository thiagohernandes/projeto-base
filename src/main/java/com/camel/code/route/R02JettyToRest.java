package com.camel.code.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.camel.code.domain.Funcionario;
import com.camel.code.util.UtilApp;
import com.google.gson.Gson;

@Component
public class R02JettyToRest extends RouteBuilder {

	private final String rotaJetty8083 = "jetty://http://localhost:8083/msg-to-rest";
	private final String rotaJetty8084 = "jetty://http://localhost:8084/rota84";
	private final String rotaRestFuncionarios = "jetty://http://localhost:8080/api/funcionarios/todos?bridgeEndpoint=true";
	private final String msgFrom8083 = "msgFrom8083";
	private final String valorOut8083 = "Esse valor foi setado na chamada do serviço da porta 8083";
	private final String msgFrom8083Value = "Olá serviço REST!";
	
	@Autowired
	UtilApp utilApp;
	
	@Override
	public void configure() throws Exception {
		
		from(rotaJetty8083)
			.process(new Processor() {
	            @Override
	            public void process(Exchange exchange) throws Exception {
	               log.info("---> Log 8083");
	               exchange.setProperty(msgFrom8083, msgFrom8083Value);
	            }
	        })
	    	.to(rotaRestFuncionarios)
	    	.process(new Processor() {
	            @Override
	            public void process(Exchange exchange) throws Exception {
	            	log.info("---> Isso veio da URL 8083: '{}' ", exchange.getProperty(msgFrom8083));
	            	log.info("---> Isso será a saída para outro redirecionamento (String): '{}' ", exchange.getIn().getBody(String.class));
	            	Gson g = new Gson();
	            	Funcionario[] p = g.fromJson(exchange.getIn().getBody(String.class), Funcionario[].class);
	            	log.info("---> JSON de objetos de Funcionários '{}' ", g.toJson(p));
	            	exchange.getOut().setBody(valorOut8083);
	            }
	        })
	    	.to(rotaJetty8084);
		
		from(rotaJetty8084)
			.convertBodyTo(String.class)
			.process(new Processor() {
	            @Override
	            public void process(Exchange exchange) throws Exception {
	               log.info("---> Log 8084 '{}'", exchange.getIn().getBody());
	            }
	        });
	}
	
}
