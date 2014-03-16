package com.quintech.criteria;

import java.util.Collections;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

public final class JoinInfo<T, J> implements FromInfo<T, J>
{
    private final AndInfo<T, J> andInfo;
    private final RootInfo<J> joinFrom;
    
    public static class AndInfo<T, J>
    {
        private final RootInfo<T> mainFrom;
        private final Class<J> joinClass;
        private SingularAttribute<T, ?> sourceField;
        private SingularAttribute<J, ?> joinField;

        protected AndInfo(RootInfo<T> selectFrom, Class<J> joinClass)
        {
            super();
            this.mainFrom = selectFrom;
            this.joinClass = joinClass;
        }

        public JoinInfo<T, J> joinOn(SingularAttribute<T, ?> sourceField, SingularAttribute<J, ?> joinField)
        {
            this.joinField = joinField;
            this.sourceField = sourceField;
            return new JoinInfo<T, J>(this);
        }
    }
    
    public CriteriaBuilder getCriteriaBuilder()
    {
        return andInfo.mainFrom.getCriteriaBuilder();
    }
    
    private Root<T> getMainEntity()
    {
        return andInfo.mainFrom.getFrom();
    }
    
    public Root<J> getFrom()
    {
        return this.joinFrom.getFrom();
    }

    protected void addOrderBy(Order order)
    {
        this.joinFrom.addOrderBy(order);
    }
    
    public void addPredicate(Predicate predicate)
    {
        this.joinFrom.addPredicate(predicate);
    }
    
    public List<Predicate> getPredicates()
    {
        return Collections.unmodifiableList(this.joinFrom.getPredicates());
    }
    
    public List<Order> getOrderBys()
    {
        return Collections.unmodifiableList(this.joinFrom.getOrderBys());
    }

    public JoinInfo(AndInfo<T, J> andFrom)
    {
        super();
        this.andInfo = andFrom;
        this.joinFrom = new RootInfo<J>(andFrom.mainFrom, andFrom.joinClass);
        
        if (this.andInfo.sourceField != null && this.andInfo.joinField != null)
        {
            addPredicate(getCriteriaBuilder().equal(this.getMainEntity().get(this.andInfo.sourceField),
                this.getFrom().get(this.andInfo.joinField)));
        }
        andFrom.mainFrom.addJoin(this);
    }

    public List<T> resultList()
    {
        return this.andInfo.mainFrom.resultList();
    }

    public T singleResult()
    {
        return this.andInfo.mainFrom.singleResult();
    }

    public <V> RootFieldCriteria<T, J> with(SingularAttribute<J, ? extends V> field)
    {
        return new RootFieldCriteria<T, J>(this, field);
    }

    public <V> RootFieldCriteria<T, J> and(SingularAttribute<J, ? extends V> field)
    {
        return with(field);
    }

    public <V> JoinInfo<T, J> descendingBy(SingularAttribute<J, ? extends V> field)
    {
        addOrderBy(joinFrom.getCriteriaBuilder().desc(joinFrom.getFrom().get(field)));
        return this;
    }

    @Override
    public <V> JoinInfo<T, J> ascendingBy(SingularAttribute<J, ? extends V> field)
    {
        addOrderBy(joinFrom.getCriteriaBuilder().asc(joinFrom.getFrom().get(field)));
        return this;

    }

    @Override
    public <K> AndInfo<T, K> andFrom(Class<K> joinClass)
    {
        return new AndInfo<T, K>(this.andInfo.mainFrom, joinClass);
    }
}
