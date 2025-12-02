# 🏗️ Progetto  Spring Boot REST API

Questo progetto espone delle API REST sviluppate in **Spring Boot** per l’esecuzione e il monitoraggio di query SQL su database **Oracle**.  


---

## 🚀 Funzionalità principali

- **Esecuzione di query SQL** tramite DAO e script `.sql` presenti in resources/sql
- **Inserimento lettura e cancellazione dati automatica tramite Swagger** 
- **Gestione dei parametri dinamici** (es. `referenceDate`)
- **Documentazione interattiva** tramite **Swagger UI**
- **Strutturazione multilayer (Controller, Service, DAO)**


---

## 🧱 Architettura del progetto

Il progetto segue il classico pattern **3-tier** di Spring Boot:

Swagger UI / Client HTTP  

↓  

Controller (gestisce la richiesta)  

↓  

Service (logica di business)  

↓  

 DAO (esecuzione query SQL)  

↓  

Database Oracle

## 🌐 Endpoint REST – TNzDpReController

L’applicazione segue un’architettura a livelli:

Controller
Espone le API REST e gestisce le richieste provenienti dal client.

Service
Contiene la logica applicativa e coordina le operazioni richieste dai controller.

DAO/Repository
Si occupa dell’accesso al database e dell’esecuzione delle query, mantenendo separata la logica di persistenza.

Ogni endpoint del controller delega al service, che a sua volta interagisce con il livello di persistenza per ottenere o modificare i dati del database.

### 📘 Lista degli endpoint principali

---

### 1️⃣ **GET /all**
**Descrizione:**  
Recupera tutti i record della tabella `TNzDpRe` filtrati in base alla data di riferimento (`referenceDate`).

**Parametri:**
- `referenceDate` *(string, formato yyyy-MM-dd)* → Data di riferimento.

**Esempio di chiamata Swagger:**  
[GET /all/2024-12-31](http://localhost:8080/api/tnz-dp-re/all?referenceDate=2024-12-31)


**Operazione SQL eseguita:**
```sql
SELECT * FROM  user002.dp_re where REFERENCE_DATE=TO_DATE(?,'YYYY-MM-DD')
```
2️⃣ POST /FillTNzDpRe

Descrizione:
Popola la tabella TNzDpRe con i dati provenienti dalla tabella PERIMETRO.
La data di riferimento viene passata come parametro referenceDate.

Parametri:
referenceDate (string, formato yyyy-MM-dd) → Data di riferimento per il popolamento.

Esempio di chiamata Swagger:
[POST /FillTNzDpRe?referenceDate=2024-12-31](http://localhost:8080/api/tnz-dp-re/FillTNzDpRe?referenceDate=2024-12-31)
```sql
INSERT INTO user002.dp_re (ID, REFERENCE_DATE, DESCRIPTION, AMOUNT, STATUS)
SELECT
    p.IDENTIFIER,
    p.REFERENCE_DATE,
    'Descrizione fissa',
    p.VALUE,
    'ACTIVE'
FROM
    user002.PERIMETRO p
    where p.REFERENCE_DATE= TO_DATE(?,'YYYY-MM-DD')
```
3️⃣ GET /descriptions

Descrizione:
Restituisce solo le descrizioni presenti nella tabella TNzDpRe.

Esempio di chiamata Swagger:
[GET /5](http://localhost:8080/api/tnz-dp-re/descriptions)
Operazione SQL eseguita:
```sql
SELECT description FROM user002.dp_re
```

4️⃣ GET /{id}

Descrizione:
Restituisce un singolo record della tabella TNzDpRe tramite l’ID.

Esempio di chiamata Swagger:
[GET /5](http://localhost:8080/api/tnz-dp-re/ID_001)
Operazione SQL eseguita:
```sql
SELECT * FROM user002.dp_re WHERE id = ? 
```
5️⃣ DELETE /{id}

Descrizione:
Cancella un record dalla tabella TNzDpRe tramite l’ID fornito.

Esempio di chiamata Swagger:
[DELETE /5](http://localhost:8080/api/tnz-dp-re/ID_001)
Operazione SQL eseguita:
```sql
DELETE FROM  user002.dp_re WHERE id = + ?
```
