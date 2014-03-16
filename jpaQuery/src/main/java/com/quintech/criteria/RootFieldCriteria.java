package com.quintech.criteria;

import java.util.Collection;

import javax.persistence.criteria.Path;
import javax.persistence.metamodel.SingularAttribute;

public final class RootFieldCriteria<T, U>
{
    private final FromInfo<T, U> rootInfo;
    private final SingularAttribute<U, ?> field;
    private final SingularAttribute<?, ?> relatedField;

    public RootFieldCriteria(FromInfo<T, U> rootInfo, SingularAttribute<U, ?> field)
    {
        super();
        this.rootInfo = rootInfo;
        this.field = field;
        this.relatedField = null;
    }

    public <R> RootFieldCriteria(FromInfo<T, U> rootInfo, SingularAttribute<U, R> field, SingularAttribute<R, ?> relatedField)
    {
        super();
        this.rootInfo = rootInfo;
        this.field = field;
        this.relatedField = relatedField;
    }

    public <V> FromInfo<T, U> equalTo(V value)
    {
        this.rootInfo.addPredicate(PredicateUtils.equalTo(this.rootInfo.getCriteriaBuilder(),
            getField(), value));

        return this.rootInfo;
    }

    @SuppressWarnings("unchecked")
    protected <R> Path<?> getField()
    {
        if (relatedField == null)
        {
            return this.rootInfo.getFrom().get(field);
        }
        return ((Path<R>) this.rootInfo.getFrom().get(field)).get((SingularAttribute<R, ?>) relatedField);
    }

    @SuppressWarnings("unchecked")
    public FromInfo<T, U> equalToIgnoreCase(String value)
    {
        this.rootInfo.addPredicate(PredicateUtils.equalToIgnoreCase(this.rootInfo.getCriteriaBuilder(),
            (Path<String>) getField(), value));

        return this.rootInfo;
    }

    @SuppressWarnings("unchecked")
    public FromInfo<T, U> startsWith(String value)
    {
        this.rootInfo.addPredicate(PredicateUtils.startsWith(this.rootInfo.getCriteriaBuilder(),
            (Path<String>) getField(), value));

        return this.rootInfo;
    }

    @SuppressWarnings("unchecked")
    public FromInfo<T, U> startsWithIgnoreCase(String value)
    {
        this.rootInfo.addPredicate(PredicateUtils.startsWithIgnoreCase(this.rootInfo.getCriteriaBuilder(),
            (Path<String>) getField(), value));

        return this.rootInfo;
    }

    @SuppressWarnings("unchecked")
    public FromInfo<T, U> contains(String value)
    {
        this.rootInfo.addPredicate(PredicateUtils.contains(this.rootInfo.getCriteriaBuilder(),
            (Path<String>) getField(), value));

        return this.rootInfo;
    }

    @SuppressWarnings("unchecked")
    public FromInfo<T, U> containsIgnoreCase(String value)
    {
        this.rootInfo.addPredicate(PredicateUtils.containsIgnoreCase(this.rootInfo.getCriteriaBuilder(),
            (Path<String>) getField(), value));

        return this.rootInfo;
    }

    public <V> FromInfo<T, U> isNullOrNotLessThan(V value)
    {
        if (value != null)
        {
            this.rootInfo.addPredicate(PredicateUtils.isNullOrNotLessThan(this.rootInfo.getCriteriaBuilder(),
                getField(), value));
        }

        return this.rootInfo;
    }

    public <V> FromInfo<T, U> isNullOrNotBefore(V value)
    {
        return isNullOrNotLessThan(value);
    }

    public <V> FromInfo<T, U> notLessThan(V value)
    {
        if (value != null)
        {
            this.rootInfo.addPredicate(PredicateUtils.notLessThan(this.rootInfo.getCriteriaBuilder(),
                getField(), value));
        }

        return this.rootInfo;
    }

    public <V> FromInfo<T, U> notBefore(V value)
    {
        return notLessThan(value);
    }

    public <V> FromInfo<T, U> notGreaterThan(V value)
    {
        if (value != null)
        {
            this.rootInfo.addPredicate(PredicateUtils.notGreaterThan(this.rootInfo.getCriteriaBuilder(),
                getField(), value));
        }

        return this.rootInfo;
    }

    public <V> FromInfo<T, U> notAfter(V value)
    {
        return notGreaterThan(value);
    }

    public <V> FromInfo<T, U> in(Collection<V> values)
    {
        this.rootInfo.addPredicate(PredicateUtils.in(this.rootInfo.getCriteriaBuilder(), getField(),
            values));

        return this.rootInfo;
    }
}
