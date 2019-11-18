/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Kelas PalmerAlgorithm
 * Kelas yang berfungi untuk mengangani penjadwalan flow shop dengan algoritma Palmer
 * @author tedi Tri Ramdani 201573000
 */
public class PalmerAlgorithm extends Algorithm {
     /**
     * method schedule
     * Merupakan method turunan dari kelas Algorithm
     * Method ini berfungsi untuk melakukan penjadwalan menggunakan algoritma Palmer
     * Menerima parameter masukan berupa jobs bertipe ArrayList of Job dan machines bertipe Array of Machine
     * Methode ini mengembalikan sequecne hasil penjadwalan dalam bentuk arraylist of job
     * @param jobs
     * @param machines
     * @return sequence
     */
    @Override
    protected ArrayList<Job> schedule(ArrayList<Job> array, Machine[] machines) {
        ArrayList<Job> sequence = new ArrayList<Job>();
        ArrayList<Integer> slopIndex = new ArrayList<Integer>(); //variabel untuk menyimpan slope index
        int[] temp; // variabel untuk menyimpan processing time sementara
        int tempo = 0; //untuke mnyimpan nilai slope index
        int[] index = new int[machines.length - 1]; // untuk menyimpan hasil index sementara dengan rumus mencari slope index
        
        //prosedur untuk menghitung index
        for (int i = 0; i < index.length; i++) {
            index[i] = 2 * (i + 1) - (machines.length - 1) - 1;
            System.out.println(index[i]);
        }
        
        //prosedur untuk menghitung slope index
        for (int i = 0; i < array.size(); i++) {
            temp = array.get(i).getProcessingTimes();
            for (int j = 0; j < temp.length; j++) {
                tempo += (temp[j] * index[j]);
            }
            slopIndex.add(tempo);

            tempo = 0;
        }
        for (int i = 0; i < slopIndex.size(); i++) {
            System.out.println(slopIndex.get(i));
        }
        int p = 0;
        while (p < slopIndex.size()) {
            Integer max = Collections.max(slopIndex); // mencari slope index terbesar
            Integer index1 = slopIndex.indexOf(max); // mengambil index dari slope index terbesar
            sequence.add(array.get(index1)); // memasukan job dengan mengambil job pada index max
            p++;
            slopIndex.set(index1, Integer.MAX_VALUE*-1); // mengisi nilai max dengan nilai paling minimum agar tidak terpilih kembali
        }

        return sequence;
    }

   /**
     * Method computeMakeSpan merupakan method turunan dari kelas Algorithm 
     * Method ini berfungsi untuk menghitung nilai make span hasil penjadwalan menggunakan algoritma Palmer 
     * Menerima parameter masukan berupa sequecne bertipe ArrayList of Job dan machines bertipe Array of Machine 
     * Methode ini mengembalikan total nilai make span dari sequence hasil penjadwalan
     * @param jobs
     * @param machines
     * @return make span
     */
    @Override
    protected int computeMakeSpan(ArrayList<Job> sequence, Machine[] machines) {
        int makespan = 0;
        int[] temp;
        System.out.println("============================================================================");
        System.out.println("PALMER");
        Machine[] tempMachines = new Machine[machines.length];
        for (int index = 0; index < tempMachines.length; index++) {
            tempMachines[index] = new Machine(machines[index].getName());
        }
        for (int i = 0; i < sequence.size(); i++) {
            temp = sequence.get(i).getProcessingTimes();
            System.out.print((sequence.get(i)).getName() + " ");
            for (int j = 0; j < temp.length; j++) {
                tempMachines[j].setCurrentTime(tempMachines[j].getCurrentTime() + temp[j]);
                if (tempMachines[j].getCurrentTime() > tempMachines[j + 1].getCurrentTime()) {
                    tempMachines[j + 1].setCurrentTime(tempMachines[j].getCurrentTime());
                }
                System.out.print(tempMachines[j].getCurrentTime() + " ");
            }
            System.out.println("");
        }
        makespan = tempMachines[tempMachines.length - 2].getCurrentTime();
        return makespan;
    }

}
