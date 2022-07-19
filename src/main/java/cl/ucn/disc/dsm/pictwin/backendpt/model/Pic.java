package cl.ucn.disc.dsm.pictwin.backendpt.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

/**
    * Imagen Pic
*/

@Entity
@Table(name = "pics")
@Builder // Replace the constructor
@NoArgsConstructor
@AllArgsConstructor

public final class Pic {

    /**
 	    * Id
 	*/
    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	private Long id;

    /**
 	    * Momento que se guarda la Pic
     */
    @Getter
	@Builder.Default
	private ZonedDateTime timestamp = ZonedDateTime.now();

    /**
     	* Dislikes
     */
    @Getter
	@Builder.Default
	private Integer dislikes = 0;

    /**
     	* Latitud
     */
    @Getter
	private Double latitude;
    /**
     	* Longitud
     */
    @Getter
	private Double longitude;
    /**
     	* El error
     */
    @Getter
	private Double error;

    /**
 	    * Views
 	*/
    @Getter
	@Builder.Default
	private Integer views = 0;
    /**
     	* Nombre
     */
    @Getter
	private String name;

    /**
 	    * Imagen
 	*/
    @Getter
	private byte[] picture;

    /**
     	* Dueño
     */
    @Getter
	@Setter
	@ManyToOne(optional = false)
	@JsonBackReference
	private User owner;

    /**
        * Aumenta en uno los dislikes
        *
        * @return número de dislikes
 	*/
    public Integer incrementDislikes() {
        this.dislikes++;
        return this.dislikes;
    }

    /**
         *	Aumenta en uno las views
         *
         *	@return número de views
     */
    public Integer incrementViews() {
        this.views++;
        return this.views;

    }

}
