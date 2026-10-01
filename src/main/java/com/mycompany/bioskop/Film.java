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
 * Superclass: representasi sebuah film di bioskop.
 */
public class Film {
    private String judul;
    private String genre;
    private int durasiMenit;
    private double hargaDasar;
    private String studio;
    private String jamTayang;

    // Static: pencatat total objek film yang berhasil dibuat
    private static int totalFilmDibuat = 0;

    public Film(String judul, String genre, int durasiMenit, double hargaDasar,
                String studio, String jamTayang) {
        // Memakai setter agar validasi ikut berjalan saat objek dibuat
        setJudul(judul);
        setGenre(genre);
        setDurasiMenit(durasiMenit);
        setHargaDasar(hargaDasar);
        setStudio(studio);
        setJamTayang(jamTayang);
        totalFilmDibuat++; // hanya naik jika semua validasi lolos
    }

    // ===== Getter =====
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

    // ===== Setter dengan validasi =====
    public void setJudul(String judul) {
        if (judul == null || judul.trim().isEmpty()) {
            throw new IllegalArgumentException("Judul film tidak boleh kosong.");
        }
        this.judul = judul.trim();
    }

    public void setGenre(String genre) {
        if (genre == null || genre.trim().isEmpty()) {
            throw new IllegalArgumentException("Genre tidak boleh kosong.");
        }
        this.genre = genre.trim();
    }

    public void setDurasiMenit(int durasiMenit) {
        if (durasiMenit <= 0 || durasiMenit > 300) {
            throw new IllegalArgumentException("Durasi harus 1-300 menit.");
        }
        this.durasiMenit = durasiMenit;
    }

    public void setHargaDasar(double hargaDasar) {
        if (hargaDasar <= 0) {
            throw new IllegalArgumentException("Harga dasar harus lebih dari 0.");
        }
        this.hargaDasar = hargaDasar;
    }

    public void setStudio(String studio) {
        if (studio == null || studio.trim().isEmpty()) {
            throw new IllegalArgumentException("Studio tidak boleh kosong.");
        }
        this.studio = studio.trim();
    }

    public void setJamTayang(String jamTayang) {
        // Format HH:mm, contoh 19:30
        if (jamTayang == null || !jamTayang.trim().matches("([01]\\d|2[0-3]):[0-5]\\d")) {
            throw new IllegalArgumentException("Jam tayang harus berformat HH:mm (contoh 19:30).");
        }
        this.jamTayang = jamTayang.trim();
    }

    // ===== Method yang akan di-override subclass =====
    public double hitungHargaTiket() {
        return hargaDasar;
    }

    // Mencetak bagian umum satu baris tabel (tanpa newline),
    // subclass melanjutkan dengan tipe dan info khususnya.
    public void tampilkanInfo() {
        System.out.printf("%-22s | %-10s | %3d mnt | %-9s | %-5s | Rp%,10.0f",
                judul, genre, durasiMenit, studio, jamTayang, hitungHargaTiket());
    }
}