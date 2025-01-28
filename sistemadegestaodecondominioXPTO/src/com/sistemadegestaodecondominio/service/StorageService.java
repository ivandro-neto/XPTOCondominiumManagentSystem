package com.sistemadegestaodecondominio.service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.sistemadegestaodecondominio.model.Fracao;

public class StorageService<T> implements IStorage<T> {
    private final String directory;

    // Construtor para permitir customização do diretório
    public StorageService(String directory) {
        this.directory = directory.endsWith("/") ? directory : directory + "/";
        File dir = new File(this.directory);
        if (!dir.exists()) {
            dir.mkdirs(); // Garante que o diretório exista
        }
    }

    @Override
   public void Salvar(T data, String path) {
        String fullPath = directory + path;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fullPath))) {
            if (data instanceof List<?>) {
                for (Object item : (List<?>) data) {
                    writer.write(item.toString()); // Escreve cada objeto como texto no ficheiro
                    writer.newLine(); // Nova linha para o próximo objeto
                }
            } else {
                writer.write(data.toString());
            }
        } catch (IOException e) {
            System.err.println("Erro ao salvar o objeto no caminho '" + fullPath + "': " + e.getMessage());
        }
    }

    @Override
    @SuppressWarnings("unchecked") // Suprime aviso de cast genérico
    public ArrayList<T> Carregar(String path) {
        String fullPath = directory + path;
        try (BufferedReader reader = new BufferedReader(new FileReader(fullPath))) {
            String line;
            ArrayList<T> items = new ArrayList<T>();
            while ((line = reader.readLine()) != null) {
                // A conversão de linha para objeto pode ser feita aqui dependendo do formato
                // Por exemplo, se for uma representação simples de objetos Fracao
                // Exemplo de conversão simples (pode ser ajustado conforme os dados):
                T item = (T) line; // Aqui você pode usar um conversor adequado para transformar a string de volta em um objeto
                items.add(item);
            }
            return items;
        } catch (IOException e) {
            System.err.println("Erro ao carregar o objeto do arquivo '" + fullPath + "': " + e.getMessage());
        }
        return new ArrayList<>(); // Retorna uma lista vazia caso ocorra algum erro
    }
}
