/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package oficinamecanica;

import view.MenuEstoque;
import models.Servico;

/**
 * Essa será nossa classe Main
 *
 * @author Camila
 */

public class Principal {

    public static void main(String[] args) {
        MenuEstoque menu = new MenuEstoque();
        menu.exibirMenu();

        Servico servico = Servico.TROCA_OLEO;
        System.out.println("Serviço: " + servico.getDescricao());
        System.out.println("Preço: R$" + servico.getPreco());

    }
}
