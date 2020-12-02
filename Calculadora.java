import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Calculadora {
    private List<String> userInput;
    private Set<String> recuperarList;
    private List<Integer> auxInt;
    private List<Character> auxOpe;
    private int total;
    private List<String> ope;

    public Calculadora() {
        this.userInput = new ArrayList<String>();
        this.recuperarList = new TreeSet<String>();
        this.auxInt = new ArrayList<>();
        this.auxOpe = new ArrayList<>();
        this.total = 0;

        this.ope = new ArrayList<>();

        // Operadores validos
        this.ope.add("/");
        this.ope.add("*");
        this.ope.add("+");
        this.ope.add("-");
        this.ope.add("=");
    }

    public boolean isParsable(String s) {
        if (s == null) {
            return false;
        }
        try {
            Integer.parseInt(s);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public String lastInput() {
        if (userInput.size() != 0) {
            return userInput.get(userInput.size() - 1);
        } else {
            return "";
        }
    }

    public void limpaArr() {
        this.userInput.clear();
        this.auxOpe.clear();
        this.auxInt.clear();
    }

    public void limpaTotal() {
        this.total = 0;
    }
    public Set<String> getRecuperarList() { return recuperarList; }
    public void inserir(String s) {
        this.userInput.add(s);
    }

    public void armazenar() {
        this.recuperarList.add(Integer.toString(total));
    }

    public void recuperarNum(String num) {
        if(this.isParsable(num)){
            this.userInput.add(num);
            this.recuperarList.remove(num);
        }
    }

    public void parseAll() {
        for(String s : userInput) {
            if(this.isParsable(s)) {
                this.auxInt.add(Integer.parseInt(s));
            } else {
                this.auxOpe.add(s.charAt(0));
            }
        }
        this.userInput.clear();
    }

    public void calcular() {
        int j=0;
        for(int i =0; i < this.auxInt.size()-1; i++) {
            if (total == 0) {
                this.total = ope(auxInt.get(i), auxInt.get(i + 1), auxOpe.get(i));
            } else if (total > 0) {
                this.total = ope(total, auxInt.get(i + 1), auxOpe.get(i));
            }
        }
        this.limpaArr();
    }

    public boolean opeValido(String s) {
        if (!this.isParsable(s)) {
            for(String o : ope) {
                if (s.equals(o)) {
                    return true;
                }
            }
        } else {
            return true;
        }
        return false;
    }

    public int ope(int x, int y, char op) throws ArithmeticException {
        if (op == '+') {
            return x + y;
        } else if (op == '-') {
            return x - y;
        } else if (op == '/') {
            return x / y;
        } else if (op == '*') {
            return x * y;
        }
        return -1;
    }

    public int getTotal() {
        this.auxInt.clear();
        this.auxOpe.clear();

        return total;
    }
}
