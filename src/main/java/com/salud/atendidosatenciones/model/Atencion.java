package com.salud.atendidosatenciones.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "atenciones")
@Data
public class Atencion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El año de atención es obligatorio")
    private Integer anio;

    @NotNull(message = "El mes de atención es obligatorio")
    private Integer mes;

    @NotNull(message = "El día de atención es obligatorio")
    private Integer dia;

    @NotNull(message = "La categoría es obligatoria")
    private String categoria;

    @NotNull(message = "La red es obligatoria")
    private String red;

    @NotNull(message = "La microred es obligatoria")
    private String microred;

    @NotNull(message = "El código único es obligatorio")
    private Long codigoUnico;

    @NotNull(message = "El nombre del establecimiento es obligatorio")
    private String nombreEstablecimiento;

    @NotNull(message = "La provincia es obligatoria")
    private String provincia;

    @NotNull(message = "El distrito es obligatorio")
    private String distrito;

    @NotNull(message = "El EDA Reg es obligatorio")
    private Integer edaReg;

    @NotNull(message = "El tipo EDA es obligatorio (S: meses, M: años)")
    private String tipoEda;   // S o M

    @NotNull(message = "El sexo es obligatorio (F/M)")
    private String sexo;

    private String etnia;

    private String tipoSegur;

    private String ups;       // Unidad Productora de Servicios

    // Cantidad de personas atendidas
    private Integer atendidos;

    // Cantidad de consultas
    private Integer consulta;

    private String fechaCierre;
}
