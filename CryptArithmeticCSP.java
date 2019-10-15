import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class CryptArithmeticCSP extends CSP {

    public class cryptVariable extends Variable {

        public cryptVariable(String name) {
            this.name = name;
            this.value = -1;
            domain = new ArrayList<Integer>();
            for (int i = 0; i <= 9; i++) {
                domain.add(i);
            }
        }

        public boolean equals(Variable v2) {
            if (v2.value.equals(this.value) && !this.value.equals(-1)) return true;
            else return false;
        }

        public boolean unAssigned() {
            if ((int) this.value == -1) return true;
            else return false;
        }

        public void takeOnValue(Object value) {
            this.value = value;
        }

        public void removeValue() {
            this.value = -1;
        }

        public void printSolutionValue() {
            System.out.println(this.name + ": " + this.value);
        }
    }

    public class carryingVariable extends Variable {

        public carryingVariable() {
            this.value = -1;
            this.name = "meCarry";
            domain = new ArrayList();
            domain.add(0);
            domain.add(1);
        }

        public boolean unAssigned() {
            if ((int) this.value == -1) return true;
            return false;
        }

        public void takeOnValue(Object value) {
            this.value = value;
        }

        public void removeValue() {
            this.value = -1;
        }

        public void printSolutionValue() {

        }
    }

    public class resultConstraint extends Constraint {
        ArrayList<Variable> av1;
        ArrayList<Variable> av2;
        ArrayList<Variable> sum;

        public resultConstraint(ArrayList<Variable> av1, ArrayList<Variable> av2, ArrayList<Variable> sum) {
            this.av1 = av1;
            this.av2 = av2;
            this.sum = sum;
        }



        public int convertToInt(ArrayList<Variable> a) {
            int ans = 0;
            int mul = 1;
            for (Variable v : a) {
                if ((int) v.value < 0) return -1;
                ans += mul * (int) v.value;
                mul *= 10;
            }
            return ans;
        }

        public boolean satisfied() {
            int a = convertToInt(av1);
            int b = convertToInt(av2);
            int c = convertToInt(sum);
            if (a < 0 || b < 0 || c < 0) return true;
            if (a + b == c) return true;
            else return false;
        }
    }

    public class AllDiffConstraint extends Constraint {
        ArrayList<Variable> v;

        public AllDiffConstraint(ArrayList<Variable> v) {
            this.v = v;
        }

        public boolean satisfied() {
            boolean[] check = new boolean[20];
            for (int i = 0; i < 10; i++) check[i] = false;
            for (Variable curV : this.v) {
                if ((int) curV.value == -1) continue;
                if (check[(int) curV.value]) return false;
                check[(int) curV.value] = true;
            }
            return true;
        }
    }

    public class sumConstraint extends Constraint {
        carryingVariable c;
        Variable sum;
        carryingVariable nextC;

        public sumConstraint(Variable v1, Variable v2, carryingVariable c, Variable sum, carryingVariable nextC) {
            this.v1 = v1;
            this.v2 = v2;
            this.c = c;
            this.sum = sum;
            this.nextC = nextC;
        }

        public boolean satisfied() {
            if ((int) v1.value == -1 || (int) v2.value == -1 || (int) sum.value == -1 || (int) c.value == -1 || (int) nextC.value == -1)
                return true;
            if ((int) v1.value + (int) v2.value + (int) c.value == (int) sum.value + (int) nextC.value * 10)
                return true;
            else return false;
        }

        public void print() {
            System.out.println(v1.value + " " + v2.value + " " + c.value + " " + sum.value + " " + nextC.value);
        }
    }

    public CryptArithmeticCSP() {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a string of lowercase/upper case letters represent the first number:");
        String s1 = sc.nextLine();
        System.out.println("Enter 2nd string of lowercase/upper case letters represent the 2nd number:");
        String s2 = sc.nextLine();
        System.out.println("Enter 3rd string of lowercase/upper case letters represent the resulting number:");
        String sSum = sc.nextLine();
        System.out.println("Choose one the following model: ");
        System.out.println("1. The constraints in AIMA.");
        System.out.println("2. My simplified constraints");
        System.out.println("Enter 1 or 2:");
        int a = sc.nextInt();
        if (a == 1) {
            boolean[] check = new boolean[200];
            for (int i = 0; i < 200; i++) check[i] = false;
            carryingVariable[] carry = new carryingVariable[10];
            for (int i = 0; i < 9; i++) carry[i] = new carryingVariable();
            cryptVariable zero = new cryptVariable("0");
            int maxLength = 0;
            zero.value = 0;
            maxLength = Math.max(maxLength, Math.max(s1.length(), Math.max(s2.length(), sSum.length())));
            while (s1.length() < maxLength) {
                s1 = "0" + s1;
            }
            while (s2.length() < maxLength) {
                s2 = "0" + s2;
            }
            while (sSum.length() < maxLength) {
                sSum = "0" + sSum;
            }
            System.out.println(s1 + " " + s2 + " " + sSum);
            Variable lastcur1 = new Variable();
            Variable lastcur2 = new Variable();
            Variable lastcurSum = new Variable();
            ArrayList<Variable> tmpArray = new ArrayList<>();
            carry[s1.length()].value = 0;
            for (int i = s1.length() - 1; i >= 0; i--) {
                char c = s1.charAt(i);
                Variable cur1 = new Variable();
                if (c == '0') {
                    cur1 = zero;
                } else if (check[c - 'A']) {
                    for (Variable v : this.variables)
                        if (v.name.equals("" + c)) {
                            cur1 = v;
                            break;
                        }
                } else {
                    check[c - 'A'] = true;
                    Variable tmp = new cryptVariable(c + "");
                    cur1 = tmp;
                    tmpArray.add(cur1);
                    this.variables.add(tmp);
                }
                c = s2.charAt(i);
                Variable cur2 = new Variable();
                if (c == '0') {
                    cur2 = zero;
                } else if (check[c - 'A']) {
                    for (Variable v : this.variables)
                        if (v.name.equals("" + c)) {
                            cur2 = v;
                            break;
                        }
                } else {
                    check[c - 'A'] = true;
                    Variable tmp = new cryptVariable(c + "");
                    tmpArray.add(tmp);
                    cur2 = tmp;
                    this.variables.add(tmp);
                }
                this.variables.add(carry[i]);
                c = sSum.charAt(i);
                Variable curSum = new Variable();
                if (c == '0') {
                    curSum = zero;
                } else if (check[c - 'A']) {
                    for (Variable v : this.variables)
                        if (v.name.equals("" + c)) {
                            curSum = v;
                            break;
                        }
                } else {
                    check[c - 'A'] = true;
                    Variable tmp = new cryptVariable(c + "");
                    curSum = tmp;
                    tmpArray.add(tmp);
                    this.variables.add(tmp);
                }
                if (cur1 != zero) {
                    lastcur1 = cur1;
                }
                if (cur2 != zero) {
                    lastcur2 = cur2;
                }
                if (curSum != zero) {
                    lastcurSum = curSum;
                }
                this.constraints.add(new sumConstraint(cur1, cur2, carry[i + 1], curSum, carry[i]));
                System.out.println(cur1.name + " " + cur2.name + " " + carry[i + 1].name + " " + curSum.name + " " + carry[i].name);
            }
            constraints.add(new AllDiffConstraint(tmpArray));
            for (Variable v : this.variables) System.out.println(v.name);
            constraints.add(new NotEqualConstraint(lastcur1, zero));
            constraints.add(new NotEqualConstraint(lastcur2, zero));
            constraints.add(new NotEqualConstraint(lastcurSum, zero));
        } else {
            boolean[] check = new boolean[200];
            for (int i = 0; i < 200; i++) check[i] = false;
            ArrayList<Variable> av1 = new ArrayList<>();
            ArrayList<Variable> av2 = new ArrayList<>();
            ArrayList<Variable> sum = new ArrayList<>();
            for (int i = s1.length() - 1; i >= 0; i--) {
                char c = s1.charAt(i);
                if (check[c - 'A']) {
                    for (Variable v : this.variables)
                        if (v.name.equals("" + c)) {
                            av1.add(v);
                            break;
                        }
                } else {
                    check[c - 'A'] = true;
                    Variable tmp = new cryptVariable(c + "");
                    av1.add(tmp);
                    this.variables.add(tmp);
                }
            }
            for (int i = s2.length() - 1; i >= 0; i--) {
                char c = s2.charAt(i);
                if (check[c - 'A']) {
                    for (Variable v : this.variables)
                        if (v.name.equals("" + c)) {
                            av2.add(v);
                            break;
                        }
                } else {
                    check[c - 'A'] = true;
                    Variable tmp = new cryptVariable(c + "");
                    av2.add(tmp);
                    this.variables.add(tmp);
                }
            }
            for (int i = sSum.length() - 1; i >= 0; i--) {
                char c = sSum.charAt(i);
                if (check[c - 'A']) {
                    for (Variable v : this.variables)
                        if (v.name.equals("" + c)) {
                            sum.add(v);
                            break;
                        }
                } else {
                    check[c - 'A'] = true;
                    Variable tmp = new cryptVariable(c + "");
                    sum.add(tmp);
                    this.variables.add(tmp);
                }
            }
            constraints.add(new AllDiffConstraint(this.variables));
            cryptVariable zero = new cryptVariable("0");
            zero.value = 0;
            constraints.add(new NotEqualConstraint(av1.get(av1.size()-1), zero));
            constraints.add(new NotEqualConstraint(av2.get(av2.size()-1), zero));
            constraints.add(new NotEqualConstraint(sum.get(sum.size()-1), zero));
            constraints.add(new resultConstraint(av1,av2,sum));
        }
    }

    public static void main(String[] args) {
        System.out.println("Crypt Arithmetic Problem ( AIMA 6.1.3 )");
        CSP csp = new CryptArithmeticCSP();
        System.out.println("Backtracking search solver");
        System.out.println("It might take approximately 4 minutes to solve send + more = money example!");
        System.out.println("So give it some time :)");
        Solver solver = new Solver();
        long start = new Date().getTime();
        solver.solve(csp);
        long end = new Date().getTime();
        System.out.format("time: %.3f secs\n", (end - start) / 1000.0);
        for (CSP.Variable v : csp.variables) {
            if ((int) v.value == -1) {
                System.out.println(v.name);
                System.out.println("There is no solution!");
                return;
            }
            v.printSolutionValue();
        }
    }
}
