/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Kelas GuptaAlgorithm
 * Kelas yang beffungsi menangani penjadwalan flow shop dengan algoritma Gupta
 * @author Tedi Tri Ramdani 2015730001
 */
public class GuptaAlgorithm extends Algorithm {
    
    /**
     * method schedule
     * Merupakan method turunan dari kelas Algorithm
     * Method ini berfungsi untuk melakukan penjadwalan menggunakan algoritma Gupta
     * Menerima parameter masukan berupa jobs bertipe ArrayList of Job dan machines bertipe Array of Machine
     * Methode ini mengembalikan sequecne hasil penjadwalan dalam bentuk arraylist of job
     * @param jobs
     * @param machines
     * @return sequence
     */
    @Override
    protected ArrayList<Job> schedule(ArrayList<Job> jobs, Machine[] machines) {
        int ei = 0; //inisialisasi bobot
        ArrayList<Job> sequence = new ArrayList<Job>();
        int[] subjobs = new int[jobs.get(0).getProcessingTimes().length]; //get processing time dari antrian
        ArrayList<Integer> arraylist = new ArrayList<Integer>(); // variabel untuk menyimpan hasil penambahan 2 job terdekat
        ArrayList<Double> slopIndex = new ArrayList<Double>(); // variabel untuk menyimpan slope index
        for (int i = 0; i < jobs.size(); i++) {
            subjobs = jobs.get(i).getProcessingTimes();
            for (int j = 0; j < subjobs.length - 1; j++) {
                arraylist.add(subjobs[j] + subjobs[j + 1]); //masukan penambahan dua job terdekat ke dalam arraylist
                System.out.print(subjobs[j] + subjobs[j + 1] + " ");
            }
            /**
             * Menentukan nilai bobot pada job untuk perhitungan slope index
             */
            if (jobs.get(i).processingTimes[0] >= jobs.get(i).processingTimes[jobs.get(i).processingTimes.length - 1]) {
                ei = -1;
            } else {
                ei = 1;
            }
            slopIndex.add((double) ei / (double) Collections.min(arraylist)); //menambahkan isi slope index
            System.out.println("");
            System.out.println(slopIndex.get(i));
            arraylist.clear(); // menghapus arraylist
        }
        int p = 0;
        while (p < slopIndex.size()) {
            Double max = Collections.max(slopIndex); //mengambil nilai max pada koleksi slope index
            Integer index1 = slopIndex.indexOf(max); // mengambil indexdengan nilai slope index terbesar
            sequence.add(jobs.get(index1)); //memasukan job dengan index yang memiliki slope index terbesar
            p++;
            slopIndex.set(index1, Double.MAX_VALUE*(-1)); // mengatur nilai maksimal menjadi minimal agar tidak terpilih kembali
        }
        return sequence;
    }

    /**
     * method schedule
     * Merupakan method turunan dari kelas Algorithm
     * Method ini berfungsi untuk menghitung nilai make span hasil penjadwalan menggunakan algoritma Gupta
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
        System.out.println("Gupta");
        Machine[] tempMachines = new Machine[machines.length];
        for(int index = 0;index<tempMachines.length;index++){
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
