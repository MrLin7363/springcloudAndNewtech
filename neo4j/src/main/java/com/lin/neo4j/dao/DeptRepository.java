package com.lin.neo4j.dao;

import com.lin.neo4j.domain.Dept;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptRepository extends Neo4jRepository<Dept,Long> {

    @Query(value = "create (d:Dept {deptno:{0},dname:{1},location:{2}})")
    void create(Integer deptno, String dname, String location);

    Dept findByDeptno(Integer deptno);

    List<Dept> findByDeptnoOrderByDeptnoAsc(Integer deptno);

}
