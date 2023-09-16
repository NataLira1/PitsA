package com.ufcg.psoft.commerce.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ufcg.psoft.commerce.dto.Entregador.EntregadorGetRequestDTO;
import com.ufcg.psoft.commerce.dto.Entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Entregador.EntregadorResponseDTO;
import com.ufcg.psoft.commerce.exception.CommerceException;
import com.ufcg.psoft.commerce.exception.entregador.EntregadorInvalido;
import com.ufcg.psoft.commerce.exception.entregador.EntregadorNotFound;
import com.ufcg.psoft.commerce.models.Entregador;
import com.ufcg.psoft.commerce.service.entregador.EntregadorV1CriarService;
import com.ufcg.psoft.commerce.service.entregador.EntregadorV1DeleteService;
import com.ufcg.psoft.commerce.service.entregador.EntregadorV1GetAllService;
import com.ufcg.psoft.commerce.service.entregador.EntregadorV1GetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/v1/entregadores")
public class EntregadorV1RestController {

    @Autowired
    EntregadorV1GetAllService entregadorV1GetAllService;

    @Autowired
    EntregadorV1GetService entregadorV1GetService;

    @Autowired
    EntregadorV1CriarService entregadorV1CriarService;

    @Autowired
    EntregadorV1DeleteService entregadorV1DeleteService;

    @Autowired
    ObjectMapper mapper;

    @GetMapping
    public ResponseEntity<List<EntregadorResponseDTO>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(Optional.ofNullable(entregadorV1GetAllService.getAll()).get());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntregadorResponseDTO> getEntregador(@PathVariable Long id){
        Optional<Entregador> entregador = Optional.ofNullable(entregadorV1GetService.getEntregador(id));

        if(entregador.isEmpty()){
            throw new EntregadorNotFound(new CommerceException("O entregador consultado nao existe!"));
        }

        EntregadorResponseDTO responseDTO = mapper.convertValue(entregador.get(), EntregadorResponseDTO.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @PostMapping
    public ResponseEntity<EntregadorResponseDTO> criarEntregador(@RequestBody EntregadorPostPutRequestDTO novoEntregador){
        Optional<EntregadorResponseDTO> responseDTO = Optional.ofNullable(entregadorV1CriarService.criarEntregador(novoEntregador));

        if(responseDTO.isEmpty()){
            throw new EntregadorInvalido(new CommerceException("te"));
        }


        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO.get());
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntregadorResponseDTO> editarEntregador(
            @PathVariable Long id,
            @RequestBody EntregadorPostPutRequestDTO novoEntregador,
            @RequestParam String codigoAcesso
        ){

        Optional<Entregador> entregador = Optional.ofNullable(entregadorV1GetService.getEntregador(id));

        if(entregador.isEmpty()){
            throw new EntregadorNotFound(new CommerceException("O entregador consultado nao existe!"));
        }

        if(!Objects.equals(entregador.get().getCodigoAcesso(), codigoAcesso)){
            throw new EntregadorNotFound(new CommerceException("Codigo de acesso invalido!"));

        }

        EntregadorResponseDTO responseDTO = mapper.convertValue(entregador.get(), EntregadorResponseDTO.class);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EntregadorResponseDTO> deletarEntregador(
            @PathVariable Long id,
            @RequestParam String codigoAcesso
    ){
        Optional<Entregador> entregador = Optional.ofNullable(entregadorV1GetService.getEntregador(id));

        if(entregador.isEmpty()){
            throw new EntregadorNotFound(new CommerceException("O entregador consultado nao existe!"));
        }

        if(!Objects.equals(entregador.get().getCodigoAcesso(), codigoAcesso)){
            throw new EntregadorNotFound(new CommerceException("Codigo de acesso invalido!"));
        }


        entregadorV1DeleteService.deletar(id);
        EntregadorResponseDTO responseDTO = mapper.convertValue(entregadorV1GetService.getEntregador(id), EntregadorResponseDTO.class);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(responseDTO);
    }
}
