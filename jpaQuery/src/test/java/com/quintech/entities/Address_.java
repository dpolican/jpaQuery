/** 
 *  Generated by OpenJPA MetaModel Generator Tool.
**/

package com.quintech.entities;

import java.lang.Integer;
import java.lang.String;
import javax.persistence.metamodel.SingularAttribute;

@javax.persistence.metamodel.StaticMetamodel
(value=com.quintech.entities.Address.class)
@javax.annotation.Generated
(value="org.apache.openjpa.persistence.meta.AnnotationProcessor6",date="Sat Mar 08 13:36:14 MST 2014")
public class Address_ {
    public static volatile SingularAttribute<Address,String> city;
    public static volatile SingularAttribute<Address,Integer> id;
    public static volatile SingularAttribute<Address,Integer> personId;
    public static volatile SingularAttribute<Address,String> state;
    public static volatile SingularAttribute<Address,String> street1;
    public static volatile SingularAttribute<Address,String> street2;
    public static volatile SingularAttribute<Address,String> zip;
}
