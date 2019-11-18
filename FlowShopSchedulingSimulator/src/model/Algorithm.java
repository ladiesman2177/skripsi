/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.util.ArrayList;

/**
 * Kelas Algorithm
 * merupakan kelas abstrak untuk objek Algorithm
 * @author Tedi Tri Ramdani 2015730001
 */
public abstract class Algorithm {
    /**
     * method abstark schedule
     * @param jobs
     * @param machines
     * @return 
     * merupakan cetak biru untuk method schedule dari masing-masing algoritma penjadwalan
     * Menerima parameter masukan berupa jobs bertipe ArrayList of job dan machines bertipe array of machine
     * Method ini mengembalikan sequence hasil penjadwalan berupa arraylist of job
     */
    protected abstract ArrayList<Job>schedule(ArrayList<Job> jobs, Machine[]machines);
    
    /**
     * method computeMakeSpan
     * @param sequence
     * @param machines
     * @return 
     * merupakan cetak biru untuk method computeMakeSpan dari masing-masing algoritma penjadwalan
     * Menerima parameter masukan berupa Sequence bertipe ArrayList of job dan machines bertipe array of machine
     * Method ini mengembalikan total nilai make span dari sequence hasil penjadwalan
     */
    protected abstract int computeMakeSpan(ArrayList<Job> sequence, Machine[]machines);
}
