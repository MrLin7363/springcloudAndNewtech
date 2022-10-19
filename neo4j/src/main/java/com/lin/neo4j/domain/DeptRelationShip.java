package com.lin.neo4j.domain;

import lombok.Data;
import org.neo4j.ogm.annotation.EndNode;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.RelationshipEntity;
import org.neo4j.ogm.annotation.StartNode;

/**
 * 部门关系
 */
@Data
@RelationshipEntity(type = "DeptRelationShip")
public class DeptRelationShip {
    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @StartNode
    private Dept startNode;

    @EndNode
    private Person endNode;
}
