/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.bioskop;

/**
 *
 * @author A S U S
 */


/**
 * Subclass: film format reguler 2D.
 */
public class Film2D extends Film {
    private String bahasaSubtitle;

    public Film2D(String judul, String genre, int durasiMenit, double hargaDasar,
                  String studio, String jamTayang, String bahasaSubtitle) {
        super(judul, genre, durasiMenit, hargaDasar, studio, jamTayang);
        setBahasaSubtitle(bahasaSubtitle);
    }

    public String getBahasaSubtitle() {
        return bahasaSubtitle;
    }

    public void setBahasaSubtitle(String bahasaSubtitle) {
        if (bahasaSubtitle == null || bahasaSubtitle.trim().isEmpty()) {
            throw new IllegalArgumentException("Bahasa subtitle tidak boleh kosong.");
        }
        this.bahasaSubtitle = bahasaSubtitle.trim();
    }

    @Override
    public double hitungHargaTiket() {
        return super.hitungHargaTiket(); // 2D memakai harga dasar
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf(" | %-4s | Subtitle: %s%n", "2D", bahasaSubtitle);
    }
}
