package process;


import java.io.IOException;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eduardo
 */
public class Menu {
  
    public static void print() throws IOException{
        System.out.println("---------------- Matrix -----------------");
        System.out.println("|                                       |");
        System.out.println("| 1.Cifrar                              |");
        System.out.println("| 1.Descifrar                           |");
        System.out.println("| 3.Gauss-Jordan                        |");
        System.out.println("|                                       |");
        System.out.println("-----------------------------------------");
        getOption();
    }
    static void getOption() throws IOException{
        System.out.println("Ingresa una opción del menú para continuar:");
        Scanner scanner = new Scanner(System.in);
        int menuSelection = scanner.nextInt();
        selectMenu(menuSelection);
    }
    
    static void selectMenu(int option) throws IOException{
       
        switch(option){
            case 1:
                Encoding.getText();
                break;
            case 2:
//                Decoding.getFileOne();
                break;
            case 3:
                System.out.println("Seleccionaste opción 3");
                break;
            default:
                System.out.println("ERROR - Ingresaste una opción inválida");
                getOption();
                break;
                
        }
    
    }
    public static void main(String[] args) throws IOException {
        print();
    }
    
    
}
