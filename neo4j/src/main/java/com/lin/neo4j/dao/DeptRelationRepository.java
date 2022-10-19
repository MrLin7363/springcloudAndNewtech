package com.lin.neo4j.dao;

import com.lin.neo4j.domain.DeptRelationShip;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptRelationRepository extends Neo4jRepository<DeptRelationShip, Long> {

}
