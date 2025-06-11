package com.example.mutuo;

import java.io.FileOutputStream;

public class App {
    public static void main(String[] args) {
        try {
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

            GeneraPdf generatore = new GeneraPdf(dettaglio);
            byte[] pdfBytes = generatore.generaLetteraM001();

            FileOutputStream fos = new FileOutputStream("LetteraM001.pdf");
            fos.write(pdfBytes);
            fos.close();

            System.out.println("Lettera generata con successo!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
