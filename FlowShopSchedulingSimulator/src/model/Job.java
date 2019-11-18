/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.scene.paint.Color;

/**
 * Kelas job
 * Kelas yang menjadi representasi dari objek Job
 * memeiliki Atribut sebagai berikut :
 * String name
 * int[] processintTimes
 * int dueDate
 * Color color
 * @author Tedi Tri Ramdani
 */
public class Job {
    
    private String name;
    int[] processingTimes;
    private int dueDate;
    private Color color;
    
    /**
     * Method konstruktor kelas Job
     * berfungsi untuk menginisialisasi objek kelas Job
     * Menerima parameter berupa name, subJobs, dan dueDate
     * @param name
     * @param subjobs
     * @param dueDate 
     */
    public Job(String name, int[] subjobs, int dueDate) {
        this.name = name;
        this.processingTimes = subjobs;
        this.dueDate = dueDate;
        this.color = Color.color(Math.random(),Math.random(),Math.random());//waran representasi job dibangkitkan secara random
    }
    
    /**
     * Method getName
     * Method ini befungsi untuk mengembalikan atribut name pada kelas Job
     * @return name
     */
    
    public String getName() {
        return this.name;
    }

    /**
     * method getProcessingTime
     * Method ini digunakan untuk mengembalikan nilai atrbiut processingTime dari kelas Job
     * @return processingTime
     */
    public int[] getProcessingTimes() {
        return this.processingTimes;
    }
    
    /**
     * method getColor
     * Method ini digunakan untuk mengembalikan nilai color dari kelas Job
     * @return color
     */
    public Color getColor(){
        return this.color;
    }
    
    /**
     * method getDueDate
     * Method ini digunakan untuk mengembalikan nilai atrbiut dueDate dari kelas Job
     * @return dueDate
     */
    public int getDueDate(){
        return this.dueDate;
    }
}
