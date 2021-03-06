package ucdb.teleview.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import ucdb.teleview.R;
import ucdb.teleview.api.ApiWeb;
import ucdb.teleview.models.Empresa;

/**
 * Created by Junio on 27/08/2015.
 */
public class ActivityCategoria extends AppCompatActivity {

    public static final String USER_KEY ="user_key" ;
    //List do XML
    @Bind(R.id.user_list_view)
    ListView userListView;
    //Adapter que vai setar os dados na List
    ArrayAdapter<Empresa> adapter;

    //Tela de carregando
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categoria);
        ButterKnife.bind(this);

        //Tela de carregando
        progress = ProgressDialog.show(ActivityCategoria.this, null, "Processando...");

            int idCategoria = (int) getIntent().getExtras().getSerializable(USER_KEY);

            //Busca relacionada a cada categoria, passa o id como atributo para web pesquisar via JDBC
            ApiWeb.getRotas().categoria(idCategoria, new Callback<List<Empresa>>() {
                @Override
                public void success(List<Empresa> lista, Response response) {
                    //Seta os atributos no Adapter para depois incluir na ListView
                    adapter = new ArrayAdapter<>(ActivityCategoria.this, android.R.layout.simple_list_item_1, lista);
                    userListView.setAdapter(adapter);
                    fechaProgressDialog();

                }

                @Override
                public void failure(RetrofitError error) {
                    fechaProgressDialog();
                    Toast.makeText(ActivityCategoria.this, "Falha na conexão com servidor!", Toast.LENGTH_LONG).show();
                    finish();
                }
            });

            //Clique na empresa abre uma nova tela com os dados dela preenchidos
            userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Carregando a Intent para Fazer o Registro ou Alteração
                    Intent irParaEmpresa = new Intent(ActivityCategoria.this, ActivityEmpresa.class);
                    //Passando como parametro para a tela de cadastro um item clicado
                    Empresa empresa = adapter.getItem(position);
                    //String idEmpresa = String.valueOf(empresa.getId());
                    irParaEmpresa.putExtra(ActivityEmpresa.USER_KEY, empresa);
                    startActivity(irParaEmpresa);
                }

            });


    }

    private void fechaProgressDialog() {
        progress.dismiss();
    }
}
