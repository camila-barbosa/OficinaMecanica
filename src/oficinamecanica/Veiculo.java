/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oficinamecanica;

/**
 * Classe que representa um veículo no sistema da oficina, nela contém
 * informações básicas e seu status.
 *
 * @author barbo
 */
public class Veiculo {

    private String placa;
    private String modelo;
    private String cor;
    private StatusOrdem status;
    private Cliente cliente;

    //construtor
    public Veiculo(String placa, String modelo, String cor, Cliente cliente) {
        if (status == null) {
            throw new IllegalArgumentException("Status não pode ser nulo");
        }
        this.placa = placa;
        this.modelo = modelo;
        this.cor = cor;
        this.cliente = cliente;
        this.status = status;
    }

    //getters e setters
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getCor() {
        return cor;
    }

    public void setCor(String cor) {
        this.cor = cor;
    }
    
    public Cliente getCliente(){
        return cliente;
    }
    public void setCliente(Cliente cliente){
        this.cliente=cliente;
    }
    public StatusOrdem getStatus(){
        return status;
    }
    public void setStatus(StatusOrdem status){
        this.status=status;
    }
    
    /**
     * Verifica qual status do veículo
     *
     * @return status atual do veículo;
     */
    public StatusOrdem vererificarStatus(){
        return this.status;
    }
    
    @Override
    public String toString() {
        return "Veículo [Placa: " + placa + ", Modelo: " + modelo + 
               ", Cor: " + cor + ", Status: " + status + "]";
    }
}
