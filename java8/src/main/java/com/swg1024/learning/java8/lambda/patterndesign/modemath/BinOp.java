package com.swg1024.learning.java8.lambda.patterndesign.modemath;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年04月05日 13:26
 */
@Getter
@AllArgsConstructor
class BinOp extends Expr {
    private String opname;
    private Expr left, right;
}
