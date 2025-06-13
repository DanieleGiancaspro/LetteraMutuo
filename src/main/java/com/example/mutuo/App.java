package com.example.mutuo;

import java.io.FileOutputStream;

/*
 * Questo main demo-popola un bean DettaglioMutuo con dati di esempio,
 * genera il PDF chiamando GeneraPdf.generaLetteraM001()
 * e salva il risultato su disco come “LetteraM001.pdf”.
 */
public class App {

    public static void main(String[] args) {
        try {
            // 1) Creazione e popolazione del bean di dati
            // Qui vengono settati manualmente i valori per testare la generazione del PDF.
            DettaglioMutuo dettaglio = new DettaglioMutuo();
            dettaglio.setNome("Mario");
            dettaglio.setCognome("Rossi");
            dettaglio.setIndirizzo("Via Roma 1");
            dettaglio.setCap("00100");
            dettaglio.setCitta("Roma");
            dettaglio.setDataInizio("01/01/2024");
            dettaglio.setDataFine("31/03/2024");
            dettaglio.setDataAccredito("15/06/2024");
            dettaglio.setDataValuta("15/06/2024");
            dettaglio.setImporto("1234.56");
            dettaglio.setNumeroMutuo("123456");

            // 2) Generazione del PDF
            // Passiamo il bean al generatore, che ritorna l’intero documento
            // come array di byte. Non è ancora scritto su file, è in memoria.
            GeneraPdf generatore = new GeneraPdf(dettaglio);
            byte[] pdfBytes = generatore.generaLetteraM001();

            // 3) Scrittura su file
            // Apriamo un FileOutputStream e scriviamo l’array di byte in locale.
            // Se esiste già, verrà sovrascritto. Se è aperto in un altro programma,
            // verrà lanciata un’eccezione.
            try (FileOutputStream fos = new FileOutputStream("LetteraM001.pdf")) {
                fos.write(pdfBytes);
            }

            // 4) Conferma a console
            System.out.println("Lettera generata con successo!");

        } catch (Exception e) {
            // In caso di errori (I/O, PDF, ecc.), stampiamo lo stacktrace per debug
            e.printStackTrace();
        }
    }
}
