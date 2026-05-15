package dao;

import model.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

public class CitaDAO {
    private static final String ARCHIVO_CITAS = "citas.txt";

    public List<Cita> cargarCitas() {
        List<Cita> citas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_CITAS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    Cita cita = lineaToCita(linea);
                    if (cita != null) citas.add(cita);
                }
            }
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            System.out.println("Error al cargar citas: " + e.getMessage());
        }
        return citas;
    }

    private Cita lineaToCita(String linea) {
        return null;
    }

    public void guardarCita(Cita cita) {
        try (FileWriter fw = new FileWriter(ARCHIVO_CITAS, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(cita.toArchivo());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar cita: " + e.getMessage());
        }
    }

    public void actualizarCitas(List<Cita> citas) {
        try (FileWriter fw = new FileWriter(ARCHIVO_CITAS);
             BufferedWriter bw = new BufferedWriter(fw)) {
            for (Cita c : citas) {
                bw.write(c.toArchivo());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error al actualizar citas: " + e.getMessage());
        }
    }
}