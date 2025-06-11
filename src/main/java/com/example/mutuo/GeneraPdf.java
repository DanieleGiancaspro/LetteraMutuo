package com.example.mutuo;

import com.lowagie.text.*;
import com.lowagie.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.net.URL;

public class GeneraPdf {
        private DettaglioMutuo dettaglio;

        public GeneraPdf(DettaglioMutuo dettaglio) {
                this.dettaglio = dettaglio;
        }

        public byte[] generaLetteraM001() throws Exception {
                Document document = new Document();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                PdfWriter.getInstance(document, baos);
                document.open();

                Font normal = FontFactory.getFont(FontFactory.HELVETICA, 12);
                Font bold = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);

                URL logoUrl = getClass().getClassLoader().getResource("logo.png");
                Image spazioLogo = Image.getInstance(logoUrl);
                spazioLogo.scaleToFit(500, 100); // larghezza massima e altezza
                document.add(spazioLogo);

                document.add(new Paragraph("All.1 Regolarizzazione rate parziali – richiesta bonifico\n\n", bold));

                PdfPTable tabellaDati = new PdfPTable(1);
                tabellaDati.setWidthPercentage(100);

                String testoDati = "Cognome e Nome muturatario/eredi: " + dettaglio.getCognome() + " "
                                + dettaglio.getNome()
                                + "\n" +
                                "Indirizzo: " + dettaglio.getIndirizzo();

                PdfPCell cellDati = new PdfPCell(new Paragraph(testoDati, normal));
                cellDati.setBorder(Rectangle.BOX);
                cellDati.setPadding(8f);

                tabellaDati.addCell(cellDati);
                document.add(tabellaDati);

                document.add(new Paragraph("CAP e Città: " + dettaglio.getCap() + " " + dettaglio.getCitta(), normal));
                document.add(new Paragraph("\nOggetto: Regolarizzazione rate mutuo - richiesta bonifico.\n", normal));

                document.add(new Paragraph("Si comunica che le rate di mutuo comprese nel periodo da " +
                                dettaglio.getDataInizio() + " a " + dettaglio.getDataFine()
                                + " risultano parzialmente rimborsate.\n",
                                normal));

                document.add(new Paragraph(
                                "Pertanto, dovrà procedere con la richiesta di un bonifico di regolarizzazione da richiedere alla sua banca ovvero al suo ufficio postale, inviando il modello seguente completo dei dati mancanti:\n",
                                normal));

                PdfPTable tabellaDestinatari = new PdfPTable(1);
                tabellaDestinatari.setSpacingBefore(10f);
                tabellaDestinatari.setWidthPercentage(100);

                String testoDestinatari = "Destinatari\n- Alla Banca e/o Posta";

                PdfPCell cellDestinatari = new PdfPCell(new Paragraph(testoDestinatari, normal));
                cellDestinatari.setBorder(Rectangle.BOX);
                cellDestinatari.setPadding(8f);

                tabellaDestinatari.addCell(cellDestinatari);
                document.add(tabellaDestinatari);

                Paragraph mailParagraph = new Paragraph();
                mailParagraph.add(new Chunk("- p.c. all’INPS ", normal));
                mailParagraph.add(new Chunk("mail: contabilita.mutui@inps.it\n", bold));
                document.add(mailParagraph);

                document.add(new Paragraph("Il sottoscritto " + dettaglio.getCognome() + " " + dettaglio.getNome() +
                                " chiede di disporre un bonifico il cui accredito dovrà pervenire entro " +
                                dettaglio.getDataAccredito() + " in favore di:\n", normal));

                document.add(new Paragraph("Beneficiario: INPS – DIREZIONE GENERALE", normal));
                document.add(new Paragraph(
                                "presso Banca di Credito Cooperativo di Roma – Ag. 15 – Via Civiltà del Lavoro, 79 – 00144 Roma",
                                normal));
                Paragraph ibanParagraph = new Paragraph();
                ibanParagraph.add(new Chunk("IBAN: ", bold));
                ibanParagraph.add(new Chunk("IT 73 K 08327 03210 000000000050", bold));
                document.add(ibanParagraph);
                document.add(new Paragraph("Accredito valuta entro il " + dettaglio.getDataValuta(), normal));
                document.add(new Paragraph("Importo: € " + dettaglio.getImporto() + "\n", normal));
                document.add(new Paragraph("vd.sotto", normal));

                // IMMAGINE SPAZIO TABELLA CENTRATA
                URL tabellaUrl = getClass().getClassLoader().getResource("spazio_tabella.png");
                Image spazioTabella = Image.getInstance(tabellaUrl);
                spazioTabella.scaleToFit(400, 150); // Ridotta per stare in pagina
                spazioTabella.setAlignment(Image.ALIGN_CENTER);
                document.add(spazioTabella);

                document.add(new Paragraph("Causale: " + dettaglio.getCognome() + " " + dettaglio.getNome() +
                                " conguaglio rate periodo da " + dettaglio.getDataInizio() + " a "
                                + dettaglio.getDataFine() +
                                " mutuo n. " + dettaglio.getNumeroMutuo(), normal));

                document.add(new Paragraph("\n\nfirma", normal));
                document.add(new Paragraph("......................................................", normal));

                document.close();
                return baos.toByteArray();
        }
}
