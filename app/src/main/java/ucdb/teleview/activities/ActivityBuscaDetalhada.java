package ucdb.teleview.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ucdb.teleview.R;
import ucdb.teleview.api.ApiMaps;
import ucdb.teleview.api.ApiWeb;
import ucdb.teleview.models.Bairro;
import ucdb.teleview.models.Categoria;
import ucdb.teleview.models.Empresa;
import ucdb.teleview.models.Regiao;

/**
 * Created by Junio on 25/09/2015.
 */
public class ActivityBuscaDetalhada extends AppCompatActivity implements OnMapReadyCallback {

    public static final String CAT_KEY ="user_key" ;

    @Bind(R.id.sp_item_categoria)
    Spinner spCategoria;

    String cat;

    //Adapter que vai setar os dados na List
    ArrayAdapter<Empresa> adapter;

    //Tela de carregando
    ProgressDialog progress;

    //Maps
    Marker marker;
    List<LatLng> locais = new ArrayList<>();

    //posição do adapter
    int posicao = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busca_detalhada);
        ButterKnife.bind(this);

        //Abre a tela com o processo de carregando
        abreProgressDialog();

        //Mostra o local do usuário no inicio
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Chama os objetos para completar os spiner
        addItemsOnSpinner();

    }

    //Adicionando todos os objetos nos Spinner
    public void addItemsOnSpinner() {

        //Busca todas as categorias
        ApiWeb.getRotas().listacategoria(new Callback<List<Categoria>>() {
            @Override
            public void success(List<Categoria> categorias, Response response) {
                ArrayAdapter<Categoria> dataAdapter = new ArrayAdapter<Categoria>(ActivityBuscaDetalhada.this, android.R.layout.simple_spinner_item, categorias);
                dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCategoria.setAdapter(dataAdapter);
                fechaProgressDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                fechaProgressDialog();
                Toast.makeText(ActivityBuscaDetalhada.this, "Falha em sua conexão de rede", Toast.LENGTH_LONG).show();
                finish();
            }
        });

    }

    public void abreProgressDialog(){
        //Inicia o ProgressDialog
        progress = ProgressDialog.show(ActivityBuscaDetalhada.this, null, "Processando...");
    }

    //Fecha a tela de Progress Dialog
    private void fechaProgressDialog() {
        //Remove o ProgressDialog da tela
        progress.dismiss();
    }

    //Clique do botão, verifica qual Categoria escolheu
    @OnClick(R.id.btn_buscar2)
    public void buscaDetalhada2() {

        abreProgressDialog();

        int id=0;

        //ARRUMAR DEPOIS
        cat = spCategoria.getSelectedItem().toString();

        if(cat.equals("Academia")){
            id = 1;
        }else if(cat.equals("Supermercado")){
            id = 2;
        }else if(cat.equals("Restaurante")){
            id = 3;
        }else if(cat.equals("Farmacia")){
            id = 4;
        }else if(cat.equals("Caixa Eletronico")){
            id = 5;
        }else if(cat.equals("Borracharia")){
            id = 6;
        }else if(cat.equals("Hotel")){
            id = 7;
        }else if(cat.equals("Posto de Combustivel")){
            id = 8;
        }else if(cat.equals("Teatro")){
            id = 9;
        }

        //Manda buscar a categoria pelo id
        buscaEmpresaPorId(id);

    }

    public void buscaEmpresaPorId(int id){

        //limpa todas os locais para receber novos locais
        if (posicao!=0){
            locais.clear();
        }


        //Busca relacionada a cada categoria, passa o id como atributo para web pesquisar via JDBC
        ApiWeb.getRotas().categoria(id, new Callback<List<Empresa>>() {
            @Override
            public void success(List<Empresa> lista, Response response) {

                //Seta a lista de empresas no Adapter para depois incluir no Maps
                adapter = new ArrayAdapter<>(ActivityBuscaDetalhada.this, android.R.layout.simple_list_item_1, lista);

                //Seta todas as empresas no mapa
                MapFragment map = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
                map.getMapAsync(ActivityBuscaDetalhada.this);
                ApiMaps localizador = new ApiMaps(ActivityBuscaDetalhada.this);

                //Seta todos os endereços no maps
                for (Empresa emp : lista) {
                    //Maps
                    LatLng endereco = localizador.getCoordenada(emp.getEndereco().toString() + " " + emp.getNumero() + ", Campo Grande, MS");
                    locais.add(endereco);

                }

                fechaProgressDialog();
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(ActivityBuscaDetalhada.this, "Falha na conexão com servidor!", Toast.LENGTH_LONG).show();
                fechaProgressDialog();
            }
        });

    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        //Limpa o mapa para setar novos locais e zera o número da posição para setar lá em baixo
        if(posicao!=0){
            googleMap.clear();
            posicao=0;
        }

        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                if (marker != null) {
                    marker.remove();
                }
                //Preenche localização do usuário
                LatLng local = new LatLng(location.getLatitude(), location.getLongitude());
                marker = googleMap.addMarker(new MarkerOptions()
                        .title("Posição atual")
                        .position(local));

                //Leva a camera até o local do maps
                //googleMap.moveCamera(CameraUpdateFactory.newLatLng(local));
            }
        });


        //Preencher com enderecos
        for (LatLng l : locais) {

            //Pega a empresa no adapter referente aquela posição e seta na tela
            final Empresa empresa = adapter.getItem(posicao);

            googleMap.addMarker(new MarkerOptions()
                    .title(empresa.getNome())
                    .snippet("Telefone: " + empresa.getTelefone())
                    .position(l));

            //Leva a camera até o local do maps
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(l, 13));

            //Seta mais um para pegar a proxima empresa
            posicao++;

        }

        //adapter.clear();


    }



}