package com.tfg.gestorcuentas.service.proveedor;

import com.tfg.gestorcuentas.data.repository.IProveedorJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProveedorService implements IProveedorService {
    private IProveedorJPARepository iProveedorJPARepository;

    @Autowired
    public ProveedorService(IProveedorJPARepository iProveedorJPARepository) {
        this.iProveedorJPARepository = iProveedorJPARepository;
    }

}
