/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import static practica1.Practica1.scanner;

/**
 *
 * @author Eduardo
 */
public class Encoding {
    //Matriz del texto a cifrar
    public static double matrixToEncoding[][];
    //Matriz que guarda las filas de la matriz original
    public static int rowsForFinalMatrix = 0;
    public static int reportNumber=1;
    public static String htmlToPrint;
    
    static void createHtml() {
        try {
          File myObj = new File("reporteDeCoficacion+"+reportNumber+".html");
          if (myObj.createNewFile()) {
              //Si el archivo ha sido creado con exito
            try {
              FileWriter myWriter = new FileWriter("reporteDeCoficacion+"+reportNumber+".html");
              //Escribiendo el encabezado
              myWriter.write("<!DOCTYPE html><html lang=\"es\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>Document</title><link rel=\"stylesheet\" href=\"https://eduardomep.github.io/practica1-ipc/htmlTemplate/style.css\"></head><body><div class=\"hero\"><h1>Mep Encoder</h1><img src=\"https://eduardomep.github.io/practica1-ipc/htmlTemplate/img/logo.png\" alt=\"Mep Encoder\"><div class=\"version\"><p>V 1.0 Beta</p></div><p class=\"made\">With ♥ by <a href=\"https://www.instagram.com/eduardomep/\">eduardomep</a></p></div><div class=\"content\">");
              //Escribindo el contenido
              myWriter.write(htmlToPrint);
              //Escribiendo el final de mi documento
              myWriter.write("</div></body></html>");
              myWriter.close();
              System.out.println("hetml creado");
            } catch (IOException e) {
              System.out.println("An error occurred.");
              e.printStackTrace();
            }
          } else {
            reportNumber++;
            createHtml();
          }
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
        }
    }    

    public static void multiplyMatrix(double[][] matrixNxN){
        //Guardamos las columnas de la matriz final obtenidas de la matriz a multiplicar
        int colsForFinalMatrix = matrixNxN.length;
        double[][] finalMatrix = new double[rowsForFinalMatrix][colsForFinalMatrix];
        //MATRIX TO ENCODING 4X5
        //NXN 5X5
        //FINAL MATRIX 4X5
        System.out.println("La matrix final será de :" +rowsForFinalMatrix +"x"+matrixNxN.length);
        //Aquí se multipllica la matriz
        for(int row=0; row<rowsForFinalMatrix; row++){
            for(int col=0; col<colsForFinalMatrix; col++){
                for(int auxCol=0; auxCol<colsForFinalMatrix; auxCol++){
//                    System.out.println("Multiplico "+matrixToEncoding[row][auxCol]+"*"+matrixNxN[auxCol][row] +"="+(matrixToEncoding[row][auxCol]*matrixNxN[auxCol][row]) );
                    finalMatrix[row][col] = finalMatrix[row][col] + (matrixToEncoding[row][auxCol]*matrixNxN[auxCol][col]);
//                    finalMatrix[row][col] = auxCol;
                }
            }       
        }
        //Creamos contraseña cifrada
        System.out.println("-------------- la contraseña numerica es -------------------------------------");
        for(int row=0; row<rowsForFinalMatrix; row++){
            for(int col=0; col<colsForFinalMatrix; col++){
                System.out.print(finalMatrix[row][col]);
                if(col!=colsForFinalMatrix-1){
                    System.out.print(",");
                }
                else{
                }
            }
            System.out.println("");
        }
        createHtml();
        
        

        
    }
    //Método para verificar si una matriz tiene inversa
    public static void checkInverse(){
        System.out.println("Verificando si la matriz tiene inversa");
    }
    

    
    //Método para verificar si el archivo es válido para multiplicación
    public static void checkMatrixSize(int matrixCols, int matrixRows, Scanner scanner, double[][] matrix)throws IOException{
        int initialMatrixCols = 0;
        int rowIndex = 0;
        int totalOfRows = 0;
        String matrixItems ="";
        System.out.println("La matrix original es de "+ matrixRows +" x "+matrixCols);
        rowsForFinalMatrix = matrixRows;
        //Leyendo cada una de las filas 
        while(scanner.hasNextLine()){
            //process each line
            String line = scanner.nextLine();
                     totalOfRows++;
                    //Verificando si estamos en la primera fila 
                    if(rowIndex == 0){
                        //Asignando número de columnas que deberá tener la matriz
                        initialMatrixCols = line.split(",").length;
                        matrixItems =  line;
                    }
                    //Si no estamos en la primera fila
                    else{
                        //verificamos si la fila actual tiene el mismo número de elementos que la fila 1
                        if(line.split(",").length == initialMatrixCols){
                            matrixItems = matrixItems+"," + line;
                        }
                        else{
                            System.out.println("La matriz ingresada no es válida, verifica tus elementos de la matriz o si existe una fila en blanco" );
                            break;
                        }
                    }
                //Si hay menos de 3 columnas en a la fila actual
            rowIndex++;

        }
        if(totalOfRows == matrixCols && totalOfRows == initialMatrixCols ){
//            checkInverse(); 
               System.out.println("Crea una matriz de " + totalOfRows+" x "+initialMatrixCols);
               GaussJordan.convertFileToMatrix(totalOfRows,initialMatrixCols,matrixItems,"multiply",matrix);
                
//            multiplyMatrix();
        }
        else{
            System.out.println("La matriz ingresada no es válida, verifica tus elementos de la matriz o si existe una fila en blanco");
            getMatrix(matrixRows,matrixCols,matrix);
            
        }
    }
    
    //Método para pedir la ruta del archivo nxn
    static void getMatrix(int matrixCols, int matrixRows, double[][] matrix) throws FileNotFoundException, IOException{
        System.out.println("Ingresa la ruta de la matriz para multiplicar:");
        String fileDir = scanner.nextLine();
        try {
            Scanner scanner = new Scanner(new File(fileDir));
            checkMatrixSize(matrixRows,matrixCols,scanner,matrix);    
        } catch (Exception e) { 
            System.out.println("La ruta no existe, intentalo de nuevo" );
            getMatrix(matrixCols, matrixRows,matrix);
        }
    }
    
    //Metodo para obtener el texto
    public static void getText() throws IOException {
        System.out.println("Ingresa un texto a codificar");

        String originalText = scanner.nextLine();
        //Guardando la longitud del texto
        //Contenido para el html
        htmlToPrint = htmlToPrint + "<h1>Texto a codificar: " + originalText +"</h1>";
        int textLenght = originalText.length();
        // Variables para el tamaño de la matriz
        int matrixRows = 0;
        int matrixCols = 0;
        //Guardando en un array los caracteres del texto
        /**
         * char[] es el tipo de dato para el arreglo 
         *  el método toCharArray() convierte la cadena en un arreglo de caracteres de tipo char
         */
        char[] characters=originalText.toCharArray();
        //Verificando la multiplicidad de la longitud de la cadena y obteniendo el valor de filas de la matriz
        if(textLenght%3 == 0){
            matrixRows = 3;
        }
        else if (textLenght%4 == 0){
            matrixRows = 4;
        }
        else if (textLenght%5 == 0){
            matrixRows = 5;
        }
        else if (textLenght%7 == 0){
            matrixRows = 7;
        }
        else if (textLenght%11 == 0){
            matrixRows = 11;
        }
        else if (textLenght%13 == 0){
            matrixRows = 13;
        }
        else if (textLenght%17 == 0){
            matrixRows = 17;
        }
        //Obtenendo el valor de columnas de la matriz
        matrixCols = textLenght/matrixRows;
        //Asignando el valor de la matriz
        
        double matrix[][] = new double[matrixRows][matrixCols];
        //Agregando un contador auxiliar para recorrer el arreglo de caracteres en el for posterior
        int charactersIndex = 0;
        //Recorriendo la matriz para almacenar los datos
        for (int rowIndex=0; rowIndex<matrixRows; rowIndex++){
            for (int colIndex=0; colIndex<matrixCols; colIndex++){
                //Almacenando los datos del array de caracteres convertidos a decimal en la posición de la matriz usando contador auxiliar
                matrix[rowIndex][colIndex] = (int)characters[charactersIndex];
                //Aumentando contado auxiliar
                charactersIndex++;
            }
        }
        //Obtenemos el tamaño de caracteres de la matriz
        System.out.println("El texto tiene: " + charactersIndex +" caracteres");
        //Contenido para el html
        htmlToPrint = htmlToPrint + "<p>El texto tiene: " + charactersIndex +" caracteres y se almacenara en una matriz de "+matrixRows+"x"+matrixCols+"</p>";
        //Imprimiendo matriz en html
        htmlToPrint = htmlToPrint +"<table>";
        for (int row=0; row<matrixRows; row++){
            htmlToPrint = htmlToPrint +"<tr>";
            for (int col=0; col<matrixCols; col++){
                //Almacenando los datos del array de caracteres convertidos a decimal en la posición de la matriz usando contador auxiliar
                htmlToPrint = htmlToPrint +"<td>"+matrix[row][col] +"</td>"; 
                //Aumentando contado auxiliar
                charactersIndex++;
            }
            htmlToPrint = htmlToPrint +"</tr>";
        }
        htmlToPrint = htmlToPrint +"</table>";        
        matrixToEncoding = matrix;
        getMatrix(matrixRows, matrixCols,matrix);
        








//        //Solicitando ruta para guardar archivo
//        System.out.println("Ingresa la ruta del archivo de la matriz: ");
//        String fileDir = scanner.nextLine();
//        //Creando archivo con matriz
//        try {  
//            File file = new File(fileDir);  
//            if (file.createNewFile()) {  
//                FileWriter myWriter = new FileWriter(fileDir);
//                for(int row=0; row<matrixRows; row++){
//                    //Agregando alto de línea
//                    for(int col=0; col<matrixCols; col++){
//                        //Guardando el número en la matriz de archivo
//                        //Convirtiendo a tipo texto
//                        myWriter.write(String.valueOf(matrix[row][col]));
//                        if(col==matrixCols-1){
//                        }else{
//                            myWriter.write(",");
//                        }
//                    }
//                    myWriter.write("\n");
//                }
//                myWriter.close();
//                System.out.println("Archivo creado con éxito"); 
//                
//            } else {  
//                System.out.println("Ya existe un archivo con ese nombre");  
//            }  
//            //capturando posible error al crear archivo
//        } catch (IOException e) {
//            System.out.println("Ah ocurrido un error al intentar crear el archivo");
//            e.printStackTrace();  
//        }  

    }
    public static void main(String[] args) throws IOException {
        getText();    
    }
    
    
}
