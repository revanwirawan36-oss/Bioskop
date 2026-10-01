/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.bioskop;

/**
 *
 * @author A S U S
 */

import java.util.Scanner;

/**
 * Class utama: menu CLI Sistem Manajemen Bioskop.
 */
public class Bioskop {
    private static final Scanner scanner = new Scanner(System.in);
    private static final Film[] daftarFilm = new Film[10];
    private static int jumlahFilm = 0;

    public static void main(String[] args) {
        isiDataAwal();
        boolean isRunning = true;

        System.out.println("==================================");
        System.out.println("   Selamat Datang di Smart Cinema");
        System.out.println("==================================");

        while (isRunning) {
            System.out.println("\n=== MENU UTAMA ===");
            System.out.println("1. Tambah Film Baru");
            System.out.println("2. Tampilkan Seluruh Film");
            System.out.println("3. Cari Film");
            System.out.println("4. Beli Tiket");
            System.out.println("5. Keluar");
            int pilihan = bacaInt("Pilih Menu (1-5): ");

            switch (pilihan) {
                case 1 -> menuTambahFilm();
                case 2 -> tampilkanSemuaFilm();
                case 3 -> menuCariFilm();
                case 4 -> menuBeliTiket();
                case 5 -> {
                    System.out.println("Terima kasih telah menggunakan Smart Cinema!");
                    isRunning = false;
                }
                default -> System.out.println("Pilihan tidak valid. Masukkan angka 1-5.");
            }
        }
        scanner.close();
    }

    // ===== Data awal (5 film: 2D, 3D, IMAX) =====
    private static void isiDataAwal() {
        tambahKeArray(new Film2D("Laskar Pelangi 2", "Drama", 110, 45000, "Studio 1", "13:00", "Indonesia"));
        tambahKeArray(new Film2D("Pengabdi Setan 3", "Horor", 105, 45000, "Studio 2", "19:30", "Inggris"));
        tambahKeArray(new Film3D("Avatar: Samudra", "Fiksi Ilmiah", 160, 50000, "Studio 3", "16:00", 10000));
        tambahKeArray(new Film3D("Kung Fu Panda 5", "Animasi", 95, 50000, "Studio 3", "10:30", 10000));
        tambahKeArray(new FilmIMAX("Dune: Kiamat", "Aksi", 150, 60000, "IMAX", "20:00", 26.5));
    }

    private static boolean tambahKeArray(Film film) {
        if (jumlahFilm < daftarFilm.length) {
            daftarFilm[jumlahFilm] = film;
            jumlahFilm++;
            return true;
        }
        return false;
    }

    // ===== Menu 1: Tambah Film =====
    private static void menuTambahFilm() {
        if (jumlahFilm >= daftarFilm.length) {
            System.out.println("Maaf, kapasitas daftar film sudah penuh!");
            return;
        }

        System.out.println("\n--- Form Tambah Film ---");
        System.out.println("Pilih tipe film:");
        System.out.println("1. Film 2D");
        System.out.println("2. Film 3D");
        System.out.println("3. Film IMAX");
        int tipe = bacaInt("Pilih tipe (1-3): ");

        if (tipe < 1 || tipe > 3) {
            System.out.println("Tipe tidak valid. Penambahan dibatalkan.");
            return;
        }

        try {
            String judul = bacaString("Judul          : ");
            String genre = bacaString("Genre          : ");
            int durasi = bacaInt("Durasi (menit) : ");
            double harga = bacaDouble("Harga dasar    : ");
            String studio = bacaString("Studio         : ");
            String jam = bacaString("Jam tayang (HH:mm): ");

            Film filmBaru;
            if (tipe == 1) {
                String subtitle = bacaString("Bahasa subtitle: ");
                filmBaru = new Film2D(judul, genre, durasi, harga, studio, jam, subtitle);
            } else if (tipe == 2) {
                double kacamata = bacaDouble("Biaya kacamata 3D: ");
                filmBaru = new Film3D(judul, genre, durasi, harga, studio, jam, kacamata);
            } else {
                double layar = bacaDouble("Ukuran layar (meter): ");
                filmBaru = new FilmIMAX(judul, genre, durasi, harga, studio, jam, layar);
            }

            tambahKeArray(filmBaru);
            System.out.println("Sukses! Film \"" + filmBaru.getJudul() + "\" berhasil ditambahkan.");
        } catch (IllegalArgumentException e) {
            System.out.println("Gagal menambah film: " + e.getMessage());
        }
    }

    // ===== Menu 2: Tampilkan Semua =====
    private static void tampilkanSemuaFilm() {
        System.out.println("\n--- Daftar Film Smart Cinema ---");
        if (jumlahFilm == 0) {
            System.out.println("Belum ada film yang tersimpan.");
            return;
        }
        cetakHeader();
        for (int i = 0; i < jumlahFilm; i++) {
            System.out.printf("%-3d", i + 1);
            daftarFilm[i].tampilkanInfo(); // polymorphism: memanggil versi override
        }
        System.out.println("Total film dibuat (counter static): " + Film.getTotalFilmDibuat());
    }

    private static void cetakHeader() {
        System.out.printf("%-3s%-22s | %-10s | %-7s | %-9s | %-5s | %-12s | %-4s | %s%n",
                "No", "Judul", "Genre", "Durasi", "Studio", "Jam", "Harga Tiket", "Tipe", "Info Khusus");
        System.out.println("-".repeat(125));
    }

    // ===== Menu 3: Cari Film (method overloading) =====
    private static void menuCariFilm() {
        System.out.println("\n--- Cari Film ---");
        System.out.println("1. Berdasarkan judul");
        System.out.println("2. Berdasarkan genre dan harga tiket maksimal");
        System.out.println("3. Berdasarkan durasi maksimal");
        int pilihan = bacaInt("Pilih cara pencarian (1-3): ");

        switch (pilihan) {
            case 1 -> cariFilm(bacaString("Kata kunci judul: "));
            case 2 -> {
                String genre = bacaString("Genre: ");
                double hargaMaks = bacaDouble("Harga tiket maksimal: ");
                cariFilm(genre, hargaMaks);
            }
            case 3 -> cariFilm(bacaInt("Durasi maksimal (menit): "));
            default -> System.out.println("Pilihan tidak valid.");
        }
    }

    // Overloading 1: parameter String (judul)
    private static void cariFilm(String judul) {
        System.out.println("\nHasil pencarian judul \"" + judul + "\":");
        cetakHeader();
        int ditemukan = 0;
        for (int i = 0; i < jumlahFilm; i++) {
            if (daftarFilm[i].getJudul().toLowerCase().contains(judul.trim().toLowerCase())) {
                System.out.printf("%-3d", i + 1);
                daftarFilm[i].tampilkanInfo();
                ditemukan++;
            }
        }
        cetakRingkasan(ditemukan);
    }

    // Overloading 2: parameter String (genre) dan double (harga maksimal)
    private static void cariFilm(String genre, double hargaMaks) {
        System.out.println("\nHasil pencarian genre \"" + genre + "\" dengan harga <= Rp"
                + String.format("%,.0f", hargaMaks) + ":");
        cetakHeader();
        int ditemukan = 0;
        for (int i = 0; i < jumlahFilm; i++) {
            Film f = daftarFilm[i];
            if (f.getGenre().equalsIgnoreCase(genre.trim()) && f.hitungHargaTiket() <= hargaMaks) {
                System.out.printf("%-3d", i + 1);
                f.tampilkanInfo();
                ditemukan++;
            }
        }
        cetakRingkasan(ditemukan);
    }

    // Overloading 3: parameter int (durasi maksimal)
    private static void cariFilm(int durasiMaks) {
        System.out.println("\nHasil pencarian film dengan durasi <= " + durasiMaks + " menit:");
        cetakHeader();
        int ditemukan = 0;
        for (int i = 0; i < jumlahFilm; i++) {
            if (daftarFilm[i].getDurasiMenit() <= durasiMaks) {
                System.out.printf("%-3d", i + 1);
                daftarFilm[i].tampilkanInfo();
                ditemukan++;
            }
        }
        cetakRingkasan(ditemukan);
    }

    private static void cetakRingkasan(int ditemukan) {
        if (ditemukan == 0) {
            System.out.println("Tidak ada film yang cocok.");
        } else {
            System.out.println("Ditemukan " + ditemukan + " film.");
        }
    }

    // ===== Menu 4: Beli Tiket =====
    private static void menuBeliTiket() {
        if (jumlahFilm == 0) {
            System.out.println("Belum ada film yang bisa dipesan.");
            return;
        }
        tampilkanSemuaFilm();
        int nomor = bacaInt("\nPilih nomor film: ");
        if (nomor < 1 || nomor > jumlahFilm) {
            System.out.println("Nomor film tidak valid.");
            return;
        }
        int jumlahTiket = bacaInt("Jumlah tiket: ");
        if (jumlahTiket <= 0) {
            System.out.println("Jumlah tiket harus lebih dari 0.");
            return;
        }

        Film dipilih = daftarFilm[nomor - 1];
        double total = dipilih.hitungHargaTiket() * jumlahTiket;
        System.out.println("\n--- Struk Pembelian ---");
        System.out.printf("Film         : %s (%s, %s)%n", dipilih.getJudul(), dipilih.getStudio(), dipilih.getJamTayang());
        System.out.printf("Harga tiket  : Rp%,.0f%n", dipilih.hitungHargaTiket());
        System.out.printf("Jumlah       : %d%n", jumlahTiket);
        System.out.printf("Total bayar  : Rp%,.0f%n", total);
    }

    // ===== Helper input =====
    private static String bacaString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int bacaInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka bulat. Coba lagi.");
            }
        }
    }

    private static double bacaDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                return Double.parseDouble(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input harus berupa angka. Coba lagi.");
            }
        }
    }
}
