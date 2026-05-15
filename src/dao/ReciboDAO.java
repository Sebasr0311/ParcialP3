package dao;

import model.*;
import java.io.*;
import java.util.*;

public class ReciboDAO {
    private static final String ARCHIVO_RECIBOS = "recibos.txt";

    public List<Recibo> cargarRecibos() {
        List<Recibo> recibos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_RECIBOS))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    // Cargar recibo desde archivo si es necesario
                }
            }
        } catch (FileNotFoundException e) {
        } catch (Exception e) {
            System.out.println("Error al cargar recibos: " + e.getMessage());
        }
        return recibos;
    }

    public void guardarRecibo(Recibo recibo) {
        try (FileWriter fw = new FileWriter(ARCHIVO_RECIBOS, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(recibo.toArchivo());
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar recibo: " + e.getMessage());
        }
    }
}