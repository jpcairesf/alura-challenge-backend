package com.alurachallenge.backend.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("receita")
public class Receita extends Movimentacao {
}
