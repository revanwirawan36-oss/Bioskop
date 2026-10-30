package com.mycompany.bioskop;


public class Film3D extends Film implements Fasilitas {
    private double biayaKacamata;

    public Film3D(String judul, String genre, int durasiMenit, double hargaDasar,
                  String studio, String jamTayang, double biayaKacamata) throws Exception {
        super(judul, genre, durasiMenit, hargaDasar, studio, jamTayang);
        setBiayaKacamata(biayaKacamata);
    }

    public double getBiayaKacamata() {
        return biayaKacamata;
    }

    public void setBiayaKacamata(double biayaKacamata) throws Exception {
        if (biayaKacamata < 0) {
            throw new Exception("Biaya kacamata tidak boleh negatif.");
        }
        this.biayaKacamata = biayaKacamata;
    }

    @Override
    public String getTipe() {
        return "3D";
    }

    @Override
    public String getDeskripsiFasilitas() {
        return "Kacamata 3D (biaya Rp" + String.format("%,.0f", biayaKacamata) + ")";
    }

    @Override
    public double hitungHargaTiket() {
        return super.hitungHargaTiket() + biayaKacamata;
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf(" | %-4s | Kacamata 3D: Rp%,.0f%n", getTipe(), biayaKacamata);
    }

    @Override
    public void simulasiTayang() {
        super.simulasiTayang();
        System.out.println("   [3D] Kacamata 3D dibagikan ke penonton, proyektor dual-lens menyala.");
    }
}