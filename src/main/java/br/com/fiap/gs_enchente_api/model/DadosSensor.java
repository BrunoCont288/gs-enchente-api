package br.com.fiap.gs_enchente_api.model; // O pacote pode variar se você usou outro nome

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity // Anotação que diz ao Spring que esta classe é uma tabela no banco
public class DadosSensor {

    @Id // Marca o campo 'id' como a chave primária da tabela
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Define que o ID será gerado automaticamente pelo banco
    private Long id;

    private Double nivelAgua; // Em metros
    private String clima; // Ex: "Ensolarado", "Chuvoso", "Tempestade"
    private LocalDateTime dataLeitura;

    // Este método será executado automaticamente antes de um novo dado ser salvo
    @PrePersist
    public void prePersist() {
        dataLeitura = LocalDateTime.now();
    }

    // Getters e Setters (essenciais para o Spring/JPA funcionar)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getNivelAgua() {
        return nivelAgua;
    }

    public void setNivelAgua(Double nivelAgua) {
        this.nivelAgua = nivelAgua;
    }

    public String getClima() {
        return clima;
    }

    public void setClima(String clima) {
        this.clima = clima;
    }

    public LocalDateTime getDataLeitura() {
        return dataLeitura;
    }

    public void setDataLeitura(LocalDateTime dataLeitura) {
        this.dataLeitura = dataLeitura;
    }
}