package com.mycompany.bioskop;

public class Lobi {
    private String namaRuangan;
    private int kapasitasKursi;

    public Lobi(String namaRuangan, int kapasitasKursi) {
        this.namaRuangan = namaRuangan;
        this.kapasitasKursi = kapasitasKursi;
    }

    public void bukaLobi() {
        System.out.println("-> [" + namaRuangan + "] dibuka. Kapasitas: " + kapasitasKursi + " kursi.");
    }
}