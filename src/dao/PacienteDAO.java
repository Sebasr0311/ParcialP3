package dao;

import model.*;
import java.io.*;
import java.util.*;

public class PacienteDAO {
    private static final String ARCHIVO_PEDIATRICOS = "pacientes_pediatricos.txt";
    private static final String ARCHIVO_ADULTOS = "pacientes_adultos.txt";
    private static final String ARCHIVO_ADULTOS_MAYORES = "pacientes_adultos_mayores.txt";

    public List<Paciente> cargarPacientes() {
        List<Paciente> pacientes = new ArrayList<>();
        pacientes.addAll(cargarDeArchivo(ARCHIVO_PEDIATRICOS, "pediatrico"));
        pacientes.addAll(cargarDeArchivo(ARCHIVO_ADULTOS, "adulto"));
        pacientes.addAll(cargarDeArchivo(ARCHIVO_ADULTOS_MAYORES, "adulto_mayor"));
        return pacientes;
    }

    private List<Paciente> cargarDeArchivo(String archivo, String tipo) {
        List<Paciente> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] partes = linea.split("\\|");
                    lista.add(Paciente.desdeArchivo(tipo, partes));
                }
            }
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            System.out.println("Error al cargar pacientes: " + e.getMessage());
        }
        return lista;
    }

    public void guardarPaciente(Paciente paciente) {
        String archivo;
        if (paciente instanceof PacientePediatrico) archivo = ARCHIVO_PEDIATRICOS;
        else if (paciente instanceof PacienteAdulto) archivo = ARCHIVO_ADULTOS;
        else archivo = ARCHIVO_ADULTOS_MAYORES;

        try (FileWriter fw = new FileWriter(archivo, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(paciente.toArchivo());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar paciente: " + e.getMessage());
        }
    }

    public void actualizarPacientes(List<Paciente> pacientes) {
        List<Paciente> pediatricos = new ArrayList<>();
        List<Paciente> adultos = new ArrayList<>();
        List<Paciente> adultosMayores = new ArrayList<>();
        for (Paciente p : pacientes) {
            if (p instanceof PacientePediatrico) pediatricos.add(p);
            else if (p instanceof PacienteAdulto) adultos.add(p);
            else adultosMayores.add(p);
        }
        guardarTodos(ARCHIVO_PEDIATRICOS, pediatricos);
        guardarTodos(ARCHIVO_ADULTOS, adultos);
        guardarTodos(ARCHIVO_ADULTOS_MAYORES, adultosMayores);
    }

    private void guardarTodos(String archivo, List<Paciente> pacientes) {
        try (FileWriter fw = new FileWriter(archivo);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (Paciente p : pacientes) {
                bw.write(p.toArchivo());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar pacientes: " + e.getMessage());
        }
    }
}