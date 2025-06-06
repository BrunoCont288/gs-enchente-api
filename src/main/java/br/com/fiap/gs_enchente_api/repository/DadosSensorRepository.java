package br.com.fiap.gs_enchente_api.repository;

import br.com.fiap.gs_enchente_api.model.DadosSensor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Opcional, mas boa prática para indicar a camada
public interface DadosSensorRepository extends JpaRepository<DadosSensor, Long> {
    // A mágica acontece aqui!
    // JpaRepository já nos dá métodos como:
    // - save() -> para salvar um objeto DadosSensor
    // - findById() -> para buscar um por ID
    // - findAll() -> para buscar todos os registros
    // - deleteById() -> para apagar um registro
}