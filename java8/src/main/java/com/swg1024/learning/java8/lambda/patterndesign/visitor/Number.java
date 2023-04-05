package com.swg1024.learning.java8.lambda.patterndesign.visitor;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年04月05日 13:24
 */
@Getter
@AllArgsConstructor
public class Number extends Expr {
    private int val;
}
