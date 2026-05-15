package model;

public abstract class Medico {
    protected String numeroRegistroMedico;
    protected String nombreCompleto;
    protected int numeroTelefono;
    protected String correoElectronico;

    public Medico() {}

    public Medico(String numeroRegistroMedico, String nombreCompleto, int numeroTelefono, String correoElectronico) {
        this.numeroRegistroMedico = numeroRegistroMedico;
        this.nombreCompleto = nombreCompleto;
        this.numeroTelefono = numeroTelefono;
        this.correoElectronico = correoElectronico;
    }

    public String getNumeroRegistroMedico() { return numeroRegistroMedico; }
    public void setNumeroRegistroMedico(String numeroRegistroMedico) { this.numeroRegistroMedico = numeroRegistroMedico; }
    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }
    public int getNumeroTelefono() { return numeroTelefono; }
    public void setNumeroTelefono(int numeroTelefono) { this.numeroTelefono = numeroTelefono; }
    public String getCorreoElectronico() { return correoElectronico; }
    public void setCorreoElectronico(String correoElectronico) { this.correoElectronico = correoElectronico; }

    public abstract double obtenerTarifa(String franjaHoraria);
    public abstract String getTipo();
    public abstract String toArchivo();

    public static Medico desdeArchivo(String tipo, String[] datos) {
        if ("general".equals(tipo)) {
            return new MedicoGeneral(datos[0], datos[1], Integer.parseInt(datos[2]), datos[3], datos[4], Boolean.parseBoolean(datos[5]));
        } else {
            return new MedicoEspecialista(datos[0], datos[1], Integer.parseInt(datos[2]), datos[3], datos[4], datos[5]);
        }
    }
}