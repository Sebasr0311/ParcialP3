package model;

import java.time.LocalDate;

public class PacienteAdultoMayor extends Paciente {
    private String nombreCuidador;
    private boolean requiereAtencionDomiciliaria;

    public PacienteAdultoMayor() { super(); }

    public PacienteAdultoMayor(String numeroIdentificacion, String nombreCompleto, LocalDate fechaNacimiento,
                                int numeroTelefono, String eps, String nombreCuidador, boolean requiereAtencionDomiciliaria) {
        super(numeroIdentificacion, nombreCompleto, fechaNacimiento, numeroTelefono, eps);
        this.nombreCuidador = nombreCuidador;
        this.requiereAtencionDomiciliaria = requiereAtencionDomiciliaria;
    }

    public String getNombreCuidador() { return nombreCuidador; }
    public void setNombreCuidador(String nombreCuidador) { this.nombreCuidador = nombreCuidador; }
    public boolean isRequiereAtencionDomiciliaria() { return requiereAtencionDomiciliaria; }
    public void setRequiereAtencionDomiciliaria(boolean requiereAtencionDomiciliaria) { this.requiereAtencionDomiciliaria = requiereAtencionDomiciliaria; }

    @Override
    public double getDescuento() { return 0.30; }

    @Override
    public String getTipoPaciente() { return "Adulto Mayor"; }

    @Override
    public String toArchivo() {
        return "adulto_mayor|" + numeroIdentificacion + "|" + nombreCompleto + "|" + fechaNacimiento + "|" +
               numeroTelefono + "|" + eps + "|" + nombreCuidador + "|" + requiereAtencionDomiciliaria;
    }

    @Override
    public String toString() {
        return "Paciente: " + nombreCompleto + " (Adulto Mayor) - ID: " + numeroIdentificacion +
               " | Cuidador: " + nombreCuidador;
    }
}