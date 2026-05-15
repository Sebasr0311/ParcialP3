package model;

public class MedicoGeneral extends Medico {
    private String aniosExperiencia;
    private boolean atiendeUrgencias;

    public MedicoGeneral() { super(); }

    public MedicoGeneral(String numeroRegistroMedico, String nombreCompleto, int numeroTelefono,
                         String correoElectronico, String aniosExperiencia, boolean atiendeUrgencias) {
        super(numeroRegistroMedico, nombreCompleto, numeroTelefono, correoElectronico);
        this.aniosExperiencia = aniosExperiencia;
        this.atiendeUrgencias = atiendeUrgencias;
    }

    public String getAniosExperiencia() { return aniosExperiencia; }
    public void setAniosExperiencia(String aniosExperiencia) { this.aniosExperiencia = aniosExperiencia; }
    public boolean isAtiendeUrgencias() { return atiendeUrgencias; }
    public void setAtiendeUrgencias(boolean atiendeUrgencias) { this.atiendeUrgencias = atiendeUrgencias; }

    @Override
    public double obtenerTarifa(String f) {
        switch (f.toLowerCase()) {
            case "manana": return 25000;
            case "tarde": return 30000;
            case "noche": return 40000;
            default: return 0;
        }
    }

    @Override
    public String getTipo() { return "General"; }

    @Override
    public String toArchivo() {
        return "general|" + numeroRegistroMedico + "|" + nombreCompleto + "|" + numeroTelefono + "|" +
               correoElectronico + "|" + aniosExperiencia + "|" + atiendeUrgencias;
    }

    @Override
    public String toString() {
        return "Dr. " + nombreCompleto + " (General) - Registro: " + numeroRegistroMedico +
               " | Exp: " + aniosExperiencia + " anios | Urgencias: " + (atiendeUrgencias ? "Si" : "No");
    }
}