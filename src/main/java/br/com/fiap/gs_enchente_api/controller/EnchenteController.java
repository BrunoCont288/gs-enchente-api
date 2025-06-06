package br.com.fiap.gs_enchente_api.controller;

import br.com.fiap.gs_enchente_api.model.DadosSensor;
import br.com.fiap.gs_enchente_api.service.AlertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enchente") // Todos os endpoints desta classe começarão com /api/enchente
public class EnchenteController {

    @Autowired
    private AlertaService alertaService;

    // Endpoint para leitura de dados de sensores simulados (Nível da água, clima)
    @PostMapping("/sensor")
    @ResponseStatus(HttpStatus.CREATED)
    public DadosSensor registrarLeitura(@RequestBody DadosSensor dados) {
        return alertaService.salvarDados(dados);
    }

    // Endpoint para emissão de alertas conforme níveis de risco
    @GetMapping("/alerta/{id}")
    public ResponseEntity<String> emitirAlerta(@PathVariable Long id) {
        return alertaService.buscarPorId(id)
                .map(dados -> ResponseEntity.ok(alertaService.verificarRisco(dados)))
                .orElse(ResponseEntity.notFound().build());
    }

    // Endpoint para ações de controle (simulação de ativação de barreiras e registro de históricos)
    @PostMapping("/controle/barreira/{idSensor}")
    public ResponseEntity<String> ativarBarreira(@PathVariable Long idSensor) {
        String resultado = alertaService.acionarBarreira(idSensor);
        return ResponseEntity.ok(resultado);
    }

    // Endpoint extra para listar todos os registros (ajuda a visualizar)
    @GetMapping("/historico")
    public List<DadosSensor> listarHistorico() {
        return alertaService.buscarTodos();
    }
}