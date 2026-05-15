package model;

import java.time.LocalDate;

public abstract class Paciente {
    protected String numeroIdentificacion;
    protected String nombreCompleto;
    protected LocalDate fechaNacimiento;
    protected int numeroTelefono;
    protected String eps;

    public Paciente() {}

    public Paciente(String numeroIdentificacion, String nombreCompleto, LocalDate fechaNacimiento,
                    int numeroTelefono, String eps) {
        this.numeroIdentificacion = numeroIdentificacion;
        this.nombreCompleto = nombreCompleto;
        this.fechaNacimiento = fechaNacimiento;
        this.numeroTelefono = numeroTelefono;
        this.eps = eps;
    }

    public String getNumeroIdentificacion() { return numeroIdentificacion; }
    public void setNumeroIdentificacion(String numeroIdentificacion) { this.numeroIdentificacion = numeroIdentificacion; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }
    public int getNumeroTelefono() { return numeroTelefono; }
    public void setNumeroTelefono(int numeroTelefono) { this.numeroTelefono = numeroTelefono; }
    public String getEps() { return eps; }
    public void setEps(String eps) { this.eps = eps; }

    public int getEdad() {
        return LocalDate.now().getYear() - fechaNacimiento.getYear();
    }

    public abstract double getDescuento();
    public abstract String getTipoPaciente();
    public abstract String toArchivo();

    public static Paciente desdeArchivo(String tipo, String[] datos) {
        LocalDate fecha = LocalDate.parse(datos[2]);
        if ("pediatrico".equals(tipo)) {
            return new PacientePediatrico(datos[0], datos[1], fecha, Integer.parseInt(datos[3]), datos[4], datos[5], datos[6]);
        } else if ("adulto".equals(tipo)) {
            return new PacienteAdulto(datos[0], datos[1], fecha, Integer.parseInt(datos[3]), datos[4], datos[5], Boolean.parseBoolean(datos[6]));
        } else {
            return new PacienteAdultoMayor(datos[0], datos[1], fecha, Integer.parseInt(datos[3]), datos[4], datos[5], Boolean.parseBoolean(datos[6]));
        }
    }
}