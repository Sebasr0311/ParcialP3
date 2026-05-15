package dao;

import model.*;
import java.io.*;
import java.util.*;

public class MedicoDAO {
    private static final String ARCHIVO_GENERALES = "medicos_generales.txt";
    private static final String ARCHIVO_ESPECIALISTAS = "medicos_especialistas.txt";

    public List<Medico> cargarMedicos() {
        List<Medico> medicos = new ArrayList<>();
        medicos.addAll(cargarDeArchivo(ARCHIVO_GENERALES, "general"));
        medicos.addAll(cargarDeArchivo(ARCHIVO_ESPECIALISTAS, "especialista"));
        return medicos;
    }

    private List<Medico> cargarDeArchivo(String archivo, String tipo) {
        List<Medico> lista = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    String[] partes = linea.split("\\|");
                    lista.add(Medico.desdeArchivo(tipo, partes));
                }
            }
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            System.out.println("Error al cargar médicos: " + e.getMessage());
        }
        return lista;
    }

    public void guardarMedico(Medico medico) {
        String archivo = medico.getTipo().equals("General") ? ARCHIVO_GENERALES : ARCHIVO_ESPECIALISTAS;
        try (FileWriter fw = new FileWriter(archivo, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(medico.toArchivo());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar médico: " + e.getMessage());
        }
    }

    public void actualizarMedicos(List<Medico> medicos) {
        List<Medico> generales = new ArrayList<>();
        List<Medico> especialistas = new ArrayList<>();
        for (Medico m : medicos) {
            if (m.getTipo().equals("General")) {
                generales.add(m);
            } else {
                especialistas.add(m);
            }
        }
        guardarTodos(ARCHIVO_GENERALES, generales);
        guardarTodos(ARCHIVO_ESPECIALISTAS, especialistas);
    }

    private void guardarTodos(String archivo, List<Medico> medicos) {
        try (FileWriter fw = new FileWriter(archivo);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (Medico m : medicos) {
                bw.write(m.toArchivo());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar médicos: " + e.getMessage());
        }
    }
}