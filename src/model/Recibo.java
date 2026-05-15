package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Recibo {
    private String id;
    private Cita cita;
    private double tarifaBase;
    private double porcentajeDescuento;
    private double valorDescuento;
    private double valorFinal;
    private LocalDateTime fechaGeneracion;

    public Recibo() {}

    public Recibo(Cita cita) {
        this.id = java.util.UUID.randomUUID().toString().substring(0, 8);
        this.cita = cita;
        this.tarifaBase = cita.getMedico().obtenerTarifa(cita.getFranjaHoraria());
        this.porcentajeDescuento = cita.getPaciente().getDescuento() * 100;
        this.valorDescuento = tarifaBase * cita.getPaciente().getDescuento();
        this.valorFinal = tarifaBase - valorDescuento;
        this.fechaGeneracion = LocalDateTime.now();
    }

    public String getId() { return id; }
    public Cita getCita() { return cita; }
    public double getTarifaBase() { return tarifaBase; }
    public double getPorcentajeDescuento() { return porcentajeDescuento; }
    public double getValorDescuento() { return valorDescuento; }
    public double getValorFinal() { return valorFinal; }
    public LocalDateTime getFechaGeneracion() { return fechaGeneracion; }

    public String generarRecibo() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n═══════════════════════════════════════════════════════════════\n");
        sb.append("                    RECIBO DE ATENCIÓN MÉDICA\n");
        sb.append("                        MEDISALUD\n");
        sb.append("═══════════════════════════════════════════════════════════════\n\n");
        sb.append("DATOS DEL PACIENTE:\n");
        sb.append("  Nombre: ").append(cita.getPaciente().getNombreCompleto()).append("\n");
        sb.append("  Tipo: ").append(cita.getPaciente().getTipoPaciente()).append("\n");
        sb.append("  Identificación: ").append(cita.getPaciente().getNumeroIdentificacion()).append("\n");
        sb.append("  EPS: ").append(cita.getPaciente().getEps()).append("\n\n");
        sb.append("DATOS DEL MÉDICO:\n");
        sb.append("  Nombre: Dr. ").append(cita.getMedico().getNombreCompleto()).append("\n");
        sb.append("  Tipo: ").append(cita.getMedico().getTipo()).append("\n");
        sb.append("  Registro: ").append(cita.getMedico().getNumeroRegistroMedico()).append("\n\n");
        sb.append("DETALLE DE LA CONSULTA:\n");
        sb.append("  Fecha: ").append(cita.getFecha()).append("\n");
        sb.append("  Franja Horaria: ").append(cita.getFranjaHoraria()).append("\n\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("  TARIFA BASE:............$").append(String.format("%.2f", tarifaBase)).append("\n");
        sb.append("  DESCUENTO (").append(String.format("%.0f", porcentajeDescuento)).append("%):.........-$").append(String.format("%.2f", valorDescuento)).append("\n");
        sb.append("━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━\n");
        sb.append("  TOTAL A PAGAR:.........$").append(String.format("%.2f", valorFinal)).append("\n");
        sb.append("═══════════════════════════════════════════════════════════════\n");
        sb.append("  Fecha de emisión: ").append(fechaGeneracion.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))).append("\n");
        sb.append("  Recibo #: ").append(id).append("\n");
        sb.append("═══════════════════════════════════════════════════════════════\n");
        return sb.toString();
    }

    public String toArchivo() {
        return id + "|" + cita.toArchivo() + "|" + tarifaBase + "|" + porcentajeDescuento + "|" +
               valorDescuento + "|" + valorFinal + "|" + fechaGeneracion;
    }
}