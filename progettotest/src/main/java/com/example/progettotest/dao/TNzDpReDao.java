package com.example.progettotest.dao;

import java.io.IOException;
import java.sql.Connection;
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

    // Metodo privato per leggere il file SQL
    private String loadSql(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            return new String(resource.getInputStream().readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("Errore nel caricamento del file SQL: " + path, e);
        }
    }

    // Recupera tutti i record delle descrizioni
    public List<String> findAllDescriptions() {
        List<String> descriptions = new ArrayList<>();
        String sql = loadSql("sql/find_all_descriptions.sql");

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
    public TNzDpRe findById(String id) {
        String sql = loadSql("sql/getById.sql");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new TNzDpRe(
                        rs.getString("id"),
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
    public void deleteById(String id) {
        String sql = loadSql("sql/deleteById.sql");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Esegui query SELECT ALL parametrica per data di riferimento
    public List<TNzDpRe> findAll(String referenceDate) {
        List<TNzDpRe> records = new ArrayList<>();
        String sql = loadSql("sql/findAll.sql");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, referenceDate);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                TNzDpRe record = new TNzDpRe(
                        rs.getString("id"),
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

    // Popola la tabella TNzDpRe dalla tabella Perimetro
    public void fillTNzDpRe(String referenceDate) {
        String sql = loadSql("sql/fillTNZdpre.sql");

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, referenceDate);
            stmt.executeUpdat
