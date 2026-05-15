package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Cita {
    private String id;
    private Paciente paciente;
    private Medico medico;
    private LocalDate fecha;
    private String franjaHoraria;
    private String estado;

    public Cita() {}

    public Cita(Paciente paciente, Medico medico, LocalDate fecha, String franjaHoraria) {
        this.id = java.util.UUID.randomUUID().toString().substring(0, 8);
        this.paciente = paciente;
        this.medico = medico;
        this.fecha = fecha;
        this.franjaHoraria = franjaHoraria;
        this.estado = "pendiente";
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public Paciente getPaciente() { return paciente; }
    public void setPaciente(Paciente paciente) { this.paciente = paciente; }
    public Medico getMedico() { return medico; }
    public void setMedico(Medico medico) { this.medico = medico; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public String getFranjaHoraria() { return franjaHoraria; }
    public void setFranjaHoraria(String franjaHoraria) { this.franjaHoraria = franjaHoraria; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public void completar() { this.estado = "atendida"; }
    public void cancelar() { this.estado = "cancelada"; }

    public String toArchivo() {
        return id + "|" + paciente.getNumeroIdentificacion() + "|" + medico.getNumeroRegistroMedico() + "|" +
               fecha + "|" + franjaHoraria + "|" + estado;
    }

    @Override
    public String toString() {
        return "Cita #" + id + " - Paciente: " + paciente.getNombreCompleto() +
               " | Médico: " + medico.getNombreCompleto() +
               " | Fecha: " + fecha + " (" + franjaHoraria + ") | Estado: " + estado;
    }
}