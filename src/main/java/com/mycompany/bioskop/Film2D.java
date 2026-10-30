package com.mycompany.bioskop;

/**
 * Subclass: film format reguler 2D.
 */
public class Film2D extends Film {
    private String bahasaSubtitle;

    public Film2D(String judul, String genre, int durasiMenit, double hargaDasar,
                  String studio, String jamTayang, String bahasaSubtitle) throws Exception {
        super(judul, genre, durasiMenit, hargaDasar, studio, jamTayang);
        setBahasaSubtitle(bahasaSubtitle);
    }

    public String getBahasaSubtitle() {
        return bahasaSubtitle;
    }

    public void setBahasaSubtitle(String bahasaSubtitle) throws Exception {
        if (bahasaSubtitle == null || bahasaSubtitle.trim().isEmpty()) {
            throw new Exception("Bahasa subtitle tidak boleh kosong.");
        }
        this.bahasaSubtitle = bahasaSubtitle.trim();
    }

    @Override
    public String getTipe() {
        return "2D";
    }

    @Override
    public double hitungHargaTiket() {
        return super.hitungHargaTiket(); // 2D memakai harga dasar
    }

    @Override
    public void tampilkanInfo() {
        super.tampilkanInfo();
        System.out.printf(" | %-4s | Subtitle: %s%n", getTipe(), bahasaSubtitle);
    }

    @Override
    public void simulasiTayang() {
        super.simulasiTayang();
        System.out.println("   [2D] Proyektor standar menyala, subtitle " + bahasaSubtitle + " ditampilkan.");
    }
}