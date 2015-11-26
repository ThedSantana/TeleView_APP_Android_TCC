package ucdb.teleview.models;

import java.io.Serializable;

import se.emilsjolander.sprinkles.Model;
import se.emilsjolander.sprinkles.annotations.AutoIncrement;
import se.emilsjolander.sprinkles.annotations.Column;
import se.emilsjolander.sprinkles.annotations.Key;
import se.emilsjolander.sprinkles.annotations.Table;

/**
 * Created by Junio on 30/08/2015.
 */

@Table("Empresa")
public class Empresa extends Model implements Serializable {

    @Key
    @AutoIncrement
    @Column("id")
    private Long id;
    @Column("regiao")
    private Regiao regiao;
    @Column("categoria")
    private Categoria categoria;
    @Column("nome")
    private String nome;
    @Column("endereco")
    private String endereco;
    @Column("numero")
    private int numero;
    @Column("telefone")
    private String telefone;
    @Column("atendimentoSegSex")
    private String atendimentoSegSex;
    @Column("atendimentoSabado")
    private String atendimentoSabado;
    @Column("atendimentoDoming")
    private String atendimentoDoming;
    @Column("bairro")
    private Bairro bairro;

    public Empresa(){

    }

    public Regiao getRegiao() { return regiao; }

    public void setRegiao(Regiao regiao) {
        this.regiao = regiao;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getAtendimentoSegSex() {
        return atendimentoSegSex;
    }

    public void setAtendimentoSegSex(String atendimentoSegSex) { this.atendimentoSegSex = atendimentoSegSex; }

    public String getAtendimentoSabado() { return atendimentoSabado; }

    public String getAtendimentoDoming() { return atendimentoDoming; }

    public void setAtendimentoSabado(String atendimentoSabado) { this.atendimentoSabado = atendimentoSabado; }

    public void setAtendimentoDoming(String atendimentoDoming) { this.atendimentoDoming = atendimentoDoming; }

    public Bairro getBairro() { return bairro; }

    public void setBairro(Bairro bairro) {
        this.bairro = bairro;
    }

    @Override
    public String toString() {
        return  this.nome + "\r\n" + this.endereco + " nº " + this.numero;
    }
}
