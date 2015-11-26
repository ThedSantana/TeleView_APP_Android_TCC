package ucdb.teleview.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ucdb.teleview.R;
import ucdb.teleview.api.ApiWeb;
import ucdb.teleview.models.Empresa;

public class MainActivity extends AppCompatActivity {

    //Acessa o botão
    @Bind(R.id.et_buscar)
    EditText edBuscar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
    }
    @OnClick(R.id.btn_academia)
    public void academia(){
        Intent irParaCategoria =  new Intent(MainActivity.this, ActivityCategoria.class);
        irParaCategoria.putExtra(ActivityCategoria.USER_KEY, 1);
        startActivity(irParaCategoria);
    }
    @OnClick(R.id.btn_supermercado)
    public void supermercado(){
        buscaCategoria(2);
        Intent irParaCategoria =  new Intent(MainActivity.this, ActivityCategoria.class);
        irParaCategoria.putExtra(ActivityCategoria.USER_KEY, 2);
        startActivity(irParaCategoria);
    }
    @OnClick(R.id.btn_restaurante)
    public void restaurante(){
        buscaCategoria(3);
        Intent irParaCategoria =  new Intent(MainActivity.this, ActivityCategoria.class);
        irParaCategoria.putExtra(ActivityCategoria.USER_KEY, 3);
        startActivity(irParaCategoria);
    }
    @OnClick(R.id.btn_farmacia)
    public void farmacia(){
        buscaCategoria(4);
        Intent irParaCategoria =  new Intent(MainActivity.this, ActivityCategoria.class);
        irParaCategoria.putExtra(ActivityCategoria.USER_KEY, 4);
        startActivity(irParaCategoria);
    }
    @OnClick(R.id.btn_banco)
    public void banco(){
        buscaCategoria(5);
        Intent irParaCategoria =  new Intent(MainActivity.this, ActivityCategoria.class);
        irParaCategoria.putExtra(ActivityCategoria.USER_KEY, 5);
        startActivity(irParaCategoria);
    }
    @OnClick(R.id.btn_borracharia)
    public void hospital(){
        buscaCategoria(6);
        Intent irParaCategoria =  new Intent(MainActivity.this, ActivityCategoria.class);
        irParaCategoria.putExtra(ActivityCategoria.USER_KEY, 6);
        startActivity(irParaCategoria);
    }
    @OnClick(R.id.btn_hotel)
    public void hotel(){
        buscaCategoria(7);
        Intent irParaCategoria =  new Intent(MainActivity.this, ActivityCategoria.class);
        irParaCategoria.putExtra(ActivityCategoria.USER_KEY, 7);
        startActivity(irParaCategoria);
    }
    @OnClick(R.id.btn_posto)
    public void posto(){
        buscaCategoria(8);
        Intent irParaCategoria =  new Intent(MainActivity.this, ActivityCategoria.class);
        irParaCategoria.putExtra(ActivityCategoria.USER_KEY, 8);
        startActivity(irParaCategoria);
    }
    @OnClick(R.id.btn_teatro)
    public void teatro(){
        buscaCategoria(9);
        Intent irParaCategoria =  new Intent(MainActivity.this, ActivityCategoria.class);
        irParaCategoria.putExtra(ActivityCategoria.USER_KEY, 9);
        startActivity(irParaCategoria);
    }
    //Busca por filtro de editTexf
    @OnClick(R.id.btn_buscar)
    public void buscar(){
        String valorDoBuscar = edBuscar.getText().toString();

        //compara se o campo está vazio
        if(valorDoBuscar.isEmpty()){
            Toast.makeText(MainActivity.this, "Busca não pode ser vazia!", Toast.LENGTH_LONG).show();
        }else{
            Intent irParaCategoria =  new Intent(MainActivity.this, ActivityBuscarCategoria.class);
            irParaCategoria.putExtra(ActivityCategoria.USER_KEY, valorDoBuscar);
            startActivity(irParaCategoria);
        }

    }
    //Buscar por filtros do Busca detalhada
    @OnClick(R.id.btn_busca_detalhada)
    public void buscaDetalhada(){
        Intent irParaCategoria =  new Intent(MainActivity.this, ActivityBuscaDetalhada.class);
        startActivity(irParaCategoria);
    }

    public void buscaCategoria(int idCategoria){

    }
}
