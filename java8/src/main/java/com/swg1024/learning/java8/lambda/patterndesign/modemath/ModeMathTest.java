package com.swg1024.learning.java8.lambda.patterndesign.modemath;


/**
 * @version 1.0
 * @author: nicholas
 * @createTime: 2023年04月05日 13:28
 */
public class ModeMathTest
{

    public Expr simplifyExpression(Expr expr) {
        if (expr instanceof BinOp
            && ((BinOp)expr).getOpname().equals("+")
            && ((BinOp)expr).getRight() instanceof Number
            && ((BinOp)expr).getLeft() instanceof Number
            && ((Number)((BinOp)expr).getLeft()).getVal() == 0
        ) {
            return ((BinOp) expr).getRight();
        }
        return expr;
    }
}
