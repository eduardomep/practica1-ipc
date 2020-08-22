package process;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Eduardo
 */
import java.io.File;
import java.io.IOException;
import java.util.Scanner; // Import the Scanner class to read text files

public class GaussJordan {

    
    public static void printMatrix(double[][] matrix, double[][] inverseMatrix){
        for(int row=0; row<matrix.length;row++){
            System.out.print("| ");
            for(int col=0; col<matrix.length; col++){
                System.out.print(" "+matrix[row][col]+" ");
                
            }
            for(int col=0; col<matrix.length; col++){
                System.out.print("| "+inverseMatrix[row][col]+" ");   
            }
            System.out.print(" | ");
            System.out.println("");
        }
    }
        
    
    
    
    public static void CalcInverse(double matrix[][], String operationToDo, double[][] matrixToEncoding ) throws IOException{
        //Creamos otra matriz y copiamos sus elementos para enviarla como parametro para multipliar
        double matrixNxN[][] = new double[matrix.length][matrix.length];
        for (int col = 0; col < matrix.length; col++) {
            for (int row = 0; row < matrix.length; row++) {
                matrixNxN[col][row]=matrix[col][row];
            }
        }
        //Matriz escalonada para comparar
        double inverseMatrix[][] = new double [matrix.length][matrix.length];
//        //Asignando una copia de datos de matriz a matriz
//        for(int row=0; row<matrix.length;row++){
//            for(int col=0; col<matrix.length; col++){
//                inverseMatrix[row][col] = matrix[row][col];
//            }
//        }

        
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
//        
                
        


        
        
        //Reiniciando spaceCounter para operear la inversa
        spaceCounter =0;
        double auxiliarOperator = 0;
        //Almacenamiento del número a operar por cada fila y columna
        double operation=0;
        //Operando para obtener la matriz inversa
        boolean rowOperation = true;
        
        

//        for(int row=0; row<matrix.length;row++){
//            for(int col=0; col<matrix.length; col++){ 
//                System.out.println("["+row+"]"+"["+col+"]");
//                //Verifico si estoy en el número de la diagonal para asignar operación
//                    if(spaceCounter == col){
//                        if(matrix[row][col]==1){
//                            break;
//                        }
//                        else{
//                            if(matrix[row][col] > 0){
//                                operation = (1/matrix[row][col]);
//                            }
//                            else if(matrix[row][col] < 0){
//                                operation = matrix[row][col]*(-1/matrix[row][col]);  
//                            }
//                            else if(matrix[row][col] == 0){
//                                //Invertcambio de filas
//                            }
//                        }
//                    }
//                    //Sino estoy en la diagonal, 
//                    matrix[row][col] = matrix[row][col] * operation;
//                    inverseMatrix[row][col] = inverseMatrix[row][col] * operation;
//            }
//            //Sumo un número para mi diagonal
//             spaceCounter++;
//        }

//NUEVO FORR----------------------------------------

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
                            printMatrix(matrix, inverseMatrix);
                            System.out.println("");
                            System.out.println("---CAMBIO DE FILA----");
                        }
                    }
                }
            }
        }
    //Verificación de operación tras validación
        printMatrix(matrix, inverseMatrix);
       if(operationToDo=="multiply"){
           Encoding.multiplyMatrix(matrixNxN);
       }
    } 
    

    
    
    //Método para convertir a matriz una cadena 
    public static void convertFileToMatrix(int rows, int columns, String matrixItems, String operation, double[][] matrixToEncoding) throws IOException{
        //Creamos una matriz con el tamaño obtenido de la lectura del metodo checkMatrixSize()
        double matrix[][] = new double[rows][columns];
        //Asignamos un cantador para llevar el control indice del arreglo StringMatrixItems[]
        int indexForMatrixItems=0;        
        //Guardamos parametro de los elementos de la matriz en un arreglo
        String stringMatrixItems[] = matrixItems.split(",");
        //Recorremos el arreglo matrix[][]
        for(int row=0; row<rows;row++){
            for(int col=0; col<columns; col++){
                //Asignamos en la posición de la matriz el dato convertido a double
                matrix[row][col] = Double.parseDouble(stringMatrixItems[indexForMatrixItems]);
                //Aumento de indice para el arreglo strinMatrixItems[]
                indexForMatrixItems++;
            }
        }
        CalcInverse(matrix,operation,matrixToEncoding);
        
        
    }
    //Método para verificar si la matriz es cuadrada
    public static void checkMatrixSize()throws IOException{
        Scanner scanner = new Scanner(new File("C:\\Users\\Eduardo\\Desktop\\Universidad\\IPC1\\Laboratorio\\Practicas\\Practica1\\matrix.txt"));
        int initialMatrixCols = 0;
        int rowIndex = 0;
        int totalOfRows = 0;
        String matrixItems ="";
        //Leyendo cada una de las filas 
        while(scanner.hasNextLine()){
            //process each line
            String line = scanner.nextLine();
            //Valiedando que la matriz tenga filas mayores que 3
            //Filas que se puede procesar
                //Verificando que la fila tenga más de 3 columnas
                if(line.split(",").length>= 3){
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
                            System.out.println("La matriz ingresada no es cuadrada, verifica tus elementos de la matriz o si existe una fila en blanco" );
                            break;
                        }
                    }
                }
                //Si hay menos de 3 columnas en a la fila actual
                else{
                    System.out.println("La matriz ingresada no es cuadrada, verifica tus elementos de la matriz o si existe una fila en blanco");
                    break;
                }
            rowIndex++;

        }
        
        //Verificando que el número de columnas sea igual que el número de filas
        if(totalOfRows == initialMatrixCols){
            double[][] matrixToEncoding = {{0,2},{0,2}};
            convertFileToMatrix(totalOfRows, initialMatrixCols,matrixItems,"",matrixToEncoding);
        }
        else{
            System.out.println("La matriz ingresada no es cuadrada, verifica tus elementos de la matriz o si existe una fila en blanco");
            
        }
    }

    public static void main(String[] args) throws IOException{
        checkMatrixSize();
    }
}
