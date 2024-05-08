package com.polyglot.demo.project.interfaces;

public interface WithOverloadedMethod {

    public String concat();

    public String concat(String a);

    public String concat(String a , String b);

    public String concat(String a , int b);

    void pythonConstructor();

}
