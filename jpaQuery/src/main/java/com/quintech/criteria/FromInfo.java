package com.quintech.criteria;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import com.quintech.criteria.JoinInfo.AndInfo;


public interface FromInfo<T, U>
{
    CriteriaBuilder getCriteriaBuilder();

    Root<U> getFrom();

    void addPredicate(Predicate predicate);

    <J> AndInfo<T, J> andFrom(Class<J> joinClass);
    
    <V> FromInfo<T, U> descendingBy(SingularAttribute<U, ? extends V> field);

    <V> FromInfo<T, U> ascendingBy(SingularAttribute<U, ? extends V> field);

    List<Predicate> getPredicates();

    List<Order> getOrderBys();

    List<T> resultList();

    T singleResult();

    <V> RootFieldCriteria<T, U> and(SingularAttribute<U, ? extends V> field);
}
