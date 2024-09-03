import java.util.Stack;

interface Command {
    void execute();
    void undo();
}

class Calculator {
    private double currentValue = 0;

    public double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(double value) {
        this.currentValue = value;
    }

    public void add(double value) {
        currentValue += value;
    }

    public void subtract(double value) {
        currentValue -= value;
    }

    public void multiply(double value) {
        currentValue *= value;
    }

    public void divide(double value) {
        if (value != 0) {
            currentValue /= value;
        } else {
            throw new ArithmeticException("Division by zero");
        }
    }
}

class AddCommand implements Command {
    private Calculator calculator;
    private double value;

    public AddCommand(Calculator calculator, double value) {
        this.calculator = calculator;
        this.value = value;
    }

    @Override
    public void execute() {
        calculator.add(value);
    }

    @Override
    public void undo() {
        calculator.subtract(value);
    }
}

class SubtractCommand implements Command {
    private Calculator calculator;
    private double value;

    public SubtractCommand(Calculator calculator, double value) {
        this.calculator = calculator;
        this.value = value;
    }

    @Override
    public void execute() {
        calculator.subtract(value);
    }

    @Override
    public void undo() {
        calculator.add(value);
    }
}

class MultiplyCommand implements Command {
    private Calculator calculator;
    private double value;

    public MultiplyCommand(Calculator calculator, double value) {
        this.calculator = calculator;
        this.value = value;
    }

    @Override
    public void execute() {
        calculator.multiply(value);
    }

    @Override
    public void undo() {
        calculator.divide(value);
    }
}

class DivideCommand implements Command {
    private Calculator calculator;
    private double value;

    public DivideCommand(Calculator calculator, double value) {
        this.calculator = calculator;
        this.value = value;
    }

    @Override
    public void execute() {
        calculator.divide(value);
    }

    @Override
    public void undo() {
        calculator.multiply(value);
    }
}

public class CalculatorApp {
    private Stack<Command> undoStack = new Stack<>();
    private Stack<Command> redoStack = new Stack<>();
    private Calculator calculator = new Calculator();

    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.undo();
            redoStack.push(command);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }

    public double getCurrentValue() {
        return calculator.getCurrentValue();
    }

    public static void main(String[] args) {
        CalculatorApp app = new CalculatorApp();
        app.executeCommand(new AddCommand(app.calculator, 10));
        System.out.println(app.getCurrentValue()); // 10.0
        app.executeCommand(new SubtractCommand(app.calculator, 5));
        System.out.println(app.getCurrentValue()); // 5.0
        app.undo();
        System.out.println(app.getCurrentValue()); // 10.0
        app.redo();
        System.out.println(app.getCurrentValue()); // 5.0
    }
}








