package org.example;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/*
Descripción
Se pide implementar el clásico problema del productor-consumidor utilizando colas bloqueantes en Java. Los productores generarán números aleatorios y los pondrán en una cola, mientras que los consumidores los tomarán de la cola y los procesarán.

Requerimientos
Implementar una clase de productor que genere números y los ponga en una cola.
Implementar una clase de consumidor que tome números de la cola y los procese (por ejemplo, sumarlos).
Utilizar una cola bloqueante para sincronizar la producción y el consumo.
Medir el rendimiento del sistema con múltiples productores y consumidores.
 */
public class ProducerConsumer {
    private static final int QUEUE_CAPACITY = 10;
    private static final int PRODUCER_COUNT = 2;
    private static final int CONSUMER_COUNT = 2;
    private static final int PRODUCE_COUNT = 100;

    public static void main(String[] args) {
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(QUEUE_CAPACITY);

        Producer producer = new Producer();
        Consumer consumer = new Consumer();

        Thread producerThread = new Thread(producer);
        Thread consumerThread = new Thread(consumer);

        long startTime = System.currentTimeMillis();
        producerThread.start();
        consumerThread.start();
        long endTime = System.currentTimeMillis();

        System.out.println("Tiempo de ejecución: " + (endTime - startTime) + " ms");
    }

    static class Producer implements Runnable {
        ArrayList<Integer> list = new ArrayList<>();
        @Override
        public void run() {
            for (int i = 0; i < PRODUCE_COUNT; i++) {
                list.add(i);
            }
        }
    }

    static class Consumer implements Runnable {

        @Override
        public void run() {

        }
    }
}
