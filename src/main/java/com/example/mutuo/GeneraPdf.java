package com.example.mutuo;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.net.URL;

/**
 * GeneraPdf si occupa di costruire il documento PDF
 * secondo il modello “Lettera M001” usando la libreria OpenPDF.
 * Prende in input un bean DettaglioMutuo e restituisce un array di byte
 * contenente il PDF pronto per essere salvato su file.
 */
public class GeneraPdf {
        /** Bean che contiene tutti i dati da inserire nel PDF */
        private DettaglioMutuo dettaglio;

        /**
         * Costruttore.
         * 
         * @param dettaglio istanza di DettaglioMutuo con tutti i campi valorizzati
         */
        public GeneraPdf(DettaglioMutuo dettaglio) {
                this.dettaglio = dettaglio;
        }

        /**
         * Genera la lettera M001.
         * Apre un Document, vi aggiunge paragrafi, tabelle e immagini,
         * e infine restituisce i byte del PDF.
         *
         * @return byte[] contenente il PDF
         * @throws Exception in caso di errori I/O o di generazione PDF
         */
        public byte[] generaLetteraM001() throws Exception {
                // 1) Creazione del documento in memoria
                Document document = new Document();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PdfWriter.getInstance(document, baos);
                document.open();

                // 2) Definizione dei font base
                Font normal = FontFactory.getFont(FontFactory.HELVETICA, 12);
                Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

                // 3) Logo INPS in alto
                URL logoUrl = getClass().getClassLoader().getResource("logo.png");
                Image spazioLogo = Image.getInstance(logoUrl);
                spazioLogo.scaleToFit(500, 100); // dimensione max
                spazioLogo.setAlignment(Image.ALIGN_LEFT); // allineamento a sinistra
                document.add(spazioLogo);

                document.add(
                                new Paragraph(
                                                "All.1 Regolarizzazione rate parziali – richiesta bonifico\n\n",
                                                bold));

                // 5) Box dati mutuatario (nome, cognome, indirizzo)
                PdfPTable tabellaDati = new PdfPTable(1);
                tabellaDati.setWidthPercentage(100);

                String testoDati = "Cognome e Nome mutuatario/eredi: " + dettaglio.getCognome() + " "
                                + dettaglio.getNome() + "\n" +
                                "Indirizzo: " + dettaglio.getIndirizzo() + "\n" +
                                "CAP e Città: " + dettaglio.getCap() + " " + dettaglio.getCitta();

                PdfPCell cellDati = new PdfPCell(new Paragraph(testoDati, normal));
                cellDati.setBorder(Rectangle.BOX);
                cellDati.setPadding(8f);

                tabellaDati.addCell(cellDati);
                document.add(tabellaDati);

                // 6) Righe successive fuori dal box
                document.add(new Paragraph("\nOggetto: Regolarizzazione rate mutuo - richiesta bonifico.\n", normal));

                document.add(
                                new Paragraph(
                                                "Si comunica che le rate di mutuo comprese nel periodo da " +
                                                                dettaglio.getDataInizio() + " a "
                                                                + dettaglio.getDataFine() +
                                                                " risultano parzialmente rimborsate.\n",
                                                normal));

                document.add(
                                new Paragraph(
                                                "Pertanto, dovrà procedere con la richiesta di un bonifico di regolarizzazione "
                                                                +
                                                                "da richiedere alla sua banca ovvero al suo ufficio postale, inviando il modello "
                                                                +
                                                                "seguente completo dei dati mancanti:\n",
                                                normal));

                // 7) Box destinatari
                PdfPTable tabellaDestinatari = new PdfPTable(1);
                tabellaDestinatari.setWidthPercentage(100);
                tabellaDestinatari.setSpacingBefore(10f);

                // costruisco un paragrafo “ricco” con Destinatari, riga della banca e riga mail
                // in grassetto
                Paragraph contenutoDestinatari = new Paragraph();
                contenutoDestinatari.setFont(normal); // font nero normale per “Destinatari” e “- Alla Banca…”
                contenutoDestinatari.add(new Chunk("Destinatari\n"));
                contenutoDestinatari.add(new Chunk("- Alla Banca e/o Posta\n"));

                // poi la mail, in nero **grassetto**
                contenutoDestinatari.add(new Chunk("- p.c. all’INPS mail: ", normal));
                contenutoDestinatari.add(new Chunk("contabilita.mutui@inps.it\n", bold));

                PdfPCell cellDestinatari = new PdfPCell(contenutoDestinatari);
                cellDestinatari.setBorder(Rectangle.BOX);
                cellDestinatari.setPadding(8f);

                tabellaDestinatari.addCell(cellDestinatari);
                document.add(tabellaDestinatari);

                // 9) Frase introduttiva al bonifico
                document.add(
                                new Paragraph(
                                                "Il sottoscritto " + dettaglio.getCognome() + " " + dettaglio.getNome()
                                                                +
                                                                " chiede di disporre un bonifico il cui accredito dovrà pervenire entro "
                                                                +
                                                                dettaglio.getDataAccredito() + " in favore di:\n",
                                                normal));

                // 10) Beneficiario e banca
                document.add(new Paragraph("Beneficiario: INPS – DIREZIONE GENERALE", normal));
                document.add(
                                new Paragraph(
                                                "presso Banca di Credito Cooperativo di Roma – Ag. 15 – Via Civiltà del Lavoro, 79 – 00144 Roma",
                                                normal));

                // 11) Riga IBAN in grassetto
                Paragraph ibanParagraph = new Paragraph();
                ibanParagraph.add(new Chunk("IBAN: ", bold));
                ibanParagraph.add(new Chunk("IT 73 K 08327 03210 000000000050", bold));
                document.add(ibanParagraph);

                // 12) Altre righe informative
                document.add(new Paragraph("Accredito valuta entro il " + dettaglio.getDataValuta(), normal));
                document.add(new Paragraph("Importo: € " + dettaglio.getImporto() + "\n", normal));
                document.add(new Paragraph("vd.sotto", normal));

                // 13) Placeholder per la tabella centrale
                URL tabellaUrl = getClass().getClassLoader().getResource("spazio_tabella.png");
                Image spazioTabella = Image.getInstance(tabellaUrl);
                spazioTabella.scaleToFit(400, 150); // dimensioni
                spazioTabella.setAlignment(Image.ALIGN_CENTER); // centrata
                document.add(spazioTabella);

                // 14) Causale finale e firma
                document.add(
                                new Paragraph(
                                                "Causale: " + dettaglio.getCognome() + " " + dettaglio.getNome() +
                                                                " conguaglio rate periodo da "
                                                                + dettaglio.getDataInizio() + " a " +
                                                                dettaglio.getDataFine() + " mutuo n. "
                                                                + dettaglio.getNumeroMutuo(),
                                                normal));
                document.add(new Paragraph("\n\nfirma", normal));
                document.add(new Paragraph("......................................................", normal));

                // 15) Chiusura del documento e restituzione dei byte
                document.close();
                return baos.toByteArray();
        }
}
