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
@Table("Regiao")
public class Regiao extends Model implements Serializable{

    @Key
    @AutoIncrement
    @Column("id")
    private Long id;
    @Column("nome")
    private String nome;

    public Regiao(){

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

    @Override
    public String toString() {
        return nome;
    }
}
