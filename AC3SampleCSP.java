import java.util.ArrayList;
import java.util.Date;

public class AC3SampleCSP extends CSP {

    public class numericVariable extends Variable {

        public numericVariable(String name) {
            this.name = name;
            this.value = 0;
            domain = new ArrayList<Integer>();
            for (int i = 1; i <= 1000; i++) {
                domain.add(i);
            }
        }

        public boolean unAssigned() {
            if ((int)this.value == 0) return true;
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

    public class SquareEqualConstraint extends Constraint {

        public SquareEqualConstraint(Variable v1, Variable v2) {
            this.v1 = v1;
            this.v2 = v2;
        }

        public boolean satisfied() {
            if (( ((int)v1.value*(int)v1.value == (int)v2.value) && !v1.unAssigned() && !v2.unAssigned()) || (v1.unAssigned() || v2.unAssigned()))
                return true;
            else return false;
        }
    }

    public AC3SampleCSP() {
        Variable x = new numericVariable("X");
        Variable y = new numericVariable("Y");
        this.variables.add(y);
        this.variables.add(x);
        this.constraints.add(new SquareEqualConstraint(x, y));
        System.out.println("Domain size before running AC-3:");
        System.out.println("X's domain: " + x.domain.size());
        System.out.println("Y's domain: " + y.domain.size());
        runAC3();
        System.out.println("Domain size after running AC-3:");
        System.out.println("X's domain: " + x.domain.size());
        System.out.println("Y's domain: " + y.domain.size());
    }

    public static void main(String[] args) {
        System.out.println("AC-3 Sample Problem");
        CSP csp = new AC3SampleCSP();
        System.out.println("Backtracking search solver");
        Solver solver = new Solver();
        long start = new Date().getTime();
        solver.solve(csp);
        long end = new Date().getTime();
        System.out.format("time: %.3f secs\n", (end - start) / 1000.0);
        for (CSP.Variable v : csp.variables) {
            v.printSolutionValue();
        }
    }
}
