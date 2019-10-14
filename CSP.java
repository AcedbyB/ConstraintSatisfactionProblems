import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class CSP {

    public ArrayList<Variable> variables = new ArrayList<>();
    public ArrayList<Constraint> constraints = new ArrayList<>();

    public boolean runAC3() {
        class Arc {
            Constraint c;
            boolean second;

            public Arc(Constraint c, boolean second) {
                this.c = c;
                this.second = second;
            }
        }

        Queue<Arc> queue = new LinkedList<>();
        for (Constraint c : constraints) {
            queue.add(new Arc(c, false));
        }
        while (!queue.isEmpty()) {
            Arc curArc = queue.remove();
            Variable v1;
            Variable v2;
            Constraint curConstraint = curArc.c;
            if (!curArc.second) {
                v1 = curArc.c.v1;
                v2 = curArc.c.v2;
            } else {
                v1 = curArc.c.v2;
                v2 = curArc.c.v1;
            }
            ArrayList<Object> valuesToRemove = new ArrayList<>();
            for (Object value1 : v1.domain) {
                boolean ok = false;
                for (Object value2 : v2.domain) {
                    v1.takeOnValue(value1);
                    v2.takeOnValue(value2);

                    if (curConstraint.satisfied()) {
                        ok = true;
                        break;
                    }
                }
                if (!ok) {
                    valuesToRemove.add(value1);
                }
            }
            if (valuesToRemove.size() > 0) {
                if (valuesToRemove.size() == v1.domain.size()) {
                    for (Variable v : variables) v.removeValue();
                    return false;
                }
                v1.domain.removeAll(valuesToRemove);
                for (Constraint c : constraints) {
                    if (c.v1 == v1) {
                        queue.add(new Arc(c, true));
                    } else if (c.v2 == v1) {
                        queue.add(new Arc(c, false));
                    }
                }
            }
        }
        for (Variable v : variables) v.removeValue();
        return true;
    }

    public abstract class Constraint {
        public Variable v1;
        public Variable v2;

        public abstract boolean satisfied();
    }

    public class NotEqualConstraint extends Constraint {

        public NotEqualConstraint(Variable v1, Variable v2) {
            this.v1 = v1;
            this.v2 = v2;
        }

        public boolean satisfied() {
            if ((!v1.equals(v2) && !v1.unAssigned() && !v2.unAssigned()) || (v1.unAssigned() || v2.unAssigned()))
                return true;
            else return false;
        }
    }

    public class LessThanConstraint extends Constraint {
        int diff;

        public LessThanConstraint(Variable v1, Variable v2, int diff) {
            this.v1 = v1;
            this.v2 = v2;
            this.diff = diff;
        }

        public boolean satisfied() {
            if ((v1.lessThan(v2, diff) && !v1.unAssigned() && !v2.unAssigned()) || (v1.unAssigned() || v2.unAssigned()))
                return true;
            else return false;
        }
    }

    public class DisjunctiveConstraint extends Constraint {
        Constraint c1;
        Constraint c2;

        public DisjunctiveConstraint(Constraint c1, Constraint c2) {
            this.c1 = c1;
            this.c2 = c2;
        }

        public boolean satisfied() {
            if (c1.satisfied() || c2.satisfied()) return true;
            else return false;
        }
    }

    public class Variable {
        String name;
        Object value;
        ArrayList domain;

        public boolean equals(Variable v2) {
            return true;
        }

        public boolean unAssigned() {
            return false;
        }

        public void takeOnValue(Object obj) {
        }

        public void removeValue() {
        }

        public void printSolutionValue() {
        }

        public boolean lessThan(Variable v2, int timeConsume) {
            return true;
        }
    }

}
