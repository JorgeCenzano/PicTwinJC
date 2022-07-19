package cl.ucn.disc.dsm.pictwin.backendpt.dao;

import cl.ucn.disc.dsm.pictwin.backendpt.model.Pic;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio de Pic
 */

@Repository
public interface PicRepository extends ListCrudRepository<Pic, Long> {

}
