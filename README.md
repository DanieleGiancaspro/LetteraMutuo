# Lettera Mutuo

Questo progetto è un'applicazione Java che genera lettere relative ai dettagli di un mutuo utilizzando la libreria OpenPDF.

## Struttura del Progetto

Il progetto è organizzato come segue:

- **pom.xml**: File di configurazione di Maven che contiene le dipendenze del progetto e le informazioni di base come `groupId` e `artifactId`.
- **src/main/java/com/example/mutuo/App.java**: Punto di ingresso dell'applicazione. Qui viene creato un oggetto `DettaglioMutuo` e viene invocata la generazione del PDF.
- **src/main/java/com/example/mutuo/DettaglioMutuo.java**: Classe bean che rappresenta i dettagli del mutuo, con attributi come `nome`, `cognome`, `data_inserimento`, ecc.
- **src/main/java/com/example/mutuo/GeneraPdf.java**: Classe responsabile della generazione del PDF, con metodi per creare le lettere.

## Come Configurare e Utilizzare il Progetto

1. **Installare Maven**: Assicurati di avere Maven installato sul tuo sistema. Puoi scaricarlo da [qui](https://maven.apache.org/download.cgi).

2. **Clonare il Progetto**: Clona questo repository o scarica il progetto in una directory locale.

3. **Navigare nella Directory del Progetto**: Apri il terminale e naviga nella directory dove si trova il file `pom.xml`.

4. **Aggiungere Dipendenze**: Assicurati che il file `pom.xml` contenga la seguente dipendenza per OpenPDF:

   ```xml
   <dependencies>
       <dependency>
           <groupId>com.github.librepdf</groupId>
           <artifactId>openpdf</artifactId>
           <version>1.3.43</version>
       </dependency>
   </dependencies>
   ```

5. **Compilare il Progetto**: Esegui il comando seguente per compilare il progetto:

   ```
   mvn clean install
   ```

6. **Eseguire l'Applicazione**: Dopo la compilazione, puoi eseguire l'applicazione utilizzando il comando:

   ```
   mvn exec:java -Dexec.mainClass="com.example.mutuo.App"
   ```

## Note

- Assicurati di avere Java 1.8 installato e configurato correttamente nel tuo ambiente.
- Puoi modificare i dettagli del mutuo direttamente nel codice per testare la generazione delle lettere.