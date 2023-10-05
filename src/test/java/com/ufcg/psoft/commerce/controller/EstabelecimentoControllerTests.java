        package com.ufcg.psoft.commerce.controller;

        import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

        import com.ufcg.psoft.commerce.dto.Estabelecimento.EstabelecimentoPostResponseDTO;
        import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.commerce.dto.Estabelecimento.EstabelecimentoPatchCodigoDTO;
import com.ufcg.psoft.commerce.dto.Estabelecimento.EstabelecimentoPostPutRequestDTO;
import com.ufcg.psoft.commerce.exception.CustomErrorType;
import com.ufcg.psoft.commerce.models.Entregador;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.Sabor;
import com.ufcg.psoft.commerce.models.Veiculo;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.SaborRepository;

import jakarta.transaction.Transactional;

        @SpringBootTest
        @AutoConfigureMockMvc
        @DisplayName("Testes do controlador de estabelecimentos")
        public class EstabelecimentoControllerTests {
        @Autowired
        MockMvc driver;

        @Autowired
        EstabelecimentoRepository estabelecimentoRepository;

        @Autowired
        SaborRepository saborRepository;

        ObjectMapper objectMapper = new ObjectMapper();

        @Autowired
        ModelMapper modelMapper;

        Estabelecimento estabelecimento;

        @BeforeEach
        void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        estabelecimento = estabelecimentoRepository.save(Estabelecimento.builder()
                .codigoAcesso("123456")
                .build());
        }

        @AfterEach
        void tearDown() {
        estabelecimentoRepository.deleteAll();
        }

        @Nested
        @DisplayName("Conjunto de casos de verificação dos fluxos básicos API Rest")
        class EstabelecimentoVerificacaoFluxosBasicosApiRest {
        final String URI_ESTABELECIMENTOS = "/v1/estabelecimentos";
        EstabelecimentoPostPutRequestDTO estabelecimentoPutRequestDTO;
        EstabelecimentoPostPutRequestDTO estabelecimentoPostRequestDTO;


        @BeforeEach
        void setup() {
                estabelecimentoPutRequestDTO = EstabelecimentoPostPutRequestDTO.builder()
                        .codigoAcesso("123456")
                        .build();
                estabelecimentoPostRequestDTO = EstabelecimentoPostPutRequestDTO.builder()
                        .codigoAcesso("654321")
                        .build();
        }

        @Test
        @DisplayName("Quando criamos um novo estabelecimento com dados válidos")
        void quandoCriarEstabelecimentoValido() throws Exception {
                // Arrange
                // nenhuma necessidade além do setup()
                estabelecimentoPostRequestDTO.setUsuario("igoramf");

                // Act
                String responseJsonString = driver.perform(post(URI_ESTABELECIMENTOS)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigoAcesso", estabelecimentoPostRequestDTO.getCodigoAcesso())
                                .content(objectMapper.writeValueAsString(estabelecimentoPostRequestDTO)))
                        .andExpect(status().isCreated()) // Codigo 201
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                EstabelecimentoPostResponseDTO resultado = objectMapper.readValue(responseJsonString, EstabelecimentoPostResponseDTO.class);

                // Assert
                assertAll(
                        () -> assertNotNull(resultado.getId()),
                        () -> assertEquals(estabelecimentoPostRequestDTO.getCodigoAcesso(), resultado.getCodigoAcesso())
                );
        }

            @Test
            @DisplayName("Quando criamos um novo estabelecimento com codigo de acesso menor que 6 digitos")
            void quandoCriarEstabelecimentoComCodigoDeAcessoMenorQue6Digitos() throws Exception {
                // Arrange
                // nenhuma necessidade além do setup()
                estabelecimentoPostRequestDTO.setUsuario("igoramf");
                estabelecimentoPostRequestDTO.setCodigoAcesso("1231");

                // Act
                String responseJsonString = driver.perform(post(URI_ESTABELECIMENTOS)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigoAcesso", estabelecimentoPostRequestDTO.getCodigoAcesso())
                                .content(objectMapper.writeValueAsString(estabelecimentoPostRequestDTO)))
                        .andExpect(status().isBadRequest()) // Codigo 400
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                    CustomErrorType customErrorType = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                    // ASSERT
                    assertAll(
                            () -> assertEquals("Codigo de acesso invalido!", customErrorType.getMessage())
                    );
            }


        @Test
        @DisplayName("Quando criamos um novo estabelecimento sem informar o codigo de Acesso")
        void quandoCriarEstabelecimentoComCodigoVazio() throws Exception {
                // Arrange

                // ENTREGADORES
                Set<Entregador> entregadores = new HashSet<Entregador>();
                Veiculo v = Veiculo.builder()
                        .cor("verde")
                        .placa("123123")
                        .tipo("moto")
                        .build();

                Entregador e1 = Entregador.builder()
                        .codigoAcesso("12345")
                        .usuario("igor")
                        .veiculo(v)
                        .build();

                entregadores.add(e1);


                // CARDAPIO
                Set<Sabor> sabores = new HashSet<Sabor>();
                Sabor s = Sabor.builder()
                        .nome("calabresa")
                        .tipo("salgada")
                        .valorGrande(30.00)
                        .valorMedia(20.00)
                        .disponivel(true)
                        .build();

                Sabor s2 = Sabor.builder()
                        .nome("frango")
                        .tipo("salgada")
                        .valorGrande(35.00)
                        .valorMedia(23.00)
                        .disponivel(true)
                        .build();

                sabores.add(s);
                sabores.add(s2);

                estabelecimentoPostRequestDTO.setCardapio(sabores);
                estabelecimentoPostRequestDTO.setEntregadores(entregadores);
                estabelecimentoPostRequestDTO.setUsuario("estabelecimento-1");

                estabelecimentoPostRequestDTO.setCodigoAcesso(null);

                // Act
                String responseJsonString = driver.perform(post(URI_ESTABELECIMENTOS)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigoAcesso", estabelecimentoPostRequestDTO.getCodigoAcesso())
                                .content(objectMapper.writeValueAsString(estabelecimentoPostRequestDTO)))
                        .andExpect(status().isBadRequest()) // Codigo 400
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType customErrorType = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                // ASSERT
                assertAll(
                        () -> assertEquals("Erros de validacao encontrados", customErrorType.getMessage()),
                        () -> assertEquals(
                                1, customErrorType.getErrors().size()
                        ),
                        () -> assertTrue(
                                customErrorType.getErrors().stream().anyMatch(
                                        (msg) -> msg.contains("Campo de codigo de acesso obrigatorio")
                                )
                        )
                );
        }

        @Test
        @DisplayName("Atualizacao de Estabelecimento")
        void updateEstabelecimentoComDadosValidos() throws Exception {


                // ENTREGADORES
                Set<Entregador> entregadores = new HashSet<Entregador>();
                Veiculo v = Veiculo.builder()
                        .cor("verde")
                        .placa("123123")
                        .tipo("moto")
                        .build();

                Entregador e1 = Entregador.builder()
                        .codigoAcesso("12345")
                        .usuario("igor")
                        .veiculo(v)
                        .build();

                entregadores.add(e1);


                // CARDAPIO
                Set<Sabor> sabores = new HashSet<Sabor>();
                Sabor s = Sabor.builder()
                        .nome("calabresa")
                        .tipo("salgada")
                        .valorGrande(30.00)
                        .valorMedia(20.00)
                        .disponivel(true)
                        .build();

                Sabor s2 = Sabor.builder()
                        .nome("frango")
                        .tipo("salgada")
                        .valorGrande(35.00)
                        .valorMedia(23.00)
                        .disponivel(true)
                        .build();

                sabores.add(s);
                sabores.add(s2);

                estabelecimentoPostRequestDTO.setCardapio(sabores);
                estabelecimentoPostRequestDTO.setEntregadores(entregadores);
                estabelecimentoPostRequestDTO.setUsuario("estabelecimento-1");

                Estabelecimento based = estabelecimentoRepository.save(modelMapper.map(estabelecimentoPostRequestDTO, Estabelecimento.class));

                // ALTERACOES
                estabelecimentoPostRequestDTO.setCodigoAcesso("29042003");

                // Act
                String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + based.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigo", based.getCodigoAcesso())
                                .content(objectMapper.writeValueAsString(estabelecimentoPostRequestDTO)))
                        .andExpect(status().isOk()) // Codigo 200
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                Estabelecimento resultado = objectMapper.readValue(responseJsonString, Estabelecimento.class);

                // Assert
                assertAll(
                        () -> assertNotNull(resultado.getId()),
                        () -> assertEquals(estabelecimentoPostRequestDTO.getCodigoAcesso(), resultado.getCodigoAcesso())
                );
        }

        @Test
        @DisplayName("Atualizacao de O estabelecimento consultado nao existe!")
        void updateEstabelecimentoNaoEncontrado() throws Exception {


                // ENTREGADORES
                Set<Entregador> entregadores = new HashSet<Entregador>();
                Veiculo v = Veiculo.builder()
                        .cor("verde")
                        .placa("123123")
                        .tipo("moto")
                        .build();

                Entregador e1 = Entregador.builder()
                        .codigoAcesso("12345")
                        .usuario("igor")
                        .veiculo(v)
                        .build();

                entregadores.add(e1);


                // CARDAPIO
                Set<Sabor> sabores = new HashSet<Sabor>();
                Sabor s = Sabor.builder()
                        .nome("calabresa")
                        .tipo("salgada")
                        .valorGrande(30.00)
                        .valorMedia(20.00)
                        .disponivel(true)
                        .build();

                Sabor s2 = Sabor.builder()
                        .nome("frango")
                        .tipo("salgada")
                        .valorGrande(35.00)
                        .valorMedia(23.00)
                        .disponivel(true)
                        .build();

                sabores.add(s);
                sabores.add(s2);

                estabelecimentoPostRequestDTO.setCardapio(sabores);
                estabelecimentoPostRequestDTO.setEntregadores(entregadores);

                Estabelecimento based = estabelecimentoRepository.save(modelMapper.map(estabelecimentoPostRequestDTO, Estabelecimento.class));

                // ALTERACOES
                estabelecimentoPostRequestDTO.setCodigoAcesso("29042003");

                // Act
                String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + 100)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigo", based.getCodigoAcesso())
                                .content(objectMapper.writeValueAsString(estabelecimentoPostRequestDTO)))
                        .andExpect(status().isBadRequest()) // Codigo 200
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType customErrorType = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                // ASSERT
                assertAll(
                        () -> assertEquals("O estabelecimento consultado nao existe!", customErrorType.getMessage())
                );
        }

        @Test
        @DisplayName("Patch de codigo  Estabelecimento")
        @Transactional
        void patchEstabelecimentoCodigoValido() throws Exception {


                // ENTREGADORES
                Set<Entregador> entregadores = new HashSet<Entregador>();
                Veiculo v = Veiculo.builder()
                        .cor("verde")
                        .placa("123123")
                        .tipo("moto")
                        .build();

                Entregador e1 = Entregador.builder()
                        .codigoAcesso("12345")
                        .usuario("igor")
                        .veiculo(v)
                        .build();

                entregadores.add(e1);


                // CARDAPIO
                Set<Sabor> sabores = new HashSet<Sabor>();
                Sabor s = Sabor.builder()
                        .nome("calabresa")
                        .tipo("salgada")
                        .valorGrande(30.00)
                        .valorMedia(20.00)
                        .disponivel(true)
                        .build();

                Sabor s2 = Sabor.builder()
                        .nome("frango")
                        .tipo("salgada")
                        .valorGrande(35.00)
                        .valorMedia(23.00)
                        .disponivel(true)
                        .build();

                sabores.add(s);
                sabores.add(s2);

                estabelecimentoPostRequestDTO.setCardapio(sabores);
                estabelecimentoPostRequestDTO.setEntregadores(entregadores);

                Estabelecimento based = estabelecimentoRepository.save(modelMapper.map(estabelecimentoPostRequestDTO, Estabelecimento.class));
                //PATCH
                EstabelecimentoPatchCodigoDTO estabelecimentoPatchCodigoDTO = EstabelecimentoPatchCodigoDTO.builder()
                        .codigo("29042003")
                        .build();


                // Act
                String responseJsonString = driver.perform(patch(URI_ESTABELECIMENTOS + "/" + based.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigo", based.getCodigoAcesso())
                                .content(objectMapper.writeValueAsString(estabelecimentoPatchCodigoDTO)))
                        .andExpect(status().isOk()) // Codigo 200
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                Estabelecimento resultado = objectMapper.readValue(responseJsonString, Estabelecimento.class);

                // Assert
                assertAll(
                        () -> assertNotNull(resultado.getId()),
                        () -> assertNotEquals(estabelecimentoPostRequestDTO.getCodigoAcesso(), resultado.getCodigoAcesso()),
                        () -> assertEquals(estabelecimentoPostRequestDTO.getCardapio().size(), resultado.getCardapio().size()),
                        () -> assertEquals(estabelecimentoPostRequestDTO.getEntregadores().size(), resultado.getEntregadores().size())
                );
        }

        @Test
        @DisplayName("Patch de codigo Invalido Estabelecimento")
        void patchEstabelecimentoCodigoInvalido() throws Exception {


                // ENTREGADORES
                Set<Entregador> entregadores = new HashSet<Entregador>();
                Veiculo v = Veiculo.builder()
                        .cor("verde")
                        .placa("123123")
                        .tipo("moto")
                        .build();

                Entregador e1 = Entregador.builder()
                        .codigoAcesso("12345")
                        .usuario("igor")
                        .veiculo(v)
                        .build();

                entregadores.add(e1);


                // CARDAPIO
                Set<Sabor> sabores = new HashSet<Sabor>();
                Sabor s = Sabor.builder()
                        .nome("calabresa")
                        .tipo("salgada")
                        .valorGrande(30.00)
                        .valorMedia(20.00)
                        .disponivel(true)
                        .build();

                Sabor s2 = Sabor.builder()
                        .nome("frango")
                        .tipo("salgada")
                        .valorGrande(35.00)
                        .valorMedia(23.00)
                        .disponivel(true)
                        .build();

                sabores.add(s);
                sabores.add(s2);

                estabelecimentoPostRequestDTO.setCardapio(sabores);
                estabelecimentoPostRequestDTO.setEntregadores(entregadores);

                Estabelecimento based = estabelecimentoRepository.save(modelMapper.map(estabelecimentoPostRequestDTO, Estabelecimento.class));
                //PATCH
                EstabelecimentoPatchCodigoDTO estabelecimentoPatchCodigoDTO = EstabelecimentoPatchCodigoDTO.builder()
                        .codigo(null)
                        .build();


                // Act
                String responseJsonString = driver.perform(patch(URI_ESTABELECIMENTOS + "/" + based.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigo", based.getCodigoAcesso())
                                .content(objectMapper.writeValueAsString(estabelecimentoPatchCodigoDTO)))
                        .andExpect(status().isBadRequest()) // Codigo 400
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType customErrorType = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                // ASSERT
                assertAll(
                        () -> assertEquals("Erros de validacao encontrados", customErrorType.getMessage()),
                        () -> assertEquals(
                                1, customErrorType.getErrors().size()
                        ),
                        () -> assertTrue(
                                customErrorType.getErrors().stream().anyMatch(
                                        (msg) -> msg.contains("Campo de Codigo de acesso obrigatorio")
                                )
                        )
                );
        }

        @Test
        @DisplayName("Patch de codigo de acesso do Estabelecimento Invalido")
        void patchEstabelecimentoCodigoDeAcessoDoEstabelecimentoInvalido() throws Exception {


                // ENTREGADORES
                Set<Entregador> entregadores = new HashSet<Entregador>();
                Veiculo v = Veiculo.builder()
                        .cor("verde")
                        .placa("123123")
                        .tipo("moto")
                        .build();

                Entregador e1 = Entregador.builder()
                        .codigoAcesso("12345")
                        .usuario("igor")
                        .veiculo(v)
                        .build();

                entregadores.add(e1);


                // CARDAPIO
                Set<Sabor> sabores = new HashSet<Sabor>();
                Sabor s = Sabor.builder()
                        .nome("calabresa")
                        .tipo("salgada")
                        .valorGrande(30.00)
                        .valorMedia(20.00)
                        .disponivel(true)
                        .build();

                Sabor s2 = Sabor.builder()
                        .nome("frango")
                        .tipo("salgada")
                        .valorGrande(35.00)
                        .valorMedia(23.00)
                        .disponivel(true)
                        .build();

                sabores.add(s);
                sabores.add(s2);

                estabelecimentoPostRequestDTO.setCardapio(sabores);
                estabelecimentoPostRequestDTO.setEntregadores(entregadores);

                Estabelecimento based = estabelecimentoRepository.save(modelMapper.map(estabelecimentoPostRequestDTO, Estabelecimento.class));
                //PATCH
                EstabelecimentoPatchCodigoDTO estabelecimentoPatchCodigoDTO = EstabelecimentoPatchCodigoDTO.builder()
                        .codigo("3123123")
                        .build();


                // Act
                String responseJsonString = driver.perform(patch(URI_ESTABELECIMENTOS + "/" + based.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigo", "123123123")
                                .content(objectMapper.writeValueAsString(estabelecimentoPatchCodigoDTO)))
                        .andExpect(status().isBadRequest()) // Codigo 400
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType customErrorType = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                // ASSERT
                assertAll(
                        () -> assertEquals("Codigo de acesso invalido!", customErrorType.getMessage())
                );
        }

        @Test
        @DisplayName("Patch de codigo de acesso do Estabelecimento Invalido")
        void patchEstabelecimentoComIdInvalido() throws Exception {


                // ENTREGADORES
                Set<Entregador> entregadores = new HashSet<Entregador>();
                Veiculo v = Veiculo.builder()
                        .cor("verde")
                        .placa("123123")
                        .tipo("moto")
                        .build();

                Entregador e1 = Entregador.builder()
                        .codigoAcesso("12345")
                        .usuario("igor")
                        .veiculo(v)
                        .build();

                entregadores.add(e1);


                // CARDAPIO
                Set<Sabor> sabores = new HashSet<Sabor>();
                Sabor s = Sabor.builder()
                        .nome("calabresa")
                        .tipo("salgada")
                        .valorGrande(30.00)
                        .valorMedia(20.00)
                        .disponivel(true)
                        .build();

                Sabor s2 = Sabor.builder()
                        .nome("frango")
                        .tipo("salgada")
                        .valorGrande(35.00)
                        .valorMedia(23.00)
                        .disponivel(true)
                        .build();

                sabores.add(s);
                sabores.add(s2);

                estabelecimentoPostRequestDTO.setCardapio(sabores);
                estabelecimentoPostRequestDTO.setEntregadores(entregadores);

                Estabelecimento based = estabelecimentoRepository.save(modelMapper.map(estabelecimentoPostRequestDTO, Estabelecimento.class));
                //PATCH
                EstabelecimentoPatchCodigoDTO estabelecimentoPatchCodigoDTO = EstabelecimentoPatchCodigoDTO.builder()
                        .codigo("3123123")
                        .build();


                // Act
                String responseJsonString = driver.perform(patch(URI_ESTABELECIMENTOS + "/" + 100)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigo", based.getCodigoAcesso())
                                .content(objectMapper.writeValueAsString(estabelecimentoPatchCodigoDTO)))
                        .andExpect(status().isBadRequest()) // Codigo 400
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType customErrorType = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                // ASSERT
                assertAll(
                        () -> assertEquals("O estabelecimento consultado nao existe!", customErrorType.getMessage())
                );
        }

        @Test
        @DisplayName("Atualizacao de Estabelecimento com codigo de acesso invalido")
        void updateEstabelecimentoComCodigoDeAcessoInvalido() throws Exception {


                // ENTREGADORES
                Set<Entregador> entregadores = new HashSet<Entregador>();
                Veiculo v = Veiculo.builder()
                        .cor("verde")
                        .placa("123123")
                        .tipo("moto")
                        .build();

                Entregador e1 = Entregador.builder()
                        .codigoAcesso("12345")
                        .usuario("igor")
                        .veiculo(v)
                        .build();

                entregadores.add(e1);


                // CARDAPIO
                Set<Sabor> sabores = new HashSet<Sabor>();
                Sabor s = Sabor.builder()
                        .nome("calabresa")
                        .tipo("salgada")
                        .valorGrande(30.00)
                        .valorMedia(20.00)
                        .disponivel(true)
                        .build();

                Sabor s2 = Sabor.builder()
                        .nome("frango")
                        .tipo("salgada")
                        .valorGrande(35.00)
                        .valorMedia(23.00)
                        .disponivel(true)
                        .build();

                sabores.add(s);
                sabores.add(s2);

                estabelecimentoPostRequestDTO.setCardapio(sabores);
                estabelecimentoPostRequestDTO.setEntregadores(entregadores);
                estabelecimentoPostRequestDTO.setUsuario("estabelecimento-1");

                Estabelecimento based = estabelecimentoRepository.save(modelMapper.map(estabelecimentoPostRequestDTO, Estabelecimento.class));

                // ALTERACOES
                estabelecimentoPostRequestDTO.setCodigoAcesso("29042003");

                // Act
                String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + based.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigo", "1")
                                .content(objectMapper.writeValueAsString(estabelecimentoPostRequestDTO)))
                        .andExpect(status().isBadRequest()) // Codigo 200
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType customErrorType = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                // ASSERT
                assertAll(
                        () -> assertEquals("Codigo de acesso invalido!", customErrorType.getMessage())
                );
        }


        @Test
        @DisplayName("Delete de Estabelecimento com codigo de acesso Valido")
        void deleteEstabelecimentoComCodigoDeAcessoValido() throws Exception {


                // ENTREGADORES
                Set<Entregador> entregadores = new HashSet<Entregador>();
                Veiculo v = Veiculo.builder()
                        .cor("verde")
                        .placa("123123")
                        .tipo("moto")
                        .build();

                Entregador e1 = Entregador.builder()
                        .codigoAcesso("12345")
                        .usuario("igor")
                        .veiculo(v)
                        .build();

                entregadores.add(e1);


                // CARDAPIO
                Set<Sabor> sabores = new HashSet<Sabor>();
                Sabor s = Sabor.builder()
                        .nome("calabresa")
                        .tipo("salgada")
                        .valorGrande(30.00)
                        .valorMedia(20.00)
                        .disponivel(true)
                        .build();

                Sabor s2 = Sabor.builder()
                        .nome("frango")
                        .tipo("salgada")
                        .valorGrande(35.00)
                        .valorMedia(23.00)
                        .disponivel(true)
                        .build();

                sabores.add(s);
                sabores.add(s2);

                estabelecimentoPostRequestDTO.setCardapio(sabores);
                estabelecimentoPostRequestDTO.setEntregadores(entregadores);
                estabelecimentoPostRequestDTO.setUsuario("estabelecimento-1");

                Estabelecimento based = estabelecimentoRepository.save(modelMapper.map(estabelecimentoPostRequestDTO, Estabelecimento.class));
                estabelecimentoRepository.save(modelMapper.map(estabelecimentoPutRequestDTO, Estabelecimento.class));


                // Act
                String responseJsonString = driver.perform(delete(URI_ESTABELECIMENTOS + "/" + based.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigo", estabelecimentoPostRequestDTO.getCodigoAcesso())
                                .content(objectMapper.writeValueAsString(estabelecimentoPostRequestDTO)))
                        .andExpect(status().isNoContent())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();


                // ASSERT
                assertAll(
                        () -> assertTrue(responseJsonString.isBlank())
                );
        }

        @Test
        @DisplayName("Delete de Estabelecimento com id Invalido")
        void deleteEstabelecimentoComIdInvalido() throws Exception {


                // ENTREGADORES
                Set<Entregador> entregadores = new HashSet<Entregador>();
                Veiculo v = Veiculo.builder()
                        .cor("verde")
                        .placa("123123")
                        .tipo("moto")
                        .build();

                Entregador e1 = Entregador.builder()
                        .codigoAcesso("12345")
                        .usuario("igor")
                        .veiculo(v)
                        .build();

                entregadores.add(e1);


                // CARDAPIO
                Set<Sabor> sabores = new HashSet<Sabor>();
                Sabor s = Sabor.builder()
                        .nome("calabresa")
                        .tipo("salgada")
                        .valorGrande(30.00)
                        .valorMedia(20.00)
                        .disponivel(true)
                        .build();

                Sabor s2 = Sabor.builder()
                        .nome("frango")
                        .tipo("salgada")
                        .valorGrande(35.00)
                        .valorMedia(23.00)
                        .disponivel(true)
                        .build();

                sabores.add(s);
                sabores.add(s2);

                estabelecimentoPostRequestDTO.setCardapio(sabores);
                estabelecimentoPostRequestDTO.setEntregadores(entregadores);
                estabelecimentoPostRequestDTO.setUsuario("estabelecimento-1");

                Estabelecimento based = estabelecimentoRepository.save(modelMapper.map(estabelecimentoPostRequestDTO, Estabelecimento.class));
                estabelecimentoRepository.save(modelMapper.map(estabelecimentoPutRequestDTO, Estabelecimento.class));


                // Act
                String responseJsonString = driver.perform(delete(URI_ESTABELECIMENTOS + "/" + 10)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigo", "123123")
                                .content(objectMapper.writeValueAsString(estabelecimentoPostRequestDTO)))
                        .andExpect(status().isBadRequest())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();


                CustomErrorType customErrorType = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                // ASSERT
                assertAll(
                        () -> assertEquals("O estabelecimento consultado nao existe!", customErrorType.getMessage())
                );
        }

        @Test
        @DisplayName("Delete de Estabelecimento com Codigo de acesso Invalido")
        void deleteEstabelecimentoComCodigoDeAcessoInvalido() throws Exception {


                // ENTREGADORES
                Set<Entregador> entregadores = new HashSet<Entregador>();
                Veiculo v = Veiculo.builder()
                        .cor("verde")
                        .placa("123123")
                        .tipo("moto")
                        .build();

                Entregador e1 = Entregador.builder()
                        .codigoAcesso("12345")
                        .usuario("igor")
                        .veiculo(v)
                        .build();

                entregadores.add(e1);


                // CARDAPIO
                Set<Sabor> sabores = new HashSet<Sabor>();
                Sabor s = Sabor.builder()
                        .nome("calabresa")
                        .tipo("salgada")
                        .valorGrande(30.00)
                        .valorMedia(20.00)
                        .disponivel(true)
                        .build();

                Sabor s2 = Sabor.builder()
                        .nome("frango")
                        .tipo("salgada")
                        .valorGrande(35.00)
                        .valorMedia(23.00)
                        .disponivel(true)
                        .build();

                sabores.add(s);
                sabores.add(s2);

                estabelecimentoPostRequestDTO.setCardapio(sabores);
                estabelecimentoPostRequestDTO.setEntregadores(entregadores);
                estabelecimentoPostRequestDTO.setUsuario("estabelecimento-1");

                Estabelecimento based = estabelecimentoRepository.save(modelMapper.map(estabelecimentoPostRequestDTO, Estabelecimento.class));
                estabelecimentoRepository.save(modelMapper.map(estabelecimentoPutRequestDTO, Estabelecimento.class));


                // Act
                String responseJsonString = driver.perform(delete(URI_ESTABELECIMENTOS + "/" + based.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigo", "1232134")
                                .content(objectMapper.writeValueAsString(estabelecimentoPostRequestDTO)))
                        .andExpect(status().isBadRequest())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();


                CustomErrorType customErrorType = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                // ASSERT
                assertAll(
                        () -> assertEquals("Codigo de acesso invalido!", customErrorType.getMessage())
                );
        }

        @Test
        @DisplayName("Retornar todos os Estabelecimentos")
        void getAllEstabelecimentos() throws Exception {


                // ENTREGADORES
                Set<Entregador> entregadores = new HashSet<Entregador>();
                Veiculo v = Veiculo.builder()
                        .cor("verde")
                        .placa("123123")
                        .tipo("moto")
                        .build();

                Entregador e1 = Entregador.builder()
                        .codigoAcesso("12345")
                        .usuario("igor")
                        .veiculo(v)
                        .build();

                entregadores.add(e1);


                // CARDAPIO
                Set<Sabor> sabores = new HashSet<Sabor>();
                Sabor s = Sabor.builder()
                        .nome("calabresa")
                        .tipo("salgada")
                        .valorGrande(30.00)
                        .valorMedia(20.00)
                        .disponivel(true)
                        .build();

                Sabor s2 = Sabor.builder()
                        .nome("frango")
                        .tipo("salgada")
                        .valorGrande(35.00)
                        .valorMedia(23.00)
                        .disponivel(true)
                        .build();

                sabores.add(s);
                sabores.add(s2);

                estabelecimentoPostRequestDTO.setCardapio(sabores);
                estabelecimentoPostRequestDTO.setEntregadores(entregadores);
                estabelecimentoPostRequestDTO.setUsuario("estabelecimento-1");

                estabelecimentoRepository.save(modelMapper.map(estabelecimentoPostRequestDTO, Estabelecimento.class));
                estabelecimentoRepository.save(modelMapper.map(estabelecimentoPutRequestDTO, Estabelecimento.class));


                // Act
                String responseJsonString = driver.perform(get(URI_ESTABELECIMENTOS)
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();


                Set<Estabelecimento> resultado = objectMapper.readValue(responseJsonString, new TypeReference<Set<Estabelecimento>>() {
                });

                // Assert
                assertAll(
                        () -> assertEquals(2, resultado.size())
                );
        }


        @Test
        @DisplayName("Retornar o Estabelecimento desejado")
        @Transactional
        void getOneEstabelecimentos() throws Exception {


                // ENTREGADORES
                Set<Entregador> entregadores = new HashSet<Entregador>();
                Veiculo v = Veiculo.builder()
                        .cor("verde")
                        .placa("123123")
                        .tipo("moto")
                        .build();

                Entregador e1 = Entregador.builder()
                        .codigoAcesso("12345")
                        .usuario("igor")
                        .veiculo(v)
                        .build();

                entregadores.add(e1);


                // CARDAPIO
                Set<Sabor> sabores = new HashSet<Sabor>();
                Sabor s = Sabor.builder()
                        .nome("calabresa")
                        .tipo("salgada")
                        .valorGrande(30.00)
                        .valorMedia(20.00)
                        .disponivel(true)
                        .build();

                Sabor s2 = Sabor.builder()
                        .nome("frango")
                        .tipo("salgada")
                        .valorGrande(35.00)
                        .valorMedia(23.00)
                        .disponivel(true)
                        .build();

                sabores.add(s);
                sabores.add(s2);

                estabelecimentoPostRequestDTO.setCardapio(sabores);
                estabelecimentoPostRequestDTO.setEntregadores(entregadores);
                estabelecimentoPostRequestDTO.setUsuario("estabelecimento-1");

                Estabelecimento based = estabelecimentoRepository.save(modelMapper.map(estabelecimentoPostRequestDTO, Estabelecimento.class));
                estabelecimentoRepository.save(modelMapper.map(estabelecimentoPutRequestDTO, Estabelecimento.class));


                // Act
                String responseJsonString = driver.perform(get(URI_ESTABELECIMENTOS + "/" + based.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigo", estabelecimentoPostRequestDTO.getCodigoAcesso()))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();


                Estabelecimento resultado = objectMapper.readValue(responseJsonString, Estabelecimento.class);


                // Assert
                assertAll(
                        () -> assertNotNull(resultado.getId()),
                        () -> assertEquals(estabelecimentoPostRequestDTO.getCodigoAcesso(), resultado.getCodigoAcesso()),
                        () -> assertEquals(estabelecimentoPostRequestDTO.getCardapio().size(), resultado.getCardapio().size()),
                        () -> assertEquals(estabelecimentoPostRequestDTO.getEntregadores().size(), resultado.getEntregadores().size())
                );
        }

        @Test
        @DisplayName("Tentar retornar um Estabelecimento com Id invalido")
        void getOneIdInvalido() throws Exception {


                // ENTREGADORES
                Set<Entregador> entregadores = new HashSet<Entregador>();
                Veiculo v = Veiculo.builder()
                        .cor("verde")
                        .placa("123123")
                        .tipo("moto")
                        .build();

                Entregador e1 = Entregador.builder()
                        .codigoAcesso("12345")
                        .usuario("igor")
                        .veiculo(v)
                        .build();

                entregadores.add(e1);


                // CARDAPIO
                Set<Sabor> sabores = new HashSet<Sabor>();
                Sabor s = Sabor.builder()
                        .nome("calabresa")
                        .tipo("salgada")
                        .valorGrande(30.00)
                        .valorMedia(20.00)
                        .disponivel(true)
                        .build();

                Sabor s2 = Sabor.builder()
                        .nome("frango")
                        .tipo("salgada")
                        .valorGrande(35.00)
                        .valorMedia(23.00)
                        .disponivel(true)
                        .build();

                sabores.add(s);
                sabores.add(s2);

                estabelecimentoPostRequestDTO.setCardapio(sabores);
                estabelecimentoPostRequestDTO.setEntregadores(entregadores);

                Estabelecimento based = estabelecimentoRepository.save(modelMapper.map(estabelecimentoPostRequestDTO, Estabelecimento.class));
                estabelecimentoRepository.save(modelMapper.map(estabelecimentoPutRequestDTO, Estabelecimento.class));


                // Act
                String responseJsonString = driver.perform(get(URI_ESTABELECIMENTOS + "/" + 100)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigo", estabelecimentoPostRequestDTO.getCodigoAcesso()))
                        .andExpect(status().isBadRequest())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();


                CustomErrorType customErrorType = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                // ASSERT
                assertAll(
                        () -> assertEquals("O estabelecimento consultado nao existe!", customErrorType.getMessage())
                );
        }




        /*
        @Test
        @DisplayName("Quando excluímos um estabelecimento salvo")
        void quandoExcluimosEstabelecimentoValido() throws Exception {
                // Arrange
                // nenhuma necessidade além do setup()

                // Act
                String responseJsonString = driver.perform(delete(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigoAcesso", estabelecimento.getCodigoAcesso()))
                        .andExpect(status().isNoContent()) // Codigo 204
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                // Assert
                assertTrue(responseJsonString.isBlank());
        }

        @Test
        @DisplayName("Quando atualizamos um estabelecimento salvo")
        void quandoAtualizamosEstabelecimentoValido() throws Exception {
                // Arrange
                estabelecimentoPutRequestDTO.setCodigoAcesso("131289");

                // Act
                String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigoAcesso", estabelecimento.getCodigoAcesso())
                                .content(objectMapper.writeValueAsString(estabelecimentoPutRequestDTO)))
                        .andExpect(status().isOk()) // Codigo 200
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                EstabelecimentoResponseDTO resultado = objectMapper.readValue(responseJsonString, EstabelecimentoResponseDTO.EstabelecimentoResponseDTOBuilder.class).build();

                // Assert
                assertAll(
                        () -> assertEquals(resultado.getId().longValue(), estabelecimento.getId().longValue()),
                        () -> assertEquals("131289", resultado.getCodigoAcesso())
                );
        }

        @Test
        @DisplayName("Quando alteramos um estabelecimento com codigo de acesso inválido")
        void quandoAlterarEstabelecimentoInvalido() throws Exception {
                // Arrange
                EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO = EstabelecimentoPostPutRequestDTO.builder()
                        .codigoAcesso("13")
                        .build();

                // Act
                String responseJsonString = driver.perform(put(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigoAcesso", estabelecimento.getCodigoAcesso())
                                .content(objectMapper.writeValueAsString(estabelecimentoPostPutRequestDTO)))
                        .andExpect(status().isBadRequest()) // Codigo 400
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                // Assert
                assertAll(
                        () -> assertEquals("Erros de validacao encontrados", resultado.getMessage()),
                        () -> assertEquals("Codigo de acesso deve ter exatamente 6 digitos numericos", resultado.getErrors().get(0))
                );
        }

        @Test
        @DisplayName("Quando criamos um novo estabelecimento com dados inválidos")
        void quandoCriarEstabelecimentoInvalido() throws Exception {
                // Arrange
                EstabelecimentoPostPutRequestDTO estabelecimentoPostPutRequestDTO = EstabelecimentoPostPutRequestDTO.builder()
                        .codigoAcesso("13")
                        .build();

                // Act
                String responseJsonString = driver.perform(post(URI_ESTABELECIMENTOS)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigoAcesso", estabelecimentoPostPutRequestDTO.getCodigoAcesso())
                                .content(objectMapper.writeValueAsString(estabelecimentoPostPutRequestDTO)))
                        .andExpect(status().isBadRequest()) // Codigo 400
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                // Assert
                assertAll(
                        () -> assertEquals("Erros de validacao encontrados", resultado.getMessage()),
                        () -> assertEquals("Codigo de acesso deve ter exatamente 6 digitos numericos", resultado.getErrors().get(0))
                );
        }*/

        @Test
        @DisplayName("Quando buscamos o cardapio de um estabelecimento")
        void quandoBuscarCardapioEstabelecimento() throws Exception {
                // Arrange
                Sabor sabor1 = saborRepository.save(Sabor.builder()
                        .nome("Calabresa")
                        .tipo("salgado")
                        .valorMedia(25.0)
                        .valorGrande(35.0)
                        .disponivel(true)
                        .estabelecimento(estabelecimento)
                        .build());

                Sabor sabor2 = saborRepository.save(Sabor.builder()
                        .nome("Mussarela")
                        .tipo("salgado")
                        .valorMedia(20.0)
                        .valorGrande(30.0)
                        .disponivel(true)
                        .estabelecimento(estabelecimento)
                        .build());
                Sabor sabor3 = saborRepository.save(Sabor.builder()
                        .nome("Chocolate")
                        .tipo("doce")
                        .valorMedia(25.0)
                        .valorGrande(35.0)
                        .estabelecimento(estabelecimento)
                        .build());

                Sabor sabor4 = saborRepository.save(Sabor.builder()
                        .nome("Morango")
                        .valorMedia(20.0)
                        .valorGrande(30.0)
                        .tipo("doce")
                        .estabelecimento(estabelecimento)
                        .build());

                Estabelecimento based = estabelecimentoRepository.save(estabelecimento);

                // Act
                String responseJsonString = driver.perform(get(URI_ESTABELECIMENTOS + "/" + based.getId() + "/sabores")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigo", based.getCodigoAcesso()))
                        .andExpect(status().isOk()) // Codigo 200
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                List<Sabor> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {
                });

                // Assert
                assertAll(
                        () -> assertEquals(4, resultado.size())
                );
        }

        @Test
        @DisplayName("Quando buscamos o cardapio de um estabelecimento que não existe")
        void quandoBuscarCardapioEstabelecimentoInexistente() throws Exception {
                // Arrange
                // Nenhuma necessidade além do setup()

                // Act
                String responseJsonString = driver.perform(get(URI_ESTABELECIMENTOS + "/" + 9999 + "/sabores")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(estabelecimentoPostRequestDTO)))
                        .andExpect(status().isBadRequest()) // Codigo 404
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                // Assert
                assertAll(
                        () -> assertEquals("O estabelecimento consultado nao existe!", resultado.getMessage())
                );
        }

        @Test
        @DisplayName("Quando buscamos o cardapio de um estabelecimento por tipo (salgado)")
        void quandoBuscarCardapioEstabelecimentoPorTipo() throws Exception {
                // Arrange
                Sabor sabor1 = saborRepository.save(Sabor.builder()
                        .nome("Calabresa")
                        .tipo("salgado")
                        .valorMedia(25.0)
                        .valorGrande(35.0)
                        .disponivel(true)
                        .estabelecimento(estabelecimento)
                        .build());

                Sabor sabor2 = saborRepository.save(Sabor.builder()
                        .nome("Mussarela")
                        .tipo("salgado")
                        .valorMedia(20.0)
                        .valorGrande(30.0)
                        .disponivel(true)
                        .estabelecimento(estabelecimento)
                        .build());
                Sabor sabor3 = saborRepository.save(Sabor.builder()
                        .nome("Chocolate")
                        .tipo("doce")
                        .valorMedia(25.0)
                        .valorGrande(35.0)
                        .estabelecimento(estabelecimento)
                        .build());

                Sabor sabor4 = saborRepository.save(Sabor.builder()
                        .nome("Morango")
                        .valorMedia(20.0)
                        .valorGrande(30.0)
                        .tipo("doce")
                        .estabelecimento(estabelecimento)
                        .build());


                estabelecimentoRepository.save(estabelecimento);

                // Act
                String responseJsonString = driver.perform(get(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/sabores" + "/tipo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("tipo", "salgado")
                                .param("codigo", estabelecimento.getCodigoAcesso()))
                        .andExpect(status().isOk()) // Codigo 200
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                List<Sabor> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {
                });

                // Assert
                assertAll(
                        () -> assertEquals(2, resultado.size())
                );
        }

        @Test
        @DisplayName("Quando buscamos o cardapio de um estabelecimento por tipo (salgado) com id invalido")
        void quandoBuscarCardapioEstabelecimentoPorTipoComIdInvalido() throws Exception {
                // Arrange
                Sabor sabor1 = saborRepository.save(Sabor.builder()
                .nome("Calabresa")
                .tipo("salgado")
                .valorMedia(25.0)
                .valorGrande(35.0)
                .disponivel(true)
                .estabelecimento(estabelecimento)
                .build());

                Sabor sabor2 = saborRepository.save(Sabor.builder()
                        .nome("Mussarela")
                        .tipo("salgado")
                        .valorMedia(20.0)
                        .valorGrande(30.0)
                        .disponivel(true)
                        .estabelecimento(estabelecimento)
                        .build());
                Sabor sabor3 = saborRepository.save(Sabor.builder()
                        .nome("Chocolate")
                        .tipo("doce")
                        .valorMedia(25.0)
                        .valorGrande(35.0)
                        .estabelecimento(estabelecimento)
                        .build());

                Sabor sabor4 = saborRepository.save(Sabor.builder()
                        .nome("Morango")
                        .valorMedia(20.0)
                        .valorGrande(30.0)
                        .tipo("doce")
                        .estabelecimento(estabelecimento)
                        .build());


                // Act
                String responseJsonString = driver.perform(get(URI_ESTABELECIMENTOS + "/" + 100 + "/sabores" + "/tipo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("tipo", "salgado")
                                .param("codigo", estabelecimento.getCodigoAcesso())
                                .content(objectMapper.writeValueAsString(estabelecimentoPostRequestDTO)))
                        .andExpect(status().isBadRequest()) // Codigo 400
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                // Assert
                assertAll(
                        () -> assertEquals("O estabelecimento consultado nao existe!", resultado.getMessage())
                );
        }

        @Test
        @DisplayName("Quando buscamos o cardapio de um estabelecimento por tipo (doce)")
        void quandoBuscarCardapioEstabelecimentoPorTipoDoce() throws Exception {
                // Arrange
                Sabor sabor1 = saborRepository.save(Sabor.builder()
                .nome("Calabresa")
                .tipo("salgado")
                .valorMedia(25.0)
                .valorGrande(35.0)
                .disponivel(true)
                .estabelecimento(estabelecimento)
                .build());

        Sabor sabor2 = saborRepository.save(Sabor.builder()
                .nome("Mussarela")
                .tipo("salgado")
                .valorMedia(20.0)
                .valorGrande(30.0)
                .disponivel(true)
                .estabelecimento(estabelecimento)
                .build());
        Sabor sabor3 = saborRepository.save(Sabor.builder()
                .nome("Chocolate")
                .tipo("doce")
                .valorMedia(25.0)
                .valorGrande(35.0)
                .estabelecimento(estabelecimento)
                .build());

        Sabor sabor4 = saborRepository.save(Sabor.builder()
                .nome("Morango")
                .valorMedia(20.0)
                .valorGrande(30.0)
                .tipo("doce")
                .estabelecimento(estabelecimento)
                .build());



                // Act
                String responseJsonString = driver.perform(get(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/sabores" + "/tipo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("tipo", "doce")
                                .param("codigo", estabelecimento.getCodigoAcesso()))
                        .andExpect(status().isOk()) // Codigo 200
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                List<Sabor> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {
                });

                // Assert
                assertAll(
                        () -> assertEquals(2, resultado.size())
                );
        }

        @Test
        @DisplayName("Quando buscamos o cardapio de um estabelecimento por tipo (doce) com id invalido")
        void quandoBuscarCardapioEstabelecimentoPorTipoDoceComIdInvalido() throws Exception {
                // Arrange
                Sabor sabor1 = saborRepository.save(Sabor.builder()
                .nome("Calabresa")
                .tipo("salgado")
                .valorMedia(25.0)
                .valorGrande(35.0)
                .disponivel(true)
                .estabelecimento(estabelecimento)
                .build());

                Sabor sabor2 = saborRepository.save(Sabor.builder()
                        .nome("Mussarela")
                        .tipo("salgado")
                        .valorMedia(20.0)
                        .valorGrande(30.0)
                        .disponivel(true)
                        .estabelecimento(estabelecimento)
                        .build());
                Sabor sabor3 = saborRepository.save(Sabor.builder()
                        .nome("Chocolate")
                        .tipo("doce")
                        .valorMedia(25.0)
                        .valorGrande(35.0)
                        .estabelecimento(estabelecimento)
                        .build());

                Sabor sabor4 = saborRepository.save(Sabor.builder()
                        .nome("Morango")
                        .valorMedia(20.0)
                        .valorGrande(30.0)
                        .tipo("doce")
                        .estabelecimento(estabelecimento)
                        .build());


                // Act
                String responseJsonString = driver.perform(get(URI_ESTABELECIMENTOS + "/" + 100 + "/sabores" + "/tipo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("tipo", "salgado")
                                .param("codigo", estabelecimento.getCodigoAcesso())
                                .content(objectMapper.writeValueAsString(estabelecimentoPostRequestDTO)))
                        .andExpect(status().isBadRequest()) // Codigo 400
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                // Assert
                assertAll(
                        () -> assertEquals("O estabelecimento consultado nao existe!", resultado.getMessage())
                );
        }

        @Test
        @DisplayName("Quando buscamos o cardapio de um estabelecimento passando tipo diferente de salgado/doce")
        void quandoBuscarCardapioEstabelecimentoPorTipoInvalido() throws Exception {
                // Arrange
                Sabor sabor1 = saborRepository.save(Sabor.builder()
                .nome("Calabresa")
                .tipo("salgado")
                .valorMedia(25.0)
                .valorGrande(35.0)
                .disponivel(true)
                .estabelecimento(estabelecimento)
                .build());

                Sabor sabor2 = saborRepository.save(Sabor.builder()
                        .nome("Mussarela")
                        .tipo("salgado")
                        .valorMedia(20.0)
                        .valorGrande(30.0)
                        .disponivel(true)
                        .estabelecimento(estabelecimento)
                        .build());
                Sabor sabor3 = saborRepository.save(Sabor.builder()
                        .nome("Chocolate")
                        .tipo("doce")
                        .valorMedia(25.0)
                        .valorGrande(35.0)
                        .estabelecimento(estabelecimento)
                        .build());

                Sabor sabor4 = saborRepository.save(Sabor.builder()
                        .nome("Morango")
                        .valorMedia(20.0)
                        .valorGrande(30.0)
                        .tipo("doce")
                        .estabelecimento(estabelecimento)
                        .build());


                // Act
                String responseJsonString = driver.perform(get(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/sabores" + "/tipo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("tipo", "teste")
                                .param("codigo", estabelecimento.getCodigoAcesso())
                                .content(objectMapper.writeValueAsString(estabelecimentoPostRequestDTO)))
                        .andExpect(status().isBadRequest()) // Codigo 400
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                // Assert
                assertAll(
                        () -> assertEquals("Tipo inválido", resultado.getMessage())
                );
        }

        @Test
        @DisplayName("Quando buscamos o cardapio de um estabelecimento por tipo Disponibilidade")
        void quandoBuscarCardapioEstabelecimentoPorDisponibilidade() throws Exception {
                // Arrange
                Sabor sabor1 = saborRepository.save(Sabor.builder()
                .nome("Calabresa")
                .tipo("salgado")
                .valorMedia(25.0)
                .valorGrande(35.0)
                .disponivel(true)
                .estabelecimento(estabelecimento)
                .build());

                Sabor sabor2 = saborRepository.save(Sabor.builder()
                        .nome("Mussarela")
                        .tipo("salgado")
                        .valorMedia(20.0)
                        .valorGrande(30.0)
                        .disponivel(true)
                        .estabelecimento(estabelecimento)
                        .build());
                Sabor sabor3 = saborRepository.save(Sabor.builder()
                        .nome("Chocolate")
                        .tipo("doce")
                        .valorMedia(25.0)
                        .valorGrande(35.0)
                        .estabelecimento(estabelecimento)
                        .build());

                Sabor sabor4 = saborRepository.save(Sabor.builder()
                        .nome("Morango")
                        .valorMedia(20.0)
                        .valorGrande(30.0)
                        .tipo("doce")
                        .estabelecimento(estabelecimento)
                        .build());
                
                estabelecimentoRepository.save(estabelecimento);

                // Act
                String responseJsonString = driver.perform(get(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/sabores" + "/disponibilidade")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("disponivel", "true")
                                .param("codigo", estabelecimento.getCodigoAcesso()))
                        .andExpect(status().isOk()) // Codigo 200
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                List<Sabor> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {
                });

                // Assert
                assertAll(
                        () -> assertEquals(2, resultado.size())
                );
        }

        @Test
        @DisplayName("Quando buscamos o cardapio de um estabelecimento por Disponibilidade com id invalido")
        void quandoBuscarCardapioEstabelecimentoPorDisponibilidadeComIdInvalido() throws Exception {
                // Arrange
                Sabor sabor1 = Sabor.builder()
                        .nome("Calabresa")
                        .valorMedia(25.0)
                        .valorGrande(35.0)
                        .tipo("salgado")
                        .disponivel(true)
                        .build();

                Sabor sabor2 = Sabor.builder()
                        .nome("Mussarela")
                        .valorMedia(20.0)
                        .valorGrande(30.0)
                        .tipo("salgado")
                        .disponivel(true)
                        .build();
                Sabor sabor3 = Sabor.builder()
                        .nome("Chocolate")
                        .valorMedia(25.0)
                        .valorGrande(35.0)
                        .tipo("doce")
                        .disponivel(false)
                        .build();

                Sabor sabor4 = Sabor.builder()
                        .nome("Morango")
                        .valorMedia(20.0)
                        .valorGrande(30.0)
                        .tipo("doce")
                        .disponivel(false)
                        .build();
                Estabelecimento estabelecimento1 = Estabelecimento.builder()
                        .codigoAcesso("123456")
                        .cardapio(Set.of(sabor1, sabor2, sabor3, sabor4))
                        .build();
                estabelecimentoRepository.save(estabelecimento1);

                // Act
                String responseJsonString = driver.perform(get(URI_ESTABELECIMENTOS + "/" + 100 + "/sabores" + "/disponibilidade")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("disponivel", "true")
                                .param("codigo", estabelecimento1.getCodigoAcesso()))
                        .andExpect(status().isBadRequest()) // Codigo 200
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                // Assert
                assertAll(
                        () -> assertEquals("O estabelecimento consultado nao existe!", resultado.getMessage())
                );
        }


        @Test
        @DisplayName("Quando buscamos o cardapio de um estabelecimento por tipo indisponibilidade")
        void quandoBuscarCardapioEstabelecimentoPorIndisponibilidade() throws Exception {
                // Arrange
                Sabor sabor1 = saborRepository.save(Sabor.builder()
                        .nome("Calabresa")
                        .tipo("salgado")
                        .valorMedia(25.0)
                        .valorGrande(35.0)
                        .disponivel(true)
                        .estabelecimento(estabelecimento)
                        .build());

                Sabor sabor2 = saborRepository.save(Sabor.builder()
                        .nome("Mussarela")
                        .tipo("salgado")
                        .valorMedia(20.0)
                        .valorGrande(30.0)
                        .disponivel(true)
                        .estabelecimento(estabelecimento)
                        .build());
                Sabor sabor3 = saborRepository.save(Sabor.builder()
                        .nome("Chocolate")
                        .tipo("doce")
                        .valorMedia(25.0)
                        .valorGrande(35.0)
                        .estabelecimento(estabelecimento)
                        .build());

                Sabor sabor4 = saborRepository.save(Sabor.builder()
                        .nome("Morango")
                        .valorMedia(20.0)
                        .valorGrande(30.0)
                        .tipo("doce")
                        .estabelecimento(estabelecimento)
                        .build());


                // Act
                String responseJsonString = driver.perform(get(URI_ESTABELECIMENTOS + "/" + estabelecimento.getId() + "/sabores" + "/disponibilidade")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("disponivel", "false")
                                .param("codigo", estabelecimento.getCodigoAcesso()))
                        .andExpect(status().isOk()) // Codigo 200
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                List<Sabor> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {
                });

                // Assert
                assertAll(
                        () -> assertEquals(2, resultado.size())
                );
        }
        }
        }