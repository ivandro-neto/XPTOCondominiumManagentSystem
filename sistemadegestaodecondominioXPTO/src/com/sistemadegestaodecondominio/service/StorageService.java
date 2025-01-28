package com.sistemadegestaodecondominio.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class StorageService<T> implements IStorage<T> {
    private final String directory;

    public boolean pathExiste(String path) {
        File file = new File(directory + path);  // Cria um objeto File com o caminho fornecido
        return file.exists();        // Retorna true se o caminho existir, false caso contrário
    }
    public boolean deleteFile(String path) {
        File file = new File(directory + path);  // Cria um objeto File com o caminho fornecido
        return file.delete();        // Retorna true se o arquivo foi apagado com sucesso, false caso contrário
    }

    public StorageService(String directory) {
        this.directory = directory.endsWith("/") ? directory : directory + "/";
        File dir = new File(this.directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
   public void Salvar(T data, String path) {
        String fullPath = directory + path;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath))) {
            if (data instanceof List<?>) {
                for (Object item : (List<?>) data) {
                    writer.write(item.toString()); 
                    writer.newLine(); 
                }
            } else {
                writer.write(data.toString());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar o objeto no caminho '" + fullPath + "': " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("unchecked") 
    public ArrayList<T> Carregar(String path) {
        String fullPath = directory + path;
        try (BufferedReader reader = new BufferedReader(new FileReader(fullPath))) {
            String line;
            ArrayList<T> items = new ArrayList<T>();
            while ((line = reader.readLine()) != null) {
                T item = (T) line; 
                items.add(item);
            }
            return items;
        } catch (IOException e) {
            System.err.println("Erro ao carregar o objeto do arquivo '" + fullPath + "': " + e.getMessage());
        }
        return new ArrayList<>();
    }
}
