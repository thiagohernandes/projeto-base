package com.camel.code.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@JsonInclude(Include.ALWAYS)
@AllArgsConstructor
public class Funcionario {

	@Setter @Getter private int codigo;
	@Setter @Getter private String nome;
	@Setter @Getter private Double salario;
	
}
