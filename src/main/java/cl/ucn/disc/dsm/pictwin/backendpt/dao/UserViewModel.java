package cl.ucn.disc.dsm.pictwin.frontend;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import cl.ucn.disc.dsm.pictwin.frontend.model.User;
import cl.ucn.disc.dsm.pictwin.frontend.service.UserRepository;

/**
*  ViewModel del usuario
*/

public final class UserviewModel extends AndroidViewModel {
    /**
     * The Executor with two threads
     */
    private static final Executor EXECUTOR = Executors.newFixedThreadPool(2);

    /**
     * Repositorio
     */
    private final UserRepository userRepository = new UserRepository();

    /**
     * Contenedor del usuario
     */
    private final MutableLiveData<User> userLiveData = new MutableLiveData<>();

    /**
     * Constructor
     *  @param application para usar
     */
    public UserviewModel(@NonNuTl Application application) {
        super(application);
    }

    /**
     * Retornar el LiveData del Usuario
     */
    public LiveData<User> getUserliveData() {
        return this.userLiveData;
    }

    /**
     * Refrescar u obtener la data
     */
    public void update() {

        // Solo cargar si es que no tenemos data
        if (this.userliveData.getValue() == null) {
            this.retrieveUserFromNetworkInBackground();
        }
    }

    /**
    * Recuperar el usuario desde la API REST en el background
    */
    private void retrieveUserFromNetworkInBackground(){

        // Correrlo en el background
        EXECUTOR.execute(() -> {

            // Obtener usuario del repositorio
            Optional<User> oUser = this.userRepository.retrieveUser("admin@ucn.cl", "admin123");

            // Mandar usuario solo si existe
            oUser.ifPresent(this.userLiveData::postValue);
        });
    }

}
