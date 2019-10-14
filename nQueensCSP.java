import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class nQueensCSP extends CSP {

    static int numberOfQueens;

    public class queenVariable extends Variable {

        public queenVariable(String name) {
            this.name = name;
            this.value = 0;
            domain = new ArrayList<Integer>();
            for (int i = 1; i <= numberOfQueens; i++) {
                domain.add(i);
            }
        }

        public boolean unAssigned() {
            if ((int) this.value == 0) return true;
            else return false;
        }

        public void takeOnValue(Object position) {
            this.value = position;
        }

        public void removeValue() {
            this.value = 0;
        }

        public void printSolutionValue() {
            System.out.println(this.name + ": " + this.value);
        }
    }

    public class NotAttackingConstraint extends Constraint {
        int diff;

        public NotAttackingConstraint(Variable v1, Variable v2, int diff) {
            this.v1 = v1;
            this.v2 = v2;
            this.diff = diff;
        }

        public boolean satisfied() {
            if (( (v1.value != v2.value && (Math.abs((int)v1.value - (int)v2.value) != diff)) && !v1.unAssigned() && !v2.unAssigned()) || (v1.unAssigned() || v2.unAssigned()))
                return true;
            else return false;
        }
    }

    public nQueensCSP() {
        Variable[] Queen = new queenVariable[30];
        for (int i = 1; i <= numberOfQueens; i++) {
            Queen[i] = new queenVariable("Queen" + i);
            this.variables.add(Queen[i]);
            for (int j = 1; j < i; j++) this.constraints.add(new NotAttackingConstraint(Queen[i], Queen[j], Math.abs(i-j)));
        }
    }

    public static void main(String[] args) {
        System.out.println("N-Queens Problem (AIMA 3.2.1)");
        System.out.println("What is the number of queens you want?");
        Scanner sc = new Scanner(System.in);
        numberOfQueens = sc.nextInt();
        CSP csp = new nQueensCSP();
        System.out.println("Backtracking search solver");
        System.out.println("Queen i will always be at column i. The answer being proposed here is which row the Queen i should be!");
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
