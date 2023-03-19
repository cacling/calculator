package com.example.calculator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.calculator.controller.CalculatorController;
import com.example.calculator.model.Operation;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalculatorControllerTests {

	@InjectMocks
    private CalculatorController calculatorController;

	@BeforeEach
	void init_mocks() {
		MockitoAnnotations.openMocks(this);
		calculatorController.init();
	}

	@Test
    public void testCalc() {
        Operation op = new Operation();
        op.setExpr("(3.2+0.8)*3+(3-1)");
        calculatorController.calc(op);
        assertEquals("14", op.getResult());
    }

	@Test
    public void testCalcFail() {
        Operation op = new Operation();
        op.setExpr("3+*");
        calculatorController.calc(op);
		assertEquals("500", op.getRespCode());
		assertTrue(op.getErrMsg().contains("计算公式错误"));
	}

	@Test
    public void testUndo() {
        Operation op1 = new Operation();
        op1.setExpr("1+2");
        calculatorController.calc(op1);

        Operation op2 = new Operation();
        op2.setExpr("3-1");
        calculatorController.calc(op2);

        Operation undoOp = calculatorController.undo();
        assertEquals("2", undoOp.getResult());
        assertEquals("3-1", undoOp.getExpr());
    }

    @Test
    public void testRedo() {
        Operation op1 = new Operation();
        op1.setExpr("1+2");
        calculatorController.calc(op1);

        Operation op2 = new Operation();
        op2.setExpr("3-1");
        calculatorController.calc(op2);

        calculatorController.undo();

        Operation redoOp = calculatorController.redo();
        assertEquals("2", redoOp.getResult());
        assertEquals("3-1", redoOp.getExpr());
    }

}
