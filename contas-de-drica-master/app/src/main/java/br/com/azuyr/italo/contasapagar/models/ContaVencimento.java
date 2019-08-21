package br.com.azuyr.italo.contasapagar.models;

import java.io.Serializable;

import br.com.azuyr.italo.contasapagar.utils.Util;

public class ContaVencimento implements Serializable{

    private Long id;
    private String titulo;
    private String resumo;
    private Double valor;
    private Long vencimento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Long getVencimento() {
        return vencimento;
    }

    public void setVencimento(Long vencimento) {
        this.vencimento = vencimento;
    }


    public String getResumo() {
        return resumo;
    }

    public void setResumo(String resumo) {
        this.resumo = resumo;
    }

    @Override
    public String toString() {
        return resumo + " | " + valor + " | " + Util.setLongGetDate(vencimento);
    }

}
