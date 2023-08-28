package com.tfg.gestorcuentas.service.categoria;

import com.tfg.gestorcuentas.data.entity.CategoriaEntity;
import com.tfg.gestorcuentas.data.entity.CriterioEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.data.repository.ICategoriaJPARepository;
import com.tfg.gestorcuentas.data.repository.ICriterioJPARepository;
import com.tfg.gestorcuentas.data.repository.IUsuarioJPARepository;
import com.tfg.gestorcuentas.service.categoria.model.Categoria;
import com.tfg.gestorcuentas.utils.Messages;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CategoriaServiceTest {

    @Mock
    private ICategoriaJPARepository iCategoriaJPARepository;
    @Mock
    private IUsuarioJPARepository iUsuarioJPARepository;
    @Mock
    private ICriterioJPARepository iCriterioJPARepository;
    @InjectMocks
    private CategoriaService categoriaService;
    
    @Test
    public void testSaveCategoria() {
        UsuarioEntity usuario = buildUsuarioEntity();
        Categoria categoria = new Categoria(usuario, "Mi categoria");
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setNombre("Mi categoria");
        categoriaEntity.setBorrado(0);
        categoriaEntity.setUsuario(usuario);

        given(iCategoriaJPARepository.findByNombreContainingIgnoreCase("Mi categoria")).willReturn(Optional.empty());
        given(iUsuarioJPARepository.findByUsername("javier")).willReturn(Optional.of(usuario));
        given(iCategoriaJPARepository.save(categoriaEntity)).willReturn(categoriaEntity);

        String message = categoriaService.save(categoria);

        verify(iCategoriaJPARepository).findByNombreContainingIgnoreCase("Mi categoria");
        verify(iUsuarioJPARepository).findByUsername("javier");
        verify(iCategoriaJPARepository).save(categoriaEntity);

        Assert.assertEquals(Messages.CATEGORIA_GUARDADA.getMessage(), message);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveCategoriaDataInvalid() {
        UsuarioEntity usuario = buildUsuarioEntity();
        Categoria categoria = new Categoria(usuario, null);
        categoriaService.save(categoria);
    }

    @Test
    public void testSaveCategoriaPreviouslyDeleted() {
        UsuarioEntity usuario = buildUsuarioEntity();
        Categoria categoria = new Categoria(usuario, "Mi categoria");
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(1);
        categoriaEntity.setNombre("Mi categoria");
        categoriaEntity.setBorrado(0);
        categoriaEntity.setUsuario(usuario);

        CategoriaEntity categoriaEntityDeleted = new CategoriaEntity();
        categoriaEntityDeleted.setId(1);
        categoriaEntityDeleted.setNombre("Mi categoria");
        categoriaEntityDeleted.setBorrado(1);
        categoriaEntityDeleted.setUsuario(usuario);

        given(iCategoriaJPARepository.findByNombreContainingIgnoreCase("Mi categoria")).willReturn(Optional.of(categoriaEntityDeleted));
        given(iUsuarioJPARepository.findByUsername("javier")).willReturn(Optional.of(usuario));
        given(iCategoriaJPARepository.save(categoriaEntity)).willReturn(categoriaEntity);

        String message = categoriaService.save(categoria);

        verify(iCategoriaJPARepository).findByNombreContainingIgnoreCase("Mi categoria");
        verify(iUsuarioJPARepository).findByUsername("javier");
        verify(iCategoriaJPARepository).save(categoriaEntity);

        Assert.assertEquals(Messages.CATEGORIA_GUARDADA.getMessage(), message);
    }

    @Test(expected = IllegalStateException.class)
    public void testSaveCategoriaAlreadyExists() {
        UsuarioEntity usuario = buildUsuarioEntity();
        Categoria categoria = new Categoria(usuario, "Mi categoria");
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(1);
        categoriaEntity.setNombre("Mi categoria");
        categoriaEntity.setBorrado(0);
        categoriaEntity.setUsuario(usuario);

        given(iCategoriaJPARepository.findByNombreContainingIgnoreCase("Mi categoria")).willReturn(Optional.of(categoriaEntity));
        given(iUsuarioJPARepository.findByUsername("javier")).willReturn(Optional.of(usuario));

        categoriaService.save(categoria);

        verify(iCategoriaJPARepository).findByNombreContainingIgnoreCase("Mi categoria");
        verify(iUsuarioJPARepository).findByUsername("javier");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveCategoriaUsuarioNotValid() {
        UsuarioEntity usuario = buildUsuarioEntityNotValid();
        Categoria categoria = new Categoria(usuario, "Mi categoria");
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
        Categoria categoria = new Categoria(12, "Mi categoria");
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(12);
        categoriaEntity.setNombre("Mi categoria");
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
    public void testModifyCategoriaNotValid() {
        Categoria categoria = new Categoria(12, null);
        categoriaService.modify(categoria);
    }

    @Test(expected = IllegalStateException.class)
    public void testModifyCategoriaNotFound() {
        UsuarioEntity usuario = buildUsuarioEntity();
        Categoria categoria = new Categoria(12, "Mi categoría");
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(12);
        categoriaEntity.setNombre("Mi categoría");
        categoriaEntity.setBorrado(0);
        categoriaEntity.setUsuario(usuario);

        given(iCategoriaJPARepository.findById(12)).willReturn(Optional.empty());

        categoriaService.modify(categoria);

        verify(iCategoriaJPARepository).findById(12);
    }

    @Test
    public void testFindByUsername() {
        UsuarioEntity usuario = buildUsuarioEntity();
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(12);
        categoriaEntity.setNombre("Mi categoria");
        categoriaEntity.setBorrado(0);
        categoriaEntity.setUsuario(buildUsuarioEntity());

        given(iUsuarioJPARepository.findByUsername("javier")).willReturn(Optional.of(usuario));
        given(iCategoriaJPARepository.findByUsuarioAndBorrado(usuario, 0)).willReturn(Collections.singletonList(categoriaEntity));

        List<CategoriaEntity> categoriaEntityList = categoriaService.findByUsername("javier");

        verify(iUsuarioJPARepository).findByUsername("javier");
        verify(iCategoriaJPARepository).findByUsuarioAndBorrado(usuario, 0);

        Assert.assertEquals(Collections.singletonList(categoriaEntity), categoriaEntityList);
    }

    @Test(expected = NoSuchElementException.class)
    public void testFindByUsernameUserNotExists() {
        given(iUsuarioJPARepository.findByUsername("javier")).willReturn(Optional.empty());
        categoriaService.findByUsername("javier");
        verify(iUsuarioJPARepository).findByUsername("javier");
    }

        @Test(expected = IllegalArgumentException.class)
    public void testFindByUsernameDataInvalid() {
        categoriaService.findByUsername(null);
    }

    @Test
    public void testDelete() {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(12);
        categoriaEntity.setNombre("Mi categoria");
        categoriaEntity.setBorrado(0);
        categoriaEntity.setUsuario(buildUsuarioEntity());

        CategoriaEntity categoriaEntityDeleted = new CategoriaEntity();
        categoriaEntityDeleted.setId(12);
        categoriaEntityDeleted.setNombre("Mi categoria");
        categoriaEntityDeleted.setBorrado(1);
        categoriaEntityDeleted.setUsuario(buildUsuarioEntity());

        CriterioEntity criterio = new CriterioEntity();
        criterio.setId(1);
        criterio.setNombre("Criterio");
        criterio.setBorrado(0);
        criterio.setCategoria(categoriaEntity);

        CriterioEntity criterioDeleted = new CriterioEntity();
        criterioDeleted.setId(1);
        criterioDeleted.setNombre("Criterio");
        criterioDeleted.setBorrado(1);
        criterioDeleted.setCategoria(categoriaEntity);

        given(iCategoriaJPARepository.findById(12)).willReturn(Optional.of(categoriaEntity));
        given(iCriterioJPARepository.findByCategoria_IdAndBorrado(12, 0)).willReturn(Collections.singletonList(criterio));
        given(iCriterioJPARepository.save(criterioDeleted)).willReturn(criterioDeleted);
        given(iCategoriaJPARepository.save(categoriaEntityDeleted)).willReturn(categoriaEntityDeleted);

        String msg = categoriaService.delete(12);

        verify(iCategoriaJPARepository).findById(12);
        verify(iCriterioJPARepository).findByCategoria_IdAndBorrado(12, 0);
        verify(iCriterioJPARepository).save(criterioDeleted);
        verify(iCategoriaJPARepository).save(categoriaEntityDeleted);

        Assert.assertEquals(Messages.CATEGORIA_MODIFIED.getMessage(), msg);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteIdCategoriaNull() {
        categoriaService.delete(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeleteCategoriaNotFound() {
        given(iCategoriaJPARepository.findById(12)).willReturn(Optional.empty());

        categoriaService.delete(12);

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
