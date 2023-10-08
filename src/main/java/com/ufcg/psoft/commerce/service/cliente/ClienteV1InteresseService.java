package com.ufcg.psoft.commerce.service.cliente;

import com.ufcg.psoft.commerce.exception.ClienteNaoExisteException;
import com.ufcg.psoft.commerce.exception.CodigoInvalido;
import com.ufcg.psoft.commerce.exception.SaborJaDisponivelException;
import com.ufcg.psoft.commerce.exception.SaborNaoExisteException;
import com.ufcg.psoft.commerce.models.Cliente;
import com.ufcg.psoft.commerce.models.Sabor;
import com.ufcg.psoft.commerce.repositories.ClienteRepository;
import com.ufcg.psoft.commerce.repositories.SaborRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;

@Service
public class ClienteV1InteresseService implements ClienteInteresseService {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    SaborRepository saborRepository;

    @Override
    public Sabor salvarInteresse(Long id, String codigoAcesso, Long saborId) {
        Optional<Sabor> sabor = saborRepository.findById(saborId);

        Optional<Cliente> cliente = clienteRepository.findById(id);

        if(cliente.isPresent()){
            if(cliente.get().getCodigoAcesso().equals(codigoAcesso)){
                if(sabor.isPresent()){
                    if(!sabor.get().isDisponivel()){
                        if(sabor.get().getClientesInteressados() == null){
                            sabor.get().setClientesInteressados(new HashSet<>());
                        }

                        sabor.get().getClientesInteressados().add(cliente.get());
                    }else{
                        throw new SaborJaDisponivelException();
                    }

                    return sabor.get();
                }else{
                    throw new SaborNaoExisteException();
                }

            }else{
                throw new CodigoInvalido();
            }
        }else{
            throw new ClienteNaoExisteException();
        }
    }
}
