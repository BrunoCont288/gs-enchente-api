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

    /**
     * 1. Endpoint para leitura de dados de sensores simulados.
     * !! AJUSTE PRINCIPAL !!
     * Este método agora calcula o risco e o salva junto com os dados do sensor.
     */
    public DadosSensor salvarDados(DadosSensor dados) {
        // Passo 1: Calcula a mensagem de risco usando a lógica de negócio existente.
        String mensagemDeRisco = verificarRisco(dados);

        // Passo 2: Define a mensagem calculada no campo 'nivelRisco' do objeto.
        dados.setNivelRisco(mensagemDeRisco);

        // Passo 3: Salva o objeto completo no banco.
        // A data é preenchida automaticamente pela anotação @PrePersist no modelo.
        return dadosSensorRepository.save(dados);
    }

    /**
     * 2. Lógica de negócio para emissão de alertas.
     * Esta função agora serve como um motor de cálculo para o método salvarDados
     * e também pode ser usada por outros endpoints se necessário.
     */
    public String verificarRisco(DadosSensor dados) {
        if (dados.getNivelAgua() >= 5.0 || "Tempestade".equalsIgnoreCase(dados.getClima())) {
            // Ação de controle pode ser chamada aqui se necessário
            return "ALERTA DE RISCO ALTO: Evacuação imediata recomendada!";
        } else if (dados.getNivelAgua() >= 3.0 || "Chuvoso".equalsIgnoreCase(dados.getClima())) {
            return "AVISO DE RISCO MODERADO: Prepare-se para possível enchente.";
        } else {
            return "SEM RISCO: Condições normais.";
        }
    }

    /**
     * 3. Endpoint para ações de controle.
     * (Sem alterações necessárias aqui)
     */
    public String acionarBarreira(Long idSensor) {
        Optional<DadosSensor> dadosOpt = dadosSensorRepository.findById(idSensor);
        if (dadosOpt.isEmpty()) {
            return "Sensor com ID " + idSensor + " não encontrado.";
        }
        // Lógica de simulação de acionamento de barreira.
        return "Barreiras de contenção acionadas para a área do sensor " + idSensor + ". Histórico registrado.";
    }

    // --- MÉTODOS AUXILIARES ---

    /**
     * Busca todos os registros de sensores salvos.
     */
    public List<DadosSensor> buscarTodos() {
        return dadosSensorRepository.findAll();
    }

    /**
     * Busca um registro de sensor específico pelo seu ID.
     */
    public Optional<DadosSensor> buscarPorId(Long id) {
        return dadosSensorRepository.findById(id);
    }
}