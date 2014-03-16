package com.quintech.criteria;

import java.util.Collection;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;

import org.apache.commons.lang.StringUtils;

public class PredicateUtils
{
    private static final String LIKE_WILDCARD = "%";

    private static final char ESCAPE_CHAR = '\\';

    public static String escapeSQL(String value)
    {
        final String[] searchStrings =
        { "_", "%" };
        final String[] replacementStrings =
        { ESCAPE_CHAR + "_", ESCAPE_CHAR + LIKE_WILDCARD };

        return StringUtils.replaceEach(value, searchStrings, replacementStrings);
    }

    public static <T, V> Predicate equalTo(CriteriaBuilder builder, Expression<?> path, V value)
    {
        return builder.equal(path, value);
    }

    public static <T> Predicate equalToIgnoreCase(CriteriaBuilder builder, Expression<String> path, String value)
    {
        return builder.equal(builder.upper(path), StringUtils.upperCase(value));
    }

    public static <T> Predicate startsWith(CriteriaBuilder builder, Expression<String> path, String value)
    {
        return builder.like(path, escapeSQL(value) + LIKE_WILDCARD, ESCAPE_CHAR);
    }

    public static <T> Predicate startsWithIgnoreCase(CriteriaBuilder builder, Expression<String> path, String value)
    {
        return builder.like(builder.upper(path), escapeSQL(StringUtils.upperCase(value)) + LIKE_WILDCARD, ESCAPE_CHAR);
    }

    public static <T> Predicate contains(CriteriaBuilder builder, Expression<String> path, String value)
    {
        return builder.like(path, LIKE_WILDCARD + escapeSQL(value) + LIKE_WILDCARD, ESCAPE_CHAR);
    }

    public static <T> Predicate containsIgnoreCase(CriteriaBuilder builder, Expression<String> path, String value)
    {
        return builder.like(builder.upper(path), LIKE_WILDCARD + escapeSQL(StringUtils.upperCase(value))
            + LIKE_WILDCARD, ESCAPE_CHAR);
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T, V> Predicate isNullOrNotLessThan(CriteriaBuilder builder, Expression<?> expression, V value)
    {
        if (value != null)
        {
            Predicate isNull = builder.isNull(expression);
            Predicate notLessThan =
                builder.greaterThanOrEqualTo((Expression) expression, (Expression) builder.literal(value));

            return builder.or(isNull, notLessThan);
        }

        return builder.and();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T, V> Predicate notLessThan(CriteriaBuilder builder, Expression<?> expression, V value)
    {
        if (value != null)
        {
            return builder.greaterThanOrEqualTo((Expression) expression, (Expression) builder.literal(value));
        }

        return builder.and();
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static <T, V> Predicate notGreaterThan(CriteriaBuilder builder, Expression<?> expression, V value)
    {
        if (value != null)
        {
            return builder.lessThanOrEqualTo((Expression) expression, (Expression) builder.literal(value));
        }

        return builder.and();
    }

    public static <T, V> Predicate in(CriteriaBuilder builder, Expression<?> expression, Collection<V> values)
    {
        if (values.size() > 0)
        {
            return expression.in(values);
        }

        return builder.or();
    }

}
