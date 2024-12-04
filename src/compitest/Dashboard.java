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
    private JButton syntaxButton;
    private JButton semanticButton;
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
        setBounds(100, 100, 895, 626);
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
        buttonPanel.setBackground(new Color(255, 235, 205));
        buttonPanel.setBounds(0, 0, 213, 566);
        contentPane.add(buttonPanel);
        buttonPanel.setLayout(null);
        
        // open file button
        JButton btnOpen = new JButton("OPEN");
        btnOpen.setBackground(new Color(255, 228, 196));
        btnOpen.setForeground(new Color(160, 82, 45));
        btnOpen.setFont(new Font("Georgia", Font.BOLD, 20));
        btnOpen.setBounds(10, 165, 193, 40);
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
        
        // aalysis buttons
        JButton btnLexical = new JButton("Lexical Analysis");
        btnLexical.setForeground(new Color(160, 82, 45));
        btnLexical.setBackground(new Color(255, 228, 196));
        btnLexical.setFont(new Font("Georgia", Font.BOLD, 17));
        btnLexical.setBounds(10, 220, 193, 40);
        btnLexical.addActionListener(e -> performLexicalAnalysis());
        buttonPanel.add(btnLexical);
        
        syntaxButton = new JButton("Syntax Analysis");
        syntaxButton.setBackground(new Color(255, 228, 196));
        syntaxButton.setForeground(new Color(160, 82, 45));
        syntaxButton.setFont(new Font("Georgia", Font.BOLD, 17));
        syntaxButton.setBounds(10, 275, 193, 40);
        syntaxButton.addActionListener(e -> performSyntaxAnalysis());
        syntaxButton.setEnabled(false);
        buttonPanel.add(syntaxButton);
        
        semanticButton = new JButton("Semantic Analysis");
        semanticButton.setBackground(new Color(255, 228, 196));
        semanticButton.setForeground(new Color(160, 82, 45));
        semanticButton.setFont(new Font("Georgia", Font.BOLD, 15));
        semanticButton.setBounds(10, 330, 193, 40);
        semanticButton.addActionListener(e -> performSemanticAnalysis());
        semanticButton.setEnabled(false);
        buttonPanel.add(semanticButton);
        
        // clear button
        JButton btnClear = new JButton("CLEAR");
        btnClear.setBackground(new Color(255, 228, 196));
        btnClear.setForeground(new Color(160, 82, 45));
        btnClear.setFont(new Font("Georgia", Font.BOLD, 17));
        btnClear.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        btnClear.setBorder(BorderFactory.createEtchedBorder());
        btnClear.setBounds(10, 410, 193, 40);
        btnClear.addActionListener(e -> clearAll());
        buttonPanel.add(btnClear);
        
        // content panel para sa input text or variables na itetest
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(new Color(255, 235, 205));
        contentPanel.setBounds(212, 151, 611, 368);
        contentPane.add(contentPanel);
        contentPanel.setLayout(null);
        
        codeTextArea = new JTextArea();
        codeTextArea.setBackground(new Color(255, 228, 196));
        codeTextArea.setBounds(30, 8, 551, 398);
        codeTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane codeScrollPane = new JScrollPane(codeTextArea);
        codeScrollPane.setBounds(30, 8, 551, 398);
        contentPanel.add(codeScrollPane);
        
        // display panel para sa output
        JPanel displayPanel = new JPanel();
        displayPanel.setBackground(new Color(255, 235, 205));
        displayPanel.setBounds(212, 0, 611, 149);
        contentPane.add(displayPanel);
        displayPanel.setLayout(null);

        outputTextArea = new JTextArea();
        outputTextArea.setBackground(new Color(255, 228, 196));
        outputTextArea.setEditable(false);
        outputTextArea.setBounds(28, 14, 555, 72);
        outputTextArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
        outputScrollPane.setBounds(28, 14, 555, 130);
        displayPanel.add(outputScrollPane);
        
        // exit button
        JButton btnExit = new JButton("Group");
        btnExit.setBackground(new Color(255, 228, 196));
        btnExit.setForeground(new Color(160, 82, 45));
        btnExit.setFont(new Font("Georgia", Font.BOLD, 15));
        btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Group info = new Group();
			      info.setVisible(true);
			      info.setLocationRelativeTo(null);
			      dispose();
			}
		
		});
        btnExit.setBounds(750, 540, 100, 30);
        contentPane.add(btnExit);
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
        semanticButton.setEnabled(syntaxPassed);

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
        // handle other actions if needed
    }
}