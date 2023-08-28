package com.tfg.gestorcuentas.service.movimiento;

import com.tfg.gestorcuentas.data.entity.CategoriaEntity;
import com.tfg.gestorcuentas.data.entity.CriterioEntity;
import com.tfg.gestorcuentas.data.entity.CuentaBancariaEntity;
import com.tfg.gestorcuentas.data.entity.MovimientoEntity;
import com.tfg.gestorcuentas.data.repository.ICategoriaJPARepository;
import com.tfg.gestorcuentas.data.repository.ICriterioJPARepository;
import com.tfg.gestorcuentas.data.repository.IMovimientoJPARepository;
import com.tfg.gestorcuentas.service.categoria.model.CategoriaDTO;
import com.tfg.gestorcuentas.service.movimiento.model.Movimiento;
import com.tfg.gestorcuentas.service.movimiento.model.MovimientoDTO;
import com.tfg.gestorcuentas.utils.MessageErrors;
import com.tfg.gestorcuentas.utils.Messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MovimientoService implements IMovimientoService {
    private final IMovimientoJPARepository iMovimientoJPARepository;
    private final ICategoriaJPARepository iCategoriaJPARepository;
    private final ICriterioJPARepository iCriterioJPARepository;

    @Autowired
    public MovimientoService(IMovimientoJPARepository iMovimientoJPARepository,
                             ICategoriaJPARepository iCategoriaJPARepository,
                             ICriterioJPARepository iCriterioJPARepository) {
        this.iMovimientoJPARepository = iMovimientoJPARepository;
        this.iCategoriaJPARepository = iCategoriaJPARepository;
        this.iCriterioJPARepository = iCriterioJPARepository;
    }

    @Override
    public String saveNordigen(Movimiento movimiento) {
        Set<CategoriaEntity> categoriaEntitySet = new HashSet<>();

        if (!validateMovimiento(movimiento.getMovimiento()))
            throw new IllegalArgumentException(MessageErrors.MOVIMIENTO_DATA_EMPTY.getErrorCode());
        if (!validateCuentaBancaria(movimiento.getCuentaBancaria()))
            throw new IllegalArgumentException(MessageErrors.ACCOUNT_DATA_EMPTY.getErrorCode());

        Optional<MovimientoEntity> mov = iMovimientoJPARepository.findByIdTransaccion(movimiento.getMovimiento().getIdTransaccion());

        if(mov.isPresent())  return Messages.MOVIMIENTOS_GUARDADO_OK.getMessage();

        List<CategoriaEntity> categoriasUsuario = iCategoriaJPARepository.findByUsuario_Id(movimiento
                .getCuentaBancaria().getUsuario().getId());

        for(CategoriaEntity categoria : categoriasUsuario) {
            List<CriterioEntity> criteriosCategoria = iCriterioJPARepository
                    .findByCategoria_IdAndBorrado(categoria.getId(), 0);
            for(CriterioEntity criterio : criteriosCategoria) {
                if(movimiento.getMovimiento().getInformacionMovimiento()
                        .equals(criterio.getNombre())) {
                    CategoriaEntity categoriaToSet = new CategoriaEntity();
                    categoriaToSet.setId(categoria.getId());
                    categoriaEntitySet.add(categoriaToSet);
                }
            }
        }

        MovimientoEntity movimientoEntity = new MovimientoEntity();
        movimientoEntity.setCuentaBancaria(movimiento.getCuentaBancaria());
        movimientoEntity.setCategoriaEntitySet(categoriaEntitySet);
        movimientoEntity.setIdTransaccion(movimiento.getMovimiento().getIdTransaccion());
        movimientoEntity.setCantidad(movimiento.getMovimiento().getCantidad());
        movimientoEntity.setDivisa(movimiento.getMovimiento().getDivisa());
        movimientoEntity.setDestinatario(movimiento.getMovimiento().getDestinatario());
        movimientoEntity.setFecha(movimiento.getMovimiento().getFecha());
        movimientoEntity.setInformacionMovimiento(movimiento.getMovimiento().getInformacionMovimiento());
        movimientoEntity.setBorrado(0);

        iMovimientoJPARepository.save(movimientoEntity);
        return Messages.MOVIMIENTOS_GUARDADO_OK.getMessage();
    }

    @Override
    public List<MovimientoDTO> findByIdCuenta(Integer idCuenta) {
        if(idCuenta == null) {
            throw new IllegalArgumentException(MessageErrors.ACCOUNT_DATA_EMPTY.getErrorCode());
        }
        List<MovimientoEntity> movimientos = iMovimientoJPARepository.findByCuentaBancaria_Id(idCuenta);
        return movimientos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private MovimientoDTO convertToDTO(MovimientoEntity movimientoEntity) {
        MovimientoDTO movimientoDTO = new MovimientoDTO();
        movimientoDTO.setId(movimientoEntity.getId());
        movimientoDTO.setCantidad(movimientoEntity.getCantidad());
        movimientoDTO.setDivisa(movimientoEntity.getDivisa());
        movimientoDTO.setFecha(movimientoEntity.getFecha());
        movimientoDTO.setInformacionMovimiento(movimientoEntity.getInformacionMovimiento());
        movimientoDTO.setDestinatario(movimientoEntity.getDestinatario());
        movimientoDTO.setIdTransaccion(movimientoEntity.getIdTransaccion());

        List<CategoriaDTO> categoriaDTOs = movimientoEntity.getCategoriaEntitySet().stream()
                .map(categoriaEntity -> {
                    CategoriaDTO categoriaDTO = new CategoriaDTO();
                    categoriaDTO.setId(categoriaEntity.getId());
                    categoriaDTO.setNombre(categoriaEntity.getNombre());
                    return categoriaDTO;
                })
                .collect(Collectors.toList());

        movimientoDTO.setCategorias(categoriaDTOs);

        return movimientoDTO;
    }

    private static boolean validateMovimiento(MovimientoEntity movimiento) {
        return movimiento.getCategoriaEntitySet() != null && movimiento.getIdTransaccion() != null &&
                movimiento.getCantidad() != null && movimiento.getDivisa() != null &&
                movimiento.getDestinatario() != null;
    }

    private static boolean validateCuentaBancaria(CuentaBancariaEntity cuentaBancaria) {
        return cuentaBancaria != null && cuentaBancaria.getUsuario() != null &&
                cuentaBancaria.getProveedor() != null && cuentaBancaria.getNombre() != null;
    }

}
