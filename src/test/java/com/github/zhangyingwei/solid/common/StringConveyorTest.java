package com.github.zhangyingwei.solid.common;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class StringConveyorTest {

    private String template;
    private StringConveyor conveyor;

    @Before
    public void before() {
        this.template = "\"hello\" hahahah {{ code }} this is content";
        this.conveyor = new StringConveyor(template);
    }

    @Test
    public void getUntil() {
        String res = this.conveyor.getUntil("{{",false).result();
        System.out.println(res);
        String res2 = this.conveyor.getUntil("}}",true).result();
        System.out.println(res2);
    }


    @Test
    public void getFromTo() {
        String res = this.conveyor.getFromTo("\"", "\"").result();
        System.out.println(res);

        System.out.println(this.conveyor);

        String res2 = this.conveyor.getFromTo("{{", "}}").result();
        System.out.println(res2);
    }

    @Test
    public void getBetween() {
        String res = this.conveyor.getBetween("\"", "\"").result();
        System.out.println(res);

        System.out.println(this.conveyor);

        String res2 = this.conveyor.getBetween("{{", "}}").result();
        System.out.println(res2);
    }

    @Test
    public void print() {
        System.out.println("你好，我叫张英伟");
    }
}