# 🏗️ Progetto TNzDpRe – Spring Boot REST API

Questo progetto espone delle API REST sviluppate in **Spring Boot** per l’esecuzione e il monitoraggio di query SQL su database **Oracle**.  


---

## 🚀 Funzionalità principali

- **Esecuzione di query SQL** tramite DAO e script `.sql` presenti in resources/sql
- **Inserimento automatico di dati** nel DB (es. `fillTNzDpRe`)
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
Service (logica di business e transazioni)
↓
Repository / DAO (esecuzione query SQL)
↓
Database Oracle

## 🌐 Endpoint REST – TNzDpReController

La classe `TNzDpReController` espone le API REST per interagire con la tabella `TNzDpRe`.  
Ogni endpoint utilizza il servizio `TNzDpReService`, che a sua volta comunica con il livello `DAO` per eseguire le query sul database Oracle.

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
SELECT * FROM TNZDPRE WHERE REFERENCE_DATE = TO_DATE(?, 'YYYY-MM-DD');
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
INSERT INTO TSEEUI02.T_NZ_DP_RE (ID, REFERENCE_DATE, DESCRIPTION, AMOUNT, STATUS)
SELECT
    p.IDENTIFIER,
    p.REFERENCE_DATE,
    'Descrizione fissa',
    p.VALUE,
    'ACTIVE'
FROM
    TSEEUI02.PERIMETRO p
WHERE
    p.REFERENCE_DATE = TO_DATE(?,'YYYY-MM-DD');
```
3️⃣ GET /descriptions

Descrizione:
Restituisce solo le descrizioni presenti nella tabella TNzDpRe.

Esempio di chiamata Swagger:
[GET /5](http://localhost:8080/api/tnz-dp-re/descriptions)
Operazione SQL eseguita:
```sql
SELECT DESCRIPTION FROM TNZDPRE;
```

4️⃣ GET /{id}

Descrizione:
Restituisce un singolo record della tabella TNzDpRe tramite l’ID.

Esempio di chiamata Swagger:
[GET /5](http://localhost:8080/api/tnz-dp-re/ID_001)
Operazione SQL eseguita:
```sql
SELECT * FROM TNZDPRE WHERE ID = ?;
```
5️⃣ DELETE /{id}

Descrizione:
Cancella un record dalla tabella TNzDpRe tramite l’ID fornito.

Esempio di chiamata Swagger:
[DELETE /5](http://localhost:8080/api/tnz-dp-re/ID_001)
Operazione SQL eseguita:
```sql
DELETE FROM TNZDPRE WHERE ID = ?;
```
