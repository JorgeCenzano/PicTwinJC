package cl.ucn.disc.dsm.pictwin.backendpt.services;

import cl.ucn.disc.dsm.pictwin.backendpt.model.Pic;
import cl.ucn.disc.dsm.pictwin.backendpt.model.Twin;
import cl.ucn.disc.dsm.pictwin.backendpt.model.User;

/**
  * Operaciones de PicTwin
*/
public interface PicTwin {

    /**
 	* Crea un usuario con una contraseña especifica
 	*
 	* @param user para crear
 	* @param password to hash
 	* @return el usuario creado
 	*/
    User create(User user, String password);

    /**
 	* Retorna el usuario con su email y contraseña
 	*
 	* @param email para buscar
 	* @param password para usar
 	* @return Usuario
 	*/
    User authenticate(String email, String password);

    /**
 	* Crea un Twin usando La Pic desde el usuario
 	*
 	* @param pic	para usar
 	* @param idUser que crea el Pic para usar
 	* @return Twin creado
 	*/
    Twin createTwin(Pic pic, Long idUser);

    /**
 	* Dislike de un Pic, en un Twin
 	*
 	* @param idTwin con dislike
 	* @param idUser del usuario que dio dislike al Twin
 	*/
    void dislike(Long idTwin, Long idUser);

    /**
 	* @return el número de ususarios en el sistema
 	*/
    Long getUserSize();
}
