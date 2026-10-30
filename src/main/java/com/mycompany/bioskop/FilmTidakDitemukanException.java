package com.mycompany.bioskop;

public class FilmTidakDitemukanException extends Exception {
    public FilmTidakDitemukanException(String message) {
        super(message);
    }
}
