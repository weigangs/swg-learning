package com.swg1024.learning.java8.publishersub.entity;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年02月25日 10:22
 */
public class ArithmeticCell extends SimpleCell{

    private int left;

    private int right;

    public ArithmeticCell(String name) {
        super(name);
    }

    public void setLeft(int left) {
        this.left = left;
        onNext(left + this.right);
    }

    public void setRight(int right) {
        this.right = right;
        onNext(this.left + right);
    }




}
