import java.util.ArrayList;
import java.util.Date;

public class JobShopCSP extends CSP {

    public class jobVariable extends Variable {

        public jobVariable(String name) {
            this.name = name;
            this.value = 0;
            domain = new ArrayList<Integer>();
            for (int i = 1; i <= 27; i++) {
                domain.add(i);
            }
        }

        public boolean lessThan(Variable v2, int timeConsume) {
            if (((int)this.value + timeConsume <= (int)v2.value) && (int)v2.value != 0) return true;
            else return false;
        }

        public boolean unAssigned() {
            if ((int)this.value == 0) return true;
            else return false;
        }

        public void takeOnValue(Object startTime) {
            this.value = startTime;
        }

        public void removeValue() {
            this.value = 0;
        }

        public void printSolutionValue() {
            System.out.println(this.name + ": " + this.value);
        }
    }

    public JobShopCSP() {
        Variable AxelF = new jobVariable("AxelF");
        Variable AxelB = new jobVariable("AxelB");
        Variable WheelRF = new jobVariable("WheelRF");
        Variable WheelLF = new jobVariable("WheelLF");
        Variable WheelRB = new jobVariable("WheelRB");
        Variable WheelLB = new jobVariable("WheelLB");
        Variable NutsRF = new jobVariable("NutsRF");
        Variable NutsLF = new jobVariable("NutsLF");
        Variable NutsRB = new jobVariable("NutsRB");
        Variable NutsLB = new jobVariable("NutsLB");
        Variable CapRF = new jobVariable("CapRF");
        Variable CapLF = new jobVariable("CapLF");
        Variable CapRB = new jobVariable("CapRB");
        Variable CapLB = new jobVariable("CapLB");
        Variable Inspect = new jobVariable("Inspect");
        this.variables.add(AxelF);
        this.variables.add(AxelB);
        this.variables.add(WheelRF);
        this.variables.add(WheelLF);
        this.variables.add(WheelLB);
        this.variables.add(WheelRB);
        this.variables.add(NutsRF);
        this.variables.add(NutsLF);
        this.variables.add(NutsRB);
        this.variables.add(NutsLB);
        this.variables.add(CapRF);
        this.variables.add(CapLF);
        this.variables.add(CapRB);
        this.variables.add(CapLB);
        this.variables.add(Inspect);
        this.constraints.add(new LessThanConstraint(AxelF, WheelRF, 10));
        this.constraints.add(new LessThanConstraint(AxelF, WheelLF, 10));
        this.constraints.add(new LessThanConstraint(AxelB, WheelLB, 10));
        this.constraints.add(new LessThanConstraint(AxelB, WheelRB, 10));
        this.constraints.add(new LessThanConstraint(WheelRF, NutsRF, 1));
        this.constraints.add(new LessThanConstraint(WheelLF, NutsLF, 1));
        this.constraints.add(new LessThanConstraint(WheelRB, NutsRB, 1));
        this.constraints.add(new LessThanConstraint(WheelLB, NutsLB, 1));
        this.constraints.add(new LessThanConstraint(NutsRF, CapRF, 2));
        this.constraints.add(new LessThanConstraint(NutsLF, CapLF, 2));
        this.constraints.add(new LessThanConstraint(NutsRB, CapRB, 2));
        this.constraints.add(new LessThanConstraint(NutsLB, CapLB, 2));
        this.constraints.add(new LessThanConstraint(CapRF, Inspect, 1));
        this.constraints.add(new LessThanConstraint(CapLF, Inspect, 1));
        this.constraints.add(new LessThanConstraint(CapRB, Inspect, 1));
        this.constraints.add(new LessThanConstraint(CapLB, Inspect, 1));
        runAC3();
        this.constraints.add(new DisjunctiveConstraint(new LessThanConstraint(AxelF, AxelB, 10), new LessThanConstraint(AxelB, AxelF, 10)));
    }

    public static void main(String[] args) {
        System.out.println("Job-Shop Scheduling Problem (AIMA 6.1.2)");
        CSP csp = new JobShopCSP();
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
