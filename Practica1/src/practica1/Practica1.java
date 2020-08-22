/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practica1;
import process.Menu;
import java.util.Scanner;
import java.io.IOException;  
/**
 *
 * @author Eduardomep
 */
public class Practica1 {
    public static Scanner scanner = new Scanner(System.in);
    //Método main para iniciar el menú del programa
    public static void main(String[] args) throws IOException {
       init();
    }
    public static void init() throws IOException {
       Menu menu = new Menu();
       menu.print();
    }
    
}
