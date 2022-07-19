package cl.ucn.disc.dsm.pictwin.backendpt.web;

import cl.ucn.disc.dsm.pictwin.backendpt.Utils;
import cl.ucn.disc.dsm.pictwin.backendpt.model.Pic;
import cl.ucn.disc.dsm.pictwin.backendpt.model.Twin;
import cl.ucn.disc.dsm.pictwin.backendpt.model.User;
import cl.ucn.disc.dsm.pictwin.backendpt.services.PicTwin;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

/**
  * Fivet Controller.
*/
@RestController
 @Slf4j
 public class PicTwinRestController {

        /**
         * Servicio PicTwin
         */
        private final PicTwin picTwin;

        /**
         * Constructor
         *
         * @param picTwin el servicio
         */
        @Autowired
        public PicTwinRestController(PicTwin picTwin) {
                this.picTwin = picTwin;
        }

        /**
         * Crear usuario
         *
         * @param user para crear
         * @param password para usar
         * @return usuario
         */
        @RequestMapping(value = {"/users", "/users/"}, method = RequestMethod.POST)
        public User create(@Valid @RequestBody User user, @RequestParam String password) {

                // Debug
                Utils.printObject("User", user);

                // Llamar al controller
                return this.picTwin.create(user, password);
        }
         /**
        * Atenticar al usuario
        *
        * @param email para usar
        * @param password para usar
        * @return Usuario
         */
        @RequestMapping(value = {"/users", "/users/"}, method = RequestMethod.GET)
        public User authenticate(@RequestParam String email, @RequestParam String password) {

                return this.picTwin.authenticate(email, password);

        }

         /**
        * Crear Twin
        *
        * @param pic para usar
        * @param idUser para usar
        * @return Twin
        */
        @RequestMapping(value = {"/twins", "/twins/"}, method = RequestMethod.POST)
        public Twin createTwin(@Valid @RequestBody Pic pic, @RequestParam Long idUser) {

                return this.picTwin.createTwin(pic, idUser);

        }

         /**
         * Dislike al Twin
        *
        * @param idTwin para usar
        * @param idUser para usar
        */
        @RequestMapping(value = {"/twins", "/twins/"}, method = RequestMethod.PATCH)
        public void dislike(@RequestParam Long idTwin, @RequestParam Long idUser) {
                 this.picTwin.dislike(idTwin, idUser);

        }

         /**
        * Mostrar un mensaje en {@link MethodArgumentNotValidException}
        *
        * @param ex to catch.
        * @return Mapeo de errores
         */
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(MethodArgumentNotValidException.class)
        public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
                 Map<String, String> errors = new HashMap<>();
                 ex.getBindingResult().getAllErrors().forEach((error) -> {
                         String fieldName = ((FieldError) error).getField();
                         String errorMessage = error.getDefaultMessage();
                         errors.put(fieldName, errorMessage);

                 });
                 return errors;

        }

        /**
         * Mostrar un mensaje en {@link IllegalArgumentException}
         *
         * @param ex to catch.
         * @return el mensaje
         */
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ExceptionHandler(IllegalArgumentException.class)
        public String handleValidationExceptions(IllegalArgumentException ex) {
                return ex.getMessage();
        }
}