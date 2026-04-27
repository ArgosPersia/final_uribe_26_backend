package com.example.API.analitica.modelos;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "usuarios")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre completo del usuario
    @Column(nullable = false)
    private String nombre;

    // Correo electronico - unico por usuario
    @Column(nullable = false, unique = true)
    private String correo;

    // Contrasena (en un proyecto real iria encriptada con BCrypt)
    @Column(nullable = false)
    private String password;

    // Token de recuperacion de contrasena (se genera al solicitar reset)
    @Column
    private String tokenRecuperacion;
}
