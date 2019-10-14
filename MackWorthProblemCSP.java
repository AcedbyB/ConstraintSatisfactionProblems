import java.util.ArrayList;
import java.util.Date;

public class MackWorthProblemCSP extends CSP {

    public class numericVariable extends Variable {

        public numericVariable(String name) {
            this.name = name;
            this.value = 0;
            domain = new ArrayList<Integer>();
        }

        public boolean unAssigned() {
            if ((int) this.value == 0) return true;
            else return false;
        }

        public boolean lessThan(Variable v2, int diff) {
            if (((int)this.value + diff <= (int)v2.value) && (int)v2.value != 0) return true;
            else return false;
        }

        public void takeOnValue(Object number) {
            this.value = number;
        }

        public void removeValue() {
            this.value = 0;
        }

        public void printSolutionValue() {
            System.out.println(this.name + ": " + this.value);
        }
    }

    public MackWorthProblemCSP() {
        Variable[] x = new numericVariable[10];
        for (int i = 1; i <= 5; i++) {
            x[i] = new numericVariable("X" + i);
            this.variables.add(x[i]);
            x[i].domain.add(18);
            x[i].domain.add(27);
            if (i <= 2) x[i].domain.add(36);
        }
        this.constraints.add(new LessThanConstraint(x[1], x[3], 1));
        this.constraints.add(new LessThanConstraint(x[2], x[3], 1));
        this.constraints.add(new LessThanConstraint(x[4], x[3], 1));
        this.constraints.add(new LessThanConstraint(x[5], x[3], 1));
        this.constraints.add(new LessThanConstraint(x[4], x[5], 1));
        if (!runAC3()) {
            System.out.println("There is no solution according to AC3");
        }
    }

    public static void main(String[] args) {
        System.out.println("MackWorth AC-3 Problem");
        long start = new Date().getTime();
        CSP csp = new MackWorthProblemCSP();
        long end = new Date().getTime();
        System.out.format("time: %.3f secs\n", (end - start) / 1000.0);
        for (CSP.Variable v : csp.variables) {
            v.printSolutionValue();
        }
    }
}
