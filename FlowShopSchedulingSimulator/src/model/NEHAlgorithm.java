/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Kelas NehAlgorithm
 * Keas yang befungsi untuk menangani penjadwalan flow shop dengan algoritma NEH
 * @author Tedi Tri Ramdani 2015730001
 */
public class NEHAlgorithm extends Algorithm {
    /**
     * method schedule
     * Merupakan method turunan dari kelas Algorithm
     * Method ini berfungsi untuk melakukan penjadwalan menggunakan algoritma NEH
     * Menerima parameter masukan berupa jobs bertipe ArrayList of Job dan machines bertipe Array of Machine
     * Methode ini mengembalikan sequecne hasil penjadwalan dalam bentuk arraylist of job
     * @param jobs
     * @param machines
     * @return sequence
     */
    @Override
    protected ArrayList<Job> schedule(ArrayList<Job> jobs, Machine[] machines) {
        ArrayList<Job> sequence = new ArrayList<Job>();
        ArrayList<Integer> index = new ArrayList<Integer>();
        int[] temp;
        int temp1 = 0;
        for (int i = 0; i < jobs.size(); i++) {
            temp = new int[jobs.get(i).getProcessingTimes().length];
            temp = jobs.get(i).getProcessingTimes();
            for (int j = 0; j < temp.length; j++) {
                temp1 += temp[j];   
            }
            index.add(temp1);
            temp1 = 0;
        }
        int res = 0;
        int min = 1000000;
        int subresult = 0;
        ArrayList<Job> temporer = new ArrayList<Job>();
        int[] tempo = new int[jobs.size()];
        int size = 0;
        /**
         * prosedur untuk mengecek tiap kemungkinan urutan job untuk mendapatkaan nilai meka span terbaik
         */
        for (int i = 0; i < tempo.length; i++) {
            Integer max = Collections.max(index);
            Integer index1 = index.indexOf(max);
            size++;
            for (int j = 0; j < size; j++) {
                temporer.add(j, jobs.get(index1));
                for (int o = 0; o < temporer.size(); o++) {
                    temp = temporer.get(o).getProcessingTimes();
                    for (int k = 0; k < temp.length; k++) {
                        machines[k].setCurrentTime(machines[k].getCurrentTime() + temp[k]);
                        if (machines[k].getCurrentTime() > machines[k + 1].getCurrentTime()) {
                            machines[k + 1].setCurrentTime(machines[k].getCurrentTime());
                        }
                    }
                }
                subresult = machines[machines.length - 2].getCurrentTime();
                if (subresult < min) {
                    min = subresult;
                    res = min;
                    sequence.clear();
                    sequence.addAll(temporer);
                }
                if (j != temporer.size() - 1) {
                    temporer.remove(j);
                } else {
                    temporer.clear();
                    temporer.addAll(sequence);
                }
                for (int x = 0; x < machines.length; x++) {
                    machines[x].setCurrentTime(0);
                }

            }
            min = 1000000;
            index.set(index1, -1000);
        }

        return sequence;
    }

    /**
     * Method computeMakeSpan merupakan method turunan dari kelas Algorithm 
     * Method ini berfungsi untuk menghitung nilai make span hasil penjadwalan menggunakan algoritma NEH 
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
        System.out.println("NEH");
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
