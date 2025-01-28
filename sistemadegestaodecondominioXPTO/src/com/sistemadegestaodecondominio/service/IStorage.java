package com.sistemadegestaodecondominio.service;

import java.util.ArrayList;

public interface IStorage<T> {
  void Salvar(T data, String path);
  ArrayList<T> Carregar(String path);
}
