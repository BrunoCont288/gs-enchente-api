package br.com.fiap.gs_enchente_api.controller;

import br.com.fiap.gs_enchente_api.model.DadosSensor;
import br.com.fiap.gs_enchente_api.service.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller principal para gerenciar os dados de sensores e alertas de enchente.
 */
@RestController
@RequestMapping("/api/enchente")
// ============================================================================================
// == CORREÇÃO APLICADA AQUI: A porta foi trocada para 8081, a porta do seu app na web. ==
@CrossOrigin(origins = "http://localhost:8081")
// ============================================================================================
public class EnchenteController {

    @Autowired
    private AlertaService alertaService;

    /**
     * [POST /api/enchente/sensor]
     * Endpoint para registrar uma nova leitura de dados de um sensor.
     * Recebe os dados do sensor no corpo da requisição e os salva.
     * @param dados Os dados do sensor a serem salvos.
     * @return Os dados do sensor que foram salvos, com o ID preenchido.
     */
    @PostMapping("/sensor")
    public ResponseEntity<DadosSensor> registrarLeitura(@RequestBody DadosSensor dados) {
        DadosSensor dadosSalvos = alertaService.salvarDados(dados);
        return ResponseEntity.status(HttpStatus.CREATED).body(dadosSalvos);
    }

    /**
     * [GET /api/enchente/alerta/{id}]
     * Endpoint para verificar o nível de risco com base em uma leitura de sensor específica.
     * @param id O ID do registro do sensor a ser verificado.
     * @return Uma string com a descrição do nível de risco.
     */
    @GetMapping("/alerta/{id}")
    public ResponseEntity<String> emitirAlerta(@PathVariable Long id) {
        return alertaService.buscarPorId(id)
                .map(dados -> ResponseEntity.ok(alertaService.verificarRisco(dados)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * [POST /api/enchente/controle/barreira/{idSensor}]
     * Endpoint para simular a ativação de uma barreira de contenção.
     * @param idSensor O ID do sensor cujo alerta justifica a ação de controle.
     * @return Uma string confirmando a ação.
     */
    @PostMapping("/controle/barreira/{idSensor}")
    public ResponseEntity<String> ativarBarreira(@PathVariable Long idSensor) {
        String resultado = alertaService.acionarBarreira(idSensor);
        return ResponseEntity.ok(resultado);
    }

    /**
     * [GET /api/enchente/historico]
     * Endpoint para listar todos os registros de dados de sensores.
     * Útil para visualizar o histórico de leituras.
     * @return Uma lista com todos os dados de sensores.
     */
    @GetMapping("/historico")
    public ResponseEntity<List<DadosSensor>> listarHistorico() {
        List<DadosSensor> historico = alertaService.buscarTodos();
        return ResponseEntity.ok(historico);
    }
}