package cl.ucn.disc.dsm.pictwin.backendpt.services;

import cl.ucn.disc.dsm.pictwin.backendpt.dao.PicRepository;
import cl.ucn.disc.dsm.pictwin.backendpt.dao.TwinRepository;
import cl.ucn.disc.dsm.pictwin.backendpt.dao.UserRepository;
import cl.ucn.disc.dsm.pictwin.backendpt.model.Pic;
import cl.ucn.disc.dsm.pictwin.backendpt.model.Twin;
import cl.ucn.disc.dsm.pictwin.backendpt.model.User;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
* Implementación del {@link PicTwin}
*/
@Slf4j
@Service
public class PicTwinImpl implements PicTwin {
    /**
     * Hasher.
     */
    private final static PasswordEncoder PASSWORD_ENCODER = new Argon2PasswordEncoder();

    /**
     * Random
     */
    private final static Random RANDOM = new Random();

    /**
     * Repositorio del Pic
     */
    private final PicRepository picRepository;

    /**
     * Repositorio del Twin
     */
    private final TwinRepository twinRepository;

    /**
     * Repositorio del User
     */
    private final UserRepository userRepository;

    /**
     * Construcción del PicTwinImplementation
     *
     * @param picRepository  para usar
     * @param twinRepository para usar
     * @param userRepository para usar
     */
    @Autowired
    public PicTwinImpl(PicRepository picRepository, TwinRepository twinRepository, UserRepository userRepository) {
        this.picRepository = picRepository;
        this.twinRepository = twinRepository;
        this.userRepository = userRepository;
    }

    /**
     * Se crea un usuario con una contraseña especifica
     *
     * @param user     para usar
     * @param password to hash.
     * @return Usuario creado
     */
    @Override
    @Transactional
    public User create(@NonNull User user, @NonNull String password) {
        // Si el usuario realmente existe...
        if (this.userRepository.findOneByEmail(user.getEmail()).isPresent()) {

            throw new IllegalArgumentException("The User with email <" + user.getEmail() + "> it's already in the " + "system");

        }
        // Usando el codificador de contraseña para el hash
        String passwdHash = PASSWORD_ENCODER.encode(password);

        // Reemplazar contraseña con el hash
        user.setPassword(passwdHash);

        // Guardar dentro del repositorio
        return this.userRepository.save(user);

    }

    /**
     * Retorna el usuario con su contraseña y email
     *
     * @param email    para buscar
     * @param password para usar
     * @return Usuario
     */
    @Override
    public User authenticate(@NonNull String email, @NonNull String password) {

        // Encontrar por email
        Optional<User> userOptional = this.userRepository.findOneByEmail(email);
        log.debug("User: {}", userOptional);
        return userOptional.orElseThrow(() -> new RuntimeException("Wrong Credendials or User Not Found"));
    }

    /**
     * Crear un Twin usando una Pic desde el usuario
     *
     * @param pic    para usar
     * @param idUser del creador de la Pic para usarse
     * @return Twin creado
     */

    @Override
    @Transactional
    public Twin createTwin(@NonNull Pic pic, @NonNull Long idUser) {

        // Usuario
        User owner = this.userRepository.findById(idUser).orElseThrow();
        log.debug("Pics: {} in User: {}", owner.getTwins().size(), owner.getEmail());

        // Enviar Usuario
        pic.setOwner(owner);

        // Almacenar Pic
        this.picRepository.save(pic);

        // Obtener todos los Pic
        List<Pic> pics = this.picRepository.findAll();
        log.debug(" Number of Pics in the database: {}", pics.size());

        // Eliminar Pic propios
        List<Pic> picsFiltered = pics.stream().filter(p -> !p.getOwner().getId().equals(idUser)).toList();
        if (picsFiltered.size() == 0) {
            log.warn("Re-using Pics from database.");
            picsFiltered = pics;
        }

        // Seleccionar un Pic random
        // FIXME: Sort by views and select the least used
        Pic your = picsFiltered.size() == 0 ? pic : picsFiltered.get(RANDOM.nextInt(picsFiltered.size()));

        // Aumentar Views
        your.incrementViews();

        // Guardar el incremento
        this.picRepository.save(your);

        // Almacenar Twin
        Twin twin = Twin.builder()
                .my(pic)
                .yours(your)
                .owner(owner)
                .build();

        // Guardar Twin
        this.twinRepository.save(twin);

        // Agregar Twin al usuario
        owner.add(twin);
        this.userRepository.save(owner);

        return twin;

    }

    /**
     * Dar Dislike a Pic en una Twin
     *
     * @param idTwin con dislike
     * @param idUser que dio dislike al Twin
     */
    @Override
    @Transactional
    public void dislike(@NonNull Long idTwin, @NonNull Long idUser) {
        // Recuperar Twin
        Optional<Twin> oTwin = this.twinRepository.findById(idTwin);

        // Verificar que exista
        Twin twin = oTwin.orElseThrow(() -> new RuntimeException("Can't find Twin with id:" + idTwin));

        // Comprobar dueño de la Pic
        if (!idUser.equals(twin.getMy().getOwner().getId())) {
            throw new RuntimeException("Twin id<" + idTwin + "> not owned by User id<" + idUser + ">!");
        }

        // Enviar dislike y guardar
        twin.setDislike(true);
        this.twinRepository.save(twin);

        // Incrementar los dislikes del Twin y guardar
        Pic yours = twin.getYours();
        yours.incrementDislikes();
        this.picRepository.save(yours);

        // Incrementar los Strikes en el usuario y guardar
        User user = yours.getOwner();
        user.incrementStrikes();
        this.userRepository.save(user);
    }

    /**
     * @return números de usuarios en el sistema
    */
    @Override
    public Long getUserSize() {

        return this.userRepository.count();

    }
}