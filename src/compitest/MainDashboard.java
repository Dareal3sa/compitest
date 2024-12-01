package compitest;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.*;
import javax.swing.border.EmptyBorder;


public class MainDashboard extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea codeTextArea;
    private JTextArea outputTextArea;
    private JButton syntaxButton;
    private JButton semanticButton;
    private boolean lexicalPassed = false;
    private boolean syntaxPassed = false;
    
    private List<String> fileContents;
    private String contents;

    // Lexical Analysis components
    private final String[] keywords = {"public", "private", "class", "static", "void", "int", "double", "String"};
    private final String[] operators = {"+", "-", "*", "/", "=", "==", "!=", "<", ">", "<=", ">="};
    private final Pattern identifierPattern = Pattern.compile("[a-zA-Z][a-zA-Z0-9]*");
    private final Pattern numberPattern = Pattern.compile("\\d+(\\.\\d+)?");

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                MainDashboard frame = new MainDashboard();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public MainDashboard() {
    	setTitle("compiler");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 895, 626);
        fileContents = new ArrayList<>();
        initializeComponents();
    }

    private void initializeComponents() {
        contentPane = new JPanel();
        contentPane.setBackground(new Color(0, 102, 102));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        // Button Panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(0, 102, 102));
        buttonPanel.setBounds(0, 0, 213, 566);
        contentPane.add(buttonPanel);
        buttonPanel.setLayout(null);
        
        // Open Button
        JButton btnOpen = new JButton("OPEN");
        btnOpen.setBounds(10, 66, 193, 33);
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
        buttonPanel.add(btnOpen);
        
        // Analysis Buttons
        JButton btnLexical = new JButton("Lexical Analysis");
        btnLexical.setBounds(10, 165, 193, 33);
        btnLexical.addActionListener(e -> performLexicalAnalysis());
        buttonPanel.add(btnLexical);
        
        syntaxButton = new JButton("Syntax Analysis");
        syntaxButton.setBounds(10, 264, 193, 33);
        syntaxButton.addActionListener(e -> performSyntaxAnalysis());
        syntaxButton.setEnabled(false);
        buttonPanel.add(syntaxButton);
        
        semanticButton = new JButton("Semantic Analysis");
        semanticButton.setBounds(10, 363, 193, 33);
        semanticButton.addActionListener(e -> performSemanticAnalysis());
        semanticButton.setEnabled(false);
        buttonPanel.add(semanticButton);
        
        // clear button
        JButton btnClear = new JButton("CLEAR");
        btnClear.setBounds(10, 462, 193, 33);
        btnClear.addActionListener(e -> clearAll());
        buttonPanel.add(btnClear);
        
        // content panel para sa input text or variables na itetest
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(0, 102, 102));
        contentPanel.setBounds(212, 151, 611, 368);
        contentPane.add(contentPanel);
        contentPanel.setLayout(null);
        
        codeTextArea = new JTextArea();
        codeTextArea.setBounds(30, 8, 551, 398);
        codeTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane codeScrollPane = new JScrollPane(codeTextArea);
        codeScrollPane.setBounds(30, 8, 551, 398);
        contentPanel.add(codeScrollPane);
        
     // display panel para sa output
        JPanel displayPanel = new JPanel();
        displayPanel.setBackground(new Color(0, 102, 102));
        displayPanel.setBounds(212, 0, 611, 149);
        contentPane.add(displayPanel);
        displayPanel.setLayout(null);

        outputTextArea = new JTextArea();
        outputTextArea.setBounds(28, 14, 555, 72);
        outputTextArea.setEditable(false);
        outputTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputScrollPane.setBounds(28, 14, 555, 72);
        displayPanel.add(outputScrollPane);
        
        // exit button
        JButton btnExit = new JButton("Group");
        btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				group info = new group();
			      info.setVisible(true);
			      info.setLocationRelativeTo(null);
			      dispose();
			}
		
		});
        btnExit.setBounds(780, 553, 89, 23);
        contentPane.add(btnExit);
    }
    

 // Method para sa Lexical Analysis
    private void performLexicalAnalysis() {
        String code = codeTextArea.getText();
        
        // instance
        CompilerComponents analyzer = new CompilerComponents();
        
        // Perform lexical analysis
        String lexAnalysisResult = analyzer.lexicalAnalysis(code);
        
        StringBuilder output = new StringBuilder();
        output.append("Lexical Analysis Results:\n\n");
        output.append("Tokens: ").append(lexAnalysisResult).append("\n\n");
        
        outputTextArea.setText(output.toString());
        lexicalPassed = true; 
        syntaxButton.setEnabled(lexicalPassed);
        
        if (lexicalPassed) {
            outputTextArea.append("\nLexical Analysis Passed!");
        } else {
            outputTextArea.append("\nLexical Analysis Failed!");
        }
    }

 // Method para sa Syntax Analysis
    private void performSyntaxAnalysis() {
        String code = codeTextArea.getText();

        CompilerComponents analyzer = new CompilerComponents();

        // Calls and performs yung lexical analysis
        String lexAnalysisResult = analyzer.lexicalAnalysis(code);

     // Perform syntax analysis
        boolean syntaxValid = analyzer.syntaxAnalysis(lexAnalysisResult);

        StringBuilder output = new StringBuilder();
        output.append("Syntax Analysis Results:\n\n");
        output.append("Expected Pattern: <data_type> <identifier> <assignment_operator> <value> <delimiter>\n");
        output.append("Actual Pattern: ").append(lexAnalysisResult).append("\n\n");

        syntaxPassed = syntaxValid;
        semanticButton.setEnabled(syntaxPassed);

        outputTextArea.setText(output.toString());
        if (syntaxValid) {
            outputTextArea.append("\nSyntax Analysis Passed!");
        } else {
            outputTextArea.append("\nSyntax Analysis Failed!");
        }
    }


 // Method para sa Semantic Analysis
    private void performSemanticAnalysis() {
        String code = codeTextArea.getText();
        List<String> codeLines = new ArrayList<>(Arrays.asList(code.split("\n")));
        
        CompilerComponents analyzer = new CompilerComponents();
        
        // Perform semantic analysis
        String semAnalysisResult = analyzer.semanticAnalysis(codeLines);
        
        StringBuilder output = new StringBuilder();
        output.append("Semantic Analysis Results:\n\n");
        
        for (int i = 0; i < codeLines.size(); i++) {
            output.append("Line ").append(i + 1).append(": ");
            output.append(codeLines.get(i)).append("\n");
            output.append("Result: ").append(analyzer.semanticAnalysisSingleLine(codeLines.get(i))).append("\n\n");
        }
        
        output.append("Final Result: ").append(semAnalysisResult);
        
        outputTextArea.setText(output.toString());
    }


    private void clearAll() {
        codeTextArea.setText("");
        outputTextArea.setText("");
        lexicalPassed = false;
        syntaxPassed = false;
        syntaxButton.setEnabled(false);
        semanticButton.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Handle other actions if needed
    }
}