import java.util.ArrayList;
import java.util.Date;

public class AustraliaMapCSP extends CSP {

    public class stateVariable extends Variable {

        public stateVariable(String name, String color) {
            this.name = name;
            value = color;
            domain = new ArrayList<String>();
            domain.add("green");
            domain.add("red");
            domain.add("blue");
        }

        @Override
        public boolean equals(Variable v2) {
            if (v2.value.equals(this.value) && !this.value.equals("")) return true;

            else return false;
        }

        public boolean unAssigned() {
            if (this.value.equals("")) return true;
            else return false;
        }

        public void takeOnValue(Object color) {
            this.value = color;
        }

        public void removeValue() {
            this.value = "";
        }

        public void printSolutionValue() {
            System.out.println(this.name + ": " + this.value);
        }
    }

    public AustraliaMapCSP() {
        Variable WA = new stateVariable("WA", "");
        Variable NT = new stateVariable("NT", "");
        Variable Q = new stateVariable("Q", "");
        Variable NSW = new stateVariable("NSW", "");
        Variable V = new stateVariable("V", "");
        Variable SA = new stateVariable("SA", "");
        Variable T = new stateVariable("T", "");
        this.variables.add(WA);
        this.variables.add(NT);
        this.variables.add(Q);
        this.variables.add(NSW);
        this.variables.add(V);
        this.variables.add(SA);
        this.variables.add(T);
        this.constraints.add(new NotEqualConstraint(SA, WA));
        this.constraints.add(new NotEqualConstraint(SA, NT));
        this.constraints.add(new NotEqualConstraint(SA, Q));
        this.constraints.add(new NotEqualConstraint(SA, NSW));
        this.constraints.add(new NotEqualConstraint(SA, V));
        this.constraints.add(new NotEqualConstraint(WA, NT));
        this.constraints.add(new NotEqualConstraint(NT, Q));
        this.constraints.add(new NotEqualConstraint(Q, NSW));
        this.constraints.add(new NotEqualConstraint(NSW, V));
//        Constraint test = new NotEqualConstraint(SA,WA);
//        System.out.println(test.v1);
    }

    public static void main(String[] args) {
        System.out.println("Australia Map Coloring Problem (AIMA 6.1.1)");
        CSP csp = new AustraliaMapCSP();
        System.out.println("Backtracking search solver");
        Solver solver = new Solver();
        long start = new Date().getTime();
        solver.solve(csp);
        long end = new Date().getTime();
        System.out.format("time: %.3f secs\n", (end-start)/1000.0);
        for(Variable v: csp.variables) {
            v.printSolutionValue();
        }
    }
}
