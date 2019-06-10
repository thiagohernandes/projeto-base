package com.camel.code.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import com.camel.code.predicate.CustomPredicate;

@Component
public class R04Http4PredicateChoice  extends RouteBuilder {

	private final String rotaJetty8088 = "jetty://http://localhost:8088/predicate-teste";
	private final String rotaHttp4 = "http4://localhost:8080/api/funcionarios/todos?bridgeEndpoint=true";
	private final String rotaHttp4Inexistente = "http4://localhost:8080/api/funcionarios/todos-inexistente?bridgeEndpoint=true";
	private final String rotaHttp4Empty = "http4://localhost:8080/api/funcionarios/todos-empty?bridgeEndpoint=true";
	private final String directHttp = "direct:httpfuncionario2";
	private final String propListFuncionarios = "propListFuncionarios";
	private final String propListaVazia = "propListaVazia";
	
	private CustomPredicate customPredicate = new CustomPredicate();
	
	@Override
	public void configure() throws Exception {
		from(rotaJetty8088)
			.to(directHttp);
		
		from(directHttp)
			.doTry()
				.convertBodyTo(String.class)
				.setHeader(Exchange.HTTP_METHOD, constant(org.apache.camel.component.http4.HttpMethods.GET))
				.to(rotaHttp4)
				.process(new Processor() {
		            @Override
		            public void process(Exchange exchange) throws Exception {
		            	String jsonList = exchange.getIn().getBody(String.class);
		               log.info("---> Lista " + jsonList);
		               exchange.setProperty(propListFuncionarios, jsonList);
		            }
		        })
				.choice()
					.when(customPredicate.getSimulacaoPredicate())
						.process(new Processor() {
				            @Override
				            public void process(Exchange exchange) throws Exception {
				               log.info("---> Log Predicate AND: " + exchange.getProperty(propListFuncionarios).toString());
				            }
				        })
					.otherwise()
					.process(new Processor() {
			            @Override
			            public void process(Exchange exchange) throws Exception {
			               log.info("---> Log Predicate OR: " + exchange.getProperty(propListaVazia).toString());
			            }
			        })	
				.endChoice()
			.endDoTry()
			.doCatch(Exception.class)
				.process(new Processor() {
		            @SuppressWarnings("static-access")
					@Override
		            public void process(Exchange exchange) throws Exception {
		               log.error("---> Exceção original: ", exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class));
		            }
		        })
			.end();
	}

}
