package com.lin.neo4j;

import com.lin.neo4j.dao.DeptRelationRepository;
import com.lin.neo4j.dao.DeptRepository;
import com.lin.neo4j.domain.Dept;
import com.lin.neo4j.domain.DeptRelationShip;
import com.lin.neo4j.domain.Person;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class RelationTest {
    @Autowired
    private DeptRepository deptRepository;

    @Autowired
    private DeptRelationRepository deptRelationRepository;

    @Test
    public void testRelation() {
        DeptRelationShip deptRelationShip = new DeptRelationShip();
        deptRelationShip.setName("归属");

        Dept dept = deptRepository.findById(86L).orElse(null);
        deptRelationShip.setStartNode(dept);

        Person person = new Person();
        person.setName("霖");
//        person.setId(106L);
        deptRelationShip.setEndNode(person);

        // 这里的保存关系 会连带保存person新节点，如果person设置id且该id已存在，则不新建person节点
        deptRelationRepository.save(deptRelationShip);
    }

    @Test
    public void testRelationQuery() {
        Iterable<DeptRelationShip> deptRelationAll = deptRelationRepository.findAll();
        deptRelationAll.forEach(e-> System.out.println(e));
    }
}
