package com.mycompany.bioskop;

public class Pelanggan {
    private String id;
    private String nama;

    public Pelanggan(String id, String nama) throws Exception {
        setId(id);
        setNama(nama);
    }

    public String getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public void setId(String id) throws Exception {
        if (id == null || id.trim().isEmpty()) {
            throw new Exception("ID pelanggan tidak boleh kosong.");
        }
        this.id = id.trim().toUpperCase();
    }

    public void setNama(String nama) throws Exception {
        if (nama == null || nama.trim().isEmpty()) {
            throw new Exception("Nama pelanggan tidak boleh kosong.");
        }
        this.nama = nama.trim();
    }
}