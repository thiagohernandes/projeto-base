package com.camel.code.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.camel.code.domain.Funcionario;

@Service
public class FuncionarioService {

	private final List<Funcionario> listaFuncionarios = Arrays.asList(new Funcionario(221, "Nelson da Silva", 6800.00),
			new Funcionario(221, "Débora Vieira", 9000.00),
			new Funcionario(221, "Gilson Antunes Braga", 7800.00),
			new Funcionario(221, "Cleusa Dias Duarte", 6000.00),
			new Funcionario(221, "Marcelo Kiven Straus", 8100.00));
	
	public List<Funcionario> listaFuncionarios() {
		return listaFuncionarios;
	}
}
