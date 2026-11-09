package com.mycompany.bioskop;

import java.util.ArrayList;
import java.util.HashMap;

public class SmartCinema {
    private String namaBioskop;

    private Lobi lobi;
    private LoketTiket loket;

    private ArrayList<Film> daftarFilm;
    private HashMap<String, Pelanggan> dataPelanggan;

    public SmartCinema(String namaBioskop) {
        this.namaBioskop = namaBioskop;

        // komposisi: komponen dibuat di dalam constructor
        this.lobi = new Lobi("Lobi Utama", 40);
        this.loket = new LoketTiket(4);

        this.daftarFilm = new ArrayList<>();
        this.dataPelanggan = new HashMap<>();
    }

    public void bukaBioskop() {
        System.out.println("\n*** Menginisialisasi sistem " + this.namaBioskop + " ***");
        lobi.bukaLobi();
        loket.bukaLoket();
    }

    public void infoGedung() {
        System.out.println("\n--- Info Gedung " + namaBioskop + " ---");
        lobi.bukaLobi();
        loket.bukaLoket();
        System.out.println("Total film     : " + daftarFilm.size());
        System.out.println("Total pelanggan: " + dataPelanggan.size());
    }

    // ===== film (agregasi: arraylist) =====
    public void tambahFilm(Film f) {
        daftarFilm.add(f);
        System.out.println("Sukses! Film \"" + f.getJudul() + "\" berhasil ditambahkan. (Total film: " + daftarFilm.size() + ")");
    }

    public void lihatSemuaFilm() {
        if (daftarFilm.isEmpty()) {
            System.out.println("Belum ada film yang tersimpan.");
            return;
        }
        cetakHeader();
        for (int i = 0; i < daftarFilm.size(); i++) {
            System.out.printf("%-3d", i + 1);
            daftarFilm.get(i).tampilkanInfo();
        }
        System.out.println("Total film dibuat (counter static): " + Film.getTotalFilmDibuat());
    }

    private void cetakHeader() {
        System.out.printf("%-3s%-22s | %-12s | %-7s | %-10s | %-5s | %-12s | %-4s | %s%n",
                "No", "Judul", "Genre", "Durasi", "Studio", "Jam", "Harga Tiket", "Tipe", "Info Khusus");
        System.out.println("-".repeat(128));
    }

    public void cariFilm(String judul) throws FilmTidakDitemukanException {
        System.out.println("\nHasil pencarian judul \"" + judul + "\":");
        cetakHeader();
        int ditemukan = 0;
        for (int i = 0; i < daftarFilm.size(); i++) {
            if (daftarFilm.get(i).getJudul().toLowerCase().contains(judul.trim().toLowerCase())) {
                System.out.printf("%-3d", i + 1);
                daftarFilm.get(i).tampilkanInfo();
                ditemukan++;
            }
        }
        if (ditemukan == 0) {
            throw new FilmTidakDitemukanException("Gagal: film dengan judul '" + judul + "' tidak terdaftar di sistem.");
        }
        System.out.println("Ditemukan " + ditemukan + " film.");
    }

    public void cariFilm(String genre, double hargaMaks) throws FilmTidakDitemukanException {
        System.out.println("\nHasil pencarian genre \"" + genre + "\" dengan harga <= Rp"
                + String.format("%,.0f", hargaMaks) + ":");
        cetakHeader();
        int ditemukan = 0;
        for (int i = 0; i < daftarFilm.size(); i++) {
            Film f = daftarFilm.get(i);
            if (f.getGenre().equalsIgnoreCase(genre.trim()) && f.hitungHargaTiket() <= hargaMaks) {
                System.out.printf("%-3d", i + 1);
                f.tampilkanInfo();
                ditemukan++;
            }
        }
        if (ditemukan == 0) {
            throw new FilmTidakDitemukanException("Gagal: tidak ada film genre '" + genre + "' dengan harga <= Rp"
                    + String.format("%,.0f", hargaMaks) + ".");
        }
        System.out.println("Ditemukan " + ditemukan + " film.");
    }

    public void cariFilm(int durasiMaks) throws FilmTidakDitemukanException {
        System.out.println("\nHasil pencarian film dengan durasi <= " + durasiMaks + " menit:");
        cetakHeader();
        int ditemukan = 0;
        for (int i = 0; i < daftarFilm.size(); i++) {
            if (daftarFilm.get(i).getDurasiMenit() <= durasiMaks) {
                System.out.printf("%-3d", i + 1);
                daftarFilm.get(i).tampilkanInfo();
                ditemukan++;
            }
        }
        if (ditemukan == 0) {
            throw new FilmTidakDitemukanException("Gagal: tidak ada film dengan durasi <= " + durasiMaks + " menit.");
        }
        System.out.println("Ditemukan " + ditemukan + " film.");
    }

    // ===== pelanggan (agregasi: hashmap) =====
    public void tambahPelanggan(Pelanggan p) throws Exception {
        if (dataPelanggan.containsKey(p.getId())) {
            throw new Exception("ID " + p.getId() + " sudah terdaftar atas nama "
                    + dataPelanggan.get(p.getId()).getNama() + ".");
        }
        dataPelanggan.put(p.getId(), p);
    }

    public void cariPelanggan(String id) {
        String key = id.trim().toUpperCase();
        if (dataPelanggan.containsKey(key)) {
            System.out.println("Ditemukan! Nama: " + dataPelanggan.get(key).getNama());
        } else {
            System.out.println("Pelanggan dengan ID tersebut tidak terdaftar.");
        }
    }

    public void lihatSemuaPelanggan() {
        System.out.println("Total Pelanggan Terdaftar: " + dataPelanggan.size());
        for (Pelanggan p : dataPelanggan.values()) {
            System.out.println("- ID: " + p.getId() + " | Nama: " + p.getNama());
        }
    }

    // ===== getter =====
    public Pelanggan getPelanggan(String id) {
        return dataPelanggan.get(id.trim().toUpperCase());
    }

    public Film getFilm(int index) {
        return daftarFilm.get(index);
    }

    public int getJumlahFilm() {
        return daftarFilm.size();
    }

    public int getJumlahPelanggan() {
        return dataPelanggan.size();
    }
}