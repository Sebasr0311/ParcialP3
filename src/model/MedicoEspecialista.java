package model;

public class MedicoEspecialista extends Medico {
    private String especialidad;
    private String numeroConsultorio;

    public MedicoEspecialista() { super(); }

    public MedicoEspecialista(String numeroRegistroMedico, String nombreCompleto, int numeroTelefono,
                              String correoElectronico, String especialidad, String numeroConsultorio) {
        super(numeroRegistroMedico, nombreCompleto, numeroTelefono, correoElectronico);
        this.especialidad = especialidad;
        this.numeroConsultorio = numeroConsultorio;
    }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }
    public String getNumeroConsultorio() { return numeroConsultorio; }
    public void setNumeroConsultorio(String numeroConsultorio) { this.numeroConsultorio = numeroConsultorio; }

    @Override
    public double obtenerTarifa(String franjaHoraria) {
        switch (franjaHoraria.toLowerCase()) {
            case "mañana": case "manana": return 60000;
            case "tarde": return 70000;
            case "noche": return 90000;
            default: return 0;
        }
    }

    @Override
    public String getTipo() { return "Especialista"; }

    @Override
    public String toArchivo() {
        return "especialista|" + numeroRegistroMedico + "|" + nombreCompleto + "|" + numeroTelefono + "|" +
               correoElectronico + "|" + especialidad + "|" + numeroConsultorio;
    }

    @Override
    public String toString() {
        return "Dr. " + nombreCompleto + " (" + especialidad + ") - Registro: " + numeroRegistroMedico +
               " | Consultorio: " + numeroConsultorio;
    }
}