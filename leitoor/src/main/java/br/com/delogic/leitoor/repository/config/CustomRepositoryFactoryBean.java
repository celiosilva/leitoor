package br.com.delogic.leitoor.repository.config;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

public class CustomRepositoryFactoryBean<R extends JpaRepository<T, I>, T, I extends Serializable> extends
    JpaRepositoryFactoryBean<R, T, I> {

    @SuppressWarnings("rawtypes")
    protected RepositoryFactorySupport createRepositoryFactory(EntityManager entityManager) {
        return new MyRepositoryFactory(entityManager);
    }

    private static class MyRepositoryFactory<T, I extends Serializable> extends JpaRepositoryFactory {

        private EntityManager        entityManager;

        public MyRepositoryFactory(EntityManager entityManager) {
            super(entityManager);
            this.entityManager = entityManager;
        }

        @SuppressWarnings({ "rawtypes", "unchecked" })
        protected Object getTargetRepository(RepositoryMetadata metadata) {

            return new CustomRepositoryImpl((Class<T>) metadata.getDomainType(), entityManager);
        }

        protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata) {

            // The RepositoryMetadata can be safely ignored, it is used by the
            // JpaRepositoryFactory
            // to check for QueryDslJpaRepository's which is out of scope.
            return EntityRepository.class;
        }
    }

}
