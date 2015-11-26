package ucdb.teleview.activities;

import android.app.ProgressDialog;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import ucdb.teleview.R;
import ucdb.teleview.api.ApiMaps;
import ucdb.teleview.models.Empresa;

/**
 * Created by Junio on 19/09/2015.
 */
public class ActivityEmpresa extends AppCompatActivity implements OnMapReadyCallback {

    public static final String USER_KEY ="user_key" ;
    //List do XML
    //@Bind(R.id.user_list_view)
    //ListView userListView;

    @Bind(R.id.tv_nome)
    TextView tvNome;
    @Bind(R.id.tv_endereco)
    TextView tvEndereco;
    @Bind(R.id.tv_telefone)
    TextView tvTelefone;
    @Bind(R.id.tv_bairro)
    TextView tvBairro;
    @Bind(R.id.tv_atendimento_seg_sex)
    TextView tvAtendimentoSegSex;
    @Bind(R.id.tv_atendimento_sab)
    TextView tvAtendimentoSab;
    @Bind(R.id.tv_atendimento_dom)
    TextView tvAtendimentoDom;

    Marker marker;
    List<LatLng> locais = new ArrayList<>();

    Empresa empresa;

    //Tela de carregando
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);
        ButterKnife.bind(this);

        //Tela de carregando
        progress = ProgressDialog.show(ActivityEmpresa.this, null, "Processando...");

        //Maps
        MapFragment map = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        map.getMapAsync(this);

        empresa = (Empresa) getIntent().getExtras().getSerializable(USER_KEY);

        tvNome.setText(empresa.getNome());
        tvEndereco.setText(empresa.getEndereco() +" nº"+ empresa.getNumero());
        tvTelefone.setText("Telefone: "+ empresa.getTelefone());
        tvBairro.setText("Bairro: "+ empresa.getBairro().getNome());
        tvAtendimentoSegSex.setText("Seg a Sex: " + empresa.getAtendimentoSegSex());
        tvAtendimentoSab.setText("Sábado: "+ empresa.getAtendimentoSabado());
        tvAtendimentoDom.setText("Domingo: "+ empresa.getAtendimentoDoming());

        //Add link no telefone
        Linkify.addLinks(tvTelefone, Linkify.PHONE_NUMBERS);

        //Maps
        ApiMaps localizador = new ApiMaps(ActivityEmpresa.this);
        LatLng endereco = localizador.getCoordenada(empresa.getEndereco().toString() +" "+ empresa.getNumero() + ", Campo Grande, MS");
        locais.add(endereco);

        fechaProgressDialog();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        empresa = (Empresa) getIntent().getExtras().getSerializable(USER_KEY);

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
            }
        });

        //Preencher com enderecos
        for (LatLng l : locais) {
            googleMap.addMarker(new MarkerOptions()
                    .title(empresa.getNome())
                    .snippet(empresa.getCategoria().getNome())
                    .position(l));

            //Leva a camera até o local do maps
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(l, 13));
        }

    }

    private void fechaProgressDialog() {
        progress.dismiss();
    }
}
