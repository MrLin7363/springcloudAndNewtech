package com.lin.neo4j.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@Data
@NodeEntity
public class Person {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
}
