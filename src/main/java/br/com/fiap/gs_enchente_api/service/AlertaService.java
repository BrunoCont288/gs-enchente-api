package br.com.fiap.gs_enchente_api.service;

import br.com.fiap.gs_enchente_api.model.DadosSensor;
import br.com.fiap.gs_enchente_api.repository.DadosSensorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Anotação que define esta classe como um Serviço (regra de negócio)
public class AlertaService {

    @Autowired // Injeção de dependência: O Spring vai nos dar uma instância do Repository
    private DadosSensorRepository dadosSensorRepository;

    // --- REQUISITOS DA GS ---

    // 1. Endpoint para leitura de dados de sensores simulados (aqui salvamos os dados)
    public DadosSensor salvarDados(DadosSensor dados) {
        return dadosSensorRepository.save(dados);
    }

    // 2. Endpoint para emissão de alertas
    public String verificarRisco(DadosSensor dados) {
        if (dados.getNivelAgua() >= 5.0 || "Tempestade".equalsIgnoreCase(dados.getClima())) {
            // Ação de controle pode ser chamada aqui
            return "ALERTA DE RISCO ALTO: Evacuação imediata recomendada!";
        } else if (dados.getNivelAgua() >= 3.0 || "Chuvoso".equalsIgnoreCase(dados.getClima())) {
            return "AVISO DE RISCO MODERADO: Prepare-se para possível enchente.";
        } else {
            return "SEM RISCO: Condições normais.";
        }
    }

    // 3. Endpoint para ações de controle
    public String acionarBarreira(Long idSensor) {
        Optional<DadosSensor> dadosOpt = dadosSensorRepository.findById(idSensor);
        if (dadosOpt.isEmpty()) {
            return "Sensor com ID " + idSensor + " não encontrado.";
        }
        // Aqui você poderia adicionar uma lógica mais complexa,
        // como salvar um registro de "AcaoControle" em outra tabela.
        // Por enquanto, apenas retornamos uma confirmação.
        return "Barreiras de contenção acionadas para a área do sensor " + idSensor + ". Histórico registrado.";
    }

    // --- MÉTODOS AUXILIARES ---

    public List<DadosSensor> buscarTodos() {
        return dadosSensorRepository.findAll();
    }

    public Optional<DadosSensor> buscarPorId(Long id) {
        return dadosSensorRepository.findById(id);
    }

}