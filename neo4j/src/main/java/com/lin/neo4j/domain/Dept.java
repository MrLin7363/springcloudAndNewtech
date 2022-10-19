package com.lin.neo4j.domain;

import lombok.Builder;
import lombok.Data;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Property;

@Data
@Builder
@NodeEntity("Dept") // 标签：默认就是类名
public class Dept {
    @Id // 声明主键
    @GeneratedValue  // 声明该字段为neo4j自动生成的<id>映射的字段
    private Long id;

    @Property("deptno") // 如果名称不一样可用
    private Integer deptno;

    private String dname;

    private String location;
}
