
package com.example.progettotest.dao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.example.progettotest.model.TNzDpRe;
import javax.sql.DataSource;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

@Repository
public class TNzDpReDao {

    private final DataSource dataSource;
    @Autowired
    public TNzDpReDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    // Recupera tutti i record (solo ID e description per esempio)
    public List<String> findAllDescriptions() {
       List<String> descriptions = new ArrayList<>();

    // 🔹 Leggo il file SQL direttamente qui
    String sql = "";
    try {
        ClassPathResource resource = new ClassPathResource("sql/find_all_descriptions.sql");
        sql = new String(resource.getInputStream().readAllBytes());
    } catch (IOException e) {
        throw new RuntimeException("Errore nel caricamento del file SQL", e);
    }
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                descriptions.add(rs.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return descriptions;
    }

    // Trova record per ID
    public TNzDpRe findById(Long id) {
      // 🔹 Leggo il file SQL direttamente qui
    String sql = "";
    try {
        ClassPathResource resource = new ClassPathResource("sql/getById.sql");
        sql = new String(resource.getInputStream().readAllBytes());
    } catch (IOException e) {
        throw new RuntimeException("Errore nel caricamento del file SQL", e);
    }
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TNzDpRe(
                        rs.getLong("id"),
                        rs.getDate("reference_date"),
                        rs.getString("description"),
                        rs.getInt("amount"),
                        rs.getString("status")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Cancella record per ID
    public void deleteById(Long id) {
          // 🔹 Leggo il file SQL direttamente qui
    String sql = "";
    try {
        ClassPathResource resource = new ClassPathResource("sql/deteleById.sql");
        sql = new String(resource.getInputStream().readAllBytes());
    } catch (IOException e) {
        throw new RuntimeException("Errore nel caricamento del file SQL", e);
    }
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //ESEGUI QUERY SELECT ALL
    public List<TNzDpRe> findAll(String referenceDate) {
      
        List<TNzDpRe> records = new ArrayList<>();
       String sql = "";
    try {
        ClassPathResource resource = new ClassPathResource("sql/findAll.sql");
        sql = new String(resource.getInputStream().readAllBytes());
    } catch (IOException e) {
        throw new RuntimeException("Errore nel caricamento del file SQL", e);
    }
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ) {
// Impostiamo i parametri nella query (gli indici partono da 1)
           
            stmt.setString(1, referenceDate );

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TNzDpRe record = new TNzDpRe(
                        rs.getLong("id"),
                        rs.getDate("reference_date"),
                        rs.getString("description"),
                        rs.getInt("amount"),
                        rs.getString("status")
                );
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public void fillTNzDpRe(String referenceDate) {

          String sql = "";
    try {
        ClassPathResource resource = new ClassPathResource("sql/fillTNZdpre.sql");
        sql = new String(resource.getInputStream().readAllBytes());
    } catch (IOException e) {
        throw new RuntimeException("Errore nel caricamento del file SQL", e);
    }
        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, referenceDate);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
}