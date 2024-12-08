package compitest;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class Dashboard extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea codeTextArea;
    private JTextArea outputTextArea;
    private JButton btnSyntax;
    private JButton btnSemantic;
    private boolean lexicalPassed = false;
    private boolean syntaxPassed = false;
    

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Dashboard frame = new Dashboard();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public Dashboard() {
    	setTitle("compiler");
    	setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1280, 960);
        mainContentsPane();
    }

    private void mainContentsPane() {
        contentPane = new JPanel();
        contentPane.setBackground(new Color(255, 235, 205));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // button panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBounds(0, 0, 250, 900);
        buttonPanel.setBackground(new Color(255, 235, 205));
        contentPane.add(buttonPanel);
        
        // open file button
        JButton btnOpen = new JButton("OPEN");
        btnOpen.setBounds(28, 116, 193, 40);
        btnOpen.setBackground(new Color(255, 228, 196));
        btnOpen.setForeground(new Color(160, 82, 45));
        btnOpen.setFont(new Font("Georgia", Font.BOLD, 20));
        btnOpen.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Text & Java Files", "txt", "java");
                fileChooser.setFileFilter(filter);
                
                int returnVal = fileChooser.showOpenDialog(null);
                
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        StringBuilder content = new StringBuilder();
                        String line;
                        
                        while ((line = reader.readLine()) != null) {
                            content.append(line).append("\n");
                        }
                        reader.close();
                        
                        codeTextArea.setText(content.toString());
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, 
                            "Error reading file: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        buttonPanel.setLayout(null);
        buttonPanel.add(btnOpen);
        
        // aalysis buttons
        JButton btnLexical = new JButton("Lexical Analysis");
        btnLexical.setBounds(28, 272, 193, 40);
        btnLexical.setForeground(new Color(160, 82, 45));
        btnLexical.setBackground(new Color(255, 228, 196));
        btnLexical.setFont(new Font("Georgia", Font.BOLD, 17));
        btnLexical.addActionListener(e -> performLexicalAnalysis());
        buttonPanel.add(btnLexical);
        
        btnSyntax = new JButton("Syntax Analysis");
        btnSyntax.setBounds(28, 428, 193, 40);
        btnSyntax.setBackground(new Color(255, 228, 196));
        btnSyntax.setForeground(new Color(160, 82, 45));
        btnSyntax.setFont(new Font("Georgia", Font.BOLD, 17));
        btnSyntax.addActionListener(e -> performSyntaxAnalysis());
        btnSyntax.setEnabled(false);
        buttonPanel.add(btnSyntax);
        
        btnSemantic = new JButton("Semantic Analysis");
        btnSemantic.setBounds(28, 584, 193, 40);
        btnSemantic.setBackground(new Color(255, 228, 196));
        btnSemantic.setForeground(new Color(160, 82, 45));
        btnSemantic.setFont(new Font("Georgia", Font.BOLD, 15));
        btnSemantic.addActionListener(e -> performSemanticAnalysis());
        btnSemantic.setEnabled(false);
        buttonPanel.add(btnSemantic);
        
        // clear button
        JButton btnClear = new JButton("CLEAR");
        btnClear.setBounds(28, 740, 193, 40);
        btnClear.setBackground(new Color(255, 228, 196));
        btnClear.setForeground(new Color(160, 82, 45));
        btnClear.setFont(new Font("Georgia", Font.BOLD, 17));
        btnClear.setBorder(new LineBorder(new Color(0, 0, 0)));
        btnClear.setBorder(BorderFactory.createEtchedBorder());
        btnClear.addActionListener(e -> clearAll());
        buttonPanel.add(btnClear);
        
        // content panel para sa input text or variables na itetest
        JPanel contentPanel = new JPanel();
        contentPanel.setBounds(255, 220, 950, 630);
        contentPanel.setBackground(new Color(255, 235, 205));
        contentPane.add(contentPanel);
        contentPanel.setLayout(null);
        
        codeTextArea = new JTextArea();
        codeTextArea.setBackground(new Color(255, 228, 196));
        codeTextArea.setBounds(30, 28, 905, 595);
        codeTextArea.setFont(new Font("Monospaced", Font.PLAIN, 20));
        JScrollPane codeScrollPane = new JScrollPane(codeTextArea);
        codeScrollPane.setBounds(30, 28, 905, 595);
        contentPanel.add(codeScrollPane);
        
        // display panel para sa output
        JPanel displayPanel = new JPanel();
        displayPanel.setBounds(255, 0, 950, 215);
        displayPanel.setBackground(new Color(255, 235, 205));
        contentPane.add(displayPanel);
        displayPanel.setLayout(null);

        outputTextArea = new JTextArea();
        outputTextArea.setBackground(new Color(255, 228, 196));
        outputTextArea.setEditable(false);
        outputTextArea.setBounds(28, 14, 905, 190);
        outputTextArea.setFont(new Font("Monospaced", Font.BOLD, 18));
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputScrollPane.setBounds(28, 14, 905, 190);
        displayPanel.add(outputScrollPane);
        
        JButton btnReturn = new JButton("Back");
        btnReturn.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Main entry = new Main();
				      entry.setVisible(true);
				      entry.setLocationRelativeTo(null);
				      dispose();
				}
        });
        btnReturn.setFont(new Font("Impact", Font.PLAIN, 9));
        btnReturn.setForeground(new Color(255, 239, 213));
        btnReturn.setBackground(new Color(0, 0, 0));
        btnReturn.setBounds(1135, 861, 60, 50);
        contentPane.add(btnReturn);
    }
    
    // method para sa Lexical Analysis
    private void performLexicalAnalysis() {
        String code = codeTextArea.getText();
        
        // instance
        Components analyzer = new Components();
        
        // perform lexical analysis
        String lexAnalysisResult = analyzer.lexicalAnalysis(code);
        
        StringBuilder output = new StringBuilder();
        output.append("Lexical Analysis Results:\n\n");
        output.append("Tokens: ").append(lexAnalysisResult).append("\n");
        
        outputTextArea.setText(output.toString());
        lexicalPassed = true; 
        btnSyntax.setEnabled(lexicalPassed);
        
        if (lexicalPassed) {
            outputTextArea.append("\nLexical Analysis Passed!");
        } else {
            outputTextArea.append("\nLexical Analysis Failed!");
        }
    }

    // Method para sa Syntax Analysis
    private void performSyntaxAnalysis() {
        String code = codeTextArea.getText();

        Components analyzer = new Components();

        // calls and performs yung lexical analysis
        String lexAnalysisResult = analyzer.lexicalAnalysis(code);

        // perform syntax analysis
        boolean syntaxValid = analyzer.syntaxAnalysis(lexAnalysisResult);

        StringBuilder output = new StringBuilder();
        output.append("Syntax Analysis Results:\n\n");
        output.append("Expected Pattern: <data_type> <identifier> <assignment_operator> <value> <delimiter>\n");
        output.append("Actual Pattern: ").append(lexAnalysisResult).append("\n\n");

        syntaxPassed = syntaxValid;
        btnSemantic.setEnabled(syntaxPassed);
        btnSyntax.setEnabled(false);

        outputTextArea.setText(output.toString());
        if (syntaxValid) {
            outputTextArea.append("\nSyntax Analysis Passed!");
        } else {
            outputTextArea.append("\nSyntax Analysis Failed!");
        }
    }

    // method para sa Semantic Analysis
    private void performSemanticAnalysis() {
        String code = codeTextArea.getText();
        List<String> codeLines = new ArrayList<>(Arrays.asList(code.split("\n")));

        Components analyzer = new Components();

        // perform semantic analysis
        String semAnalysisResult = analyzer.semanticAnalysis(codeLines);

        outputTextArea.setText("Semantic Analysis Results:\n\n" + semAnalysisResult);
        btnSemantic.setEnabled(false);
    }

    private void clearAll() {
        codeTextArea.setText("");
        outputTextArea.setText("");
        lexicalPassed = false;
        syntaxPassed = false;
        btnSyntax.setEnabled(false);
        btnSemantic.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // handle other actions if needed
    }
}