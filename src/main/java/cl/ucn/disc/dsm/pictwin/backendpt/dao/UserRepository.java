package cl.ucn.disc.dsm.pictwin.frontend.service;

import java.io.I0Exception;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import cl.ucn.disc.dsm.pictwin.frontend.model.User;
import lombok.extern.s1f4j.S1f4j;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.HttpException;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*import cl.ucn.disc.dsm.pictwin.backendpt.model.User;
import lombok.NonNull;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;*/

/**
    * Repositorio de Users
*/

@Slf4j
public final class UserRepository{


    /**
     * API Rest
     */

    // The local network (access point)

    // private static final String BASE_URL = "http://10.0.0.145:8080";

    // The internal network
    private static final String BASE_URL="http://192.168.43.14:8080";

    /**
     *API Rest
     */

    private final PicTwinAPIRest apiRest;

    /**
     * Constructor
     */
    public UserRepository(){

            log.debug("Building UserRepository with URL: {}",BASE_URL);

            // Logger
            HttpLoggingInterceptor thelogging=new HttpLoggingInterceptor(log::debug);
            theLogging.setLevel(HttpLoggingInterceptor.Level.BODY);

            // OkHttp
            OkHttpClient theClient=new OkHttpClient.Builder()
                .addInterceptor(theLogging)
                .readTimeout(30,TimeUnit.SECONDS)
                .connectTimeout(50,TimeUnit.SECONDS)
                .writeTimeout(50,TimeUnit.SECONDS)
                .callTimeout(50,TimeUnit.SECONDS)
                .build();

            // The Retrofit
            Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(theClient)
                .build();

            // The API Rest
            this.apiRest=retrofit.create(PicTwinAPIRest.class);

    }

    /**
     * Recupera un usuario desde PicTwin API Rest
     *
     * @param email para usar
     * @param password para usar
     * @return Al usuario
     */

    // @SneakyThrows
    public Optional<User> retrieveUser(final String email,final String password){

            // The Call
            Call<User> cUser=this.apiRest.retrieveUser(email,password);

            try{
                // The Exec
                Response<User> rUser=cUser.execute();

                // Code in 2xx range
                if(rUser.isSuccessful()){

                    // Check for body
                    if(ruser.body()==null){
                    return Optional.empty();
                    }

                // Return the User
                return Optional.of(rUser.body());
                }

            // Some unknow error
            throw new RuntimeException("No se puede recuperar el usuario",new HttpException(rUser));
            }catch (IOException ex) {

            // IO error
            throw new RuntimeException("No se puede recuperar el usuario",ex);
            }
    }
}

