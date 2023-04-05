package com.swg1024.learning.java8.lambda.patterndesign.visitor;

import java.util.Comparator;

/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年04月05日 15:05
 */
class SimplifyExprVisitor {

    Expr visit(BinOp e) {
        if ("+".equals(e.getOpname()) && e.getRight() instanceof Number && ((Number) e.getRight()).getVal() == 0) {
            return e.getLeft();
        }
        return e;
    }
}
