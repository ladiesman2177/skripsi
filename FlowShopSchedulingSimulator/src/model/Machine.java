/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 * Kelas machine 
 * Kelas yang merepresentasikan objek machine
 * Kelas ini memiliki atribut :
 * String name
 * int currentTime
 * @author Tedi Tri Ramdani 
 */
public class Machine {

    private String name;
    private int currentTime;
    
    /**
     * Method konstruktor kelas Machine
     * Method ini berfungsi untuk menginisialisasi objek kelas Machine
     * menerima parameter masukan berupa name bertipe String
     * @param name 
     */
    public Machine(String name) {
        this.name = name;
        this.currentTime = 0;
    }
    
    /**
     * Method setName
     * Method ini berfungsi untuk mengubah nilai atribut name pada kelas Machine
     * Menerima parameter masukan berupa nem bertipe String
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(int currentTime) {
        this.currentTime = currentTime;
    }
}
