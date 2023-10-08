package com.ufcg.psoft.commerce.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.commerce.dto.sabor.SaborPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.sabor.SaborResponseDTO;
import com.ufcg.psoft.commerce.exception.CustomErrorType;
import com.ufcg.psoft.commerce.models.Entregador;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Sabor;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.SaborRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de Sabores")
public class SaborControllerTests {
        final String URI_SABORES = "/v1/sabores";

        @Autowired
        MockMvc driver;

        @Autowired
        SaborRepository saborRepository;
        @Autowired
        EstabelecimentoRepository estabelecimentoRepository;

        ObjectMapper objectMapper = new ObjectMapper();
        Sabor sabor;
        Estabelecimento estabelecimento;
        SaborPostPutRequestDTO saborPostPutRequestDTO;

        @BeforeEach
        void setup() {

                Set<Sabor> cardapio = new HashSet<Sabor>();
                Set<Entregador> entregadores = new HashSet<Entregador>();

                objectMapper.registerModule(new JavaTimeModule());
                estabelecimento = estabelecimentoRepository.save(Estabelecimento.builder()
                                .usuario("Jardel")
                                .codigoAcesso("654321")
                                .cardapio(cardapio)
                                .entregadores(entregadores)
                                .build());
                sabor = saborRepository.save(Sabor.builder()
                                .nome("Calabresa")
                                .tipo("salgado")
                                .valorMedia(10.0)
                                .valorGrande(15.0)
                                .disponivel(true)
                                .build());
                saborPostPutRequestDTO = SaborPostPutRequestDTO.builder()
                                .nome(sabor.getNome())
                                .tipo(sabor.getTipo())
                                .valorMedia(sabor.getValorMedia())
                                .valorGrande(sabor.getValorGrande())
                                .disponivel(sabor.isDisponivel())
                                .build();
        }

        @AfterEach
        void tearDown() {
                estabelecimentoRepository.deleteAll();
                saborRepository.deleteAll();
        }

         @Nested
         @DisplayName("Conjunto de casos de verificação dos fluxos básicos API Rest")
         class SaborVerificacaoFluxosBasicosApiRest {

         @Test
         @DisplayName("Quando buscamos por todos sabores salvos")
         void quandoBuscamosPorTodosSaboresSalvos() throws Exception {
         saborRepository.deleteAll();
         // Arrange
         // Vamos ter 2 sabores no banco
         Sabor sabor1 = Sabor.builder()
         .nome("Chocolate")
         .tipo("doce")
         .valorMedia(10.0)
         .valorGrande(15.0)
         .disponivel(true)
         .estabelecimento(estabelecimento)
         .build();
         Sabor sabor2 = Sabor.builder()
         .nome("Frango")
         .tipo("salgado")
         .valorMedia(10.0)
         .valorGrande(15.0)
         .disponivel(true)
         .estabelecimento(estabelecimento)
         .build();
         saborRepository.saveAll(Arrays.asList(sabor1, sabor2));

         // Act
         String responseJsonString = driver.perform(get(URI_SABORES)
         .contentType(MediaType.APPLICATION_JSON)
         .param("idEstabelecimento", estabelecimento.getId().toString())
         .param("codigoDeAcesso", estabelecimento.getCodigoAcesso())
         .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
         .andExpect(status().isOk()) // Codigo 200
         .andDo(print())
         .andReturn().getResponse().getContentAsString();

         List<SaborResponseDTO> resultado = objectMapper.readValue(responseJsonString,
         new TypeReference<>() {
         });

         // Assert
         assertAll(
         () -> assertEquals(2, resultado.size()));
         }

         @Test
         @DisplayName("Quando buscamos um sabor salvo pelo id")
         void quandoBuscamosPorUmSaborSalvo() throws Exception {
         // Arrange
         // nenhuma necessidade além do setup()

         // Act
         String responseJsonString = driver.perform(get(URI_SABORES + "/" +
         sabor.getId())
         .contentType(MediaType.APPLICATION_JSON)
         .param("idEstabelecimento", estabelecimento.getId().toString())
         .param("codigoDeAcesso", estabelecimento.getCodigoAcesso())
         .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
         .andExpect(status().isOk()) // Codigo 200
         .andDo(print())
         .andReturn().getResponse().getContentAsString();

         SaborResponseDTO resultado = objectMapper.readValue(responseJsonString,
         SaborResponseDTO.SaborResponseDTOBuilder.class).build();

         // Assert
         assertAll(
         () -> assertEquals(sabor.getId().longValue(), resultado.getId().longValue()),
         () -> assertEquals(sabor.getNome(), resultado.getNome()),
         () -> assertEquals(sabor.getTipo(), resultado.getTipo()),
         () -> assertEquals(sabor.getValorMedia(), resultado.getValorMedia()),
         () -> assertEquals(sabor.getValorGrande(), resultado.getValorGrande()),
         () -> assertEquals(sabor.isDisponivel(), resultado.isDisponivel()));
         }

         @Test
         @DisplayName("Quando buscamos um sabor inexistente")
         void quandoBuscamosPorUmSaborInexistente() throws Exception {
         // Arrange
         // nenhuma necessidade além do setup()

         // Act
         String responseJsonString = driver.perform(get(URI_SABORES + "/999999")
         .contentType(MediaType.APPLICATION_JSON)
         .param("idEstabelecimento", estabelecimento.getId().toString())
         .param("codigoDeAcesso", estabelecimento.getCodigoAcesso())
         .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
         .andExpect(status().isBadRequest()) // Codigo 400
         .andDo(print())
         .andReturn().getResponse().getContentAsString();

         CustomErrorType resultado = objectMapper.readValue(responseJsonString,
         CustomErrorType.class);

         // Assert
         assertAll(
         () -> assertEquals("O sabor consultado nao existe!", resultado.getMessage()));
         }

         @Test
         @DisplayName("Quando buscamos um sabor com id estabelecimento inválido")
         void quandoBuscamosPorUmSaborComIdEstabelecimentoInvalido() throws Exception
         {
         // Arrange
         // nenhuma necessidade além do setup()

         // Act
         String responseJsonString = driver.perform(get(URI_SABORES + "/" +
         sabor.getId())
         .contentType(MediaType.APPLICATION_JSON)
         .param("idEstabelecimento", "77777")
         .param("codigoDeAcesso", estabelecimento.getCodigoAcesso())
         .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
         .andExpect(status().isBadRequest()) // Codigo 400
         .andDo(print())
         .andReturn().getResponse().getContentAsString();

         CustomErrorType resultado = objectMapper.readValue(responseJsonString,
         CustomErrorType.class);

         // Assert
         assertAll(
         () -> assertEquals("O estabelecimento consultado nao existe!",
         resultado.getMessage()));
         }

         @Test
         @DisplayName("Quando buscamos um sabor com código de acesso inválido")
         void quandoBuscamosPorUmSaborComCodigoInvalido() throws Exception {
         // Arrange
         // nenhuma necessidade além do setup()

         // Act
         String responseJsonString = driver.perform(get(URI_SABORES + "/" +
         sabor.getId())
         .contentType(MediaType.APPLICATION_JSON)
         .param("idEstabelecimento", estabelecimento.getId().toString())
         .param("codigoDeAcesso", "999999")
         .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
         .andExpect(status().isBadRequest()) // Codigo 400
         .andDo(print())
         .andReturn().getResponse().getContentAsString();

         CustomErrorType resultado = objectMapper.readValue(responseJsonString,
         CustomErrorType.class);

         // Assert
         assertAll(
         () -> assertEquals("Codigo de acesso invalido!", resultado.getMessage()));
         }

         @Test
         @DisplayName("Quando criamos um novo sabor com dados válidos")
         void quandoCriarSaborValido() throws Exception {
         // Arrange
         // Nenhuma alteração além do setup()

         // Act
         String responseJsonString = driver.perform(post(URI_SABORES)
         .contentType(MediaType.APPLICATION_JSON)
         .param("idEstabelecimento", estabelecimento.getId().toString())
         .param("codigoDeAcesso", estabelecimento.getCodigoAcesso())
         .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
         .andExpect(status().isCreated()) // Codigo 201
         .andDo(print())
         .andReturn().getResponse().getContentAsString();

         SaborResponseDTO resultado = objectMapper.readValue(responseJsonString,
         SaborResponseDTO.class);
         estabelecimento =
         estabelecimentoRepository.findById(estabelecimento.getId()).get();
         // Assert

         assertAll(
         () -> assertEquals(saborPostPutRequestDTO.getNome(), resultado.getNome()),
         () -> assertEquals(saborPostPutRequestDTO.getTipo(), resultado.getTipo()),
         () -> assertEquals(saborPostPutRequestDTO.getValorMedia(),
         resultado.getValorMedia()),
         () -> assertEquals(saborPostPutRequestDTO.getValorGrande(),
         resultado.getValorGrande()),
         () -> assertEquals(saborPostPutRequestDTO.isDisponivel(),
         resultado.isDisponivel()));
         }

         @Test
         @DisplayName("Quando criamos um novo sabor com dados inválidos")
         void quandoCriarSaborInvalido() throws Exception {
         // Arrange
         SaborPostPutRequestDTO saborPostPutRequestDTO2 =
         SaborPostPutRequestDTO.builder()
         .nome("")
         .tipo("")
         .valorMedia(0)
         .valorGrande(0)
         .disponivel(false)
         .build();
         // nenhuma necessidade além do setup()

         // Act
         String responseJsonString = driver.perform(post(URI_SABORES)
         .contentType(MediaType.APPLICATION_JSON)
         .param("idEstabelecimento", estabelecimento.getId().toString())
         .param("codigoDeAcesso", estabelecimento.getCodigoAcesso())
         .content(objectMapper.writeValueAsString(saborPostPutRequestDTO2)))
         .andExpect(status().isBadRequest()) // Codigo 400
         .andDo(print())
         .andReturn().getResponse().getContentAsString();

         CustomErrorType e = objectMapper.readValue(responseJsonString,
         CustomErrorType.class);

         // Assert
         assertEquals(e.getMessage(), "Erros de validacao encontrados");
         }

         @Test
         @DisplayName("Quando criamos um novo sabor com id estabelecimento inexistente")
         void quandoCriarSaborIdEstabelecimentoInexistente() throws Exception {
         // Arrange

         // Act
         String responseJsonString = driver.perform(post(URI_SABORES)
         .contentType(MediaType.APPLICATION_JSON)
         .param("idEstabelecimento", "12")
         .param("codigoDeAcesso", estabelecimento.getCodigoAcesso())
         .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
         .andExpect(status().isBadRequest()) // Codigo 400
         .andDo(print())
         .andReturn().getResponse().getContentAsString();

         CustomErrorType e = objectMapper.readValue(responseJsonString,
         CustomErrorType.class);

         // Assert
         assertEquals(e.getMessage(), "O estabelecimento consultado nao existe!");
         }

         @Test
         @DisplayName("Quando criamos um novo sabor com codigo de acesso invalido")
         void quandoCriarSaborComCodigoAcessoInvalido() throws Exception {
         // Arrange
         // Nenhuma alteração além do setup()

         // Act
         String responseJsonString = driver.perform(post(URI_SABORES)
         .contentType(MediaType.APPLICATION_JSON)
         .param("idEstabelecimento", estabelecimento.getId().toString())
         .param("codigoDeAcesso", "2")
         .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
         .andExpect(status().isBadRequest()) // Codigo 201
         .andDo(print())
         .andReturn().getResponse().getContentAsString();

         CustomErrorType e = objectMapper.readValue(responseJsonString,
         CustomErrorType.class);

         // Assert
         assertEquals(e.getMessage(), "Codigo de acesso invalido!");
         }

         @Test
         @DisplayName("Quando alteramos o sabor com dados válidos")
         void quandoAlteramosSaborValido() throws Exception {
         // Arrange
         sabor = saborRepository.save(Sabor.builder()
         .nome("Frango")
         .tipo("Salgado")
         .valorMedia(10.0)
         .valorGrande(15.0)
         .disponivel(true)
         .estabelecimento(estabelecimento)
         .build());

         Long saborId = sabor.getId();
         saborPostPutRequestDTO.builder()
         .nome("Queijo")
         .tipo("Doce")
         .valorMedia(5)
         .valorGrande(7)
         .disponivel(true)
         .build();

         // Act
         String responseJsonString = driver.perform(put(URI_SABORES + "/" +
         sabor.getId())
         .contentType(MediaType.APPLICATION_JSON)
         .param("idEstabelecimento", estabelecimento.getId().toString())
         .param("codigoDeAcesso", estabelecimento.getCodigoAcesso())
         .param("saborId", saborId.toString())
         .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
         .andExpect(status().isOk()) // Codigo 200
         .andDo(print())
         .andReturn().getResponse().getContentAsString();

         SaborResponseDTO resultado = objectMapper.readValue(responseJsonString,
         SaborResponseDTO.class);
         // Assert
         assertAll(
         () -> assertEquals(saborPostPutRequestDTO.getNome(), resultado.getNome()),
         () -> assertEquals(saborPostPutRequestDTO.getTipo(), resultado.getTipo()),
         () -> assertEquals(saborPostPutRequestDTO.getValorMedia(),
         resultado.getValorMedia()),
         () -> assertEquals(saborPostPutRequestDTO.getValorGrande(),
         resultado.getValorGrande()),
         () -> assertEquals(saborPostPutRequestDTO.isDisponivel(),
         resultado.isDisponivel()));
         }

         @Test
         @DisplayName("Quando alteramos o sabor com dados inválidos")
         void quandoAlteramosSaborInvalido() throws Exception {
         // Arrange
         Long saborId = sabor.getId();
         SaborPostPutRequestDTO saborPostPutRequestDTO2 =
         SaborPostPutRequestDTO.builder()
         .nome("")
         .tipo("")
         .valorMedia(0)
         .valorGrande(0)
         .disponivel(false)
         .build();

         // Act
         String responseJsonString = driver.perform(put(URI_SABORES + "/" +
         sabor.getId())
         .contentType(MediaType.APPLICATION_JSON)
         .param("idEstabelecimento", estabelecimento.getId().toString())
         .param("codigoDeAcesso", estabelecimento.getCodigoAcesso())
         .param("saborId", saborId.toString())
         .content(objectMapper.writeValueAsString(saborPostPutRequestDTO2)))
         .andExpect(status().isBadRequest()) // Codigo 400
         .andDo(print())
         .andReturn().getResponse().getContentAsString();

         CustomErrorType e = objectMapper.readValue(responseJsonString,
         CustomErrorType.class);
         // Assert
         assertEquals(e.getMessage(), "Erros de validacao encontrados");
         }

         @Test
         @DisplayName("Quando alteramos o sabor com id do estabelecimento inexistente")
         void quandoAlteramosSaborIdEstabelecimentoInexistente() throws Exception {
         // Arrange
         Long saborId = sabor.getId();

         // Act
         String responseJsonString = driver.perform(put(URI_SABORES + "/" +
         sabor.getId())
         .contentType(MediaType.APPLICATION_JSON)
         .param("idEstabelecimento", "123")
         .param("codigoDeAcesso", estabelecimento.getCodigoAcesso())
         .param("saborId", saborId.toString())
         .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
         .andExpect(status().isBadRequest()) // Codigo 400
         .andDo(print())
         .andReturn().getResponse().getContentAsString();

         CustomErrorType e = objectMapper.readValue(responseJsonString,
         CustomErrorType.class);
         // Assert
         assertEquals(e.getMessage(), "O estabelecimento consultado nao existe!");
         }

         @Test
         @DisplayName("Quando alteramos o sabor com codigo de acesso invalido")
         void quandoAlteramosSaborEstabelecimentoComCodigoAcessoInvalido() throws
         Exception {
         // Arrange
         Long saborId = sabor.getId();

         // Act
         String responseJsonString = driver.perform(put(URI_SABORES + "/" +
         sabor.getId())
         .contentType(MediaType.APPLICATION_JSON)
         .param("idEstabelecimento", estabelecimento.getId().toString())
         .param("codigoDeAcesso", "456")
         .param("saborId", saborId.toString())
         .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
         .andExpect(status().isBadRequest()) // Codigo 400
         .andDo(print())
         .andReturn().getResponse().getContentAsString();

         CustomErrorType e = objectMapper.readValue(responseJsonString,
         CustomErrorType.class);
         // Assert
         assertEquals(e.getMessage(), "Codigo de acesso invalido!");
         }

         @Test
         @DisplayName("Quando alteramos o sabor com id sabor invalido")
         void quandoAlteramosSaborIdSaborInvalido() throws Exception {
         // Arrange
         Long saborId = sabor.getId();

         // Act
         String responseJsonString = driver.perform(put(URI_SABORES + "/" + "789")
         .contentType(MediaType.APPLICATION_JSON)
         .param("idEstabelecimento", estabelecimento.getId().toString())
         .param("codigoDeAcesso", estabelecimento.getCodigoAcesso().toString())
         .param("saborId", "789")
         .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
         .andExpect(status().isBadRequest()) // Codigo 400
         .andDo(print())
         .andReturn().getResponse().getContentAsString();

         CustomErrorType e = objectMapper.readValue(responseJsonString,
         CustomErrorType.class);
         // Assert
         assertEquals(e.getMessage(), "O sabor consultado nao existe!");
         }

         @Test
         @DisplayName("Quando excluímos um sabor salvo")
         void quandoExcluimosSaborValido() throws Exception {
         // Arrange
         saborRepository.deleteAll();
         // Arrange
         // Vamos ter 2 sabores no banco
         Sabor sabor = saborRepository.save(Sabor.builder()
         .nome("Chocolate")
         .tipo("doce")
         .valorMedia(10.0)
         .valorGrande(15.0)
         .disponivel(true)
         .estabelecimento(estabelecimento)
         .build());

         // Act
         String responseJsonString = driver.perform(delete(URI_SABORES + "/" +
         sabor.getId())
         .contentType(MediaType.APPLICATION_JSON)
         // .param("saborId", sabor.getId().toString())
         .param("idEstabelecimento", estabelecimento.getId().toString())
         .param("codigoDeAcesso", estabelecimento.getCodigoAcesso()))
         .andExpect(status().isNoContent()) // Codigo 204
         .andDo(print())
         .andReturn().getResponse().getContentAsString();

         // Assert
         assertTrue(responseJsonString.isBlank());
         }

         @Test
         @DisplayName("Quando excluímos um sabor inexistente")
         void quandoExcluimosSaborInexistente() throws Exception {
         // Arrange
         // nenhuma necessidade além do setup()

         // Act
         String responseJsonString = driver.perform(delete(URI_SABORES + "/99999")
         .contentType(MediaType.APPLICATION_JSON)
         .param("saborId", "999999")
         .param("idEstabelecimento", estabelecimento.getId().toString())
         .param("codigoDeAcesso", estabelecimento.getCodigoAcesso()))
         .andExpect(status().isBadRequest()) // Codigo 400
         .andDo(print())
         .andReturn().getResponse().getContentAsString();

         CustomErrorType resultado = objectMapper.readValue(responseJsonString,
         CustomErrorType.class);

         // Assert
         assertAll(
         () -> assertEquals("O sabor consultado nao existe!", resultado.getMessage()));
         }

         @Test
         @DisplayName("Quando excluímos um sabor passando id estabelecimento inválido")
         void quandoExcluimosSaborComIdEstabelecimentoInvalido() throws Exception {
         // Arrange
         // nenhuma necessidade além do setup()

         // Act
         String responseJsonString = driver.perform(delete(URI_SABORES + "/" +
         sabor.getId())
         .contentType(MediaType.APPLICATION_JSON)
         .param("saborId", sabor.getId().toString())
         .param("idEstabelecimento", "555555")
         .param("codigoDeAcesso", estabelecimento.getCodigoAcesso()))
         .andExpect(status().isBadRequest()) // Codigo 400
         .andDo(print())
         .andReturn().getResponse().getContentAsString();

         CustomErrorType resultado = objectMapper.readValue(responseJsonString,
         CustomErrorType.class);

         // Assert
         assertAll(
         () -> assertEquals("O estabelecimento consultado nao existe!",
         resultado.getMessage()));
         }

         @Test
         @DisplayName("Quando excluímos um sabor passando código de acesso inválido")
         void quandoExcluimosSaborCodigoAcessoInvalido() throws Exception {
         // Arrange
         // nenhuma necessidade além do setup()

         // Act
         String responseJsonString = driver.perform(delete(URI_SABORES + "/" +
         sabor.getId())
         .contentType(MediaType.APPLICATION_JSON)
         .param("saborId", sabor.getId().toString())
         .param("idEstabelecimento", estabelecimento.getId().toString())
         .param("codigoDeAcesso", "999999"))
         .andExpect(status().isBadRequest()) // Codigo 400
         .andDo(print())
         .andReturn().getResponse().getContentAsString();

         CustomErrorType resultado = objectMapper.readValue(responseJsonString,
         CustomErrorType.class);

         // Assert
         assertAll(
         () -> assertEquals("Codigo de acesso invalido!", resultado.getMessage()));
         }
         }

        @Nested
        @DisplayName("Conjunto de casos de verificação de nome")
        class SaborVerificacaoNome {

                @Test
                @DisplayName("Quando alteramos um sabor com nome válido")
                void quandoAlteramosSaborNomeValido() throws Exception {
                        // Arrange
                        saborRepository.deleteAll();
                        sabor = saborRepository.save(Sabor.builder()
                                        .nome("Calabresa")
                                        .tipo("salgado")
                                        .valorMedia(10.0)
                                        .valorGrande(15.0)
                                        .disponivel(true)
                                        .estabelecimento(estabelecimento)
                                        .build());

                        saborPostPutRequestDTO.setNome("Portuguesa");

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES + "/" + sabor.getId())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        // .param("saborId", sabor.getId().toString())
                                        .param("idEstabelecimento", estabelecimento.getId().toString())
                                        .param("codigoDeAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isOk()) // Codigo 200
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        SaborResponseDTO resultado = objectMapper.readValue(responseJsonString,
                                        SaborResponseDTO.SaborResponseDTOBuilder.class).build();

                        // Assert
                        assertEquals("Portuguesa", resultado.getNome());
                }

                @Test
                @DisplayName("Quando alteramos um sabor com nome vazio")
                void quandoAlteramosSaborNomeVazio() throws Exception {
                        // Arrange
                        saborRepository.deleteAll();
                        sabor = saborRepository.save(Sabor.builder()
                                        .nome("Calabresa")
                                        .tipo("salgado")
                                        .valorMedia(10.0)
                                        .valorGrande(15.0)
                                        .disponivel(true)
                                        .estabelecimento(estabelecimento)
                                        .build());
                        saborPostPutRequestDTO.setNome("");

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES + "/" + sabor.getId())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        // .param("saborId", sabor.getId().toString())
                                        .param("idEstabelecimento", estabelecimento.getId().toString())
                                        .param("codigoDeAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isBadRequest()) // Codigo 400
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                                        CustomErrorType.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals("Erros de validacao encontrados", resultado.getMessage()),
                                        () -> assertEquals("CAMPO NOME OBRIGATORIO", resultado.getErrors().get(0)));
                }
        }

        @Nested
        @DisplayName("Conjunto de casos de verificação de tipo")
        class SaborVerificacaoTipo {

                @Test
                @DisplayName("Quando alteramos um sabor com tipo válido")
                void quandoAlteramosSaborTipoValido() throws Exception {
                        // Arrange
                        saborRepository.deleteAll();
                        sabor = saborRepository.save(Sabor.builder()
                                        .nome("Calabresa")
                                        .tipo("salgado")
                                        .valorMedia(10.0)
                                        .valorGrande(15.0)
                                        .disponivel(true)
                                        .estabelecimento(estabelecimento)
                                        .build());
                        saborPostPutRequestDTO.setTipo("doce");

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES + "/" + sabor.getId())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        // .param("saborId", sabor.getId().toString())
                                        .param("idEstabelecimento", estabelecimento.getId().toString())
                                        .param("codigoDeAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isOk()) // Codigo 200
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        SaborResponseDTO resultado = objectMapper.readValue(responseJsonString,
                                        SaborResponseDTO.SaborResponseDTOBuilder.class).build();

                        // Assert
                        assertEquals("doce", resultado.getTipo());
                }

                @Test
                @DisplayName("Quando alteramos um sabor com tipo nulo")
                void quandoAlteramosSaborTipoNulo() throws Exception {
                        // Arrange
                        saborRepository.deleteAll();
                        sabor = saborRepository.save(Sabor.builder()
                                        .nome("Calabresa")
                                        .tipo("salgado")
                                        .valorMedia(10.0)
                                        .valorGrande(15.0)
                                        .disponivel(true)
                                        .estabelecimento(estabelecimento)
                                        .build());
                        saborPostPutRequestDTO.setTipo(null);

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES + "/" + sabor.getId())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        // .param("saborId", sabor.getId().toString())
                                        .param("idEstabelecimento", estabelecimento.getId().toString())
                                        .param("codigoDeAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isBadRequest()) // Codigo 400
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);



                        // Assert
                        assertAll(
                                        () -> assertEquals("Erros de validacao encontrados", resultado.getMessage()),
                                        () -> assertEquals("CAMPO TIPO NÃO PODE SER NULL",
                                                        resultado.getErrors().get(0)));
                }

                @Test
                @DisplayName("Quando alteramos um sabor com tipo inválido")
                void quandoAlteramosSaborTipoInvalido() throws Exception {
                        // Arrange
                        saborRepository.deleteAll();
                        sabor = saborRepository.save(Sabor.builder()
                                        .nome("Calabresa")
                                        .tipo("salgado")
                                        .valorMedia(10.0)
                                        .valorGrande(15.0)
                                        .disponivel(true)
                                        .estabelecimento(estabelecimento)
                                        .build());
                        saborPostPutRequestDTO.setTipo("tipo invalido");

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES + "/" + sabor.getId())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        // .param("saborId", sabor.getId().toString())
                                        .param("idEstabelecimento", estabelecimento.getId().toString())
                                        .param("codigoDeAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isBadRequest()) // Codigo 400
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                                        CustomErrorType.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals("Tipo inválido", resultado.getMessage())

                        );
                }
        }

        @Nested
        @DisplayName("Conjunto de casos de verificação de preco")
        class SaborVerificacaoPreco {

                @Test
                @DisplayName("Quando alteramos um sabor com precos válidos")
                void quandoAlteramosSaborPrecosValidos() throws Exception {
                        // Arrange
                        saborRepository.deleteAll();
                        sabor = saborRepository.save(Sabor.builder()
                                        .nome("Calabresa")
                                        .tipo("salgado")
                                        .valorMedia(10.0)
                                        .valorGrande(15.0)
                                        .disponivel(true)
                                        .estabelecimento(estabelecimento)
                                        .build());
                        saborPostPutRequestDTO.setValorMedia(40.0);
                        saborPostPutRequestDTO.setValorGrande(60.0);

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES + "/" + sabor.getId())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        // .param("saborId", sabor.getId().toString())
                                        .param("idEstabelecimento", estabelecimento.getId().toString())
                                        .param("codigoDeAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isOk()) // Codigo 200
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        SaborResponseDTO resultado = objectMapper.readValue(responseJsonString,
                                        SaborResponseDTO.SaborResponseDTOBuilder.class).build();

                        // Assert
                        assertAll(
                                        () -> assertEquals(40.0, resultado.getValorMedia()),
                                        () -> assertEquals(60.0, resultado.getValorGrande()));
                }


                @Test
                @DisplayName("Quando alteramos um sabor com precos inválidos")
                void quandoAlteramosSaborPrecosInvalidos() throws Exception {
                        // Arrange
                        saborRepository.deleteAll();
                        sabor = saborRepository.save(Sabor.builder()
                                        .nome("Calabresa")
                                        .tipo("salgado")
                                        .valorMedia(10.0)
                                        .valorGrande(15.0)
                                        .disponivel(true)
                                        .estabelecimento(estabelecimento)
                                        .build());
                        saborPostPutRequestDTO.setValorMedia(-10.0);
                        saborPostPutRequestDTO.setValorGrande(-250.0);

                        // Act
                        String responseJsonString = driver.perform(put(URI_SABORES + "/" + sabor.getId())
                                        .contentType(MediaType.APPLICATION_JSON)
                                        // .param("saborId", sabor.getId().toString())
                                        .param("idEstabelecimento", estabelecimento.getId().toString())
                                        .param("codigoDeAcesso", estabelecimento.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                                        .andExpect(status().isBadRequest()) // Codigo 400
                                        .andDo(print())
                                        .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                                        CustomErrorType.class);

                        // Assert
                        assertAll(
                                        () -> assertEquals("Valor inválido", resultado.getMessage()));
                }

                @Test
                @DisplayName("Quando alteramos um sabor com precos válidos e inválidos")
                void quandoAlteramosSaborPrecosValidosEInvalidos() throws Exception {
                // Arrange
                saborPostPutRequestDTO.setValorMedia(40.0);
                saborPostPutRequestDTO.setValorGrande(-250.0);

                // Act
                String responseJsonString = driver.perform(put(URI_SABORES + "/" + sabor.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                //.param("saborId", sabor.getId().toString())
                                .param("idEstabelecimento", estabelecimento.getId().toString())
                                .param("codigoDeAcesso", estabelecimento.getCodigoAcesso())
                .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
                .andExpect(status().isBadRequest()) // Codigo 400
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

                CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                CustomErrorType.class);

                // Assert
                assertAll(
                () -> assertEquals("Valor inválido", resultado.getMessage())
                );
                }
        }

        @Nested
        @DisplayName("Conjunto de casos de verificação de disponibilidade")
        class SaborVerificacaoDisponibilidade {

        @Test
        @DisplayName("Quando alteramos um sabor com disponibilidade válida")
        void quandoAlteramosSaborDisponibilidadeValida() throws Exception {
        // Arrange
        saborRepository.deleteAll();
        sabor = saborRepository.save(Sabor.builder()
                        .nome("Calabresa")
                        .tipo("salgado")
                        .valorMedia(10.0)
                        .valorGrande(15.0)
                        .disponivel(true)
                        .estabelecimento(estabelecimento)
                        .build());
        saborPostPutRequestDTO.setDisponivel(false);

        // Act
        String responseJsonString = driver.perform(put(URI_SABORES + "/" + sabor.getId())
        .contentType(MediaType.APPLICATION_JSON)
                        //.param("saborId", sabor.getId().toString())
                        .param("idEstabelecimento", estabelecimento.getId().toString())
                        .param("codigoDeAcesso", estabelecimento.getCodigoAcesso())
                        .content(objectMapper.writeValueAsString(saborPostPutRequestDTO)))
        .andExpect(status().isOk()) // Codigo 200
        .andDo(print())
        .andReturn().getResponse().getContentAsString();

        SaborResponseDTO resultado = objectMapper.readValue(responseJsonString,
        SaborResponseDTO.SaborResponseDTOBuilder.class).build();

        // Assert
        assertFalse(resultado.isDisponivel());
        }

        //ESTES TESTES NÃO FAZEM PARTE DA US6

        // @Test
        // @DisplayName("Quando alteramos a disponibilidade de um sabor para false")
        // void quandoAlteramosDisponibilidadeSaborFalse() throws Exception {
        // // Arrange
        // // Act
        // String responseJsonString = driver.perform(put(URI_SABORES + "/" +
        // sabor.getId() + "/" + false)
        // .contentType(MediaType.APPLICATION_JSON)
        // .param("saborId", sabor.getId().toString())
        // .param("estabelecimentoId", estabelecimento.getId().toString())
        // .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
        // .param("disponibilidade", String.valueOf(false))
        // .content(objectMapper.writeValueAsString(sabor)))
        // .andExpect(status().isOk()) // Codigo 200
        // .andDo(print())
        // .andReturn().getResponse().getContentAsString();

        // SaborResponseDTO resultado = objectMapper.readValue(responseJsonString,
        // SaborResponseDTO.SaborResponseDTOBuilder.class).build();

        // // Assert
        // assertFalse(resultado.isDisponivel());
        // }

        // @Test
        // @DisplayName("Quando alteramos a disponibilidade de um sabor para true")
        // void quandoAlteramosDisponibilidadeSaborTrue() throws Exception {
        // // Arrange
        // sabor.setDisponivel(false);
        // saborRepository.save(sabor);
        // // Act
        // String responseJsonString = driver.perform(put(URI_SABORES + "/" +
        // sabor.getId() + "/" + true)
        // .contentType(MediaType.APPLICATION_JSON)
        // .param("saborId", sabor.getId().toString())
        // .param("estabelecimentoId", estabelecimento.getId().toString())
        // .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
        // .param("disponibilidade", String.valueOf(true))
        // .content(objectMapper.writeValueAsString(sabor)))
        // .andExpect(status().isOk()) // Codigo 200
        // .andDo(print())
        // .andReturn().getResponse().getContentAsString();

        // SaborResponseDTO resultado = objectMapper.readValue(responseJsonString,
        // SaborResponseDTO.SaborResponseDTOBuilder.class).build();

        // // Assert
        // assertTrue(resultado.isDisponivel());
        // }

        // @Test
        // @DisplayName("Quando alteramos a disponibilidade de um sabor para false
        // quando já está false")
        // void quandoAlteramosDisponibilidadeSaborFalseQuandoJaEstaFalse() throws
        // Exception {
        // // Arrange
        // sabor.setDisponivel(false);
        // saborRepository.save(sabor);
        // // Act
        // String responseJsonString = driver.perform(put(URI_SABORES + "/" +
        // sabor.getId() + "/" + false)
        // .contentType(MediaType.APPLICATION_JSON)
        // .param("saborId", sabor.getId().toString())
        // .param("estabelecimentoId", estabelecimento.getId().toString())
        // .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
        // .param("disponibilidade", String.valueOf(false))
        // .content(objectMapper.writeValueAsString(sabor)))
        // .andExpect(status().isBadRequest()) // Codigo 200
        // .andDo(print())
        // .andReturn().getResponse().getContentAsString();

        // CustomErrorType resultado = objectMapper.readValue(responseJsonString,
        // CustomErrorType.class);

        // // Assert
        // assertEquals("O sabor consultado ja esta indisponivel!",
        // resultado.getMessage());
        // }

        // @Test
        // @DisplayName("Quando alteramos a disponibilidade de um sabor para true quando
        // já está true")
        // void quandoAlteramosDisponibilidadeSaborTrueQuandoJaEstaTrue() throws
        // Exception {
        // // Arrange
        // // Act
        // String responseJsonString = driver.perform(put(URI_SABORES + "/" +
        // sabor.getId() + "/" + true)
        // .contentType(MediaType.APPLICATION_JSON)
        // .param("saborId", sabor.getId().toString())
        // .param("estabelecimentoId", estabelecimento.getId().toString())
        // .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
        // .param("disponibilidade", String.valueOf(true))
        // .content(objectMapper.writeValueAsString(sabor)))
        // .andExpect(status().isBadRequest()) // Codigo 200
        // .andDo(print())
        // .andReturn().getResponse().getContentAsString();

        // CustomErrorType resultado = objectMapper.readValue(responseJsonString,
        // CustomErrorType.class);

        // // Assert
        // assertEquals("O sabor consultado ja esta disponivel!",
        // resultado.getMessage());
        // }

        @Test
        @DisplayName("Quando alteramos a disponibilidade de um sabor com o código de acesso errado")
        void quandoAlteramosDisponibilidadeSaborCodigoErrado() throws Exception {
        // Arrange
        saborRepository.deleteAll();
        sabor = saborRepository.save(Sabor.builder()
                        .nome("Calabresa")
                        .tipo("salgado")
                        .valorMedia(10.0)
                        .valorGrande(15.0)
                        .disponivel(true)
                        .estabelecimento(estabelecimento)
                        .build());
        // Act
        String responseJsonString = driver.perform(put(URI_SABORES + "/" +
        sabor.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("saborId", sabor.getId().toString())
                        .param("idEstabelecimento", estabelecimento.getId().toString())
                        .param("codigoDeAcesso", "aaaaaa")
                        .content(objectMapper.writeValueAsString(sabor)))
                .andExpect(status().isBadRequest()) // Codigo 200
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

        CustomErrorType resultado = objectMapper.readValue(responseJsonString,
        CustomErrorType.class);

        // Assert
        assertEquals("Codigo de acesso invalido!", resultado.getMessage());
        }
        }


    }
