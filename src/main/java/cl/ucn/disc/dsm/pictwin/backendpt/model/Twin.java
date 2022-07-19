package cl.ucn.disc.dsm.pictwin.backendpt.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Twin emparejada con la Pic
 */

@Entity
@Table(name = "twins")
@Builder
@NoArgsConstructor
@AllArgsConstructor

public final class Twin {

	/**
 	* Id
 	*/
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Getter
	private Long id;

	/**
	 * Dislike
	 */
	@Getter
	@Setter
	@Builder.Default
	private Boolean dislike = Boolean.FALSE;

	/**
	 * Pic
	 */
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@Getter
	private Pic my;

	/**
	 * Pic
	 */
	@ManyToOne(optional = false, fetch = FetchType.EAGER)
	@Getter
	private Pic yours;

	/**
	 * Dueño
	 */
	@ManyToOne(optional = false, fetch=FetchType.EAGER) @Getter
	@JsonBackReference
	private User owner;

}
