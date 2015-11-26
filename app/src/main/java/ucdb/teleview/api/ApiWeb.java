package ucdb.teleview.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import retrofit.http.GET;
import retrofit.http.Path;
import ucdb.teleview.models.Bairro;
import ucdb.teleview.models.Categoria;
import ucdb.teleview.models.Empresa;
import ucdb.teleview.models.Regiao;

/**
 * Created by Junio on 06/09/2015.
 */
public class ApiWeb {

    //public static final String BASE_URL = "http://192.168.0.15:8080/TeleView-1.0-SNAPSHOT/ws";    //Roteador do Andre
    //public static final String BASE_URL = "http://192.168.25.3:8080/TeleView-1.0-SNAPSHOT/ws";    //Roteador do casa
    //public static final String BASE_URL = "http://192.168.43.74:8080/TeleView-1.0-SNAPSHOT/ws";   //Roteador do marcelo
    //public static final String BASE_URL = "http://192.168.10.2:8080/TeleView-1.0-SNAPSHOT/ws";      //
    public static final String BASE_URL = "http://192.168.43.74:8080/TeleView-1.0-SNAPSHOT/ws";


    public static Rotas rotasApi;

    public static Rotas getRotas() {
        if (rotasApi == null) {

            //Serializador Client  Json
            Gson gson = new GsonBuilder()
                    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                    .create();

            //Inicializa o Rest Client
            RestAdapter restAdapter = new RestAdapter.Builder()
                    .setLogLevel(RestAdapter.LogLevel.FULL)
                    .setConverter(new GsonConverter(gson))
                    .setEndpoint(String.format("%s", BASE_URL))
                    .build();

            //Objeto rest
            rotasApi = restAdapter.create(Rotas.class);
        }

        return rotasApi;
    }

    public interface Rotas{

        //Listas todas as empresas
        @GET("/empresa/lista")
        public void listaempresa(Callback<List<Empresa>> callback);
        //Listas todas as categorias
        @GET("/empresa/listaCategoria")
        public void listacategoria(Callback<List<Categoria>> callback);
        //Listas todas as Regiões
        @GET("/empresa/listaRegiao")
        public void listaregiao(Callback<List<Regiao>> callback);
        //Listas todas as Bairros
        @GET("/empresa/listaBairro")
        public void listabairro(Callback<List<Bairro>> callback);

        //Manda para o projeto web os parametros de busca detalhada
        @GET("/empresa/buscaDetalhada/{categoria}/{bairro}/{endereco}")
        public void buscadetalhada(@Path("categoria") String categoria,@Path("bairro") String bairro,@Path("endereco") String endereco, Callback<List<Empresa>> callback);

        //Lista as empresas por categoria
        @GET("/empresa/categoria/{id}")
        public void categoria(@Path("id") long id, Callback<List<Empresa>> callback);
        //Lista apenas a empresa do mesmo ID
        @GET("/empresa/buscarPorId/{id}")
        public void buscarPorId(@Path("id") long id, Callback<Empresa> callback);
        //busca pelo filto do buscar avançada na MAIN
        @GET("/empresa/buscar/{texto}")
        public void buscar(@Path("texto") String texto, Callback<List<Empresa>> callback);


    }
}
