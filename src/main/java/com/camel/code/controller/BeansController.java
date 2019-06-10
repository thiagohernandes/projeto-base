package com.camel.code.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.camel.code.bean.MoveFilesBean;
import com.camel.code.util.UtilApp;

@RestController
@RequestMapping("/api/beans")
public class BeansController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UtilApp util;
	
	@GetMapping("/movefiles")
	public void callBeanMoveFiles() throws Exception {
		logger.info("*********** Iniciando callBeanMoveFiles");
		List<Object> listaRoutes = new ArrayList<>();
		listaRoutes.add(new MoveFilesBean());
		util.runContextCamel(listaRoutes,2000,false);
		logger.info("*********** Sucesso");
	}
}
