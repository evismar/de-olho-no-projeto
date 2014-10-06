package com.evismar.leipraque.beans;

import java.util.ArrayList;
import java.util.List;

public class Proposicao {
	 
    private String nome;
    private String datApresentacao;
    private String textoEmenta;
    private Deputado autor = new Deputado();
 
    
    private String tipoProposicao;
    private String tema;
    private String explicacaoEmenta;
    private String regimeTramitacao;
    private String ultimoDespacho;
    private String situacao;
    private String linkInteiroTeor;
    
    
    public Proposicao(){
 
    }
	public String getTipoProposicao() {
		return tipoProposicao;
	}

	public void setTipoProposicao(String tipoProposicao) {
		this.tipoProposicao = tipoProposicao;
	}

	public String getTema() {
		return tema;
	}

	public void setTema(String tema) {
		this.tema = tema;
	}

	public String getExplicacaoEmenta() {
		return explicacaoEmenta;
	}

	public void setExplicacaoEmenta(String explicacaoEmenta) {
		this.explicacaoEmenta = explicacaoEmenta;
	}

	public String getRegimeTramitacao() {
		return regimeTramitacao;
	}

	public void setRegimeTramitacao(String regimeTramitacao) {
		this.regimeTramitacao = regimeTramitacao;
	}

	public String getUltimoDespacho() {
		return ultimoDespacho;
	}

	public void setUltimoDespacho(String ultimoDespacho) {
		this.ultimoDespacho = ultimoDespacho;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getLinkInteiroTeor() {
		return linkInteiroTeor;
	}

	public void setLinkInteiroTeor(String linkInteiroTeor) {
		this.linkInteiroTeor = linkInteiroTeor;
	}

	public String getDatApresentacao() {
		return datApresentacao;
	}

	public void setDatApresentacao(String datApresentacao) {
		this.datApresentacao = datApresentacao;
	}

	
	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
        return nome;
    }
 
	public String getTextoEmenta() {
		return textoEmenta;
	}

	public void setTextoEmenta(String textoEmenta) {
		this.textoEmenta = textoEmenta;
	}
    @Override
    public String toString() {
        return getNome() + " - " + getDatApresentacao()+"\n\n"+getTextoEmenta();

    }
	public Deputado getAutor() {
		return autor;
	}
	public void setAutor(Deputado autor) {
		this.autor = autor;
	}
}