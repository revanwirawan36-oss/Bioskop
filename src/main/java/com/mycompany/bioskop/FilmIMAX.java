package com.mycompany.bioskop;

/**
 * Subclass: film format IMAX (layar besar, harga premium).
 */
public class FilmIMAX extends Film implements Fasilitas {
    private static final double BIAYA_PREMIUM = 0.40; // +40% dari harga dasar

    private double ukuranLayar; // dalam meter

    public FilmIMAX(String judul, String genre, int durasiMenit, double hargaDasar,
                    String studio, String jamTayang, double ukuranLayar) throws Exception {
        super(judul, genre, durasiMenit, hargaDasar, studio, jamTayang);
        setUkuranLayar(ukuranLayar);
    }

    public double getUkuranLayar() {
        return ukuranLayar;
    }

    public void setUkuranLayar(double ukuranLayar) throws Exception {
        if (ukuranLayar <= 0) {
            throw new Exception("Ukuran layar harus lebih dari 0 meter.");
        }
        this.ukuranLayar = ukuranLayar;
    }

    @Override
    public String getTipe() {
        return "IMAX";
    }

    @Override
    public String getDeskripsiFasilitas() {
        return "Layar raksasa " + ukuranLayar + " m dan audio 12 kanal";
    }

    @Override
    public double hitungHargaTiket() {
        return super.hitungHargaTiket() * (1 + BIAYA_PREMIUM);
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf(" | %-4s | Layar: %.1f m%n", getTipe(), ukuranLayar);
    }

    @Override
    public void simulasiTayang() {
        super.simulasiTayang();
        System.out.printf("   [IMAX] Proyektor laser menyala, layar %.1f m dan audio 12 kanal aktif.%n", ukuranLayar);
    }
}