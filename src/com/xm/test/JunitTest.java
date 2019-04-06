package com.xm.test;

import com.xm.entity.Classes;
import com.xm.entity.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

public class JunitTest {
    private Session session=null;

    @Before
    public void setUp(){
        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @After
    public void tearDown(){
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public void testStudentAndClasses(){

        Classes classes =  new Classes();
        classes.setClassName("高一");
        session.save(classes);
        Student stu1 =  new Student();
        stu1.setName("zhangjinfei1");
        stu1.setClasses(classes);

        Student stu2 =  new Student();
        stu2.setName("zhangjinfei2");
        stu2.setClasses(classes);
        session.save(stu1);
        session.save(stu2);
    }

    @Test
    public void getStudentByClasses(){

        Classes classes = session.get(Classes.class, 1);
        Set<Student> studentSet = classes.getStudentSet();
        for (Student student:studentSet) {
            System.out.println(student);
        }
    }

    @Test
    public void testAdd(){
        Classes classes =  new Classes();
        classes.setClassName("高123");

        Student stu1 =  new Student();
        stu1.setName("zhangjinfei11");
        session.save(classes);
        session.save(stu1);
    }

    @Test
    public void testInverse(){
        Classes classes =  session.get(Classes.class,1);
        Student stu1 =  session.get(Student.class,1);
        /*
            Inverse 双向绑定时 在一对多的配置文件中设置
         */
        stu1.setClasses(classes);
        classes.getStudentSet().add(stu1);
    }
}
