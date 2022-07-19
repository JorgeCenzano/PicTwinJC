package cl.ucn.disc.dsm.pictwin.backendpt.dao;

import cl.ucn.disc.dsm.pictwin.backendpt.model.User;
import lombok.NonNull;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
    * Repositorio de Users
*/

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {

    /**
     *	Retorna el usuario con el email
     *	@param email a usar
     *	@return the Optional of User
     */
    Optional<User> findOneByEmail(@NonNull String email);

}

