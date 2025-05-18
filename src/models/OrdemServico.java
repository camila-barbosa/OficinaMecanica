/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package models;

import java.util.Date;
import java.util.List;

/**
 *
 * @author barbo
 */
public class OrdemServico {
private String codigo;
private Date data;
private Veiculo veiculo;
private Cliente cliente;
private Mecanico mecanicoResponsavel; //criar classe mecanico
private StatusOrdem status;
private final List<Servico> servicos; //criar classe serviço
}