package com.tfg.gestorcuentas.service.categoria;

import com.tfg.gestorcuentas.data.entity.CategoriaEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.data.repository.ICategoriaJPARepository;
import com.tfg.gestorcuentas.service.categoria.model.Categoria;
import com.tfg.gestorcuentas.utils.Messages;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CategoriaServiceTest {

    @Mock
    private ICategoriaJPARepository iCategoriaJPARepository;
    @InjectMocks
    private CategoriaService categoriaService;
    
    @Test
    public void testSaveCategoria() {
        UsuarioEntity usuario = buildUsuarioEntity();
        Categoria categoria = new Categoria(usuario, "Mi categoría");
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setNombre("Mi categoría");
        categoriaEntity.setBorrado(0);
        categoriaEntity.setUsuario(usuario);

        given(iCategoriaJPARepository.save(categoriaEntity)).willReturn(categoriaEntity);

        String message = categoriaService.save(categoria);

        verify(iCategoriaJPARepository).save(categoriaEntity);

        Assert.assertEquals(Messages.CATEGORIA_GUARDADA.getMessage(), message);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveCategoriaUsuarioNotValid() {
        UsuarioEntity usuario = buildUsuarioEntityNotValid();
        Categoria categoria = new Categoria(usuario, "Mi categoría");
        categoriaService.save(categoria);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveCategoriaNotValid() {
        UsuarioEntity usuario = buildUsuarioEntity();
        Categoria categoria = new Categoria(usuario, "");
        categoriaService.save(categoria);

    }

    @Test
    public void testModifyCategoria() {
        UsuarioEntity usuario = buildUsuarioEntity();
        Categoria categoria = new Categoria(usuario, "Mi categoría");
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(12);
        categoriaEntity.setNombre("Mi categoría");
        categoriaEntity.setBorrado(0);
        categoriaEntity.setUsuario(usuario);

        given(iCategoriaJPARepository.findById(12)).willReturn(Optional.of(categoriaEntity));
        given(iCategoriaJPARepository.save(categoriaEntity)).willReturn(categoriaEntity);

        String message = categoriaService.modify(categoria);

        verify(iCategoriaJPARepository).findById(12);
        verify(iCategoriaJPARepository).save(categoriaEntity);

        Assert.assertEquals(Messages.CATEGORIA_MODIFIED.getMessage(), message);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testModifyCategoriaUsuarioNotValid() {
        UsuarioEntity usuario = buildUsuarioEntityNotValid();
        Categoria categoria = new Categoria(usuario, "Mi categoría");
        categoriaService.save(categoria);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testModifyCategoriaNotValid() {
        UsuarioEntity usuario = buildUsuarioEntity();
        Categoria categoria = new Categoria(usuario, "");
        categoriaService.save(categoria);

    }

    @Test(expected = IllegalStateException.class)
    public void testModifyCategoriaNotFound() {
        UsuarioEntity usuario = buildUsuarioEntity();
        Categoria categoria = new Categoria(usuario, "Mi categoría");
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(12);
        categoriaEntity.setNombre("Mi categoría");
        categoriaEntity.setBorrado(0);
        categoriaEntity.setUsuario(usuario);

        given(iCategoriaJPARepository.findById(12)).willReturn(Optional.empty());

        categoriaService.modify(categoria);

        verify(iCategoriaJPARepository).findById(12);
    }

    private UsuarioEntity buildUsuarioEntity() {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(1);
        entity.setUsername("javier");
        entity.setPassword("javier");
        entity.setBorrado(0);

        return entity;
    }

    private UsuarioEntity buildUsuarioEntityNotValid() {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setPassword("javier");
        entity.setBorrado(0);

        return entity;
    }
}
