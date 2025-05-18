/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package models;

/**
 *
 * @author barbo
 */
public class Mecanico extends Usuario{
   
   
    private String especialidade;
    private boolean disponivel;
    //construtor 
     public Mecanico(String nome, String cpf, String endereco, String email, String telefone, String senha, String especialidade, boolean disponivel) {
        super(nome, cpf, endereco, email, telefone, senha);
        this.disponivel=disponivel;
        this.especialidade=especialidade;
    }
    
     //getters e setters
    public String getEspecialidade(){
        return especialidade;
    }
    public void setEspecialidade(String especialidade){
        this.especialidade=especialidade;
    }
    public boolean isDisponivel(){
        return disponivel;
    }
    public void setDisponivel(Boolean disponivel){
        this.disponivel=disponivel;
    }
    
    //métodos
   
    public String realizarDiagnostico (Veiculo veiculo){
        re
    }
    
   
}
