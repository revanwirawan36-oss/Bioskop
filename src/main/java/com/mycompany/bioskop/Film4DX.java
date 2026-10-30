package com.mycompany.bioskop;

/**
 * 
 */
public class Film4DX extends Film implements Fasilitas {
    private static final double BIAYA_PER_EFEK = 5000; 

    private int jumlahEfek; 
    public Film4DX(String judul, String genre, int durasiMenit, double hargaDasar,
                   String studio, String jamTayang, int jumlahEfek) throws Exception {
        super(judul, genre, durasiMenit, hargaDasar, studio, jamTayang);
        setJumlahEfek(jumlahEfek);
    }

    public int getJumlahEfek() {
        return jumlahEfek;
    }

    public void setJumlahEfek(int jumlahEfek) throws Exception {
        if (jumlahEfek < 1 || jumlahEfek > 10) {
            throw new Exception("Jumlah efek harus 1-10.");
        }
        this.jumlahEfek = jumlahEfek;
    }

    @Override
    public String getTipe() {
        return "4DX";
    }

    @Override
    public String getDeskripsiFasilitas() {
        return "Kursi bergerak dengan " + jumlahEfek + " efek khusus";
    }

    @Override
    public double hitungHargaTiket() {
        return super.hitungHargaTiket() + (jumlahEfek * BIAYA_PER_EFEK);
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf(" | %-4s | Efek khusus: %d jenis%n", getTipe(), jumlahEfek);
    }

    @Override
    public void simulasiTayang() {
        super.simulasiTayang();
        System.out.println("   [4DX] Kursi bergerak aktif, " + jumlahEfek
                + " efek (angin, air, getaran, dll.) disinkronkan dengan adegan.");
    }
}