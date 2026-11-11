package com.mycompany.bioskop;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransaksiPembelian {

    public static void prosesBeli(Pelanggan pelanggan, Film film, int jumlah, double diskonPersen) {
        double total = (diskonPersen > 0)
                ? hitungTotal(film, jumlah, diskonPersen)
                : hitungTotal(film, jumlah);

        System.out.println("\n===== STRUK PEMBELIAN =====");
        System.out.printf("Pembeli      : %s (%s)%n", pelanggan.getNama(), pelanggan.getId());
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
        System.out.println("Status       : Berhasil dicatat di sistem.");
        System.out.println("===========================");

        catatLog(pelanggan, film, jumlah, total);
    }

    // file i/o write: mode append (true) supaya log lama tidak terhapus
    private static void catatLog(Pelanggan pelanggan, Film film, int jumlah, double total) {
        String waktu = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        try (FileWriter pipaTulis = new FileWriter("log_transaksi.txt", true)) {
            String log = "[" + waktu + "] Pembeli: " + pelanggan.getNama() + " (" + pelanggan.getId() + ")"
                    + " | Film: " + film.getJudul()
                    + " | Jumlah: " + jumlah
                    + " | Total: Rp" + String.format("%,.0f", total) + "\n";
            pipaTulis.write(log);
            System.out.println("-> [SYSTEM LOG] Transaksi dicatat ke log_transaksi.txt");
        } catch (IOException e) {
            System.out.println("Peringatan: Gagal menulis log transaksi ke file. " + e.getMessage());
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
}