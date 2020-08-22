/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package process;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import static practica1.Practica1.scanner;
import static process.Encoding.reportNumber;

/**
 *
 * @author Eduardo
 */
public class Decoding {
     //Método para verificar si el archivo es válido para multiplicación
    public static int rowsFileOne;
    public static int colsFileOne;
    public static double matrixOne[][];
    public static double matrixTwo[][];
    public static String htmlToPrint ="";   
    static void createHtml() {
        try {
          File myObj = new File("reporteDeDescifrado-"+reportNumber+".html");
          if (myObj.createNewFile()) {
              //Si el archivo ha sido creado con exito
            try {
              FileWriter myWriter = new FileWriter("reporteDeDescifrado-"+reportNumber+".html");
              //Escribiendo el encabezado
              myWriter.write("<!DOCTYPE html><html lang=\"es\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>Document</title><link rel=\"stylesheet\" href=\"https://eduardomep.github.io/practica1-ipc/htmlTemplate/style.css\"></head><body><div class=\"hero\"><h1>Mep Encoder</h1><img src=\"https://eduardomep.github.io/practica1-ipc/htmlTemplate/img/logo.png\" alt=\"Mep Encoder\"><div class=\"version\"><p>V 1.0 Beta</p></div><p class=\"made\">With ♥ by <a href=\"https://www.instagram.com/eduardomep/\">eduardomep</a></p></div><div class=\"content\">");
              //Escribindo el contenido
              myWriter.write(htmlToPrint);
              //Escribiendo el final de mi documento
              myWriter.write("</div></body></html>");
              myWriter.close();
              System.out.println("html creado");
            } catch (IOException e) {
              System.out.println("Algo salio mal.");
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
    
    static void convertToCharacter(double[][] matrix) throws IOException{
        for (int row = 0; row < rowsFileOne; row++) {
            for (int col = 0; col < colsFileOne; col++) {
                System.out.print((char)matrix[row][col]);
            }
        }
        System.out.println("");
        System.out.println("Presiona una tecla para volver al menú");
        String keyPress = scanner.nextLine();
        Menu.print();
    }
       
    static void multitply(double[][] inverseMatrix) throws IOException {
        //Guardamos las columnas de la matriz final obtenidas de la matriz a multiplicar
        int rowsForFinalMatrix = rowsFileOne;
        int colsForFinalMatrix = colsFileOne;
        double[][] finalMatrix = new double[rowsForFinalMatrix][colsForFinalMatrix];
        //MATRIX TO ENCODING 4X5
        //NXN 5X5
        //FINAL MATRIX 4X5
        System.out.println("La matrix final será de :" +rowsForFinalMatrix +"x"+colsForFinalMatrix);
        //Aquí se multipllica la matriz
        for(int row=0; row<rowsForFinalMatrix; row++){
            for(int col=0; col<colsForFinalMatrix; col++){
                for(int auxCol=0; auxCol<colsForFinalMatrix; auxCol++){
//                    System.out.println("Multiplico "+matrixToEncoding[row][auxCol]+"*"+matrixNxN[auxCol][row] +"="+(matrixToEncoding[row][auxCol]*matrixNxN[auxCol][row]) );
                    finalMatrix[row][col] = finalMatrix[row][col] + (matrixOne[row][auxCol]*inverseMatrix[auxCol][col]);
//                    finalMatrix[row][col] = auxCol;
                }
            }       
        }
        //Creamos contraseña cifrada
        System.out.println("-------------- Tu contraseña decifradaEs es -------------------------------------");
//        for(int row=0; row<rowsForFinalMatrix; row++){
//            for(int col=0; col<colsForFinalMatrix; col++){
//                System.out.print(finalMatrix[row][col]);
//                if(col!=colsForFinalMatrix-1){
//                    System.out.print(",");
//                }
//                else{
//                }
//            }
//            System.out.println("");
//        }
        //Redondeamos la matriz para evitar problemas con decimales
            for(int row=0; row<rowsForFinalMatrix; row++){
                for(int col=0; col<colsForFinalMatrix; col++){
                    finalMatrix[row][col]=Math.round(finalMatrix[row][col]);
                    System.out.print(finalMatrix[row][col]);
                    if(col!=colsForFinalMatrix-1){
                        System.out.print(",");
                    }
            }
            System.out.println("");
        }
                    convertToCharacter(finalMatrix);
        

    }
    
    
    
    public static void CalcInverse(double matrix[][] ) throws IOException{
        //Creamos otra matriz y copiamos sus elementos para enviarla como parametro para multipliar
        //Matriz escalonada para comparar
        double inverseMatrix[][] = new double [matrix.length][matrix.length];        
        //Contador de espacios para diagonal principal
        int spaceCounter = 0;
        //Cambiar todos los elementos de la diagonal principal a 0
        for(int row=0; row<matrix.length;row++){
            for(int col=0; col<matrix.length; col++){
                if(spaceCounter == col){
                    inverseMatrix[row][col] = 1;
                }
                else{
                    inverseMatrix[row][col] = 0;
                }
            }
             spaceCounter++;
        }
        //Reiniciando spaceCounter para operear la inversa
        spaceCounter =0;
        //Almacenamiento del número a operar por cada fila y columna
        double operation=0;
        //Operando para obtener la matriz inversa
    //Operando de diagonal hacía abajo
    for(int row=0; row<matrix.length;row++){
            for(int col=0; col<matrix.length; col++){
                //Paso 1 - volver 1 el valor de la diagonal y operar su columna
                //Verificando si estamos en un pivote
                if(row == col){
                    //Ciclo para dividir fila para volver 1 la diagonal
                    operation = (1/matrix[row][col]);
                    for(int operationCol=0; operationCol<matrix.length;operationCol++){
                       matrix[row][operationCol] =  matrix[row][operationCol] * operation;
                       inverseMatrix[row][operationCol] = inverseMatrix[row][operationCol] * operation;
                    }
                    double constanteUno = matrix[row][col];
                    if(constanteUno <0){
                        System.out.println("SE HIZ0 NEGATIVO PORQUE - FILA: "+row+" COLUMNA: "+col);
                    }
                    double constanteDos =0;
//                  //Obteniendo constante de fila pivote
                    for(int operationRow=0; operationRow<matrix.length;operationRow++){
                        if(operationRow!=row){
                            for(int operationCol=0; operationCol<matrix.length; operationCol++){  
                               if(operationCol==col){
                                    constanteDos = matrix[operationRow][operationCol];
                                    System.out.println("1. Estoy en: " + operationRow +"-" +operationCol);
                                    System.out.println("2. Mi constantes son: "+ constanteUno+" | "+constanteDos);
                                    for(int insideCol=0; insideCol<matrix.length; insideCol++){
                                        System.out.println("Voy a operar: "+constanteUno+"*"+matrix[operationRow][insideCol]+"-"+"("+constanteDos+"*"+matrix[row][insideCol]+")");
                                        matrix[operationRow][insideCol] = constanteUno*matrix[operationRow][insideCol] - (constanteDos*matrix[row][insideCol]);
                                        inverseMatrix[operationRow][insideCol] = constanteUno*inverseMatrix[operationRow][insideCol] - (constanteDos*inverseMatrix[row][insideCol]); 
                                        if(matrix[operationRow][insideCol] == Double.NaN || inverseMatrix[operationRow][insideCol]  == Double.NaN ){
                                            System.out.println("Esta matriz no tiene inversa, utiliza otra para encriptar tu contrseña");
                                            Encoding.getText();
                                        }
                                    }
                               }
                            }
                            System.out.println("");
                            System.out.println("---CAMBIO DE FILA----");
                        }
                    }
                }
            }
        }
    //Verificación de operación tras validación
        multitply(inverseMatrix);
    } 
    
    public static void convertToMatrix(String matrixItems, String archiveNumber) throws IOException{
        int matrixRows=0;
        int matrixCols=0;
        int indexForMatrixItems=0;
        //Guardamos parametro de los elementos de la matriz en un arreglo
        String stringMatrixItems[] = matrixItems.split(",");
        //Verificando si proceso el archivo uno
        if(archiveNumber=="archivoUno"){
            matrixRows = rowsFileOne;
            matrixCols = colsFileOne;
            matrixOne = new double [matrixRows][matrixCols];
            for (int row = 0; row < matrixRows; row++) {
                for (int col = 0; col < matrixCols; col++) {
                    matrixOne[row][col] = Double.parseDouble(stringMatrixItems[indexForMatrixItems]);
                    indexForMatrixItems++;
                }
                
            }
            System.out.println("Se proceso el archivo Uno");
            //Solicitar el segundo archivo para multiplicar
            getFile("archivoDos");
        // Verificando si proceso archivo dos
        }else{
            matrixRows = colsFileOne;
            matrixCols = colsFileOne;
            matrixTwo = new double [matrixRows][matrixCols];
            System.out.println("Creo una matriz de "+matrixRows+"x"+matrixRows);
            for (int row = 0; row < matrixRows; row++) {
                for (int col = 0; col < matrixCols; col++) {
                    matrixTwo[row][col] = Double.parseDouble(stringMatrixItems[indexForMatrixItems]);
                    indexForMatrixItems++;
                }
                
            }
            CalcInverse(matrixTwo);

        }
    }
    
    public static void checkMatrixSize( Scanner scanner, String archiveNumber)throws IOException{
        int initialMatrixCols = 0;
        int rowIndex = 0;
        int totalOfRows = 0;
        String matrixItems ="";
//        rowsForFinalMatrix = matrixRows;
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
        System.out.println("La matrix tiene" + totalOfRows+" x "+initialMatrixCols);
            if(archiveNumber=="archivoUno"){
                rowsFileOne = totalOfRows;
                colsFileOne = initialMatrixCols;
                convertToMatrix(matrixItems, archiveNumber);
                
            }
            else{
                if(totalOfRows==initialMatrixCols && totalOfRows==colsFileOne){
                    System.out.println("La matriz es cudrada y válida para multiplicar");
                    convertToMatrix(matrixItems, archiveNumber);
                }
                else{
                    System.out.println("**La matriz ingresa no es válida para descifrar, ingresa otra para continuar");
                    getFile("archivoDos");

                }
            }

    }
    
    
    
    
    static void getFile(String fileNumber){
        System.out.println("");
        if(fileNumber=="archivoUno"){
            System.out.println("Ingresa la matriz de tu contraseña cifrada (mxn): ");    
        }
        else{
            System.out.println("Ingresa la clave usada para cifrar (nxn): ");    
        }
        String fileDir = scanner.nextLine();
        try {
            Scanner scanner = new Scanner(new File(fileDir));          
            checkMatrixSize(scanner,fileNumber);    
        } catch (Exception e) { 
            System.out.println("La ruta no existe, intentalo de nuevo" );
            getFile(fileNumber);
        }
    }
    public static void main(String[] args) {
        getFile("archivoUno");
    }
    
}
