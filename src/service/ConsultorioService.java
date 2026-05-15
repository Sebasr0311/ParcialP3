package service;

import dao.*;
import model.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class ConsultorioService {
    private List<Medico> medicos;
    private List<Paciente> pacientes;
    private List<Cita> citas;
    private List<Recibo> recibos;

    private MedicoDAO medicoDAO;
    private PacienteDAO pacienteDAO;
    private CitaDAO citaDAO;
    private ReciboDAO reciboDAO;

    public ConsultorioService() {
        this.medicoDAO = new MedicoDAO();
        this.pacienteDAO = new PacienteDAO();
        this.citaDAO = new CitaDAO();
        this.reciboDAO = new ReciboDAO();
        cargarDatos();
    }

    private void cargarDatos() {
        medicos = medicoDAO.cargarMedicos();
        pacientes = pacienteDAO.cargarPacientes();
        citas = citaDAO.cargarCitas();
        recibos = new ArrayList<>();
    }

    public void registrarMedicoGeneral(String registro, String nombre, int telefono, String email,
                                        String experiencia, boolean atiendeUrgencias) {
        Medico medico = new MedicoGeneral(registro, nombre, telefono, email, experiencia, atiendeUrgencias);
        medicos.add(medico);
        medicoDAO.guardarMedico(medico);
    }

    public void registrarMedicoEspecialista(String registro, String nombre, int telefono, String email,
                                            String especialidad, String consultorio) {
        Medico medico = new MedicoEspecialista(registro, nombre, telefono, email, especialidad, consultorio);
        medicos.add(medico);
        medicoDAO.guardarMedico(medico);
    }

    public void registrarPacientePediatrico(String id, String nombre, LocalDate fechaNacimiento,
                                            int telefono, String eps, String acudiente, String parentesco) {
        Paciente paciente = new PacientePediatrico(id, nombre, fechaNacimiento, telefono, eps, acudiente, parentesco);
        pacientes.add(paciente);
        pacienteDAO.guardarPaciente(paciente);
    }

    public void registrarPacienteAdulto(String id, String nombre, LocalDate fechaNacimiento,
                                        int telefono, String eps, String ocupacion, boolean tieneSeguro) {
        Paciente paciente = new PacienteAdulto(id, nombre, fechaNacimiento, telefono, eps, ocupacion, tieneSeguro);
        pacientes.add(paciente);
        pacienteDAO.guardarPaciente(paciente);
    }

    public void registrarPacienteAdultoMayor(String id, String nombre, LocalDate fechaNacimiento,
                                              int telefono, String eps, String cuidador, boolean requiereDomicilio) {
        Paciente paciente = new PacienteAdultoMayor(id, nombre, fechaNacimiento, telefono, eps, cuidador, requiereDomicilio);
        pacientes.add(paciente);
        pacienteDAO.guardarPaciente(paciente);
    }

    public String[] registrarCita(String idPaciente, String registroMedico, LocalDate fecha, String franja) {
        String[] resultado = new String[2];
        resultado[0] = "false";

        if (!esFranjaValida(franja)) {
            resultado[1] = "Franja horaria invalida. Use: manana, tarde o noche";
            return resultado;
        }

        Paciente paciente = buscarPaciente(idPaciente);
        if (paciente == null) {
            resultado[1] = "Paciente no encontrado";
            return resultado;
        }

        Medico medico = buscarMedico(registroMedico);
        if (medico == null) {
            resultado[1] = "Medico no encontrado";
            return resultado;
        }

        if (citasYaExistente(paciente, medico, fecha)) {
            resultado[1] = "El paciente ya tiene una cita con este medico hoy";
            return resultado;
        }

        if (contarCitasEnFranja(medico, fecha, franja) >= 8) {
            resultado[1] = "Franja llena para este medico. Sugerencia: otra franquicia o medico disponible";
            return resultado;
        }

        Cita cita = new Cita(paciente, medico, fecha, franja);
        citas.add(cita);
        citaDAO.guardarCita(cita);
        resultado[0] = "true";
        resultado[1] = "Cita registrada exitosamente: " + cita.getId();
        return resultado;
    }

    private boolean esFranjaValida(String franja) {
        return franja.equalsIgnoreCase("manana") || franja.equalsIgnoreCase("tarde") || franja.equalsIgnoreCase("noche");
    }

    private Paciente buscarPaciente(String id) {
        return pacientes.stream().filter(p -> p.getNumeroIdentificacion().equals(id)).findFirst().orElse(null);
    }

    private Medico buscarMedico(String registro) {
        return medicos.stream().filter(m -> m.getNumeroRegistroMedico().equals(registro)).findFirst().orElse(null);
    }

    private boolean citasYaExistente(Paciente paciente, Medico medico, LocalDate fecha) {
        return citas.stream().anyMatch(c -> c.getPaciente().equals(paciente) &&
                                              c.getMedico().equals(medico) &&
                                              c.getFecha().equals(fecha) &&
                                              c.getEstado().equals("pendiente"));
    }

    private int contarCitasEnFranja(Medico medico, LocalDate fecha, String franja) {
        return (int) citas.stream().filter(c -> c.getMedico().equals(medico) &&
                                                 c.getFecha().equals(fecha) &&
                                                 c.getFranjaHoraria().equalsIgnoreCase(franja) &&
                                                 c.getEstado().equals("pendiente")).count();
    }

    public String completarCita(String idCita) {
        Optional<Cita> citaOpt = citas.stream().filter(c -> c.getId().equals(idCita) && c.getEstado().equals("pendiente")).findFirst();
        if (citaOpt.isEmpty()) {
            return "Cita no encontrada o ya completada";
        }

        Cita cita = citaOpt.get();
        cita.completar();

        Recibo recibo = new Recibo(cita);
        recibos.add(recibo);
        reciboDAO.guardarRecibo(recibo);

        citaDAO.actualizarCitas(citas);
        return recibo.generarRecibo();
    }

    public String generarReporteDiario() {
        StringBuilder sb = new StringBuilder();
        LocalDate hoy = LocalDate.now();

        List<Cita> citasHoy = citas.stream().filter(c -> c.getFecha().equals(hoy) && c.getEstado().equals("atendida")).collect(Collectors.toList());

        sb.append("\n============================================================\n");
        sb.append("                    REPORTE DIARIO - MEDISALUD\n");
        sb.append("                        Fecha: ").append(hoy).append("\n");
        sb.append("============================================================\n\n");

        sb.append("CANTIDAD DE CITAS ATENDIDAS: ").append(citasHoy.size()).append("\n\n");

        sb.append("PACIENTES ATENDIDOS:\n");
        sb.append("-------------------------------------------------------------\n");
        for (Cita c : citasHoy) {
            sb.append("  - ").append(c.getPaciente().getNombreCompleto())
              .append(" | Dr. ").append(c.getMedico().getNombreCompleto())
              .append(" (").append(c.getFranjaHoraria()).append(")\n");
        }

        sb.append("\nDISPONIBILIDAD DE MEDICOS POR FRANJA:\n");
        sb.append("-------------------------------------------------------------\n");
        for (Medico m : medicos) {
            sb.append("Dr. ").append(m.getNombreCompleto()).append(" (").append(m.getTipo()).append(")\n");
            for (String franja : new String[]{"manana", "tarde", "noche"}) {
                int disponibles = 8 - contarCitasEnFranja(m, hoy, franja);
                sb.append("   ").append(franja.substring(0, 1).toUpperCase() + franja.substring(1)).append(": ").append(disponibles).append(" cupos disponibles\n");
            }
        }

        double totalRecaudado = recibos.stream().mapToDouble(Recibo::getValorFinal).sum();
        sb.append("\nTOTAL RECAUDADO: $").append(String.format("%.2f", totalRecaudado)).append("\n");

        sb.append("\nDESGLOSE POR TIPO DE PACIENTE:\n");
        double pediatrico = citasHoy.stream().filter(c -> c.getPaciente() instanceof PacientePediatrico)
                                    .mapToDouble(c -> c.getMedico().obtenerTarifa(c.getFranjaHoraria())).sum();
        double adulto = citasHoy.stream().filter(c -> c.getPaciente() instanceof PacienteAdulto)
                                 .mapToDouble(c -> c.getMedico().obtenerTarifa(c.getFranjaHoraria())).sum();
        double mayor = citasHoy.stream().filter(c -> c.getPaciente() instanceof PacienteAdultoMayor)
                                .mapToDouble(c -> c.getMedico().obtenerTarifa(c.getFranjaHoraria())).sum();
        sb.append("  Pacientes Pediatricos: $").append(String.format("%.2f", pediatrico)).append("\n");
        sb.append("  Pacientes Adultos: $").append(String.format("%.2f", adulto)).append("\n");
        sb.append("  Pacientes Adultos Mayores: $").append(String.format("%.2f", mayor)).append("\n");

        long general = citasHoy.stream().filter(c -> c.getMedico().getTipo().equals("General")).count();
        long especialista = citasHoy.stream().filter(c -> c.getMedico().getTipo().equals("Especialista")).count();
        sb.append("\nCONSULTAS POR TIPO DE MEDICO:\n");
        sb.append("  Medicos Generales: ").append(general).append("\n");
        sb.append("  Medicos Especialistas: ").append(especialista).append("\n");

        sb.append("\n============================================================\n");
        exportarReporteAArchivo(sb.toString());
        return sb.toString();
    }

    private void exportarReporteAArchivo(String reporte) {
        try (FileWriter fw = new FileWriter("reporte.txt");
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(reporte);
        } catch (IOException e) {
            System.out.println("Error al exportar reporte: " + e.getMessage());
        }
    }

    public List<Medico> getMedicos() { return medicos; }
    public List<Paciente> getPacientes() { return pacientes; }
    public List<Cita> getCitas() { return citas; }

    public List<Cita> getCitasPendientes() {
        return citas.stream().filter(c -> c.getEstado().equals("pendiente")).collect(Collectors.toList());
    }
}