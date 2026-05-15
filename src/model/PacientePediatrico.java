package model;

import java.time.LocalDate;

public class PacientePediatrico extends Paciente {
    private String nombreAcudiente;
    private String parentesco;

    public PacientePediatrico() { super(); }

    public PacientePediatrico(String numeroIdentificacion, String nombreCompleto, LocalDate fechaNacimiento,
                               int numeroTelefono, String eps, String nombreAcudiente, String parentesco) {
        super(numeroIdentificacion, nombreCompleto, fechaNacimiento, numeroTelefono, eps);
        this.nombreAcudiente = nombreAcudiente;
        this.parentesco = parentesco;
    }

    public String getNombreAcudiente() { return nombreAcudiente; }
    public void setNombreAcudiente(String nombreAcudiente) { this.nombreAcudiente = nombreAcudiente; }
    public String getParentesco() { return parentesco; }
    public void setParentesco(String parentesco) { this.parentesco = parentesco; }

    @Override
    public double getDescuento() { return 0.25; }

    @Override
    public String getTipoPaciente() { return "Pediátrico"; }

    @Override
    public String toArchivo() {
        return "pediatrico|" + numeroIdentificacion + "|" + nombreCompleto + "|" + fechaNacimiento + "|" +
               numeroTelefono + "|" + eps + "|" + nombreAcudiente + "|" + parentesco;
    }

    @Override
    public String toString() {
        return "Paciente: " + nombreCompleto + " (Pediátrico) - ID: " + numeroIdentificacion +
               " | Acudiente: " + nombreAcudiente + " (" + parentesco + ")";
    }
}