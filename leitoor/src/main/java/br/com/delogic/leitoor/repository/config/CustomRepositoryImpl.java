package br.com.delogic.leitoor.repository.config;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManager;

import br.com.delogic.jfunk.data.Identifiable;

public class CustomRepositoryImpl<T extends Identifiable<Integer>, ID extends Serializable> implements
    EntityRepository<T, ID> {

    private final EntityManager em;
    private final Class<T>      type;

    public CustomRepositoryImpl(Class<T> domainClass, EntityManager em) {
        this.em = em;
        this.type = domainClass;
    }

    @Override
    public T findById(ID id) {
        return em.find(type, id);
    }

    @Override
    public T create(T entity) {
        em.persist(entity);
        em.flush();
        return entity;
    }

    @Override
    public <C extends Collection<? extends T>> C create(C entities) {
        for (T t : entities) {
            em.persist(t);
        }
        em.flush();
        return entities;
    }

    @Override
    public T update(T entity) {
        em.merge(entity);
        em.flush();
        return entity;
    }

    @Override
    public <C extends Collection<? extends T>> C update(C entities) {
        for (T t : entities) {
            em.merge(t);
        }
        em.flush();
        return entities;
    }

    @Override
    public T delete(T entity) {
        em.remove(entity);
        em.flush();
        return entity;
    }

    @Override
    public Collection<? extends T> delete(Collection<? extends T> entities) {
        for (T t : entities) {
            em.remove(t);
        }
        em.flush();
        return entities;
    }

}
