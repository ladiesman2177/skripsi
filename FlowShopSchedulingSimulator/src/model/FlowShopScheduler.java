/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Kelas FlowShopSchdeuler
 * Kelas ini berfungsi sebagai onjek yang mengatur penjadwalan flow shop pada perangkat lunak 
 * Memeiliki atribut berupa :
 * ArrayList<Job> jobs;
 * Machine[] machines;
 * Algorithm[] algorithms;
 * ArrayList<ArrayList<Job>>sequences;
 * int[] makeSpans;
 * int[] tardinesses;
     
 * @author Tedi Tri Ramdani 2015730001
 */
public class FlowShopScheduler {
    private ArrayList<Job> jobs;
    private Machine[] machines;
    private Algorithm[] algorithms;
    private ArrayList<ArrayList<Job>>sequences;
    private int[] makeSpans;
    /**
     * Konstruktor kelas FlowShopScheduler
     * berfungsi untuk menginisialisasi objek kelas FlowShoScheduler
     * Menerima parameter masukan berupa jobsData beritpe ArrayList of Jobs dan machines bertipe Array of Machine
     * @param jobsData
     * @param machinesData 
     */
    public FlowShopScheduler(ArrayList<Job>jobsData, Machine[]machinesData) {
        this.jobs = jobsData;
        this.machines = machinesData;
        this.algorithms = new Algorithm[3];
        this.algorithms[0]= new NEHAlgorithm();
        this.algorithms[1]= new PalmerAlgorithm();
        this.algorithms[2]= new GuptaAlgorithm();
        this.sequences = new ArrayList<ArrayList<Job>>();
        this.makeSpans = new int[3];
    }
    
    /**
     * Method untuk mendapatkan nilai dari atribut jobs
     * Method ini mengembalikan atribut jobs dalam bentuk ArrayList of Job
     * @return 
     */
    public ArrayList<Job> getJobs() {
        return jobs;
    }

    /**
     * Method untuk mendapatkan nilai dari atribut machines
     * Method ini mengembalikan atribut jobs dalam bentuk Array of Machine
     * @return 
     */
    public Machine[] getMachines() {
        return machines;
    }

   /**
     * Method untuk mendapatkan melakukan penjadwalan flow shop
     * Method ini melakukan penjadwalan dengan algoritma NEH, Palmer, dan Gupta
     * Method ini memasukan sequence hasil penjadwalan ke dalam atribut sequence 
     */
    public void schedule() {
        ArrayList<Job> tempSequence = new ArrayList<Job>();
        for(int i = 0;i<this.algorithms.length;i++){
            tempSequence = this.algorithms[i].schedule(this.jobs, this.machines);
            this.sequences.add(tempSequence);
        }
    }
    
    /**
     * Method untuk mendapatkan atrbiut sequence secara spesifik
     * Method ini mengembalikan sequence spesifik berdasarkan parameter index
     * 0 = sequence NEH, 1 = sequence Palmer, 2 = sequence Gupta
     * Mehtod ini mengmbalikan sequence yang dipilih dalam bentuk ArraylIst of job
     * @parma index
     * @return 
     */
    public ArrayList<Job> getSequence(int index) {
        return this.sequences.get(index);
    }

    /**
     * Method untuk menghitung nilai make span dari sequence hasil penjadwalan
     * Method ini akan memasukan nilai make span dari masing-masing algoritma ke dalam atribut makeSoans
     * 0 = make span NEH, 1 = make span Palmer, 2 = make span Gupta
     */
    public void computeMakeSpan() {
        for(int i = 0;i< this.makeSpans.length;i++){
            ArrayList<Job> temp = this.getSequence(i);
            this.makeSpans[i] = this.algorithms[i].computeMakeSpan(temp, this.machines);
        }
    }
    
    /**
     * Method untuk mendapatkan nilai make span dari sequence hasil penjadwalan
     * Method ini menerima parameter masukan berupa index bertipe int
     * 0 = make span NEH, 1 = make span Palmer, 2 = make span Gupta
     * Method ini mengembalikan nilai make span spesifik berdasarkan index 
     * @param index int
     * @return make span
     */
    public int getSelectedMakeSpan(int index) {
        return this.makeSpans[index];
    }
}
