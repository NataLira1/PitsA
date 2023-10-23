package com.ufcg.psoft.commerce.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPostPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPutConfirmarPagamentoRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoPutRequestDTO;
import com.ufcg.psoft.commerce.dto.pedido.PedidoResponseDTO;
import com.ufcg.psoft.commerce.exception.CustomErrorType;
import com.ufcg.psoft.commerce.models.Cliente;
import com.ufcg.psoft.commerce.models.Entregador;
import com.ufcg.psoft.commerce.models.Estabelecimento;
import com.ufcg.psoft.commerce.models.FormaDePagamento;
import com.ufcg.psoft.commerce.models.Pedido;
import com.ufcg.psoft.commerce.models.Pizza;
import com.ufcg.psoft.commerce.models.Sabor;
import com.ufcg.psoft.commerce.models.Veiculo;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import com.ufcg.psoft.commerce.repositories.EntregadorRepository;
import com.ufcg.psoft.commerce.repositories.EstabelecimentoRepository;
import com.ufcg.psoft.commerce.repositories.PedidoRepository;
import com.ufcg.psoft.commerce.repositories.PizzaRepository;
import com.ufcg.psoft.commerce.repositories.SaborRepository;
import com.ufcg.psoft.commerce.util.TipoPagamento;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@DisplayName("Testes do controlador de pedidos")
        public class PedidoControllerTests {
        final String URI_PEDIDOS = "/v1/pedidos";

        @Autowired
        MockMvc driver;
        @Autowired
        PedidoRepository pedidoRepository;
        @Autowired
        ClienteRepository clienteRepository;
        @Autowired
        EstabelecimentoRepository estabelecimentoRepository;
        @Autowired
        SaborRepository saborRepository;
        @Autowired
        PizzaRepository pizzaRepository;
        @Autowired
        EntregadorRepository entregadorRepository;

        ObjectMapper objectMapper = new ObjectMapper();
        Cliente cliente;
        Entregador entregador;
        Veiculo veiculo;
        Sabor sabor1;
        Sabor sabor2;
        Pizza pizzaM;
        Pizza pizzaG;
        Estabelecimento estabelecimento;
        Pedido pedido;
        Pedido pedido1;

        FormaDePagamento formaDePagamento;

        FormaDePagamento formaDePagamento1;

        FormaDePagamento formaDePagamento2;

        PedidoPostPutRequestDTO pedidoPostPutRequestDTO;
        PedidoPostPutRequestDTO pedidoPostPutRequestDTO2;

        PedidoPutConfirmarPagamentoRequestDTO pedidoPutConfirmarPagamentoRequestDTO;

        @BeforeEach
        void setup() {
        objectMapper.registerModule(new JavaTimeModule());
        Set<Sabor> cardapio = new HashSet<Sabor>();

        estabelecimento = estabelecimentoRepository.save(Estabelecimento.builder()
                .codigoAcesso("654321")
                .usuario("estabelecimento123")
                .cardapio(cardapio)
                .build());

        sabor1 = saborRepository.save(Sabor.builder()
                .nome("Sabor Um")
                .tipo("salgado")
                .valorMedia(10.0)
                .valorGrande(20.0)
                .disponivel(true)
                .estabelecimento(estabelecimento)
                .build());
        sabor2 = saborRepository.save(Sabor.builder()
                .nome("Sabor Dois")
                .tipo("doce")
                .valorMedia(15.0)
                .valorGrande(30.0)
                .disponivel(true)
                .estabelecimento(estabelecimento)
                .build());
        cardapio.add(sabor1);
        estabelecimento.setCardapio(cardapio);

        cliente = clienteRepository.save(Cliente.builder()
                .nomeCompleto("Anton Ego")
                .endereco("Paris")
                .codigoAcesso("123456")
                .usuario("usuario123")
                .build());

        veiculo = Veiculo.builder()
                .placa("ABC-1234")
                .cor("Azul")
                .tipo("Moto")
                .build();

        entregador = entregadorRepository.save(Entregador.builder()
                .nome("Joãozinho")
                .veiculo(veiculo)
                .codigoAcesso("101010")
                .estabelecimento(estabelecimento)
                .build());

        pizzaM = Pizza.builder()
                .sabor1(sabor1)
                .tamanho("media")
                .build();

        pizzaG = Pizza.builder()
                .sabor1(sabor1)
                .sabor2(sabor2)
                .tamanho("grande")
                .build();

       // pizzaM = pizzaRepository.save(pizzaM);
        //pizzaG = pizzaRepository.save(pizzaG);

        List<Pizza> pizzas = List.of(pizzaM);
        List<Pizza> pizzas1 = List.of(pizzaM, pizzaG);

        FormaDePagamento formaDePagamento = FormaDePagamento.builder()
                .tipo(TipoPagamento.PIX)
                .build();


        pedido = Pedido.builder()
                .preco(10.0)
                .enderecoEntrega("Casa 237")
                .cliente(cliente)
                .estabelecimento(estabelecimento)
                .entregador(entregador)
                //.formaDePagamento(formaDePagamento)
                .status("Pedido recebido")
                .pizzas(pizzas)
                .build();

        pedido1 = Pedido.builder()
                .preco(12.5)
                .enderecoEntrega("Casa 237")
                .cliente(cliente)
                .estabelecimento(estabelecimento)
                .entregador(entregador)
                .status("Pedido recebido")
                .pizzas(pizzas1)
                .build();

        FormaDePagamento formaDePagamento1 = FormaDePagamento.builder()
                .tipo(TipoPagamento.CARTAO_CREDITO)
                .build();

        pedidoPostPutRequestDTO = PedidoPostPutRequestDTO.builder()
                .enderecoEntrega(pedido.getEnderecoEntrega())
                .clienteId(cliente.getId())
                .estabelecimentoId(estabelecimento.getId())
                .pizzas(pedido.getPizzas())
                .statusPagamento(false)
                //.formaDePagamento(formaDePagamento1)
                .build();


        }

        @AfterEach
        void tearDown() {
//                	pedidoRepository.deleteById(1L);
        // pizzaRepository.deleteAll();
        // pedidoRepository.deleteAll();
        // clienteRepository.deleteAll();
        // saborRepository.deleteAll();
        // estabelecimentoRepository.deleteAll();
        }

        @Nested
        @DisplayName("Conjunto de casos de verificação dos fluxos básicos API Rest")
        class PedidoVerificacaoFluxosBasicosApiRest {

                @Test
                @DisplayName("Quando criamos um novo pedido com dados válidos")
                void quandoCriamosUmNovoPedidoComDadosValidos() throws Exception {
                // Arrange

                // Act
                String responseJsonString = driver.perform(post(URI_PEDIDOS)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                                .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                        .andExpect(status().isCreated())
                        .andDo(print())// Codigo 201
                        .andReturn().getResponse().getContentAsString();

                PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString, PedidoResponseDTO.class);

                // Assert
                assertAll(
                        // () -> assertNotNull(resultado.getId()),
                        () -> assertEquals(pedidoPostPutRequestDTO.getEnderecoEntrega(), resultado.getEnderecoEntrega()),
                        () -> assertEquals(pedidoPostPutRequestDTO.getPizzas().get(0).getSabor1(), resultado.getPizzas().get(0).getSabor1()),
                        () -> assertEquals(pedido.getCliente().getId(), resultado.getCliente().getId()),
                        () -> assertEquals(pedido.getEstabelecimento().getId(), resultado.getEstabelecimento().getId()),
                        () -> assertEquals(pedido.getPreco(), resultado.getPreco()));
                }

                @Test
                @DisplayName("Quando alteramos um novo pedido com dados válidos")
                void quandoAlteramosPedidoValido() throws Exception {
                // Arrange
                pedidoRepository.save(pedido);
                Sabor sabor3 = Sabor.builder()
                        .nome("Calabresa")
                        .tipo("Salgado")
                        .valorGrande(20.0)
                        .valorMedia(15)
                        .disponivel(true)
                        .estabelecimento(estabelecimento)
                        .build();

                Sabor sabor4 = Sabor.builder()
                        .nome("Calabresa")
                        .tipo("Salgado")
                        .valorGrande(20.0)
                        .valorMedia(15)
                        .disponivel(true)
                        .estabelecimento(estabelecimento)
                        .build();

                saborRepository.save(sabor3);
                saborRepository.save(sabor4);


                Pizza pizzaG2 = Pizza.builder()
                        .sabor1(sabor3)
                        .sabor2(sabor4)
                        .tamanho("grande")
                        .build();

                List<Pizza> pizzas2 = List.of(pizzaG2);

                Long pedidoId = pedido.getId();

                PedidoPutRequestDTO pedidoPutRequestDTO = PedidoPutRequestDTO.builder()
                        .enderecoEntrega("Rua Manoel Paulino")
                        .pizzas(pizzas2)
                        .status("Pedido em preparo")
                        .build();


                // Act
                String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" + pedidoId)
                                .contentType(MediaType.APPLICATION_JSON)
                                //.param("pedidoId", pedido.getId().toString())
                                .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                                .content(objectMapper.writeValueAsString(pedidoPutRequestDTO)))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString,
                        PedidoResponseDTO.class);

                // Assert
                assertAll(
                        () -> assertEquals(pedidoId, resultado.getId().longValue()),
                        () -> assertEquals(pedidoPutRequestDTO.getEnderecoEntrega(),
                                resultado.getEnderecoEntrega()),
                        //() -> assertEquals("j", "k"),
                        () -> assertEquals(pedidoPutRequestDTO.getPizzas().get(0).getSabor1(),
                                resultado.getPizzas().get(0).getSabor1()),
                        () -> assertEquals(pedido.getCliente(), resultado.getCliente()),
                        () -> assertEquals(pedido.getEstabelecimento(),
                                resultado.getEstabelecimento()),
                        () -> assertEquals(pedido.getPreco(), resultado.getPreco())
                );
                }



        @Test
        @DisplayName("Quando alteramos um pedido inexistente")
        void quandoAlteramosPedidoInexistente() throws Exception {
                // Arrange
                // nenhuma necessidade além do setup()
                pedidoRepository.save(pedido);
                Sabor sabor3 = Sabor.builder()
                        .nome("Calabresa")
                        .tipo("Salgado")
                        .valorGrande(20.0)
                        .valorMedia(15)
                        .disponivel(true)
                        .estabelecimento(estabelecimento)
                        .build();

                Sabor sabor4 = Sabor.builder()
                        .nome("Calabresa")
                        .tipo("Salgado")
                        .valorGrande(20.0)
                        .valorMedia(15)
                        .disponivel(true)
                        .estabelecimento(estabelecimento)
                        .build();

                saborRepository.save(sabor3);
                saborRepository.save(sabor4);


                Pizza pizzaG2 = Pizza.builder()
                        .sabor1(sabor3)
                        .sabor2(sabor4)
                        .tamanho("grande")
                        .build();

                List<Pizza> pizzas2 = List.of(pizzaG2);


                PedidoPutRequestDTO pedidoPutRequestDTO = PedidoPutRequestDTO.builder()
                        .enderecoEntrega("Rua Manoel Paulino")
                        .pizzas(pizzas2)
                        .status("Pedido em preparo")
                        .build();

                // Act

                        String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" + "99999")
                                        .contentType(MediaType.APPLICATION_JSON)
                                        //.param("pedidoId", pedido.getId().toString())
                                        .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                                        .content(objectMapper.writeValueAsString(pedidoPutRequestDTO)))
                                .andExpect(status().isBadRequest())
                                .andDo(print())
                                .andReturn().getResponse().getContentAsString();

                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertEquals("O pedido não existe", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando alteramos um pedido passando codigo de acesso invalido")
        void quandoAlteramosPedidoPassandoCodigoAcessoInvalido() throws Exception {
                 // Arrange
                pedidoRepository.save(pedido);
                Sabor sabor3 = Sabor.builder()
                        .nome("Calabresa")
                        .tipo("Salgado")
                        .valorGrande(20.0)
                        .valorMedia(15)
                        .disponivel(true)
                        .estabelecimento(estabelecimento)
                        .build();

                Sabor sabor4 = Sabor.builder()
                        .nome("Calabresa")
                        .tipo("Salgado")
                        .valorGrande(20.0)
                        .valorMedia(15)
                        .disponivel(true)
                        .estabelecimento(estabelecimento)
                        .build();

                saborRepository.save(sabor3);
                saborRepository.save(sabor4);


                Pizza pizzaG2 = Pizza.builder()
                        .sabor1(sabor3)
                        .sabor2(sabor4)
                        .tamanho("grande")
                        .build();

                List<Pizza> pizzas2 = List.of(pizzaG2);


                PedidoPutRequestDTO pedidoPutRequestDTO = PedidoPutRequestDTO.builder()
                        .enderecoEntrega("Rua Manoel Paulino")
                        .pizzas(pizzas2)
                        .status("Pedido em preparo")
                        .build();

                // Act
                String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" + pedido.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                //.param("pedidoId", pedido.getId().toString())
                                .param("clienteCodigoAcesso", "10")
                                .content(objectMapper.writeValueAsString(pedidoPutRequestDTO)))
                        .andExpect(status().isBadRequest())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                        CustomErrorType.class);

                // Assert
                assertEquals("Codigo de acesso invalido!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um cliente busca por todos seus pedidos salvos")
        void quandoClienteBuscaTodosPedidos() throws Exception {
                // Arrange
                pedidoRepository.save(pedido);
                pedidoRepository.save(pedido1);

                // Act
                String responseJsonString = driver.perform(get(URI_PEDIDOS+ "/cliente/")
                                .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                                .contentType(MediaType.APPLICATION_JSON))
                                //.content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                List<PedidoResponseDTO> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {});

                // Assert
                assertEquals(2, resultado.size());
                }

                @Test
                @DisplayName("Quando um cliente busca por um pedido seu salvo pelo id primeiro")
                void quandoClienteBuscaPedidoPorId() throws Exception {
                // Arrange
                pedidoRepository.save(pedido);

                // Act
                String responseJsonString = driver.perform(get(URI_PEDIDOS + "/" +
                                pedido.getId() + "/cliente/" + cliente.getId())
                                .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                                .contentType(MediaType.APPLICATION_JSON))
                                //.content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {});

             //Pedido resultado = listaResultados.get(0);

             // Assert
                assertAll(
                        () -> assertNotNull(resultado.getId()),
                        () -> assertEquals(pedido.getEnderecoEntrega(), resultado.getEnderecoEntrega()),
                        () -> assertEquals(pedido.getPizzas().get(0).getSabor1(), resultado.getPizzas().get(0).getSabor1()),
                        () -> assertEquals(pedido.getCliente(), resultado.getCliente()),
                        () -> assertEquals(pedido.getEstabelecimento(), resultado.getEstabelecimento()),
                        () -> assertEquals(pedido.getPreco(), resultado.getPreco())
                );
        }

                @Test
                @DisplayName("Quando um cliente busca por um pedido seu salvo por id inexistente")
                void quandoClienteBuscaPedidoInexistente() throws Exception {
                // Arrange
                // nenhuma necessidade além do setup()

                // Act
                        String responseJsonString = driver.perform(get(URI_PEDIDOS + "/" +
                                "123" + "/cliente/" + cliente.getId())
                                .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                                .contentType(MediaType.APPLICATION_JSON))
                                //.content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                        .andExpect(status().isBadRequest())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();


                        CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                        // Assert
                        assertEquals("O pedido não existe", resultado.getMessage());
        }

                @Test
                @DisplayName("Quando um cliente busca por um pedido feito por outro cliente")
                void quandoClienteBuscaPedidoDeOutroCliente() throws Exception {
                // Arrange
                pedidoRepository.save(pedido);
                Cliente cliente1 = clienteRepository.save(Cliente.builder()
                        .nomeCompleto("Catarina")
                        .endereco("Casinha")
                        .codigoAcesso("121212")
                        .usuario("catarina123")
                        .build());

                // Act
                String responseJsonString = driver.perform(get(URI_PEDIDOS + "/" +
                                pedido.getId() + "/cliente/" + cliente1.getId())
                                .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                                .contentType(MediaType.APPLICATION_JSON))
                                //.content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                        .andExpect(status().isBadRequest())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                // Assert
                assertEquals("Clientes distintos!", resultado.getMessage());
                }


        @Test
        @DisplayName("Quando um estabelecimento busca todos os pedidos feitos nele")
        void quandoEstabelecimentoBuscaTodosPedidos() throws Exception {
            // Arrange
                pedidoRepository.save(pedido);
                pedidoRepository.save(pedido1);

                // Act
                String responseJsonString = driver.perform(get(URI_PEDIDOS + "/estabelecimento/" +
                                estabelecimento.getId())
                                .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                .contentType(MediaType.APPLICATION_JSON))
                                //.content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                List<PedidoResponseDTO> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {});

                // Assert
                assertEquals(2, resultado.size());
                }

                @Test
                @DisplayName("Quando um estabelecimento busca por um pedido feito nele salvo pelo id primeiro")
                void quandoEstabelecimentoBuscaPedidoPorId() throws Exception {
                // Arrange
                pedidoRepository.save(pedido);

                // Act
                String responseJsonString = driver.perform(get(URI_PEDIDOS + "/" +
                                pedido.getId() + "/estabelecimeto/" + estabelecimento.getId())
                                .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                .contentType(MediaType.APPLICATION_JSON))
                                //.content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {});

                //Pedido resultado = listaResultados.get(0);

                // Assert
                assertAll(
                        //() -> assertNotNull(resultado.getId()),
                        () -> assertEquals(pedido.getEnderecoEntrega(), resultado.getEnderecoEntrega()),
                        () -> assertEquals(pedido.getPizzas().get(0).getSabor1(), resultado.getPizzas().get(0).getSabor1()),
                        () -> assertEquals(pedido.getCliente(), resultado.getCliente()),
                        () -> assertEquals(pedido.getEstabelecimento(), resultado.getEstabelecimento()),
                        () -> assertEquals(pedido.getPreco(), resultado.getPreco())
                );
        }

        @Test
        @DisplayName("Quando um estabelecimento busca por um pedido feito nele salvo pelo id inexistente")
        void quandoEstabelecimentoBuscaPedidoInexistente() throws Exception {
                // Arrange
                // nenhuma necessidade além do setup()
                // Act
                String responseJsonString = driver.perform(get(URI_PEDIDOS + "/" +
                                "123" + "/estabelecimeto/" + estabelecimento.getId())
                                .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso())
                                .contentType(MediaType.APPLICATION_JSON))
                                //.content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                        .andExpect(status().isBadRequest())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                // Assert
                assertEquals("O pedido não existe", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um estabelecimento busca por um pedido feito em outro estabelecimento")
        void quandoEstabelecimentoBuscaPedidoDeOutroEstabelecimento() throws Exception {
                // Arrange
                pedidoRepository.save(pedido);
                Estabelecimento estabelecimento1 =
                        estabelecimentoRepository.save(Estabelecimento.builder()
                                .codigoAcesso("121212")
                                .build());

                // Act
                String responseJsonString = driver.perform(get(URI_PEDIDOS + "/" +
                                pedido.getId() + "/estabelecimeto/" + estabelecimento1.getId())
                                .param("estabelecimentoCodigoAcesso",estabelecimento1.getCodigoAcesso())
                                .contentType(MediaType.APPLICATION_JSON))
                                //.content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                        .andExpect(status().isBadRequest())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType resultado = objectMapper.readValue(responseJsonString, CustomErrorType.class);

                // Assert
                assertEquals("Estabelecimentos distintos!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um cliente excluí um pedido feito por ele salvo")
        void quandoClienteExcluiPedidoSalvo() throws Exception {
                // Arrange

                pedidoRepository.save(pedido);

                // Act
                String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/" +
                                pedido.getId() + "/cliente/" + cliente.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                        .andExpect(status().isNoContent())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                // Assert
                assertTrue(responseJsonString.isBlank());
        }

        @Test
        @DisplayName("Quando um cliente excluí um pedido inexistente")
        void quandoClienteExcluiPedidoInexistente() throws Exception {
           // Arrange
           // nenhuma necessidade além do setup()

           // Act
                String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/" +
                        "123" + "/cliente/" + cliente.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                .andExpect(status().isBadRequest())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

                CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                CustomErrorType.class);

                // Assert
                assertEquals("O pedido não existe", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um cliente excluí todos seus pedidos feitos por ele salvos")
        void quandoClienteExcluiTodosPedidosSalvos() throws Exception {
                // Arrange
                pedidoRepository.save(pedido);
                pedidoRepository.save(Pedido.builder()
                        .preco(10.0)
                        .enderecoEntrega("Casa 237")
                        .cliente(cliente)
                        .estabelecimento(estabelecimento)
                        .pizzas(List.of(pizzaM, pizzaG))
                        .build());

                // Act
                String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/cliente/" + cliente.getId())
                                .param("clienteCodigoAcesso", cliente.getCodigoAcesso())
                                .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isNoContent())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                // Assert
                assertTrue(responseJsonString.isBlank());
        }

        @Test
        @DisplayName("Quando um estabelencimento excluí um pedido feito nele salvo")
        void quandoEstabelecimentoExcluiPedidoSalvo() throws Exception {
                // Arrange
                pedidoRepository.save(pedido);

           // Act
                String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/" +
                        pedido.getId() + "/estabelecimento/" + estabelecimento.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso()))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();

           // Assert
        assertTrue(responseJsonString.isBlank());
        }

        @Test
        @DisplayName("Quando um estabelencimento excluí um pedido inexistente")
        void quandoEstabelecimentoExcluiPedidoInexistente() throws Exception {
                // Arrange
                // nenhuma necessidade além do setup()

                // Act
                 // Act
                String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/" +
                                "123" + "/estabelecimento/" + estabelecimento.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso()))
                        .andExpect(status().isBadRequest())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();
                

                CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                        CustomErrorType.class);

                // Assert
                assertEquals("O pedido não existe", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um estabelencimento excluí um pedido feito em outro estabelecimento")
        void quandoEstabelecimentoExcluiPedidoDeOutroEstabelecimento() throws Exception {
                // Arrange
                pedidoRepository.save(pedido);
                Estabelecimento estabelecimento1 =
                        estabelecimentoRepository.save(Estabelecimento.builder()
                                .codigoAcesso("121212")
                                .build());
                                
                                
                        String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/" +
                                pedido.getId() + "/estabelecimento/" + estabelecimento1.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("estabelecimentoCodigoAcesso", estabelecimento1.getCodigoAcesso()))
                        .andExpect(status().isBadRequest())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();
                

                CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                        CustomErrorType.class);

                // Assert
                assertEquals("Estabelecimentos distintos!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um estabelencimento excluí todos os pedidos feitos nele salvos")
        void quandoEstabelecimentoExcluiTodosPedidosSalvos() throws Exception {
        // Arrange
        pedidoRepository.save(pedido);
        pedidoRepository.save(Pedido.builder()
                .preco(10.0)
                .enderecoEntrega("Casa 237")
                .cliente(cliente)
                .estabelecimento(estabelecimento)
                .pizzas(List.of(pizzaM, pizzaG))
                .build());

        // Act
        String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/estabelecimento/" +
                        estabelecimento.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("estabelecimentoCodigoAcesso", estabelecimento.getCodigoAcesso()))
                .andExpect(status().isNoContent())
                .andDo(print())
                .andReturn().getResponse().getContentAsString();
        // Assert
        assertTrue(responseJsonString.isBlank());
        }
//us16
//
        @Test
        @DisplayName("Quando um cliente cancela um pedido")
        void quandoClienteCancelaPedido() throws Exception {
                // Arrange
                pedidoRepository.save(pedido);

                // Act
                String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/" +
                                pedido.getId() + "/cancelar-pedido")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                        .andExpect(status().isNoContent())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                // Assert
                assertTrue(responseJsonString.isBlank());
        }

        @Test
        @DisplayName("Quando um cliente cancela um pedido com id de pedido inexistente")
        void quandoClienteCancelaPedidoComIdInexistente() throws Exception {
                // Arrange
                pedidoRepository.save(pedido);

                // Act
                String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/" +
                                "123" + "/cancelar-pedido")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                        .andExpect(status().isBadRequest())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                // Assert
                CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                        CustomErrorType.class);

                // Assert
                assertEquals("O pedido não existe", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um cliente cancela um pedido com código de acesso inválido")
        void quandoClienteCancelaPedidoComCodigoAcessoInvalido() throws Exception {
                // Arrange
                pedidoRepository.save(pedido);

                // Act
                String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/" +
                                pedido.getId() + "/cancelar-pedido")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("clienteCodigoAcesso", "codigo invalido"))
                        .andExpect(status().isBadRequest())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                // Assert
                CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                        CustomErrorType.class);

                // Assert
                assertEquals("Codigo de acesso invalido!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um cliente cancela um pedido pedido com status Pedido pronto")
        void quandoClienteCancelaPedidoComStatusPedidoPronto() throws Exception {
                // Arrange
                //pedido = pedidoRepository.save(pedido);
                pedido.setStatus("Pedido pronto");
                pedidoRepository.save(pedido);

                // Act
                String responseJsonString = driver.perform(delete(URI_PEDIDOS + "/" +
                                pedido.getId() + "/cancelar-pedido")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("clienteCodigoAcesso", pedido.getCliente().getCodigoAcesso()))
                        .andExpect(status().isBadRequest())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                // Assert
                CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                        CustomErrorType.class);

                // Assert
                assertEquals("O pedido já está pronto e não pode ser cancelado", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um cliente busca um pedido feito em um estabelecimento")
        void quandoClienteBuscaPedidoFeitoEmEstabelecimento() throws Exception {
            // Arrange
                pedidoRepository.save(pedido);


                // Act
                String responseJsonString = driver.perform(get(URI_PEDIDOS + "/" +
                                "pedido-cliente-estabelecimento" + "/" + cliente.getId() + "/" +
                                estabelecimento.getId() + "/" + pedido.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {});

                // Assert
                //assertEquals(1, resultado.size());
                assertEquals(pedido.getId(), resultado.getId());
                assertEquals(pedido.getCliente(), resultado.getCliente());
                assertEquals(pedido.getEstabelecimento(), resultado.getEstabelecimento());
        }

        @Test
        @DisplayName("Quando um cliente busca um pedido feito em um estabelecimento inexistente")
        void quandoClienteBuscaPedidoFeitoEmEstabelecimentoInexistente() throws Exception {
                // Arrange
                pedidoRepository.save(pedido);

                // Act
                String responseJsonString = driver.perform(get(URI_PEDIDOS + "/" +
                                "pedido-cliente-estabelecimento" + "/" + cliente.getId() + "/" + "999999" +
                                "/" + pedido.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                        .andExpect(status().isBadRequest())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                        CustomErrorType.class);

                // Assert
                assertEquals("O estabelecimento consultado nao existe!",
                        resultado.getMessage());
        }

        




        @Test
        @DisplayName("Quando um cliente busca um pedido feito em um estabelecimento com pedido inexistente")
        void quandoClienteBuscaPedidoFeitoEmEstabelecimentoComPedidoInexistente() throws Exception {
                // Arrange
                pedidoRepository.save(pedido);

                // Act
                String responseJsonString = driver.perform(get(URI_PEDIDOS + "/" +
                                "pedido-cliente-estabelecimento" + "/" + cliente.getId() + "/" +
                                estabelecimento.getId() + "/" + "999999")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                        .andExpect(status().isBadRequest())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                        CustomErrorType.class);

                // Assert
                assertEquals("O pedido não existe", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um cliente busca um pedido feito em um estabelecimento com cliente inexistente")
        void quandoClienteBuscaPedidoFeitoEmEstabelecimentoComClienteInexistente() throws Exception {
                // Arrange
                pedidoRepository.save(pedido);

                // Act
                String responseJsonString = driver.perform(get(URI_PEDIDOS + "/" +
                                "pedido-cliente-estabelecimento" + "/" + "999999" + "/" +
                                estabelecimento.getId() + "/" + pedido.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                        .andExpect(status().isBadRequest())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                        CustomErrorType.class);

                // Assert
                assertEquals("O cliente consultado nao existe!", resultado.getMessage());
        }

        @Test
        @DisplayName("Quando um cliente busca todos os pedidos feitos naquele estabelcimento com pedidoId null")
        void quandoClienteBuscaTodosPedidosFeitosNaqueleEstabelecimentoComPedidoIdNull() throws Exception {
                // Arrange
                pedidoRepository.save(pedido);

                // Act
                String responseJsonString = driver.perform(get(URI_PEDIDOS +
                                "/pedidos-cliente-estabelecimento/" + cliente.getId() + "/" +
                                estabelecimento.getId())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                List<PedidoResponseDTO> resultado = objectMapper.readValue(responseJsonString, new TypeReference<>() {});

                // Assert
                assertEquals(1, resultado.size());
                assertEquals(pedido.getId(), resultado.get(0).getId());
                assertEquals(pedido.getCliente(), resultado.get(0).getCliente());
                assertEquals(pedido.getEstabelecimento(), resultado.get(0).getEstabelecimento());
        }

        @Test
        @DisplayName("Quando um cliente busca todos os pedidos feitos naquele estabelcimento com status")
        void quandoClienteBuscaTodosPedidosFeitosNaqueleEstabelecimentoComStatus() throws Exception {
                // Arrange
                Pedido pedido3 = pedidoRepository.save(Pedido.builder()
                        .preco(30.0)
                        .enderecoEntrega("Casa 237")
                        .cliente(cliente)
                        .estabelecimento(estabelecimento)
                        .pizzas(List.of(pizzaM))
                        .status("Pedido em preparo")
                        .build());

                Pedido pedido4 = pedidoRepository.save(Pedido.builder()
                        .preco(30.0)
                        .enderecoEntrega("Casa 145")
                        .cliente(cliente)
                        .estabelecimento(estabelecimento)
                        .pizzas(List.of(pizzaM))
                        .status("Pedido Entregue")
                        .build());


                // Act
                String responseJsonString = driver.perform(get(URI_PEDIDOS +
                                "/pedidos-cliente-estabelecimento/" + cliente.getId() + "/" +
                                estabelecimento.getId() + "/" + pedido3.getStatus())
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                List<PedidoResponseDTO> resultado =
                        objectMapper.readValue(responseJsonString, new TypeReference<>() {
                        });

                // Assert
                assertEquals(1, resultado.size());
                assertEquals(pedido3.getId(), resultado.get(0).getId());
                assertEquals(pedido3.getCliente(), resultado.get(0).getCliente());
                assertEquals(pedido3.getEstabelecimento(), resultado.get(0).getEstabelecimento());
        }

        @Test
        @DisplayName("Quando um cliente busca todos os pedidos feitos naquele estabelcimento filtrados por entrega")
        void quandoClienteBuscaTodosPedidosFeitosNaqueleEstabelecimentoComPedidosFiltradosPorEntrega() throws Exception {
                         // Arrange
                 Pedido pedido3 = pedidoRepository.save(Pedido.builder()
                 .preco(30.0)
                 .enderecoEntrega("Casa 237")
                 .cliente(cliente)
                .estabelecimento(estabelecimento)
               .pizzas(List.of(pizzaM))
                 .status("Pedido entregue")
                 .build());
         Pedido pedido4 = pedidoRepository.save(Pedido.builder()
                 .preco(30.0)
                .enderecoEntrega("Casa 237")
                 .cliente(cliente)
                 .estabelecimento(estabelecimento)
                 .pizzas(List.of(pizzaM))
                 .status("Pedido em preparo")
                 .build());

                 // Act
                 String responseJsonString = driver.perform(get(URI_PEDIDOS +
                                 "/pedidos-cliente-estabelecimento/" + cliente.getId() + "/" +
                                 estabelecimento.getId())
                                 .contentType(MediaType.APPLICATION_JSON)
                                 .param("clienteCodigoAcesso", cliente.getCodigoAcesso()))
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                List<PedidoResponseDTO> resultado =
                         objectMapper.readValue(responseJsonString, new TypeReference<>() {
                         });

            //Assert
                 assertEquals(2, resultado.size());
                 //assertEquals(pedido4.getId(), resultado.get(0).getId());
                 assertEquals(pedido4.getCliente(), resultado.get(0).getCliente());
                 assertEquals(pedido4.getEstabelecimento(),
                         resultado.get(0).getEstabelecimento());
                assertEquals(pedido3.getId(), resultado.get(1).getId());
                 assertEquals(pedido3.getCliente(), resultado.get(1).getCliente());
                assertEquals(pedido3.getEstabelecimento(),
                        resultado.get(1).getEstabelecimento());

        }
}

    @Nested
    @DisplayName("Alteração de estado de pedido")
    public class AlteracaoEstadoPedidoTest {
        Pedido pedido1;

        @BeforeEach
        void setUp() {
            pedido1 = pedidoRepository.save(Pedido.builder()
                    .estabelecimento(estabelecimento)
                    .cliente(cliente)
                    .enderecoEntrega("Rua 1")
                    .pizzas(List.of(pizzaG))
                    .preco(10.0)
                    .build()
            );
        }

        @Test
        @DisplayName("Quando o estabelecimento altera o status para Pedido em preparo")
        void quandoEstabelecimentoConfirmaPreparo() throws Exception {
                // Arrange
                //pedidoRepository.save(pedido);
                    pedido.setStatus("Pedido recebido");
                    pedido.setStatusPagamento(true);
                    entregador.setStatus("Aprovado");
                    Set<Entregador> entregadores = new HashSet<>();
                    entregadores.add(entregador);
                    estabelecimento.setEntregadores(entregadores);
                    entregador.setDisponivel("Disponivel");
                    pedidoRepository.save(pedido);


                    // Act
                    String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" +
                                    pedido.getId() + "/"+ pedido.getEstabelecimento().getId() + "/confirmar-preparo")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .param("codigoAcessoEstabelecimento", estabelecimento.getCodigoAcesso()))
                            //.content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString();

                    PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString,
                            PedidoResponseDTO.class);

                    // Assert
                    assertEquals( "Pedido em preparo", resultado.getStatus());
                    //assertEquals(pedido.getEntregador(), resultado.getEntregadorId());
        }

        @Test
        @DisplayName("Quando o estabelecimento altera o status para Pedido em preparo, com pedido inexistente")
        void quandoEstabelecimentoConfirmaPreparoComPedidoInexistente() throws Exception {
                // Arrange
                    //pedidoRepository.save(pedido);
                    pedido.setStatus("Pedido recebido");
                    pedido.setStatusPagamento(true);
                    entregador.setStatus("Aprovado");
                    Set<Entregador> entregadores = new HashSet<>();
                    entregadores.add(entregador);
                    estabelecimento.setEntregadores(entregadores);
                    entregador.setDisponivel("Disponivel");
                    pedidoRepository.save(pedido);


                    // Act
                    String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" +
                                    13L + "/"+ pedido.getEstabelecimento().getId() + "/confirmar-preparo")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .param("codigoAcessoEstabelecimento", estabelecimento.getCodigoAcesso()))
                            //.content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                            .andExpect(status().isBadRequest())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString();

                    CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                            CustomErrorType.class);

                    // Assert
                    assertEquals( "O pedido não existe", resultado.getMessage());
                    //assertEquals(pedido.getEntregador(), resultado.getEntregadorId());
        }

        @Test
        @DisplayName("Quando o estabelecimento altera o status para Pedido em preparo, com estabelecimento inexistente")
        void quandoEstabelecimentoConfirmaPreparoComEstabelecimentoInexistente() throws Exception {
                    // Arrange
                    //pedidoRepository.save(pedido);
                    pedido.setStatus("Pedido recebido");
                    pedido.setStatusPagamento(true);
                    entregador.setStatus("Aprovado");
                    Set<Entregador> entregadores = new HashSet<>();
                    entregadores.add(entregador);
                    estabelecimento.setEntregadores(entregadores);
                    entregador.setDisponivel("Disponivel");
                    pedidoRepository.save(pedido);


                    // Act
                    String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" +
                                    pedido.getId() + "/"+ 13L + "/confirmar-preparo")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .param("codigoAcessoEstabelecimento", estabelecimento.getCodigoAcesso()))
                            //.content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                            .andExpect(status().isBadRequest())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString();

                    CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                            CustomErrorType.class);

                    // Assert
                    assertEquals( "O estabelecimento consultado nao existe!", resultado.getMessage());
                    //assertEquals(pedido.getEntregador(), resultado.getEntregadorId());
        }

            @Test
            @DisplayName("Quando o estabelecimento altera o status para Pedido em preparo, com codigo de acesso invalido")
            void quandoEstabelecimentoConfirmaPreparoComCodigoAcessoInvalido() throws Exception {
                    // Arrange
                    //pedidoRepository.save(pedido);
                    pedido.setStatus("Pedido recebido");
                    pedido.setStatusPagamento(true);
                    entregador.setStatus("Aprovado");
                    Set<Entregador> entregadores = new HashSet<>();
                    entregadores.add(entregador);
                    estabelecimento.setEntregadores(entregadores);
                    entregador.setDisponivel("Disponivel");
                    //estabelecimento.setCodigoAcesso("473291");
                    pedidoRepository.save(pedido);


                    // Act
                    String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" +
                                    pedido.getId() + "/"+ pedido.getEstabelecimento().getId() + "/confirmar-preparo")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .param("codigoAcessoEstabelecimento", "473291"))
                            //.content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                            .andExpect(status().isBadRequest())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString();

                    CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                            CustomErrorType.class);

                    // Assert
                    assertEquals( "Codigo de acesso invalido!", resultado.getMessage());
                    //assertEquals(pedido.getEntregador(), resultado.getEntregadorId());
            }

        @Test
        @DisplayName("Quando o estabelecimento altera o status para Pedido em preparo, com pagamento não altorizado")
        void quandoEstabelecimentoConfirmaPreparoComPedidoNaoPago() throws Exception {
                // Arrange
                //pedidoRepository.save(pedido);
                pedido.setStatus("Pedido recebido");
                pedido.setStatusPagamento(false);
                entregador.setStatus("Aprovado");
                Set<Entregador> entregadores = new HashSet<>();
                entregadores.add(entregador);
                estabelecimento.setEntregadores(entregadores);
                entregador.setDisponivel("Disponivel");
                pedidoRepository.save(pedido);


                    // Act
                String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" +
                                pedido.getId() + "/"+ pedido.getEstabelecimento().getId() + "/confirmar-preparo")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("codigoAcessoEstabelecimento", estabelecimento.getCodigoAcesso()))
                            //.content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                        .andExpect(status().isBadRequest())
                        .andDo(print())
                        .andReturn().getResponse().getContentAsString();

                CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                        CustomErrorType.class);

                // Assert
                assertEquals( "Pagamento não autorizado", resultado.getMessage());
                    //assertEquals(pedido.getEntregador(), resultado.getEntregadorId());
        }

        @Test
        @DisplayName("Quando o estabelecimento termina o pedido e atualiza para Pedido pronto")
        void quandoEstabelecimentoTerminaPreparo() throws Exception {
                // Arrange
                //pedidoRepository.save(pedido);
                pedido.setStatus("Pedido em preparo");
                pedido.setStatusPagamento(true);
                entregador.setStatus("Aprovado");
                Set<Entregador> entregadores = new HashSet<>();
                entregadores.add(entregador);
                estabelecimento.setEntregadores(entregadores);
                entregador.setDisponivel("Disponivel");
                pedidoRepository.save(pedido);


                    // Act
                String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" +
                                    pedido.getId() + "/"+ pedido.getEstabelecimento().getId() + "/pedido-pronto")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .param("codigoAcessoEstabelecimento", estabelecimento.getCodigoAcesso()))
                            //.content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString();

                    PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString,
                            PedidoResponseDTO.class);

                    // Assert
                    assertEquals( "Pedido pronto", resultado.getStatus());
                    //assertEquals(pedido.getEntregador(), resultado.getEntregadorId());
        }

            @Test
            @DisplayName("Quando o estabelecimento termina o pedido e atualiza para Pedido pronto, passando codigo acesso invalido")
            void quandoEstabelecimentoTerminaPreparoComCodigoAcessoInvalido() throws Exception {
                    // Arrange
                    //pedidoRepository.save(pedido);
                    pedido.setStatus("Pedido em preparo");
                    pedido.setStatusPagamento(true);
                    entregador.setStatus("Aprovado");
                    Set<Entregador> entregadores = new HashSet<>();
                    entregadores.add(entregador);
                    estabelecimento.setEntregadores(entregadores);
                    entregador.setDisponivel("Disponivel");
                    //estabelecimento.setCodigoAcesso("999666");
                    pedidoRepository.save(pedido);


                    // Act
                    String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" +
                                    pedido.getId() + "/"+ pedido.getEstabelecimento().getId() + "/pedido-pronto")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .param("codigoAcessoEstabelecimento", "999666"))
                            //.content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                            .andExpect(status().isBadRequest())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString();

                    CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                            CustomErrorType.class);

                    // Assert
                    assertEquals( "Codigo de acesso invalido!", resultado.getMessage());
                    //assertEquals(pedido.getEntregador(), resultado.getEntregadorId());
            }



        @Test
        @DisplayName("Quando o estabelecimento associa um pedido a um entregador, com codigo de acesso invalido")
        void quandoEstabelecimentoAssociaPedidoEntregadorComCodigoAcessoInvalido() throws Exception {
            // Arrange
                    //pedidoRepository.save(pedido);
                    pedido.setStatus("Pedido pronto");
                    entregador.setStatus("Aprovado");
                    Set<Entregador> entregadores = new HashSet<>();
                    entregadores.add(entregador);
                    estabelecimento.setEntregadores(entregadores);
                    entregador.setDisponivel("Disponivel");
                    pedidoRepository.save(pedido);


            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" +
                            pedido.getId() + "/"+ pedido.getEstabelecimento().getId() + "/associar-pedido-entregador")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcessoEstabelecimento", "999666"))
                            //.content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isBadRequest())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

                CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                        CustomErrorType.class);

                // Assert
                assertEquals( "Codigo de acesso invalido!", resultado.getMessage());
            //assertEquals(pedido.getEntregador(), resultado.getEntregadorId());
        }

            @Test
            @DisplayName("Quando o estabelecimento associa um pedido a um entregador")
            void quandoEstabelecimentoAssociaPedidoEntregador() throws Exception {
                    // Arrange
                    //pedidoRepository.save(pedido);
                    pedido.setStatus("Pedido pronto");
                    entregador.setStatus("Aprovado");
                    Set<Entregador> entregadores = new HashSet<>();
                    entregadores.add(entregador);
                    estabelecimento.setEntregadores(entregadores);
                    entregador.setDisponivel("Disponivel");
                    pedidoRepository.save(pedido);


                    // Act
                    String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" +
                                    pedido.getId() + "/"+ pedido.getEstabelecimento().getId() + "/associar-pedido-entregador")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .param("codigoAcessoEstabelecimento", estabelecimento.getCodigoAcesso()))
                            //.content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                            .andExpect(status().isOk())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString();

                    PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString,
                            PedidoResponseDTO.class);

                    // Assert
                    assertEquals( "Pedido em rota", resultado.getStatus());
                    //assertEquals(pedido.getEntregador(), resultado.getEntregadorId());
            }

        @Test
        @DisplayName("Quando o cliente confirma a entrega de um pedido")
        void quandoClienteConfirmaEntregaPedido() throws Exception {
            // Arrange
            pedidoRepository.save(pedido);
            pedido.setStatus("Pedido em rota");

            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" +
                            pedido.getId() + "/" + cliente.getId() + "/cliente-confirmar-entrega")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcessoCliente", cliente.getCodigoAcesso())
                            .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                    .andExpect(status().isOk())
                    .andDo(print())
                    .andReturn().getResponse().getContentAsString();

            PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString,
                    PedidoResponseDTO.class);

            // Assert
            assertEquals(resultado.getStatus(), "Pedido entregue");
        }

            @Test
            @DisplayName("Quando o cliente confirma a entrega de um pedido. com codigo de acesso invalido")
            void quandoClienteConfirmaEntregaPedidoComCodigoAcessoInvalido() throws Exception {
                    // Arrange
                    pedidoRepository.save(pedido);
                    pedido.setStatus("Pedido em rota");

                    // Act
                    String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" +
                                    pedido.getId() + "/" + cliente.getId() + "/cliente-confirmar-entrega")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .param("codigoAcessoCliente", "999666")
                                    .content(objectMapper.writeValueAsString(pedidoPostPutRequestDTO)))
                            .andExpect(status().isBadRequest())
                            .andDo(print())
                            .andReturn().getResponse().getContentAsString();

                    CustomErrorType resultado = objectMapper.readValue(responseJsonString,
                            CustomErrorType.class);

                    // Assert
                    assertEquals( "Codigo de acesso invalido!", resultado.getMessage());
            }
    }

    @Nested
    @DisplayName("Conjunto de casos de teste da confirmação de pagamento de um pedido")
    public class PedidoConfirmarPagamentoTests {

        Pedido pedido4;

        @BeforeEach
        void setUp() {

                formaDePagamento = FormaDePagamento.builder()
                .tipo(TipoPagamento.PIX)
                .build();

         formaDePagamento1 = FormaDePagamento.builder()
                .tipo(TipoPagamento.CARTAO_DEBITO)
                .build();

         formaDePagamento2 = FormaDePagamento.builder()
                .tipo(TipoPagamento.CARTAO_CREDITO)
                .build();

        pedidoPutConfirmarPagamentoRequestDTO = PedidoPutConfirmarPagamentoRequestDTO.builder()
                .formaDePagamento(formaDePagamento)
                .build();

            pedido4 = pedidoRepository.save(Pedido.builder()
                    .estabelecimento(estabelecimento)
                    .cliente(cliente)
                    .enderecoEntrega("Rua 1")
                    .pizzas(List.of(pizzaG))
                    //.preco(10.0)
                    .entregador(entregador)
                    .statusPagamento(false)
                    //.formaDePagamento(formaDePagamento)
                    .status("Pedido recebido")
                    .build()
            );
        }

        @Test
        @DisplayName("Quando confirmamos o pagamento de um pedido por cartão de crédito")
        void confirmaPagamentoCartaoCredito() throws Exception {
            // Arrange

                pedidoPutConfirmarPagamentoRequestDTO.setFormaDePagamento(formaDePagamento2);

            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" +
                            cliente.getId() + "/confirmar-pagamento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcessoCliente", cliente.getCodigoAcesso())
                            .param("pedidoId", pedido4.getId().toString())
                            //.param("metodoPagamento", "Cartão de crédito")
                            .content(objectMapper.writeValueAsString(pedidoPutConfirmarPagamentoRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andReturn().getResponse().getContentAsString();
            // Assert
            PedidoResponseDTO resultado = objectMapper.readValue(responseJsonString, PedidoResponseDTO.class);
            assertAll(
                    () -> assertTrue(resultado.isStatusPagamento()),
                    () -> assertEquals(10, resultado.getPreco())
            );
        }

        @Test
        @DisplayName("Quando confirmamos o pagamento de um pedido por cartão de crédito")
        void confirmaPagamentoCartaoDebito() throws Exception {
            // Arrange

                pedidoPutConfirmarPagamentoRequestDTO.setFormaDePagamento(formaDePagamento1);

            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" +
                            cliente.getId() + "/confirmar-pagamento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcessoCliente", cliente.getCodigoAcesso())
                            .param("pedidoId", pedido4.getId().toString())
                            //.param("metodoPagamento", "Cartão de débito")
                            .content(objectMapper.writeValueAsString(pedidoPutConfirmarPagamentoRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andReturn().getResponse().getContentAsString();
            // Assert
            Pedido resultado = objectMapper.readValue(responseJsonString, Pedido.class);
            assertAll(
                    () -> assertTrue(resultado.isStatusPagamento()),
                    () -> assertEquals(9.75, resultado.getPreco())
            );
        }

        @Test
        @DisplayName("Quando confirmamos o pagamento de um pedido por cartão de crédito")
        void confirmaPagamentoPIX() throws Exception {
            // Arrange

                pedidoPutConfirmarPagamentoRequestDTO.setFormaDePagamento(formaDePagamento);

            // Act
            String responseJsonString = driver.perform(put(URI_PEDIDOS + "/" +
                            cliente.getId() + "/confirmar-pagamento")
                            .contentType(MediaType.APPLICATION_JSON)
                            .param("codigoAcessoCliente", cliente.getCodigoAcesso())
                            .param("pedidoId", pedido4.getId().toString())
                            //.param("metodoPagamento", "PIX")
                            .content(objectMapper.writeValueAsString(pedidoPutConfirmarPagamentoRequestDTO)))
                    .andExpect(status().isOk()) // Codigo 200
                    .andReturn().getResponse().getContentAsString();
            // Assert
            Pedido resultado = objectMapper.readValue(responseJsonString, Pedido.class);
            assertAll(
                    () -> assertTrue(resultado.isStatusPagamento()),
                    () -> assertEquals(9.5, resultado.getPreco())
            );
        }
    }
}

