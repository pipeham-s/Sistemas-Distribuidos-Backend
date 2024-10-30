package com.example.backend_sistemas_distribuidos.business.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "usuarios", uniqueConstraints ={@UniqueConstraint( columnNames = {"correo"})})
@Inheritance(strategy = InheritanceType.JOINED)  // Permite herencia con Alumnos y Administradores
@Getter
@Setter
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario  implements UserDetails {

    @Id
    @Column(unique = true, nullable = false)  // Asegura que el nombre sea único y no nulo
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;
    @Column( nullable = false)  // Asegura que el nombre sea único y no nulo
    @Getter
    @Setter
    private String nombre;
    @Column( nullable = false)  // Asegura que el nombre sea único y no nulo
    @Getter
    private String apellido;
    @Column(unique = true, nullable = false)  // Asegura que el nombre sea único y no nulo
    @Getter
    @Setter
    private String cedula;
    @Column( nullable = false)  // Asegura que el nombre sea único y no nulo
    @Getter
    @Setter
    private String correo;
    @Column( nullable = false)  // Asegura que el nombre sea único y no nulo
    @Getter
    @Setter
    private String password;
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private Role role;

    @ManyToMany(mappedBy = "usuariosProfesores", fetch = FetchType.LAZY)
    @Getter
    @Setter
    private List<Materia> materias;



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
    }

    @Override
    public String getUsername() {return correo;}

    @Override
    public boolean isAccountNonExpired() {return true;}

    @Override
    public boolean isAccountNonLocked() {return true;}

    @Override
    public boolean isCredentialsNonExpired() {return true;}

    @Override
    public boolean isEnabled() {return true;}

    public void agregarMateria(Materia materia) {
        if (this.materias == null) {
            this.materias = new ArrayList<>();
        }
        if (!this.materias.contains(materia)) {
            this.materias.add(materia);
            materia.getUsuariosProfesores().add(this);
        }
    }
}

