/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author A S U S
 */
package com.mycompany.bioskop;

/**
 * Subclass: film format 3D (ada biaya kacamata).
 */
public class Film3D extends Film {
    private double biayaKacamata;

    public Film3D(String judul, String genre, int durasiMenit, double hargaDasar,
                  String studio, String jamTayang, double biayaKacamata) {
        super(judul, genre, durasiMenit, hargaDasar, studio, jamTayang);
        setBiayaKacamata(biayaKacamata);
    }

    public double getBiayaKacamata() {
        return biayaKacamata;
    }

    public void setBiayaKacamata(double biayaKacamata) {
        if (biayaKacamata < 0) {
            throw new IllegalArgumentException("Biaya kacamata tidak boleh negatif.");
        }
        this.biayaKacamata = biayaKacamata;
    }

    @Override
    public double hitungHargaTiket() {
        return super.hitungHargaTiket() + biayaKacamata;
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf(" | %-4s | Kacamata 3D: Rp%,.0f%n", "3D", biayaKacamata);
    }
}