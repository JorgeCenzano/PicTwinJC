package cl.ucn.disc.dsm.pictwin.backendpt.dao;

import cl.ucn.disc.dsm.pictwin.backendpt.Utils;
import cl.ucn.disc.dsm.pictwin.backendpt.model.Pic;
import cl.ucn.disc.dsm.pictwin.backendpt.model.Twin;
import cl.ucn.disc.dsm.pictwin.backendpt.model.User;
import cl.ucn.disc.dsm.pictwin.backendpt.services.PicTwin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
  * El Database Loader
 */
@Slf4j
@Component
public class DatabaseLoader implements CommandLineRunner {

	/**
 	* Implementación de PicTwin
 	*/
	private final PicTwin picTwin;

	/**
 	* Constructor
 	* @param picTwin para usar
 	*/
	public DatabaseLoader(@Autowired PicTwin picTwin) {
		this.picTwin = picTwin;
	}

	/**
 	* Callback usada para correr el "bean".
 	*
 	* @param args entrada de los method arguments del main
 	* @throws Exception por error
 	*/
	@Override
	public void run(String... args) throws Exception {
		log.info("Database DataLoader: Starting seeder ..");
        // Verificar si existen datos en la base de datos
        if (this.picTwin.getUserSize() != 0) {
           	log.info("Database already seeded, skipping!");
           	return;
		}

		log.warn("No data found in database, seeding the database ..");

		// El usuario main
		User user = User.builder()
				.email("admin@ucn.cl")
				.password("admin123")
				.strikes(0)
				.build();
		Utils.printObject("User to create:", user);

		// Almacenamiento del usuario
		this.picTwin.create(user, "admin123");
		Utils.printObject("User created:", user);

		// Crear el primer Twin
		Twin twin1 = this.picTwin.createTwin(Pic.builder()
				.name("The first Pic: UCN")
				.latitude(-23.6803026)
				.longitude(-70.4121427)
				.error(3.5)
				.owner(user)
				.build(), user.getId());
		Utils.printObject("Twin1 created:", twin1);

		// Crear el segundo Twin
		Twin twin2 = this.picTwin.createTwin(Pic.builder()
				.name("The second Pic: Parque de los Eventos")
				.latitude(-23.6281221)
				.longitude(-70.3952909)
				.error(5.7)
				.owner(user)
				.build(), user.getId());
		Utils.printObject("Twin2 created:", twin2);

		// Crear el tercer Twin
		Twin twin3 = this.picTwin.createTwin(Pic.builder()
				.name("The third Pic: Quebrada Carrizo")
				.latitude(-23.6977891)
				.longitude(-70.4105903)
				.error(5.7)
				.owner(user)
				.build(), user.getId());
		Utils.printObject("Twin3 created", twin3);

		// Crear el cuarto Twin
		Twin twin4 = this.picTwin.createTwin(Pic.builder()
				.name("The fourth Pic: Balneario Juan Lopez")
				.latitude(-23.5114433)
				.longitude(-70.5218646)
				.error(0.3)
				.owner(user)
				.build(), user.getId());
		Utils.printObject("Twin4", twin4);

		log.info("Database Data Loader: Done.");
	}

}


