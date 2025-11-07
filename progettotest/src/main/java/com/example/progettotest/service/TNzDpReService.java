package com.example.progettotest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import com.example.progettotest.dao.TNzDpReDao;
import com.example.progettotest.model.TNzDpRe;

import jakarta.transaction.Transactional;

import java.sql.Date;
@Service
public class TNzDpReService {

    private final TNzDpReDao tnzDpReDao;
    
    @Autowired
    public TNzDpReService(TNzDpReDao tnzDpReDao) {
        this.tnzDpReDao = tnzDpReDao;
    }

    // Recupera tutte le descrizioni dei record
    public List<String> getAllDescriptions() {
        return tnzDpReDao.findAllDescriptions();
    }

    // Recupera un record per ID
   public Optional<TNzDpRe> getById(Long id) {
    TNzDpRe DP_RE_table = tnzDpReDao.findById(id);
    if (DP_RE_table == null) return Optional.empty();
    return Optional.of(DP_RE_table);
}


    // Cancella un record per ID
    public void deleteById(Long id) {
        tnzDpReDao.deleteById(id);
    }

    //esegui query select all
    public List<TNzDpRe> getAllRecords(String referenceDate) {
        return tnzDpReDao.findAll( referenceDate);
    }

@Transactional
public void fillTNzDpRe(String referenceDate) {
    tnzDpReDao.fillTNzDpRe(referenceDate);
}
}