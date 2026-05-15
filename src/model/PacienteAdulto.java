package model;

import java.time.LocalDate;

public class PacienteAdulto extends Paciente {
    private String ocupacion;
    private boolean tieneSeguroPrivado;

    public PacienteAdulto() { super(); }

    public PacienteAdulto(String numeroIdentificacion, String nombreCompleto, LocalDate fechaNacimiento,
                          int numeroTelefono, String eps, String ocupacion, boolean tieneSeguroPrivado) {
        super(numeroIdentificacion, nombreCompleto, fechaNacimiento, numeroTelefono, eps);
        this.ocupacion = ocupacion;
        this.tieneSeguroPrivado = tieneSeguroPrivado;
    }

    public String getOcupacion() { return ocupacion; }
    public void setOcupacion(String ocupacion) { this.ocupacion = ocupacion; }
    public boolean isTieneSeguroPrivado() { return tieneSeguroPrivado; }
    public void setTieneSeguroPrivado(boolean tieneSeguroPrivado) { this.tieneSeguroPrivado = tieneSeguroPrivado; }

    @Override
    public double getDescuento() { return 0.0; }

    @Override
    public String getTipoPaciente() { return "Adulto"; }

    @Override
    public String toArchivo() {
        return "adulto|" + numeroIdentificacion + "|" + nombreCompleto + "|" + fechaNacimiento + "|" +
               numeroTelefono + "|" + eps + "|" + ocupacion + "|" + tieneSeguroPrivado;
    }

    @Override
    public String toString() {
        return "Paciente: " + nombreCompleto + " (Adulto) - ID: " + numeroIdentificacion +
               " | Ocupación: " + ocupacion;
    }
}