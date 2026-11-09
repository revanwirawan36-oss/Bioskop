package com.mycompany.bioskop;

public class LoketTiket {
    private int jumlahLoket;

    public LoketTiket(int jumlahLoket) {
        this.jumlahLoket = jumlahLoket;
    }

    public void bukaLoket() {
        System.out.println("-> [Loket Tiket] dibuka. Loket aktif: " + jumlahLoket + ".");
    }
}