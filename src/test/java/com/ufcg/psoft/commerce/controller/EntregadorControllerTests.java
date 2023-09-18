package com.ufcg.psoft.commerce.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.commerce.dto.Entregador.EntregadorGetRequestDTO;
import com.ufcg.psoft.commerce.dto.Entregador.EntregadorPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.Entregador.EntregadorResponseDTO;
import com.ufcg.psoft.commerce.exception.CustomErrorType;
import com.ufcg.psoft.commerce.exception.entregador.EntregadorNotFound;
import com.ufcg.psoft.commerce.models.Entregador;
import com.ufcg.psoft.commerce.models.Veiculo;
import com.ufcg.psoft.commerce.repositories.EntregadorRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de Entregadores")
public class EntregadorControllerTests {

    final String URI_ENTREGADORES = "/v1/entregadores";
/*
    final String URI_ENTREGADORES = "/entregadores";

    @Autowired
    MockMvc driver;

    @Autowired
    EntregadorRepository entregadorRepository;

    ObjectMapper objectMapper = new ObjectMapper();

    Entregador entregador;
    Veiculo veiculo;

    EntregadorPostPutRequestDTO entregadorPostPutRequestDTO;

    EntregadorGetRequestDTO entregadorDTO;

    @BeforeEach
    void setup() {
        objectMapper.registerModule(new JavaTimeModule());

        veiculo = Veiculo.builder()
                .cor("Vermelho")
                .placa("GGF-1111")
                .tipo("carro")
                .build();

        entregador = entregadorRepository.save(Entregador.builder()
                .nome("Lana Del Rey")
                .veiculo(veiculo)
                .codigoAcesso("123456")
                .build()
        );
        entregadorPostPutRequestDTO = EntregadorPostPutRequestDTO.builder()
                .nome(entregador.getNome())
                .codigoAcesso(entregador.getCodigoAcesso())
                .veiculo(entregador.getVeiculo())
                .build();

        entregadorDTO = objectMapper.convertValue(entregador, EntregadorGetRequestDTO.class);
    }

    @AfterEach
    void tearDown() {
        entregadorRepository.deleteAll();
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de fluxos básicos API Rest")
    class EntregadorVerificacaoFluxosBasicosApiRest {

        @Test
        @DisplayName("Quando buscamos por todos entregadores salvos")
        void quandoBuscamosTodosEntregadores() throws Exception {
            // Arrange
            // Vamos ter 3 entregadores no banco
            Entregador entregador1 = Entregador.builder()
                    .nome("Jose")
                    .veiculo(veiculo)
                    .build();
            Entregador entregador2 = Entregador.builder()
                    .nome("Halloran")
                    .veiculo(veiculo)
                    .build();
            entregadorRepository.saveAll(Arrays.asList(entregador1, entregador2));

            // Act
            String responseJsonString = driver.perform(get(URI_ENTREGADORES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            List<EntregadorResponseDTO> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {
            });

            // Assert
            assertEquals(3, resultado.size());
        }

        @Test
        @DisplayName("Quando buscamos por um entregador salvo pelo id")
        void quandoBuscamosEntregadorPorId() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(get(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorResponseDTO resultado = objectMapper.readValue(responseJsonString, EntregadorResponseDTO.EntregadorResponseDTOBuilder.class).build();

            // Assert
            assertAll(
                    () -> assertEquals(entregadorDTO.getId(), resultado.getId()),
                    () -> assertEquals(entregadorDTO.getNome(), resultado.getNome()),
                    () -> assertEquals(entregadorDTO.getVeiculo(), resultado.getVeiculo())
            );
        }

        @Test
        @DisplayName("Quando buscamos um entregador inexistente")
        void quandoBuscamosEntregadorInexistente() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(get(URI_ENTREGADORES + "/999999")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorNotFound resultado = objectMapper.readValue(responseJsonString, EntregadorNotFound.class);

            // Assert
            assertEquals("O entregador consultado nao existe!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando criamos um entregador com dados válidos")
        void quandoCriamosEntregadorValido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(post(URI_ENTREGADORES)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isCreated())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorResponseDTO resultado = objectMapper.readValue(responseJsonString, EntregadorResponseDTO.EntregadorResponseDTOBuilder.class).build();

            // Assert
            assertAll(
                    () -> assertEquals(entregadorPostPutRequestDTO.getNome(), resultado.getNome()),
                    () -> assertEquals(entregadorPostPutRequestDTO.getVeiculo(), resultado.getVeiculo())
            );
        }

        @Test
        @DisplayName("Quando alteramos o entregador com dados válidos")
        void quandoAlteramosEntregadorValido() throws Exception {
            // Arrange
            Long entregadorId = entregador.getId();

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorResponseDTO resultado = objectMapper.readValue(responseJsonString, EntregadorResponseDTO.EntregadorResponseDTOBuilder.class).build();

            // Assert
            assertAll(
                    () -> assertEquals(entregadorId, resultado.getId().longValue()),
                    () -> assertEquals(entregadorPostPutRequestDTO.getNome(), resultado.getNome()),
                    () -> assertEquals(entregadorPostPutRequestDTO.getVeiculo(), resultado.getVeiculo())
            );
        }

        @Test
        @DisplayName("Quando alteramos um entregador inexistente")
        void quandoAlteramosEntregadorInexistente() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/999999")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O entregador consultado nao existe!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando alteramos um entregador passando um código de acesso inválido")
        void quandoAlteramosEntregadorComCodigoAcessoInvalido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", "999999")
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Codigo de acesso invalido!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando excluímos um entregador salvo")
        void quandoExcluimosEntregadorSalvo() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(delete(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso()))
                    .andExpect(status().isNoContent())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            // Assert
            assertTrue(responseJsonString.isBlank());
        }

        @Test
        @DisplayName("Quando excluímos um entregador inexistente")
        void quandoExcluimosEntregadorInexistente() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(delete(URI_ENTREGADORES + "/999999")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso()))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O entregador consultado nao existe!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando excluímos um entregador passando um código de acesso inválido")
        void quandoExcluimosEntregadorComCodigoAcessoInvalido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(delete(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", "999999"))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Codigo de acesso invalido!", resultado.getMessage());
        }
    }


    @Nested
    @DisplayName("Conjunto de casos de verificação de nome")
    class EntregadorVerificacaoNome {

        @Test
        @DisplayName("Quando alteramos o nome do entregador com dados válidos")
        void quandoAlteramosNomeDoEntregadorValido() throws Exception {
            // Arrange
            entregadorPostPutRequestDTO.setNome("Lana Del Rey Alterada");

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorResponseDTO resultado = objectMapper.readValue(responseJsonString, EntregadorResponseDTO.EntregadorResponseDTOBuilder.class).build();

            // Assert
            assertEquals("Lana Del Rey Alterada", resultado.getNome());
        }

        @Test
        @DisplayName("Quando alteramos o entregador com nome vazio")
        void quandoAlteramosEntregadorComNomeVazio() throws Exception {
            // Arrange
            entregadorPostPutRequestDTO.setNome("");

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertAll(
                    () -> assertEquals("Erros de validacao encontrados", resultado.getMessage())
//                    () -> assertEquals("Nome e obrigatorio", resultado.getErrors().get(0))
            );
        }

        @Test
        @DisplayName("Quando alteramos o entregador passando codigo de acesso inválido")
        void quandoAlteramosEntregadorComCodigoAcessoInvalido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", "999999")
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Codigo de acesso invalido!", resultado.getMessage());
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de placa")
    class EntregadorVerificacaoPlaca {

        @Test
        @DisplayName("Quando alteramos o entregador com placa válida")
        void quandoAlteramosEntregadorComPlacaValida() throws Exception {
            // Arrange
            veiculo.setPlaca("DEF-3456");
            entregadorPostPutRequestDTO.setVeiculo(veiculo);

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorResponseDTO resultado = objectMapper.readValue(responseJsonString, EntregadorResponseDTO.EntregadorResponseDTOBuilder.class).build();

            // Assert
            assertEquals("DEF-3456", resultado.getVeiculo().getPlaca());
        }

        @Test
        @DisplayName("Quando alteramos o entregador com placa vazia")
        void quandoAlteramosEntregadorComPlacaVazia() throws Exception {
            // Arrange
            veiculo.setPlaca("");
            entregadorPostPutRequestDTO.setVeiculo(veiculo);

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertAll(
                    () -> assertEquals("Erros de validacao encontrados", resultado.getMessage())
//                    () -> assertEquals("Placa do veiculo e obrigatoria", resultado.getErrors().get(0))
            );
        }

        @Test
        @DisplayName("Quando alteramos o entregador passando codigo de acesso inválido")
        void quandoAlteramosEntregadorComCodigoAcessoInvalido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", "999999")
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Codigo de acesso invalido!", resultado.getMessage());
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de tipo")
    class EntregadorVerificacaoTipo {

        @Test
        @DisplayName("Quando alteramos o entregador com tipo de veiculo válido")
        void quandoAlteramosEntregadorComTipoVeiculoValido() throws Exception {
            // Arrange
            veiculo.setTipo("carro");
            entregadorPostPutRequestDTO.setVeiculo(veiculo);

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorResponseDTO resultado = objectMapper.readValue(responseJsonString, EntregadorResponseDTO.EntregadorResponseDTOBuilder.class).build();

            // Assert
            assertEquals("carro", resultado.getVeiculo().getTipo());
        }

        @Test
        @DisplayName("Quando alteramos o entregador com tipo de veiculo nulo")
        void quandoAlteramosEntregadorComTipoVeiculoVazio() throws Exception {
            // Arrange
            veiculo.setTipo("");
            entregadorPostPutRequestDTO.setVeiculo(veiculo);

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertAll(
                    () -> assertEquals("Erros de validacao encontrados", resultado.getMessage())
//                    () -> assertEquals("Tipo do veiculo e obrigatorio", resultado.getErrors().get(0))
            );
        }

        @Test
        @DisplayName("Quando alteramos o entregador com tipo de veiculo inválido")
        void quandoAlteramosEntregadorComTipoVeiculoInvalido() throws Exception {
            // Arrange
            veiculo.setTipo("bicicleta");
            entregadorPostPutRequestDTO.setVeiculo(veiculo);

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertAll(
                    () -> assertEquals("Erros de validacao encontrados", resultado.getMessage())
//                    () -> assertEquals("Tipo do veiculo deve ser moto ou carro", resultado.getErrors().get(0))
            );
        }

        @Test
        @DisplayName("Quando alteramos o tipo passando codigo de acesso inválido")
        void quandoAlteramosEntregadorComCodigoAcessoInvalido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", "999999")
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Codigo de acesso invalido!", resultado.getMessage());
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de verificação de cor do veiculo")
    class EntregadorVerificacaoCorVeiculo {

        @Test
        @DisplayName("Quando alteramos o entregador com cor do veiculo válida")
        void quandoAlteramosEntregadorComCorVeiculoValida() throws Exception {
            // Arrange
            veiculo.setCor("preto");
            entregadorPostPutRequestDTO.setVeiculo(veiculo);

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorResponseDTO resultado = objectMapper.readValue(responseJsonString, EntregadorResponseDTO.EntregadorResponseDTOBuilder.class).build();

            // Assert
            assertEquals("preto", resultado.getVeiculo().getCor());
        }

        @Test
        @DisplayName("Quando alteramos o entregador com cor do veiculo vazia")
        void quandoAlteramosEntregadorComCorVeiculoVazia() throws Exception {
            // Arrange
            veiculo.setCor("");
            entregadorPostPutRequestDTO.setVeiculo(veiculo);

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertAll(
                    () -> assertEquals("Erros de validacao encontrados", resultado.getMessage())
//                    () -> assertEquals("Cor do veiculo e obrigatoria", resultado.getErrors().get(0))
            );
        }

        @Test
        @DisplayName("Quando alteramos a cor do veiculo passando codigo de acesso inválido")
        void quandoAlteramosEntregadorComCodigoAcessoInvalido() throws Exception {
            // Arrange
            // nenhuma necessidade além do setup()

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId())
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", "999999")
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Codigo de acesso invalido!", resultado.getMessage());
        }
    }

    @Nested
    @DisplayName("Conjunto de casos de alteração de disponibilidade do entregador")
    class EntregadorDefinirDisponibilidade {

        @Test
        @DisplayName("Quando alteramos a disponibilidade do entregador para disponível")
        void quandoAlteramosDisponibilidadeParaDisponivel() throws Exception {
            // Arrange

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId() + "/disponibilidade")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .param("disponibilidade", "true")
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorResponseDTO resultado = objectMapper.readValue(responseJsonString, EntregadorResponseDTO.EntregadorResponseDTOBuilder.class).build();

            // Assert
//            assertTrue(resultado.isDisponibilidade());
        }

        @Test
        @DisplayName("Quando alteramos a disponibilidade do entregador para indisponível")
        void quandoAlteramosDisponibilidadeParaIndisponivel() throws Exception {
            // Arrange

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId() + "/disponibilidade")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .param("disponibilidade", "false")
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            EntregadorResponseDTO resultado = objectMapper.readValue(responseJsonString, EntregadorResponseDTO.EntregadorResponseDTOBuilder.class).build();

            // Assert
//            assertFalse(resultado.isDisponibilidade());
        }

        @Test
        @DisplayName("Quando alteramos a disponibilidade de um entregador inexistente")
        void quandoAlteramosDisponibilidadeDeEntregadorInexistente() throws Exception {
            // Arrange

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + 999999 + "/disponibilidade")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", entregador.getCodigoAcesso())
                            .param("disponibilidade", "true")
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("O entregador consultado nao existe!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando alteramos a disponibilidade de um entregador passando codigo de acesso inválido")
        void quandoAlteramosDisponibilidadeDeEntregadorComCodigoAcessoInvalido() throws Exception {
            // Arrange

            // Act
            String responseJsonString = driver.perform(put(URI_ENTREGADORES + "/" + entregador.getId() + "/disponibilidade")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcesso", "999999")
                            .param("disponibilidade", "true")
                            .content(objectMapper.writeValueAsString(entregadorPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

            // Assert
            assertEquals("Codigo de acesso invalido!", resultado.getMessage());
//            assertFalse(entregador.isDisponibilidade());
        }
    }
    */

}
