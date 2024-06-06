package org.example;

import java.util.Random;

/*
* Descripción
Se pide implementar un programa en Java que busque un número específico en una matriz grande utilizando una arquitectura de memoria compartida. El programa debe dividir la matriz entre varios hilos, cada uno de los cuales buscará el número en su porción asignada.

Requerimientos
Implementar un programa que divida una matriz en partes iguales entre varios hilos.
Cada hilo debe buscar el número en su parte de la matriz.
Utilizar mecanismos de sincronización para informar si el número ha sido encontrado y detener la búsqueda si se encuentra.
Medir el tiempo de ejecución de la búsqueda paralela y compararlo con la ejecución secuencial.*/
public class ParallelMatrixSearch {
    private static final int MATRIX_SIZE = 1000;
    private static final int THREAD_COUNT = 4;
    private static final int[][] matrix = new int[MATRIX_SIZE][MATRIX_SIZE];
    private static final int TARGET = 256; // Número a buscar

    private ParallelMatrixSearch() {
    }

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        // Inicializar la matriz con valores aleatorios
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                matrix[i][j] = random.nextInt(1000);
            }
        }

        // Secuencial
        long startTime = System.currentTimeMillis();
        boolean sequentialResult = sequentialSearch(matrix, TARGET);
        long endTime = System.currentTimeMillis();
        System.out.println("Resultado secuencial: " + sequentialResult);
        System.out.println("Tiempo secuencial: " + (endTime - startTime) + " milisegundos");

        // Paralelo
        startTime = System.currentTimeMillis();
        boolean parallelResult = parallelSearch(matrix, TARGET, THREAD_COUNT);
        endTime = System.currentTimeMillis();
        System.out.println("Resultado paralelo: " + parallelResult);
        System.out.println("Tiempo paralelo: " + (endTime - startTime) + " milisegundos");

        return;
    }

    //Busqueda secuencial simple
    private static boolean sequentialSearch(int[][] matrix, int target) {
        for (int i = 0; i < MATRIX_SIZE; i++) {
            for (int j = 0; j < MATRIX_SIZE; j++) {
                if (matrix[i][j] == target) {
                    return true;
                }
            }
        }
        return false;
    }

    //Busqueda paralela, crea 4 hilos, a los cuales se les asigna una parte de la matriz (el tamaño es igual para todos los hilos), cada hilo se encarga de buscar el número en su parte de la matriz.
    private static boolean parallelSearch(int[][] matrix, int target, int numThreads) throws InterruptedException {
        Thread[] threads = new Thread[numThreads];
        boolean[] found = new boolean[numThreads];

        //Divido la matriz en partes iguales (4 en el caso de este ejercicio)
        for (int i = 0; i < numThreads; i++) {
            int start = i * (MATRIX_SIZE / numThreads);
            int end = (i + 1) * (MATRIX_SIZE / numThreads);
            threads[i] = new Thread(new Search(matrix, target, start, end, found, i));
            threads[i].start();
        }

        //Espero a que todos los hilos terminen
        for (Thread thread : threads) {
            thread.join();
        }

        //Verifico si alguno de los hilos encontró el número, desde que ecuentre true, retorno true
        for (boolean result : found) {
            if (result) {
                return true;
            }
        }
        return false;
    }

    //Clase que implementa la interfaz Runnable, que se encarga de hacer la busqueda en la parte de la matriz correspondiente
    private static class Search implements Runnable {
        private int[][] matrix;
        private int target;
        private int start;
        private int end;
        private boolean[] found;
        private int index;

        public Search(int[][] matrix, int target, int start, int end, boolean[] found, int index) {
            this.matrix = matrix;
            this.target = target;
            this.start = start;
            this.end = end;
            this.found = found;
            this.index = index;
        }

        //Método que se encarga de buscar el número en la parte de la matriz correspondiente, es igual al metodo de busqueda secuencial.
        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                for (int j = 0; j < MATRIX_SIZE; j++) {
                    if (matrix[i][j] == target) {
                        found[index] = true;
                        return;
                    }
                }
            }
        }
    }
}


