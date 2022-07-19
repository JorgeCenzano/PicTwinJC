package cl.ucn.disc.dsm.pictwin.backendpt.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import java.util.ArrayList;
import java.util.List;

/**
 * Usuario de PicTwin
 */

@Entity
@Table(name = "users")
@Builder
@NoArgsConstructor
@AllArgsConstructor

public final class User {

    /**
     * Id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private Long id;

    /**
     * Email
     */
    @Getter
    @NonNull
    @NotBlank
    @Column(unique = true)
    private String email;

    /**
     * Contraseña
     */
    @Getter
    @Setter
    private String password;

    /**
     * Número de strikes
     */
    @Getter
    private Integer strikes;

    /**
     * Estado
     */
    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Getter
    @Setter
    private State state = State.ACTIVE;

    /**
     * Los Twins
     */
    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    @Builder.Default
    @Getter
    @JsonManagedReference
    private List<Twin> twins = new ArrayList<>();

    /**
     * Incremento de strikes
     *
     * @return Número de strikes
     */
    public Integer incrementStrikes() {

        this.strikes++;
        return this.strikes;

    }

    /**
     * Agregar twin a la lista
     *
     * @param twin to add.
     */
    public void add(final Twin twin) {

        this.twins.add(twin);

    }
}