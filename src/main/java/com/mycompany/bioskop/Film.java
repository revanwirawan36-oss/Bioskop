package com.mycompany.bioskop;


public abstract class Film {
    private String judul;
    private String genre;
    private int durasiMenit;
    private double hargaDasar;
    private String studio;
    private String jamTayang;


    private static int totalFilmDibuat = 0;

    public Film(String judul, String genre, int durasiMenit, double hargaDasar,
                String studio, String jamTayang) throws Exception {

        setJudul(judul);
        setGenre(genre);
        setDurasiMenit(durasiMenit);
        setHargaDasar(hargaDasar);
        setStudio(studio);
        setJamTayang(jamTayang);
        totalFilmDibuat++; 
    }


    public String getJudul() {
        return judul;
    }

    public String getGenre() {
        return genre;
    }

    public int getDurasiMenit() {
        return durasiMenit;
    }

    public double getHargaDasar() {
        return hargaDasar;
    }

    public String getStudio() {
        return studio;
    }

    public String getJamTayang() {
        return jamTayang;
    }

    public static int getTotalFilmDibuat() {
        return totalFilmDibuat;
    }

    //setter dengan validasi
    public void setJudul(String judul) throws Exception {
        if (judul == null || judul.trim().isEmpty()) {
            throw new Exception("Judul film tidak boleh kosong.");
        }
        this.judul = judul.trim();
    }

    public void setGenre(String genre) throws Exception {
        if (genre == null || genre.trim().isEmpty()) {
            throw new Exception("Genre tidak boleh kosong.");
        }
        this.genre = genre.trim();
    }

    public void setDurasiMenit(int durasiMenit) throws Exception {
        if (durasiMenit <= 0 || durasiMenit > 300) {
            throw new Exception("Durasi harus 1-300 menit.");
        }
        this.durasiMenit = durasiMenit;
    }

    public void setHargaDasar(double hargaDasar) throws Exception {
        if (hargaDasar <= 0) {
            throw new Exception("Harga dasar harus lebih dari 0.");
        }
        this.hargaDasar = hargaDasar;
    }

    public void setStudio(String studio) throws Exception {
        if (studio == null || studio.trim().isEmpty()) {
            throw new Exception("Studio tidak boleh kosong.");
        }
        this.studio = studio.trim();
    }

    public void setJamTayang(String jamTayang) throws Exception {

        if (jamTayang == null || !jamTayang.trim().matches("([01]\\d|2[0-3]):[0-5]\\d")) {
            throw new Exception("Jam tayang harus berformat HH:mm (contoh 19:30).");
        }
        this.jamTayang = jamTayang.trim();
    }

   
    public abstract String getTipe();

    public double hitungHargaTiket() {
        return hargaDasar;
    }


    public void tampilkanInfo() {
        System.out.printf("%-22s | %-12s | %3d mnt | %-10s | %-5s | Rp%,10.0f",
                judul, genre, durasiMenit, studio, jamTayang, hitungHargaTiket());
    }

    public void simulasiTayang() {
        System.out.printf("Memutar \"%s\" di %s pukul %s (%d menit).%n",
                judul, studio, jamTayang, durasiMenit);
    }
}