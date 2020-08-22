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
    public static String htmlToPrint ="";
    public static void CalcInverse(double matrix[][] ) throws IOException{
        //Creando una matriz para copiar los datos para multiplicar
        htmlToPrint = htmlToPrint+ "<p><b>Para asegurarnos de que esta matriz es apta para cifrar obtenemos la inversa anteriormente (El detalle de este proceso se puede observar en el reporte de decodificar)</b></p>"; 
        double matrixNxN[][] = new double[matrix.length][matrix.length];
        for (int col = 0; col < matrix.length; col++) {
            for (int row = 0; row < matrix.length; row++) {
                matrixNxN[col][row]=matrix[col][row];
            }
        }        
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
//                       if(matrix[row][col] > 0){
//                        }
//                        else if(matrix[row][col] < 0){
//                            operation = (1/matrix[row][col]);  
//                        }
                    for(int operationCol=0; operationCol<matrix.length;operationCol++){
//                         System.out.println("El pivote es: "+matrix[row][col] +" y estoy diviendo a:"+ matrix[row][operationCol]);
                       matrix[row][operationCol] =  matrix[row][operationCol] * operation;
                       inverseMatrix[row][operationCol] = inverseMatrix[row][operationCol] * operation;
                       
                    }
//                    System.out.println("El estado de la matriz es:");
//                                    printMatrix(matrix, inverseMatrix);
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
//                            printMatrix(matrix, inverseMatrix);
                            System.out.println("");
                            System.out.println("---CAMBIO DE FILA----");
                        }
                    }
                }
            }
        }
    //Verificación de operación tras validación
//        printMatrix(matrix, inverseMatrix);
    //CODIGO DESPUES DE PROCESAR
    multiplyMatrix(matrixNxN);
    } 
    

    
        //Método para convertir a matriz una cadena 
    public static void convertFileToMatrix(int rows, int columns, String matrixItems) throws IOException{
        //Creamos una matriz con el tamaño obtenido de la lectura del metodo checkMatrixSize()
        double matrix[][] = new double[rows][columns];
        //Asignamos un cantador para llevar el control indice del arreglo StringMatrixItems[]
        int indexForMatrixItems=0;        
        //Guardamos parametro de los elementos de la matriz en un arreglo
        String stringMatrixItems[] = matrixItems.split(",");
        
        //Recorremos el arreglo matrix[][]
        htmlToPrint = htmlToPrint+ "<p>El resultado de este archivo es la siguiente matriz</p>"; 
        htmlToPrint = htmlToPrint+ "<table>"; 
        for(int row=0; row<rows;row++){
            htmlToPrint = htmlToPrint+ "<tr>"; 
            for(int col=0; col<columns; col++){
                //Asignamos en la posición de la matriz el dato convertido a double
                matrix[row][col] = Double.parseDouble(stringMatrixItems[indexForMatrixItems]);
                htmlToPrint = htmlToPrint+ "<td>"+matrix[row][col]+"</td>"; 
                //Aumento de indice para el arreglo strinMatrixItems[]
                indexForMatrixItems++;
            }
            htmlToPrint = htmlToPrint+ "</tr>"; 
        }
        htmlToPrint = htmlToPrint+ "</table>"; 
   
        CalcInverse(matrix);

    }
    
    static void createHtml() {
        try {
          File myObj = new File("reporteDeCifrado-"+reportNumber+".html");
          if (myObj.createNewFile()) {
              //Si el archivo ha sido creado con exito
            try {
              FileWriter myWriter = new FileWriter("reporteDeCifrado-"+reportNumber+".html");
              //Escribiendo el encabezado
              myWriter.write("<!DOCTYPE html><html lang=\"es\"><head><meta charset=\"UTF-8\"><meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\"><title>Document</title><link rel=\"stylesheet\" href=\"https://eduardomep.github.io/practica1-ipc/htmlTemplate/style.css\"></head><body><div class=\"hero\"><h1>Mep Encoder</h1><img src=\"https://eduardomep.github.io/practica1-ipc/htmlTemplate/img/logo.png\" alt=\"Mep Encoder\"><div class=\"version\"><p>V 1.0 Beta</p></div><p class=\"made\">With ♥ by <a href=\"https://www.instagram.com/eduardomep/\">eduardomep</a></p></div><div class=\"content\">");
              //Escribindo el contenido
              myWriter.write(htmlToPrint);
              //Escribiendo el final de mi documento
              myWriter.write("</div></body></html>");
              myWriter.close();
              System.out.println("html creado");
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
    static void printMatrix(double[][] matrix, int rowsForMatrix,int colsForMatrix ){
        htmlToPrint = htmlToPrint +"<table>";
        for (int row=0; row<rowsForMatrix; row++){
            htmlToPrint = htmlToPrint +"<tr>";
            for (int col=0; col<colsForMatrix; col++){
                htmlToPrint = htmlToPrint +"<td>"+matrix[row][col] +"</td>"; 
            }
            htmlToPrint = htmlToPrint +"</tr>";
        }
        htmlToPrint = htmlToPrint +"</table>";   
    }
    
    public static void multiplyMatrix(double[][] matrixNxN){
        //Guardamos las columnas de la matriz final obtenidas de la matriz a multiplicar
        int colsForFinalMatrix = matrixNxN.length;
        htmlToPrint = htmlToPrint+ "<h1>Multiplicación de las matrices (Proceso de cifrado)</h1>";
                htmlToPrint = htmlToPrint+ "<table>";
            htmlToPrint = htmlToPrint+ "<tr>";
                htmlToPrint = htmlToPrint+ "<td>";
                    htmlToPrint = htmlToPrint+ "<table>";
                    for (int row = 0; row <rowsForFinalMatrix ; row++){ 
                        htmlToPrint = htmlToPrint+ "<tr>";
                        for (int col = 0; col <colsForFinalMatrix; col++) {
                            htmlToPrint = htmlToPrint+ "<td>"+matrixToEncoding[row][col]+"</td>";
                        }
                        htmlToPrint = htmlToPrint+ "</tr>";
                    }
                    htmlToPrint = htmlToPrint+ "</table>";
                htmlToPrint = htmlToPrint+ "</td>";
                htmlToPrint = htmlToPrint+ "<td>";
                    htmlToPrint = htmlToPrint+ "<p><b> X </b></p>";
                htmlToPrint = htmlToPrint+ "</td>";
                htmlToPrint = htmlToPrint+ "<td>";
                    htmlToPrint = htmlToPrint+ "<table>";
                    for (int row = 0; row <matrixNxN.length ; row++){ 
                        htmlToPrint = htmlToPrint+ "<tr>";
                        for (int col = 0; col <matrixNxN.length; col++) {
                            htmlToPrint = htmlToPrint+ "<td>"+matrixNxN[row][col]+"</td>";
                        }
                        htmlToPrint = htmlToPrint+ "</tr>";
                    }
                    htmlToPrint = htmlToPrint+ "</table>";
                htmlToPrint = htmlToPrint+ "</td>";
            htmlToPrint = htmlToPrint+ "</tr>";
        htmlToPrint = htmlToPrint+ "</table>";  
        
        double[][] finalMatrix = new double[rowsForFinalMatrix][colsForFinalMatrix];
        System.out.println("La matrix final será de :" +rowsForFinalMatrix +"x"+matrixNxN.length);
        //Aquí se multipllica la matriz
        for(int row=0; row<rowsForFinalMatrix; row++){
            for(int col=0; col<colsForFinalMatrix; col++){
                htmlToPrint = htmlToPrint+ "</b><p>C("+row+")("+col+")</b> </p>";
                for(int auxCol=0; auxCol<colsForFinalMatrix; auxCol++){
//                    System.out.println("Multiplico "+matrixToEncoding[row][auxCol]+"*"+matrixNxN[auxCol][row] +"="+(matrixToEncoding[row][auxCol]*matrixNxN[auxCol][row]) );
                    finalMatrix[row][col] = finalMatrix[row][col] + (matrixToEncoding[row][auxCol]*matrixNxN[auxCol][col]);
                    htmlToPrint = htmlToPrint+ "<p>C+("+row+")("+col+") = "+finalMatrix[row][col]+" + "+matrixToEncoding[row][auxCol]+" * "+matrixNxN[auxCol][col]+"</p>";
                }
                printMatrix(finalMatrix,rowsForFinalMatrix,colsForFinalMatrix);
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
        htmlToPrint = htmlToPrint+ "<h1>Contraseña cifrada: </h1>";
        printMatrix(finalMatrix,rowsForFinalMatrix,colsForFinalMatrix);

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
               System.out.println("Crea una matriz de " + totalOfRows+" x "+initialMatrixCols);
               htmlToPrint = htmlToPrint+ "<p>El tamaño de la matriz para este archivo será de: "+totalOfRows+"x"+initialMatrixCols+"</p>"; 
               convertFileToMatrix(totalOfRows,initialMatrixCols,matrixItems);
                
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
            htmlToPrint = htmlToPrint+ "<h1>Matriz cargada para multiplicar</h1> <p>La ruta usada para cargar el fue: "+fileDir+"</p>";     
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
