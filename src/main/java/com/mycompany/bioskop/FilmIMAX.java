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
 * Subclass: film format IMAX (layar besar, harga premium).
 */
public class FilmIMAX extends Film {
    private static final double BIAYA_PREMIUM = 0.40; // +40% dari harga dasar

    private double ukuranLayar; // dalam meter

    public FilmIMAX(String judul, String genre, int durasiMenit, double hargaDasar,
                    String studio, String jamTayang, double ukuranLayar) {
        super(judul, genre, durasiMenit, hargaDasar, studio, jamTayang);
        setUkuranLayar(ukuranLayar);
    }

    public double getUkuranLayar() {
        return ukuranLayar;
    }//

    public void setUkuranLayar(double ukuranLayar) {
        if (ukuranLayar <= 0) {
            throw new IllegalArgumentException("Ukuran layar harus lebih dari 0 meter.");
        }
        this.ukuranLayar = ukuranLayar;
    }

    @Override
    public double hitungHargaTiket() {
        return super.hitungHargaTiket() * (1 + BIAYA_PREMIUM);
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf(" | %-4s | Layar: %.1f m%n", "IMAX", ukuranLayar);
    }
}