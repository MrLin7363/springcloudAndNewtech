package com.lin.neo4j;

import com.lin.neo4j.dao.DeptRepository;
import com.lin.neo4j.domain.Dept;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringRunner.class)
public class NodeTest {
    @Autowired
    private DeptRepository deptRepository;

    @Test
    public void testCreate() {
       deptRepository.create(6,"技术部","深圳");
       deptRepository.save(Dept.builder().deptno(3).dname("人力资源").location("广州").build());
    }

    @Test
    public void testDelete() {
        // 这里的删除有关系也能删除掉
        deptRepository.deleteById(40L);
    }

    @Test
    public void testDeptQuery() {
        final Iterable<Dept> all = deptRepository.findAll();
        all.forEach(e-> System.out.println(e));
        System.out.println(deptRepository.findById(40L));
    }

    @Test
    public void testDeptNativeQuery() {
        Dept deptn = deptRepository.findByDeptno(4);
        System.out.println(deptn);
        System.out.println(deptRepository.findByDeptno(4));
    }
}
