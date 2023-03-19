package com.example.calculator.controller;

import com.example.calculator.model.Operation;
import com.example.calculator.service.CalculatorService;
import com.example.calculator.service.antlr4.CalculatorLexer;
import com.example.calculator.service.antlr4.CalculatorParser;
import jakarta.annotation.PostConstruct;
import java.util.Stack;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:80")
@RestController
public class CalculatorController {
	private static final Logger logger = LoggerFactory.getLogger(CalculatorController.class);

	Stack<Operation> undoStack; //存储已经执行过的运算
    Stack<Operation> redoStack; //存储撤销过的运算

	@PostConstruct
	public void init() {
		undoStack = new Stack<>();
        redoStack = new Stack<>();
	}

	//执行一个新的运算，并把它压入undo栈，清空redo栈
	@PostMapping("/calc")
    public Operation calc(@RequestBody Operation op) {
		doCalc(op);  //计算
		undoStack.push(op);
		redoStack.clear();
		logger.info("执行了: " + op);
		return op;
    }

	//恢复上一次撤销的运算，并把它压入undo栈
	@GetMapping("/redo")
    public Operation redo() {
		Operation op = new Operation();
		if (!redoStack.isEmpty()) { //如果redo栈不为空
			op = redoStack.pop(); //弹出redo栈顶元素
			undoStack.push(op); //压入undo栈顶元素
			doCalc(op);  //重新计算
			logger.info("恢复了: " + op);
		} else { //如果redo栈为空
			op.setRespCode("501");
			op.setErrMsg("没有可恢复的操作");
		}
		return op;
    }

	//撤销上一次的运算，并把它压入redo栈
	@GetMapping("/undo")
    public Operation undo() {
		Operation op = new Operation();;
		if (!undoStack.isEmpty()) { //如果undo栈不为空
			op = undoStack.pop(); //弹出undo栈顶元素
			redoStack.push(op); //压入redo栈顶元素
			doCalc(op);  //重新计算
			logger.info("撤销了: " + op);
		} else { //如果undo栈为空
			op.setRespCode("502");
			op.setErrMsg("没有可撤销的操作");
		}
		return op;
    }

	//执行运算并返回结果
	public void doCalc(Operation op) {
		try{
			String resultStr = "";
			CalculatorLexer lexer = new CalculatorLexer(CharStreams.fromString(op.getExpr()));
			CalculatorParser parser = new CalculatorParser(new CommonTokenStream(lexer));
			Float resultFloat = new CalculatorService().visit(parser.expr());
			if (resultFloat == Math.floor(resultFloat)) { //如果是整数，不返回小数点后的数字
				resultStr = String.format("%.0f", resultFloat);
			} else {
				resultStr = String.format("%s", resultFloat);
			}
			op.setResult(resultStr);
			op.setRespCode("200");
			logger.info("执行了: " + op);
		} catch(Exception exp){
			op.setRespCode("500");
			op.setErrMsg("计算公式错误："+ op.getExpr());
		}
	}

	
}
