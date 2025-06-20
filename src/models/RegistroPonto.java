/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

import java.time.LocalDateTime;

/**
 *
 * @author marcos_miller
 */
public class RegistroPonto {
    // Contador estático para gerar IDs únicos para cada registro de ponto.
    // Assim como Usuario.proximoId, ele precisa ser ajustado ao carregar dados.
    public static int proximoId = 1;

    private int id; // ID único do registro de ponto
    private int idUsuario; // ID do usuário que registrou o ponto
    private LocalDateTime dataHoraEntrada; // Data e hora da entrada registrada
    private LocalDateTime dataHoraSaida; // Data e hora da saída registrada (pode ser null se ainda não saiu)

    /**
     * Construtor para registrar uma nova entrada de ponto.
     * A data e hora de saída são inicialmente nulas.
     *
     * @param idUsuario O ID do usuário que está registrando a entrada.
     */
    public RegistroPonto(int idUsuario) {
        this.id = proximoId++; // Atribui um ID único e incrementa
        this.idUsuario = idUsuario;
        this.dataHoraEntrada = LocalDateTime.now(); // Registra a data e hora atual como entrada
        this.dataHoraSaida = null; // Saída ainda não registrada
    }

    // Construtor usado principalmente pelo Gson ao carregar do JSON,
    // ou para reconstruir um objeto completo com todos os dados.
    public RegistroPonto(int id, int idUsuario, LocalDateTime dataHoraEntrada, LocalDateTime dataHoraSaida) {
        this.id = id;
        this.idUsuario = idUsuario;
        this.dataHoraEntrada = dataHoraEntrada;
        this.dataHoraSaida = dataHoraSaida;
    }


    // --- Getters ---
    public int getId() {
        return id;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public LocalDateTime getDataHoraEntrada() {
        return dataHoraEntrada;
    }

    public LocalDateTime getDataHoraSaida() {
        return dataHoraSaida;
    }

    // --- Setters ---
    // (A maioria dos setters não é necessária para um registro, exceto para 'dataHoraSaida')

    /**
     * Define a data e hora de saída para este registro de ponto.
     * @param dataHoraSaida A data e hora da saída.
     */
    public void setDataHoraSaida(LocalDateTime dataHoraSaida) {
        this.dataHoraSaida = dataHoraSaida;
    }

    @Override
    public String toString() {
        String saida = (dataHoraSaida == null) ? "Não Registrado" : dataHoraSaida.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm dd/MM"));
        return "RegistroPonto{" +
               "ID=" + id +
               ", UsuárioID=" + idUsuario +
               ", Entrada=" + dataHoraEntrada.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm dd/MM")) +
               ", Saída=" + saida +
               '}';
    }
}
