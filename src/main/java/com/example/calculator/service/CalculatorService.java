package com.example.calculator.service;

import com.example.calculator.service.antlr4.*;

//继承自 CalculatorBaseVisitor 类，用于处理浮点数计算
public class CalculatorService extends CalculatorBaseVisitor<Float> {
    @Override
    public Float visitParenExpr(CalculatorParser.ParenExprContext ctx) {
        return visit(ctx.expr());
    }
 
    // 重写 visitMultOrDiv 方法，用于处理乘法和除法运算
    @Override
    public Float visitMultOrDiv(CalculatorParser.MultOrDivContext ctx) {
        Float obj0 = ctx.expr(0).accept(this);
        Float obj1 = ctx.expr(1).accept(this);
 
        // 判断运算符是乘法还是除法，并执行相应的操作
        if ("*".equals(ctx.getChild(1).getText())) {
            return obj0 *   obj1;
        } else if ("/".equals(ctx.getChild(1).getText())) {
            return obj0 / obj1;
        }
        return 0f;
    }
 
    // 重写 visitAddOrSubstract 方法，用于处理加法和减法运算
    @Override
    public Float visitAddOrSubstract(CalculatorParser.AddOrSubstractContext ctx) {
        Float obj0 = ctx.expr(0).accept(this);
        Float obj1 = ctx.expr(1).accept(this);
 
        // 判断运算符是加法还是减法，并执行相应的操作
        if ("+".equals(ctx.getChild(1).getText())) {
            return  obj0 +   obj1;
        } else if ("-".equals(ctx.getChild(1).getText())) {
            return   obj0 -  obj1;
        }
        return 0f;
    }
 
    // 重写 visitFloat 方法，用于解析浮点数
    @Override
    public Float visitFloat(CalculatorParser.FloatContext ctx) {
        return Float.parseFloat(ctx.getText());
    }
}
