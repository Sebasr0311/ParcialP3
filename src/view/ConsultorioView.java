package view;

import service.ConsultorioService;
import model.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class ConsultorioView {
    private ConsultorioService service;
    private Scanner scanner;

    public ConsultorioView() {
        this.service = new ConsultorioService();
        this.scanner = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion;
        do {
            mostrarMenu();
            opcion = scanner.nextInt();
            scanner.nextLine();
            switch (opcion) {
                case 1: menuRegistrarMedico(); break;
                case 2: menuRegistrarPaciente(); break;
                case 3: menuRegistrarCita(); break;
                case 4: menuCompletarCita(); break;
                case 5: mostrarReporte(); break;
                case 6: listarMedicos(); break;
                case 7: listarPacientes(); break;
                case 0: System.out.println("Saliendo del sistema..."); break;
                default: System.out.println("Opcion invalida");
            }
        } while (opcion != 0);
    }

    private void mostrarMenu() {
        System.out.println("\n================== SISTEMA MEDISALUD ==================");
        System.out.println("1. Registrar Medico");
        System.out.println("2. Registrar Paciente");
        System.out.println("3. Registrar Cita");
        System.out.println("4. Completar Cita (generar recibo)");
        System.out.println("5. Generar Reporte Diario");
        System.out.println("6. Listar Medicos");
        System.out.println("7. Listar Pacientes");
        System.out.println("0. Salir");
        System.out.print("Seleccione una opcion: ");
    }

    private void menuRegistrarMedico() {
        System.out.println("\n--- REGISTRAR MEDICO ---");
        System.out.println("1. Medico General");
        System.out.println("2. Medico Especialista");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Numero de Registro: ");
        String registro = scanner.nextLine();
        System.out.print("Nombre Completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Telefono: ");
        int telefono = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Correo Electronico: ");
        String email = scanner.nextLine();

        if (tipo == 1) {
            System.out.print("Anios de Experiencia: ");
            String experiencia = scanner.nextLine();
            System.out.print("Atiende Urgencias (s/n): ");
            boolean urgencias = scanner.nextLine().equalsIgnoreCase("s");
            service.registrarMedicoGeneral(registro, nombre, telefono, email, experiencia, urgencias);
            System.out.println("Medico General registrado exitosamente!");
        } else {
            System.out.print("Especialidad: ");
            String especialidad = scanner.nextLine();
            System.out.print("Numero de Consultorio: ");
            String consultorio = scanner.nextLine();
            service.registrarMedicoEspecialista(registro, nombre, telefono, email, especialidad, consultorio);
            System.out.println("Medico Especialista registrado exitosamente!");
        }
    }

    private void menuRegistrarPaciente() {
        System.out.println("\n--- REGISTRAR PACIENTE ---");
        System.out.println("1. Paciente Pediatricos (menor de 14 anos)");
        System.out.println("2. Paciente Adulto (14-59 anos)");
        System.out.println("3. Paciente Adulto Mayor (60 anos en adelante)");
        int tipo = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Numero de Identificacion: ");
        String id = scanner.nextLine();
        System.out.print("Nombre Completo: ");
        String nombre = scanner.nextLine();
        System.out.print("Fecha de Nacimiento (yyyy-mm-dd): ");
        LocalDate fecha = LocalDate.parse(scanner.nextLine());
        System.out.print("Telefono: ");
        int telefono = scanner.nextInt();
        scanner.nextLine();
        System.out.print("EPS: ");
        String eps = scanner.nextLine();

        if (tipo == 1) {
            System.out.print("Nombre del Acudiente: ");
            String acudiente = scanner.nextLine();
            System.out.print("Parentesco: ");
            String parentesco = scanner.nextLine();
            service.registrarPacientePediatrico(id, nombre, fecha, telefono, eps, acudiente, parentesco);
            System.out.println("Paciente Pediatricos registrado con 25% de descuento!");
        } else if (tipo == 2) {
            System.out.print("Ocupacion: ");
            String ocupacion = scanner.nextLine();
            System.out.print("Tiene seguro privado (s/n): ");
            boolean seguro = scanner.nextLine().equalsIgnoreCase("s");
            service.registrarPacienteAdulto(id, nombre, fecha, telefono, eps, ocupacion, seguro);
            System.out.println("Paciente Adulto registrado!");
        } else {
            System.out.print("Nombre del Cuidador: ");
            String cuidador = scanner.nextLine();
            System.out.print("Requiere atencion domiciliaria (s/n): ");
            boolean domiciliaria = scanner.nextLine().equalsIgnoreCase("s");
            service.registrarPacienteAdultoMayor(id, nombre, fecha, telefono, eps, cuidador, domiciliaria);
            System.out.println("Paciente Adulto Mayor registrado con 30% de descuento!");
        }
    }

    private void menuRegistrarCita() {
        System.out.println("\n--- REGISTRAR CITA ---");
        System.out.println("Medicos disponibles:");
        for (Medico m : service.getMedicos()) {
            System.out.println("  - " + m.getNumeroRegistroMedico() + ": " + m.getNombreCompleto() + " (" + m.getTipo() + ")");
        }

        System.out.print("\nNumero de Identificacion del Paciente: ");
        String idPaciente = scanner.nextLine();
        System.out.print("Numero de Registro del Medico: ");
        String registroMedico = scanner.nextLine();
        System.out.print("Fecha (yyyy-mm-dd): ");
        LocalDate fecha = LocalDate.parse(scanner.nextLine());
        System.out.print("Franja Horaria (manana/tarde/noche): ");
        String franca = scanner.nextLine();

        String[] resultado = service.registrarCita(idPaciente, registroMedico, fecha, franca);
        if (resultado[0].equals("true")) {
            System.out.println(resultado[1]);
        } else {
            System.out.println("ERROR: " + resultado[1]);
        }
    }

    private void menuCompletarCita() {
        System.out.println("\n--- COMPLETAR CITA ---");
        System.out.println("Citas pendientes:");
        for (Cita c : service.getCitasPendientes()) {
            System.out.println("  " + c.getId() + ": " + c.getPaciente().getNombreCompleto() +
                               " - Dr. " + c.getMedico().getNombreCompleto());
        }

        System.out.print("\nIngrese ID de la cita a completar: ");
        String id = scanner.nextLine();

        String recibo = service.completarCita(id);
        System.out.println(recibo);
    }

    private void mostrarReporte() {
        System.out.println(service.generarReporteDiario());
    }

    private void listarMedicos() {
        System.out.println("\n--- LISTA DE MEDICOS ---");
        for (Medico m : service.getMedicos()) {
            System.out.println(m);
        }
    }

    private void listarPacientes() {
        System.out.println("\n--- LISTA DE PACIENTES ---");
        for (Paciente p : service.getPacientes()) {
            System.out.println(p);
        }
    }

    public static void main(String[] args) {
        ConsultorioView view = new ConsultorioView();
        view.iniciar();
    }
}