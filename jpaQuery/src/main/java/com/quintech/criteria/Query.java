package com.quintech.criteria;

import java.util.List;

import javax.persistence.EntityManager;

public class Query
{
    public static class Using
    {
        private final EntityManager em;

        public Using(EntityManager em)
        {
            super();
            this.em = em;
        }
        
        public <T> RootInfo<T> selectFrom(Class<T> entityClass)
        {
            return new RootInfo<T>(this.em, entityClass);
        }

        public <T> List<T> selectAllFrom(Class<T> entityClass)
        {
            return selectFrom(entityClass).resultList();
        }
    }
    
    public static Using using(EntityManager em)
    {
        return new Using(em);
    }
}
