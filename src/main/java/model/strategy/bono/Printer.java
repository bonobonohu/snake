package model.strategy.bono;

public class Printer {
    private boolean doPrint = true;

    public Printer(boolean doPrint) {
        this.doPrint = doPrint;
    }

    public void print(String text) {
        if (doPrint) {
            System.out.println(text);
        }
    }

    public void print(String text, boolean forcePrint) {
        if (forcePrint) {
            System.out.println(text);
        }
    }

    public boolean getDoPrint() {
        return doPrint;
    }

    public void setDoPrint(boolean doPrint) {
        this.doPrint = doPrint;
    }
}
