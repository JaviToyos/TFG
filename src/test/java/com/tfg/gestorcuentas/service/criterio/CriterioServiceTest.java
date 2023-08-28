package com.tfg.gestorcuentas.service.criterio;

import com.tfg.gestorcuentas.data.entity.CategoriaEntity;
import com.tfg.gestorcuentas.data.entity.CriterioEntity;
import com.tfg.gestorcuentas.data.entity.UsuarioEntity;
import com.tfg.gestorcuentas.data.repository.ICategoriaJPARepository;
import com.tfg.gestorcuentas.data.repository.ICriterioJPARepository;
import com.tfg.gestorcuentas.service.criterio.model.Criterio;
import com.tfg.gestorcuentas.utils.Messages;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class CriterioServiceTest {

    @Mock
    private ICriterioJPARepository repository;
    @Mock
    private ICategoriaJPARepository categoriaJPARepository;
    @InjectMocks
    private CriterioService criterioService;

    @Test
    public void testSave() {
        CriterioEntity criterio = buildCriterioEntity();
        Criterio crit = new Criterio(buildCategoriaEntity(), "NBA");

        given(repository.findByNombreContainingIgnoreCase("NBA")).willReturn(Optional.empty());
        given(categoriaJPARepository.findById(1)).willReturn(Optional.of(buildCategoriaEntity()));
        given(repository.save(criterio)).willReturn(criterio);

        String msg = criterioService.save(crit);

        verify(repository).findByNombreContainingIgnoreCase("NBA");
        verify(categoriaJPARepository).findById(1);
        verify(repository).save(criterio);

        Assert.assertEquals(Messages.CRITERIO_SAVED.getMessage(), msg);
    }

    @Test
    public void testSaveCriterioAlreadyDeleted() {
        CriterioEntity criterio = buildCriterioEntityDeleted();
        Criterio crit = new Criterio(buildCategoriaEntity(), "NBA");
        CriterioEntity criterioToSave = buildCriterioEntity();

        given(repository.findByNombreContainingIgnoreCase("NBA")).willReturn(Optional.of(criterio));
        given(repository.save(criterioToSave)).willReturn(criterioToSave);

        String msg = criterioService.save(crit);

        verify(repository).findByNombreContainingIgnoreCase("NBA");
        verify(repository).save(criterioToSave);

        Assert.assertEquals(Messages.CRITERIO_SAVED.getMessage(), msg);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveCriterioAlreadyExists() {
        CriterioEntity criterio = buildCriterioEntity();
        Criterio crit = new Criterio(buildCategoriaEntity(), "NBA");

        given(repository.findByNombreContainingIgnoreCase("NBA")).willReturn(Optional.of(criterio));

        criterioService.save(crit);

        verify(repository).findByNombreContainingIgnoreCase("NBA");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveCategoriaNotValid() {
        CriterioEntity criterio = buildCriterioEntityCategoriaInvalid();
        Criterio crit = new Criterio(buildCategoriaEntityInvalid(), "NBA");

        criterioService.save(crit);

        verify(repository).save(criterio);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSaveCategoriaNotFound() {
        Criterio crit = new Criterio(buildCategoriaEntity(), "NBA");

        given(categoriaJPARepository.findById(1)).willReturn(Optional.empty());

        criterioService.save(crit);

        verify(categoriaJPARepository).findById(1);
    }

    @Test
    public void testModify() {
        CriterioEntity criterio = new CriterioEntity();
        criterio.setId(1);
        criterio.setNombre("Nuevo crit");
        criterio.setCategoria(buildCategoriaEntity());

        CriterioEntity criterioExistente = new CriterioEntity();
        criterioExistente.setId(1);
        criterioExistente.setNombre("crit");
        criterioExistente.setCategoria(buildCategoriaEntity());

        given(repository.findById(1)).willReturn(Optional.of(criterioExistente));
        given(repository.save(criterio)).willReturn(criterio);

        String msg = criterioService.modify(criterio);

        verify(repository).findById(1);
        verify(repository).save(criterio);

        Assert.assertEquals(Messages.CRITERIO_MODIFIED.getMessage(), msg);
    }

    @Test(expected = IllegalStateException.class)
    public void testModifyWhenCriterioNotFound() {
        CriterioEntity criterio = new CriterioEntity();
        criterio.setId(1);
        criterio.setNombre("Nuevo crit");
        criterio.setCategoria(buildCategoriaEntity());

        given(repository.findById(1)).willReturn(Optional.empty());

        criterioService.modify(criterio);

        verify(repository).findById(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testModifyWhenIdCriterioNull() {
        CriterioEntity criterio = new CriterioEntity();
        criterio.setNombre("Nuevo crit");
        criterio.setCategoria(buildCategoriaEntity());

        criterioService.modify(criterio);
    }

    @Test
    public void testFindByCategoria() {
        CriterioEntity entityOne = new CriterioEntity();
        entityOne.setId(1);
        entityOne.setNombre("Nuevo crit");
        entityOne.setCategoria(buildCategoriaEntity());

        CriterioEntity entityTwo = new CriterioEntity();
        entityTwo.setId(2);
        entityTwo.setNombre("Nuevo crit");
        entityTwo.setCategoria(buildCategoriaEntity());

        given(repository.findByCategoria_IdAndBorrado(1, 0)).willReturn(Arrays.asList(entityTwo, entityOne));

        List<CriterioEntity> criteriosList = criterioService.findByCategoria(1);

        verify(repository).findByCategoria_IdAndBorrado(1, 0);

        Assert.assertEquals(Arrays.asList(entityTwo, entityOne), criteriosList);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindByCategoriaWithIdNull() {
        criterioService.findByCategoria(null);
    }

    @Test
    public void testDelete() {
        CriterioEntity criterio = new CriterioEntity();
        criterio.setId(1);
        criterio.setNombre("NBA");
        criterio.setBorrado(0);

        CriterioEntity criterioDeleted = new CriterioEntity();
        criterioDeleted.setId(1);
        criterioDeleted.setNombre("NBA");
        criterioDeleted.setBorrado(1);

        given(repository.findById(1)).willReturn(Optional.of(criterio));
        given(repository.save(criterioDeleted)).willReturn(criterioDeleted);

        String msg = criterioService.delete(1);

        verify(repository).findById(1);
        verify(repository).save(criterioDeleted);

        Assert.assertEquals(Messages.CRITERIO_MODIFIED.getMessage(), msg);
    }

    @Test(expected = NoSuchElementException.class)
    public void testDeleteCriterioNotFound() {
        given(repository.findById(1)).willReturn(Optional.empty());
        criterioService.delete(1);
        verify(repository).findById(1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testDeleteCriterioIdInvalid() {
        criterioService.delete(null);
    }

    private UsuarioEntity buildUsuarioEntity() {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId(1);
        entity.setUsername("javier");
        entity.setPassword("javier");
        entity.setBorrado(0);

        return entity;
    }


    private CategoriaEntity buildCategoriaEntity() {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setId(1);
        categoriaEntity.setNombre("Deporte");
        categoriaEntity.setBorrado(0);
        categoriaEntity.setUsuario(buildUsuarioEntity());

        return categoriaEntity;
    }

    private CategoriaEntity buildCategoriaEntityInvalid() {
        CategoriaEntity categoriaEntity = new CategoriaEntity();
        categoriaEntity.setNombre("Deporte");
        categoriaEntity.setBorrado(0);
        categoriaEntity.setUsuario(buildUsuarioEntity());

        return categoriaEntity;
    }

    private CriterioEntity buildCriterioEntity() {
        CriterioEntity entity = new CriterioEntity();
        entity.setNombre("NBA");
        entity.setBorrado(0);
        entity.setCategoria(buildCategoriaEntity());

        return entity;
    }

    private CriterioEntity buildCriterioEntityDeleted() {
        CriterioEntity entity = new CriterioEntity();
        entity.setNombre("NBA");
        entity.setBorrado(1);
        entity.setCategoria(buildCategoriaEntity());

        return entity;
    }

    private CriterioEntity buildCriterioEntityCategoriaInvalid() {
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setNombre("Nombre");
        categoria.setBorrado(0);

        CriterioEntity entity = new CriterioEntity();
        entity.setNombre("NBA");
        entity.setBorrado(0);
        entity.setCategoria(categoria);

        return entity;
    }
}
