package com.quintech.criteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import com.quintech.criteria.JoinInfo.AndInfo;


public final class RootInfo<T> implements FromInfo<T, T>
{
    private final RootInfo<?> rootInfo;
    protected final EntityManager em;
    private final CriteriaBuilder builder;
    private final CriteriaQuery<T> criteria;
    private final Root<T> fromEntity;
    private final List<Predicate> predicates = new ArrayList<Predicate>();
    private final List<Order> orderBys = new ArrayList<Order>();
    private final List<FromInfo<T, ?>> joins = new ArrayList<FromInfo<T, ?>>();
    private boolean distinct;

    protected RootInfo(EntityManager em, Class<T> entityClass)
    {
        super();
        this.rootInfo = null;
        this.em = em;
        this.builder = em.getCriteriaBuilder();
        this.criteria = builder.createQuery(entityClass);
        this.fromEntity = criteria.from(entityClass);
    }

    protected <U> RootInfo(RootInfo<U> rootInfo, Class<T> entityClass)
    {
        super();
        this.em = null;
        this.builder = null;
        this.criteria = null;
        this.rootInfo = rootInfo;
        this.fromEntity = rootInfo.criteria.from(entityClass);
    }

    private TypedQuery<T> createQuery()
    {
        List<Predicate> allPredicates = new ArrayList<Predicate>(this.predicates);
        List<Order> allOrderBys = new ArrayList<Order>(this.orderBys);
        for (FromInfo<T, ?> join : joins)
        {
            allPredicates.addAll(join.getPredicates());
            allOrderBys.addAll(join.getOrderBys());
        }

        if (!allOrderBys.isEmpty())
        {
            criteria.orderBy(allOrderBys);
        }

        if (!allPredicates.isEmpty())
        {
            criteria.where(allPredicates.toArray(new Predicate[allPredicates.size()]));
        }

        criteria.select(this.fromEntity).distinct(this.distinct);
        return em.createQuery(criteria);
    }

    protected void addJoin(FromInfo<T, ?> join)
    {
        joins.add(join);
    }

    public RootInfo<T> distinct()
    {
        this.distinct = true;
        return this;
    }

    public void addPredicate(Predicate predicate)
    {
        if (predicate != null)
        {
            this.predicates.add(predicate);
        }
    }

    public List<Predicate> getPredicates()
    {
        return Collections.unmodifiableList(predicates);
    }

    public List<Order> getOrderBys()
    {
        return Collections.unmodifiableList(orderBys);
    }

    public List<T> resultList()
    {
        return createQuery().getResultList();
    }

    public <J> AndInfo<T, J> andFrom(Class<J> joinClass)
    {
        return new AndInfo<T, J>(this, joinClass);
    }

    public T singleResult()
    {
        List<T> resultList = createQuery().setMaxResults(1).getResultList();
        if (resultList.size() > 0)
        {
            return resultList.get(0);
        }

        return null;
    }

    public <V> RootFieldCriteria<T, T> with(SingularAttribute<T, ? extends V> field)
    {
        return new RootFieldCriteria<T, T>(this, field);
    }

    public <R, V> RootFieldCriteria<T, T> with(SingularAttribute<T, R> field,
        SingularAttribute<R, ? extends V> relatedField)
    {
        return new RootFieldCriteria<T, T>(this, field, relatedField);
    }

    public <V> RootFieldCriteria<T, T> and(SingularAttribute<T, ? extends V> field)
    {
        return with(field);
    }

    public <R, V> RootFieldCriteria<T, T> and(SingularAttribute<T, R> field,
        SingularAttribute<R, ? extends V> relatedField)
    {
        return new RootFieldCriteria<T, T>(this, field, relatedField);
    }

    public <V> RootInfo<T> descendingBy(SingularAttribute<T, ? extends V> field)
    {
        orderBys.add(getCriteriaBuilder().desc(fromEntity.get(field)));
        return this;
    }

    public <V> RootInfo<T> ascendingBy(SingularAttribute<T, ? extends V> field)
    {
        orderBys.add(getCriteriaBuilder().asc(fromEntity.get(field)));
        return this;
    }

    public void addOrderBy(Order order)
    {
        if (order != null)
        {
            orderBys.add(order);
        }

    }

    @Override
    public CriteriaBuilder getCriteriaBuilder()
    {
        return this.builder == null ? this.rootInfo.builder : this.builder;
    }

    @Override
    public Root<T> getFrom()
    {
        return fromEntity;
    }
}
