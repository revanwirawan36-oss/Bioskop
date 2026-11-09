package com.mycompany.bioskop;

import java.util.InputMismatchException;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Bioskop {
    private static final Scanner scanner = new Scanner(System.in);
    private static SmartCinema cinema;

    public static void main(String[] args) {
        scanner.useLocale(Locale.US);
        boolean isRunning = true;

        System.out.println("==================================");
        System.out.println("   Selamat Datang di Smart Cinema");
        System.out.println("==================================");

        cinema = new SmartCinema("Smart Cinema");
        cinema.bukaBioskop();
        isiDataAwal();

        while (isRunning) {
            System.out.println("\n=== MENU UTAMA ===");
            System.out.println("1. Tambah Film Baru");
            System.out.println("2. Tampilkan Seluruh Film");
            System.out.println("3. Cari Film (Demo Custom Exception)");
            System.out.println("4. Transaksi Tiket (Demo Association)");
            System.out.println("5. Simulasi Pemutaran Film");
            System.out.println("6. Manajemen Pelanggan");
            System.out.println("7. Info Gedung (Demo Composition)");
            System.out.println("8. Keluar");

            try {
                int pilihan = bacaInt("Pilih Menu (1-8): ");

                switch (pilihan) {
                    case 1 -> menuTambahFilm();
                    case 2 -> {
                        System.out.println("\n--- Daftar Film Smart Cinema ---");
                        cinema.lihatSemuaFilm();
                    }
                    case 3 -> menuCariFilm();
                    case 4 -> menuTransaksi();
                    case 5 -> menuSimulasi();
                    case 6 -> menuPelanggan();
                    case 7 -> cinema.infoGedung();
                    case 8 -> {
                        System.out.println("Terima kasih telah menggunakan Smart Cinema!");
                        isRunning = false;
                    }
                    default -> System.out.println("Pilihan tidak valid. Masukkan angka 1-8.");
                }
            } catch (InputMismatchException e) {
                System.out.println("\n[FATAL ERROR] Input tidak valid! Anda harus memasukkan angka.");
                scanner.nextLine();
            } catch (NoSuchElementException e) {
                System.out.println("\n[INFO] Input berakhir, program ditutup.");
                isRunning = false;
            } catch (Exception e) {
                System.out.println("\n[ERROR SISTEM] Terjadi kegagalan: " + e.getMessage());
            }
        }
        scanner.close();
    }

    // objek dibuat di sini (di luar SmartCinema) lalu dimasukkan: agregasi
    private static void isiDataAwal() {
        try {
            cinema.tambahFilm(new Film2D("Laskar Pelangi 2", "Drama", 110, 45000, "Studio 1", "13:00", "Indonesia"));
            cinema.tambahFilm(new Film2D("Pengabdi Setan 3", "Horor", 105, 45000, "Studio 2", "19:30", "Inggris"));
            cinema.tambahFilm(new Film3D("Avatar: Samudra", "Fiksi Ilmiah", 160, 50000, "Studio 3", "16:00", 10000));
            cinema.tambahFilm(new Film3D("Kung Fu Panda 5", "Animasi", 95, 50000, "Studio 3", "10:30", 10000));
            cinema.tambahFilm(new FilmIMAX("Dune: Kiamat", "Aksi", 150, 60000, "IMAX", "20:00", 26.5));
            cinema.tambahFilm(new Film4DX("Fast Racer X", "Aksi", 125, 55000, "Studio 4DX", "21:15", 6));

            cinema.tambahPelanggan(new Pelanggan("PLG001", "Budi Santoso"));
            cinema.tambahPelanggan(new Pelanggan("PLG002", "Siti Aminah"));
            cinema.tambahPelanggan(new Pelanggan("PLG003", "Rina Wijaya"));
        } catch (Exception e) {
            System.out.println("[ERROR] Gagal mengisi data awal: " + e.getMessage());
        }
    }

    // ===== menu 1: tambah film =====
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

        try {
            String judul = bacaString("Judul          : ");
            String genre = bacaString("Genre          : ");
            int durasi = bacaInt("Durasi (menit) : ");
            double harga = bacaDouble("Harga dasar    : ");
            String studio = bacaString("Studio         : ");
            String jam = bacaString("Jam tayang (HH:mm): ");

            if (tipe == 1) {
                String subtitle = bacaString("Bahasa subtitle: ");
                cinema.tambahFilm(new Film2D(judul, genre, durasi, harga, studio, jam, subtitle));
            } else if (tipe == 2) {
                double kacamata = bacaDouble("Biaya kacamata 3D: ");
                cinema.tambahFilm(new Film3D(judul, genre, durasi, harga, studio, jam, kacamata));
            } else if (tipe == 3) {
                double layar = bacaDouble("Ukuran layar (meter): ");
                cinema.tambahFilm(new FilmIMAX(judul, genre, durasi, harga, studio, jam, layar));
            } else {
                int efek = bacaInt("Jumlah efek khusus (1-10): ");
                cinema.tambahFilm(new Film4DX(judul, genre, durasi, harga, studio, jam, efek));
            }
        } catch (InputMismatchException e) {
            throw e;
        } catch (Exception e) {
            System.out.println("[GAGAL] Tambah film dibatalkan: " + e.getMessage());
        }
    }

    // ===== menu 3: cari film =====
    private static void menuCariFilm() {
        System.out.println("\n--- Cari Film ---");
        System.out.println("1. Berdasarkan judul");
        System.out.println("2. Berdasarkan genre dan harga tiket maksimal");
        System.out.println("3. Berdasarkan durasi maksimal");
        int pilihan = bacaInt("Pilih cara pencarian (1-3): ");

        try {
            switch (pilihan) {
                case 1 -> cinema.cariFilm(bacaString("Kata kunci judul: "));
                case 2 -> {
                    String genre = bacaString("Genre: ");
                    double hargaMaks = bacaDouble("Harga tiket maksimal: ");
                    cinema.cariFilm(genre, hargaMaks);
                }
                case 3 -> cinema.cariFilm(bacaInt("Durasi maksimal (menit): "));
                default -> System.out.println("Pilihan tidak valid.");
            }
        } catch (FilmTidakDitemukanException e) {
            System.out.println("Peringatan: " + e.getMessage());
        } finally {
            System.out.println("-> Sesi pencarian selesai dieksekusi.");
        }
    }

    // ===== menu 4: transaksi tiket (asosiasi) =====
    private static void menuTransaksi() {
        System.out.println("\n-- Transaksi Tiket (Asosiasi) --");
        String inputId = bacaString("Masukkan ID Pelanggan: ");

        Pelanggan pembeli = cinema.getPelanggan(inputId);

        if (pembeli == null) {
            System.out.println("Gagal: Pelanggan belum terdaftar!");
        } else if (cinema.getJumlahFilm() == 0) {
            System.out.println("Gagal: Belum ada film yang bisa dipesan.");
        } else {
            cinema.lihatSemuaFilm();
            int nomor = bacaInt("\nPilih nomor film: ");
            int jumlahTiket = bacaInt("Jumlah tiket: ");

            try {
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

                Film itemBeli = cinema.getFilm(nomor - 1);
                TransaksiPembelian.prosesBeli(pembeli, itemBeli, jumlahTiket, diskon);
            } catch (InputMismatchException e) {
                throw e;
            } catch (Exception e) {
                System.out.println("[GAGAL] Pemesanan dibatalkan: " + e.getMessage());
            }
        }
    }

    private static void validasiPemesanan(int nomor, int jumlahTiket) throws Exception {
        if (nomor < 1 || nomor > cinema.getJumlahFilm()) {
            throw new Exception("Nomor film tidak valid (pilih 1-" + cinema.getJumlahFilm() + ").");
        }
        if (jumlahTiket <= 0) {
            throw new Exception("Jumlah tiket harus lebih dari 0.");
        }
    }

    // ===== menu 5: simulasi pemutaran =====
    private static void menuSimulasi() {
        if (cinema.getJumlahFilm() == 0) {
            System.out.println("Belum ada film yang bisa disimulasikan.");
            return;
        }
        System.out.println("\n--- Simulasi Pemutaran Film ---");
        System.out.println("1. Simulasikan satu film");
        System.out.println("2. Simulasikan seluruh jadwal");
        int pilihan = bacaInt("Pilih (1-2): ");

        switch (pilihan) {
            case 1 -> {
                cinema.lihatSemuaFilm();
                int nomor = bacaInt("\nPilih nomor film: ");
                if (nomor < 1 || nomor > cinema.getJumlahFilm()) {
                    System.out.println("Nomor film tidak valid.");
                } else {
                    jalankanSimulasi(cinema.getFilm(nomor - 1));
                }
            }
            case 2 -> {
                for (int i = 0; i < cinema.getJumlahFilm(); i++) {
                    jalankanSimulasi(cinema.getFilm(i));
                }
            }
            default -> System.out.println("Pilihan tidak valid.");
        }
    }

    private static void jalankanSimulasi(Film film) {
        System.out.println("\n>> Referensi bertipe Film, objek aslinya: " + film.getClass().getSimpleName());
        film.simulasiTayang();

        if (film instanceof Fasilitas) {
            Fasilitas f = (Fasilitas) film;
            System.out.println("   Fasilitas: " + f.getDeskripsiFasilitas());
        }
    }

    // ===== menu 6: manajemen pelanggan =====
    private static void menuPelanggan() {
        System.out.println("\n-- Kelola Pelanggan --");
        System.out.println("1. Tambah Pelanggan");
        System.out.println("2. Cari Pelanggan");
        System.out.println("3. Lihat Semua");
        int menu = bacaInt("Pilih: ");

        if (menu == 1) {
            String id = bacaString("Masukkan ID: ");
            String nama = bacaString("Masukkan Nama: ");
            try {
                cinema.tambahPelanggan(new Pelanggan(id, nama));
                System.out.println("Berhasil menambahkan pelanggan!");
            } catch (Exception e) {
                System.out.println("[GAGAL] Tambah pelanggan: " + e.getMessage());
            } finally {
                System.out.println("-> Proses tambah pelanggan selesai. Total pelanggan: " + cinema.getJumlahPelanggan());
            }
        } else if (menu == 2) {
            cinema.cariPelanggan(bacaString("ID yg dicari: "));
        } else if (menu == 3) {
            cinema.lihatSemuaPelanggan();
        } else {
            System.out.println("Pilihan tidak valid.");
        }
    }

    // ===== helper input =====
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