import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class App extends JFrame {

    private JPanel panelMain;
    private JTextArea resultArea;
    private JTextField userInput;
    private JButton aBtn;
    private JButton rBtn;
    private JScrollPane sp;
    private String recr;
    private String[] operadores = {"/", "+", "-", "*"};

    public App() throws ClassNotFoundException, UnsupportedLookAndFeelException, InstantiationException, IllegalAccessException {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        add(panelMain);
        setTitle("Calculadora");
        setSize(300, 300);
        Calculadora calc = new Calculadora();
        UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());

        userInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Testa se o usuario digitou um operador depois de um operador
                    for (String s : operadores) {
                        if (calc.lastInput().equals(s) && userInput.getText().equals(s)) {
                            throw new EntradaException("Entre com um numero e depois o operando Ex: (1 + 2) e não (1 + +)");
                        }
                    }
                    // Testa se o usuario digitou um numero apos um numero
                    if (calc.isParsable(userInput.getText()) && calc.isParsable(calc.lastInput())) {
                        throw new EntradaException("Entre com um numero e depois o operando Ex: (1 + 2) e não (1 + +)");
                    }
                    // Testa se o usuario digitou um operador antes de um numero
                    if (calc.lastInput().equals("") && !calc.isParsable(userInput.getText())) {
                        throw new EntradaException("Entre com um numero e depois o operando Ex: (1 + 2) e não (1 + +)");
                    }

                    // Verifica se o operador que o usuario informou é valido
                    if (!calc.opeValido(userInput.getText())) {
                        userInput.setText("");
                        throw new EntradaException("Os seguintes operadores são validos: /, +, -, *");
                    }
                    resultArea.append(userInput.getText() + "\n");
                    calc.inserir(userInput.getText());
                    resultArea.setCaretPosition(resultArea.getText().length());
                } catch (EntradaException eE) {
                    JOptionPane.showMessageDialog(null,eE.getMessage(), "ERRO", JOptionPane.ERROR_MESSAGE);
                }
                if(userInput.getText().equals("=")) {
                    calc.limpaTotal();
                    calc.parseAll();
                    try {
                        calc.calcular();
                        resultArea.append(calc.getTotal() + "\n\n");
                        calc.limpaArr();
                    } catch (ArithmeticException aE) {
                        JOptionPane.showMessageDialog(null,"Possivel Divisão por zero", "ERRO", JOptionPane.ERROR_MESSAGE);
                        resultArea.append("\n");
                        calc.limpaArr();
                        calc.limpaTotal();
                    }
                }
                userInput.setText("");
            }
        });
        rBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Set<String> recSet = calc.getRecuperarList();
                if (recSet.size() == 0) {
                    JOptionPane.showMessageDialog(null,"Não há nada para recuperar", "Recuperar", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Object[] recList = new Object[recSet.size()];
                recSet.toArray(recList);

                recr = (String) JOptionPane.showInputDialog(null,"Recuperar", "Escolha um valor para recuperar", JOptionPane.QUESTION_MESSAGE, null, recList, recSet.iterator().next());
                if(recr != null) {
                    calc.recuperarNum(recr);
                    resultArea.append(recr+"\n");
                }
            }
        });
        aBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (calc.getTotal() == 0) {
                    JOptionPane.showMessageDialog(null,"Não há nada para armazenar", "AVISO", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                calc.armazenar();
            }
        });
    }
}
