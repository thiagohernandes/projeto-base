package com.camel.code.util;

import java.util.List;

import org.apache.camel.CamelContext;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.impl.DefaultCamelContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class UtilApp {

	 private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	 public void runContextCamel(List<Object> listRoutes, int timeMilisegundos, boolean alwaysRunning) throws Exception {
		 	logger.info("*********** Iniciando o contexto do Camel");
	        CamelContext ctx = new DefaultCamelContext();
	        listRoutes.stream().forEach(i -> {
	            try {
	                ctx.addRoutes((RoutesBuilder) i);
	            } catch (Exception e) {
	            	logger.error("*********** Erro");
	                e.printStackTrace();
	            }
	        });
	        ctx.start();
	        if (!alwaysRunning) {
		        Thread.sleep(timeMilisegundos);
		        ctx.stop();
		        logger.info("*********** Sucesso");
	        }
	        logger.info("*********** Sucesso - contexto do Camel executando...");
	    }
	 
}
