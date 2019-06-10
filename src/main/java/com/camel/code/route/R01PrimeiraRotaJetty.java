package com.camel.code.route;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class R01PrimeiraRotaJetty extends RouteBuilder {

	private final String rotaJetty8082 = "jetty://http://localhost:8082/minha-primeira-rota";
	
	@Override
	public void configure() throws Exception {
		from(rotaJetty8082)
			.transform()
			.simple("Olá primeira rota!")
			.process(new Processor() {
	            @Override
	            public void process(Exchange exchange) throws Exception {
	               log.info("---> Log 8082");
	            }
	        })
	    	.log("${body}");
	}

}
