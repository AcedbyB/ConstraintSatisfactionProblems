import java.util.ArrayList;

public class Solver {

    boolean Done = false;

    public void solve(CSP csp) {
        if (Done) return;
        for(CSP.Constraint c: csp.constraints) {
            if(!c.satisfied()) {
                return;
            }
        }
        if (LegitCheck(csp.variables, csp.constraints)) {
            Done = true;
            return;
        }
        for (CSP.Variable v : csp.variables) {
            if(v.unAssigned()) {
                for(Object obj: v.domain) {
                    v.takeOnValue(obj);
                    solve(csp);
                    if(Done) return;
                    v.removeValue();
                }
            }
        }
    }

    public boolean LegitCheck(ArrayList<CSP.Variable> V, ArrayList<CSP.Constraint> C) {
        for (CSP.Variable v : V) {
            if (v.unAssigned()) return false;
        }
        for (CSP.Constraint c: C) {
            if(!c.satisfied()) return false;
        }
        return true;
    }
}
