package cl.ucn.disc.dsm.pictwin.backendpt.dao;

import cl.ucn.disc.dsm.pictwin.backendpt.model.Twin;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de Twin
 */

@Repository
public interface TwinRepository extends ListCrudRepository<Twin, Long> {

}

