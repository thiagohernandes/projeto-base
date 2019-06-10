package com.camel.code.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.camel.code.domain.Funcionario;
import com.google.gson.Gson;

@Component
public class R03Http4Processor  extends RouteBuilder {

	private final String rotaJetty8087 = "jetty://http://localhost:8087/integrar-api-funcionarios";
	private final String rotaHttp4 = "http4://localhost:8080/api/funcionarios/todos?bridgeEndpoint=true";
	private final String directHttp = "direct:httpfuncionario";
	
	@Override
	public void configure() throws Exception {
		from(rotaJetty8087)
			.to(directHttp);
		
		from(directHttp)
			.convertBodyTo(String.class)
			.setHeader(Exchange.HTTP_METHOD, constant(org.apache.camel.component.http4.HttpMethods.GET))
			.to(rotaHttp4)
			.process(new Processor() {
				@Override
		          public void process(Exchange exchange) throws Exception {
					  Gson g = new Gson();
	            	  Funcionario[] p = g.fromJson(exchange.getIn().getBody(String.class), Funcionario[].class);
	            	  String listJson = g.toJson(p);
	            	  log.info("---> JSON de objetos de Funcionários '{}' ", listJson);
		          }
			});
	}

}
