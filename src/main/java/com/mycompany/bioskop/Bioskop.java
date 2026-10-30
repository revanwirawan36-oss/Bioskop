package com.mycompany.bioskop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Bioskop {
    private static final Scanner scanner = new Scanner(System.in);

    //arraylist bertipe superclass film, menggantikan array biasa
    private static final ArrayList<Film> daftarFilm = new ArrayList<>();

    //hashmap key = id pelanggan, value = nama pelanggan
    private static final HashMap<String, String> dataPelanggan = new HashMap<>();

    public static void main(String[] args) {
        scanner.useLocale(Locale.US);
        isiDataAwal();
        boolean isRunning = true;

        System.out.println("==================================");
        System.out.println("   Selamat Datang di Smart Cinema");
        System.out.println("==================================");

        while (isRunning) {
            System.out.println("\n=== MENU UTAMA ===");
            System.out.println("1. Tambah Film Baru");
            System.out.println("2. Tampilkan Seluruh Film");
            System.out.println("3. Cari Film (Demo Custom Exception)");
            System.out.println("4. Beli Tiket");
            System.out.println("5. Simulasi Pemutaran Film");
            System.out.println("6. Manajemen Pelanggan (Demo HashMap)");
            System.out.println("7. Keluar");

            //semua logika input menu dibungkus try agar program tidak crash
            try {
                int pilihan = bacaInt("Pilih Menu (1-7): ");

                switch (pilihan) {
                    case 1 -> menuTambahFilm();
                    case 2 -> tampilkanSemuaFilm();
                    case 3 -> menuCariFilm();
                    case 4 -> menuBeliTiket();
                    case 5 -> menuSimulasi();
                    case 6 -> menuPelanggan();
                    case 7 -> {
                        System.out.println("Terima kasih telah menggunakan Smart Cinema!");
                        isRunning = false;
                    }
                    default -> System.out.println("Pilihan tidak valid. Masukkan angka 1-7.");
                }
            } catch (InputMismatchException e) {
                //terjadi jika user mengetik huruf saat program meminta angka
                System.out.println("\n[FATAL ERROR] Input tidak valid! Anda harus memasukkan angka.");
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                //input stream habis (misal tekan ctrl+d), program berhenti dengan aman
                System.out.println("\n[INFO] Input berakhir, program ditutup.");
                isRunning = false;
            } catch (Exception e) {
                System.out.println("\n[ERROR SISTEM] Terjadi kegagalan: " + e.getMessage());
            }
        }
        scanner.close();
    }

    //data awal: 6 film di arraylist dan 3 pelanggan di hashmap
    private static void isiDataAwal() {
        try {
            //upcasting: objek subclass disimpan sebagai tipe film
            daftarFilm.add(new Film2D("Laskar Pelangi 2", "Drama", 110, 45000, "Studio 1", "13:00", "Indonesia"));
            daftarFilm.add(new Film2D("Pengabdi Setan 3", "Horor", 105, 45000, "Studio 2", "19:30", "Inggris"));
            daftarFilm.add(new Film3D("Avatar: Samudra", "Fiksi Ilmiah", 160, 50000, "Studio 3", "16:00", 10000));
            daftarFilm.add(new Film3D("Kung Fu Panda 5", "Animasi", 95, 50000, "Studio 3", "10:30", 10000));
            daftarFilm.add(new FilmIMAX("Dune: Kiamat", "Aksi", 150, 60000, "IMAX", "20:00", 26.5));
            daftarFilm.add(new Film4DX("Fast Racer X", "Aksi", 125, 55000, "Studio 4DX", "21:15", 6));
        } catch (Exception e) {
            System.out.println("[ERROR] Gagal mengisi data film awal: " + e.getMessage());
        }

        dataPelanggan.put("PLG001", "Budi Santoso");
        dataPelanggan.put("PLG002", "Siti Aminah");
        dataPelanggan.put("PLG003", "Rina Wijaya");
    }

    //===== menu 1: tambah film =====
    private static void menuTambahFilm() {
        System.out.println("\n--- Form Tambah Film ---");
        System.out.println("Pilih tipe film:");
        System.out.println("1. Film 2D");
        System.out.println("2. Film 3D");
        System.out.println("3. Film IMAX");
        System.out.println("4. Film 4DX");
        int tipe = bacaInt("Pilih tipe (1-4): ");

        if (tipe < 1 || tipe > 4) {
            System.out.println("Tipe tidak valid. Penambahan dibatalkan.");
            return;
        }

        //constructor film memakai throws exception, jadi pemanggilnya wajib try-catch
        try {
            String judul = bacaString("Judul          : ");
            String genre = bacaString("Genre          : ");
            int durasi = bacaInt("Durasi (menit) : ");
            double harga = bacaDouble("Harga dasar    : ");
            String studio = bacaString("Studio         : ");
            String jam = bacaString("Jam tayang (HH:mm): ");

            Film filmBaru; //referensi superclass
            if (tipe == 1) {
                String subtitle = bacaString("Bahasa subtitle: ");
                filmBaru = new Film2D(judul, genre, durasi, harga, studio, jam, subtitle);
            } else if (tipe == 2) {
                double kacamata = bacaDouble("Biaya kacamata 3D: ");
                filmBaru = new Film3D(judul, genre, durasi, harga, studio, jam, kacamata);
            } else if (tipe == 3) {
                double layar = bacaDouble("Ukuran layar (meter): ");
                filmBaru = new FilmIMAX(judul, genre, durasi, harga, studio, jam, layar);
            } else {
                int efek = bacaInt("Jumlah efek khusus (1-10): ");
                filmBaru = new Film4DX(judul, genre, durasi, harga, studio, jam, efek);
            }

            daftarFilm.add(filmBaru);
            System.out.println("Sukses! Film \"" + filmBaru.getJudul() + "\" berhasil ditambahkan. (Total film: " + daftarFilm.size() + ")");
        } catch (InputMismatchException e) {
            //dilempar ulang agar ditangkap oleh try-catch di main
            throw e;
        } catch (Exception e) {
            System.out.println("[GAGAL] Tambah film dibatalkan: " + e.getMessage());
        }
    }

    //===== menu 2: tampilkan semua film =====
    private static void tampilkanSemuaFilm() {
        System.out.println("\n--- Daftar Film Smart Cinema ---");
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

    private static void cetakHeader() {
        System.out.printf("%-3s%-22s | %-12s | %-7s | %-10s | %-5s | %-12s | %-4s | %s%n",
                "No", "Judul", "Genre", "Durasi", "Studio", "Jam", "Harga Tiket", "Tipe", "Info Khusus");
        System.out.println("-".repeat(128));
    }

    //===== menu 3: cari film dengan custom exception =====
    private static void menuCariFilm() {
        System.out.println("\n--- Cari Film ---");
        System.out.println("1. Berdasarkan judul");
        System.out.println("2. Berdasarkan genre dan harga tiket maksimal");
        System.out.println("3. Berdasarkan durasi maksimal");
        int pilihan = bacaInt("Pilih cara pencarian (1-3): ");

        try {
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
        } catch (FilmTidakDitemukanException e) {
            System.out.println("Peringatan: " + e.getMessage());
        } finally {
            //finally selalu dieksekusi, ada error ataupun tidak
            System.out.println("-> Sesi pencarian selesai dieksekusi.");
        }
    }

    //overloading 1: parameter string (judul)
    private static void cariFilm(String judul) throws FilmTidakDitemukanException {
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

    //overloading 2: parameter string (genre) dan double (harga maksimal)
    private static void cariFilm(String genre, double hargaMaks) throws FilmTidakDitemukanException {
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

    //overloading 3: parameter int (durasi maksimal)
    private static void cariFilm(int durasiMaks) throws FilmTidakDitemukanException {
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

    //===== menu 4: beli tiket =====
    private static void menuBeliTiket() {
        if (daftarFilm.isEmpty()) {
            System.out.println("Belum ada film yang bisa dipesan.");
            return;
        }
        tampilkanSemuaFilm();
        int nomor = bacaInt("\nPilih nomor film: ");
        int jumlahTiket = bacaInt("Jumlah tiket: ");

        try {
            //throws: validasi dilempar ke pemanggil dan ditangani di catch
            validasiPemesanan(nomor, jumlahTiket);

            double diskon = 0;
            String pakaiPromo = bacaString("Punya kode promo? (y/n): ");
            if (pakaiPromo.trim().equalsIgnoreCase("y")) {
                diskon = bacaDouble("Diskon promo (%, 1-50): ");
                if (diskon < 1 || diskon > 50) {
                    System.out.println("Diskon di luar rentang, promo tidak dipakai.");
                    diskon = 0;
                }
            }

            //upcasting pada parameter: argumen bisa objek film2d/film3d/filmimax/film4dx
            prosesPemesanan(daftarFilm.get(nomor - 1), jumlahTiket, diskon);
        } catch (Exception e) {
            System.out.println("[GAGAL] Pemesanan dibatalkan: " + e.getMessage());
        }
    }

    //throws exception: method ini mungkin melempar error validasi
    private static void validasiPemesanan(int nomor, int jumlahTiket) throws Exception {
        if (nomor < 1 || nomor > daftarFilm.size()) {
            throw new Exception("Nomor film tidak valid (pilih 1-" + daftarFilm.size() + ").");
        }
        if (jumlahTiket <= 0) {
            throw new Exception("Jumlah tiket harus lebih dari 0.");
        }
    }

    private static double hitungTotal(Film film) {
        return film.hitungHargaTiket();
    }

    private static double hitungTotal(Film film, int jumlah) {
        return film.hitungHargaTiket() * jumlah;
    }

    private static double hitungTotal(Film film, int jumlah, double diskonPersen) {
        double subtotal = hitungTotal(film, jumlah);
        return subtotal - (subtotal * diskonPersen / 100);
    }

    private static void prosesPemesanan(Film film, int jumlah, double diskonPersen) {
        double total = (diskonPersen > 0)
                ? hitungTotal(film, jumlah, diskonPersen)
                : hitungTotal(film, jumlah);

        System.out.println("\n--- Struk Pembelian ---");
        System.out.printf("Film         : %s (%s, %s)%n", film.getJudul(), film.getStudio(), film.getJamTayang());
        System.out.printf("Tipe         : %s%n", film.getClass().getSimpleName());
        System.out.printf("Harga tiket  : Rp%,.0f%n", hitungTotal(film));
        if (film instanceof Fasilitas) {
            System.out.println("Fasilitas    : " + ((Fasilitas) film).getDeskripsiFasilitas());
        }
        System.out.printf("Jumlah       : %d%n", jumlah);
        if (diskonPersen > 0) {
            System.out.printf("Diskon       : %.0f%%%n", diskonPersen);
        }
        System.out.printf("Total bayar  : Rp%,.0f%n", total);
    }

    //===== menu 5: simulasi pemutaran =====
    private static void menuSimulasi() {
        if (daftarFilm.isEmpty()) {
            System.out.println("Belum ada film yang bisa disimulasikan.");
            return;
        }
        System.out.println("\n--- Simulasi Pemutaran Film ---");
        System.out.println("1. Simulasikan satu film");
        System.out.println("2. Simulasikan seluruh jadwal");
        int pilihan = bacaInt("Pilih (1-2): ");

        switch (pilihan) {
            case 1 -> {
                tampilkanSemuaFilm();
                int nomor = bacaInt("\nPilih nomor film: ");
                if (nomor < 1 || nomor > daftarFilm.size()) {
                    System.out.println("Nomor film tidak valid.");
                } else {
                    jalankanSimulasi(daftarFilm.get(nomor - 1));
                }
            }
            case 2 -> {
                for (int i = 0; i < daftarFilm.size(); i++) {
                    jalankanSimulasi(daftarFilm.get(i));
                }
            }
            default -> System.out.println("Pilihan tidak valid.");
        }
    }

    //parameter bertipe film (superclass) menerima objek subclass apa pun
    //method simulasitayang() yang dijalankan ditentukan saat runtime (dynamic binding)
    private static void jalankanSimulasi(Film film) {
        System.out.println("\n>> Referensi bertipe Film, objek aslinya: " + film.getClass().getSimpleName());
        film.simulasiTayang();

        //cek apakah film ini mengimplementasikan interface fasilitas
        if (film instanceof Fasilitas) {
            Fasilitas f = (Fasilitas) film;
            System.out.println("   Fasilitas: " + f.getDeskripsiFasilitas());
        }
    }

    //===== menu 6: manajemen pelanggan (hashmap) =====
    private static void menuPelanggan() {
        System.out.println("\n-- Kelola Pelanggan (HashMap) --");
        System.out.println("1. Tambah Pelanggan Baru");
        System.out.println("2. Cari Pelanggan via ID");
        System.out.println("3. Tampilkan Semua Pelanggan");
        int menu = bacaInt("Pilih: ");

        if (menu == 1) {
            String id = bacaString("Masukkan ID Pelanggan (sebagai Key): ");
            String nama = bacaString("Masukkan Nama Lengkap (sebagai Value): ");
            try {
                tambahPelanggan(id, nama);
                System.out.println("Berhasil menambahkan pelanggan!");
            } catch (Exception e) {
                System.out.println("[GAGAL] Tambah pelanggan: " + e.getMessage());
            } finally {
                System.out.println("-> Proses tambah pelanggan selesai. Total pelanggan: " + dataPelanggan.size());
            }
        } else if (menu == 2) {
            String cariId = bacaString("Masukkan ID Pelanggan yang dicari: ").trim().toUpperCase();
            if (dataPelanggan.containsKey(cariId)) {
                System.out.println("Ditemukan! Nama: " + dataPelanggan.get(cariId));
            } else {
                System.out.println("Pelanggan dengan ID tersebut tidak terdaftar.");
            }
        } else if (menu == 3) {
            System.out.println("Total Pelanggan Terdaftar: " + dataPelanggan.size());
            for (String key : dataPelanggan.keySet()) {
                System.out.println("- ID: " + key + " | Nama: " + dataPelanggan.get(key));
            }
        } else {
            System.out.println("Pilihan tidak valid.");
        }
    }

    //throws exception: validasi input kosong dan id duplikat
    private static void tambahPelanggan(String id, String nama) throws Exception {
        if (id == null || id.trim().isEmpty()) {
            throw new Exception("ID pelanggan tidak boleh kosong.");
        }
        if (nama == null || nama.trim().isEmpty()) {
            throw new Exception("Nama pelanggan tidak boleh kosong.");
        }
        String key = id.trim().toUpperCase();
        if (dataPelanggan.containsKey(key)) {
            throw new Exception("ID " + key + " sudah terdaftar atas nama " + dataPelanggan.get(key) + ".");
        }
        dataPelanggan.put(key, nama.trim());
    }

    //===== helper input =====
    //nextint dan nextdouble melempar inputmismatchexception jika input bukan angka
    private static String bacaString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    private static int bacaInt(String prompt) {
        System.out.print(prompt);
        int nilai = scanner.nextInt();
        scanner.nextLine();
        return nilai;
    }

    private static double bacaDouble(String prompt) {
        System.out.print(prompt);
        double nilai = scanner.nextDouble();
        scanner.nextLine();
        return nilai;
    }
}
