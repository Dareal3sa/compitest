package compitest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CompilerComponents {
    private static final String[] DATA_TYPES = {"int", "double", "String", "boolean", "char", "float", "long"};

    public String lexicalAnalysis(String code) {
        StringBuilder tokenizedLines = new StringBuilder();
        String[] lines = code.split("\n");

        for (String line : lines) {
            StringBuilder tokenizedLine = new StringBuilder();
            String[] tokens = line.trim().split("\\s+|;");

            for (String token : tokens) {
                if (isDataType(token)) {
                    tokenizedLine.append("<data_type> ");
                } else if (isIdentifier(token)) {
                    tokenizedLine.append("<identifier> ");
                } else if (isOperator(token)) {
                    tokenizedLine.append("<operator> ");
                } else if (isValue(token)) {
                    tokenizedLine.append("<value> ");
                } else if (token.equals(";")) {
                    tokenizedLine.append("<delimiter> ");
                } else {
                    tokenizedLine.append("<unknown> ");
                }
            }
            tokenizedLines.append(tokenizedLine.toString().trim()).append("\n");
        }
        return tokenizedLines.toString().trim();
    }
    
    public boolean syntaxAnalysis(String lexAnalysisResult) {
        Pattern validPattern = Pattern.compile("(<data_type> <identifier> (<operator> <value>)? <delimiter>)");
        Matcher matcher = validPattern.matcher(lexAnalysisResult);
        return matcher.matches();
    }
    
        public String semanticAnalysis(String code) {
            return semanticAnalysisSingleLine(code);
        }


    
    public String semanticAnalysis(List<String> codeLines) {
        boolean hasError = false;
        StringBuilder result = new StringBuilder();
        
        for (String line : codeLines) {
            String lineResult = semanticAnalysisSingleLine(line);
            if (lineResult.contains("Error")) {
                hasError = true;
            }
            result.append(lineResult).append("\n");
        }
        
        return hasError ? "Semantic Analysis Failed" : "Semantic Analysis Passed";
    }
    
    public String semanticAnalysisSingleLine(String line) {
        String[] tokens = line.trim().split("\\s+");
        if (tokens.length < 2) return "Error: Invalid statement";
        
        String dataType = tokens[0];
        String value = tokens.length >= 4 ? tokens[3] : "";
        
        if (!isDataType(dataType)) return "Error: Invalid data type";
        
        if (value.length() > 0 && !isValueCompatibleWithType(dataType, value)) {
            return "Error: Type mismatch - " + value + " is not compatible with " + dataType;
        }
        
        return "Valid declaration/initialization";
    }
    
    private boolean isDataType(String token) {
        return Arrays.asList(DATA_TYPES).contains(token);
    }
    
    private boolean isIdentifier(String token) {
        return token.matches("[a-zA-Z][a-zA-Z0-9]*");
    }
    
    private boolean isOperator(String token) {
        return token.equals("=");
    }
    
    private boolean isValue(String token) {
        token = token.replaceAll(";$", "");
        
        // Check for different types of values
        return token.matches("\\d+") || // integers
               token.matches("\\d*\\.\\d+") || // decimals
               token.matches("\".*\"") || // strings
               token.matches("true|false"); // booleans
    }
    
    private boolean isValueCompatibleWithType(String dataType, String value) {
        value = value.replaceAll(";$", "");
        
        switch (dataType) {
            case "int":
                return value.matches("\\d+");
            case "double":
            case "float":
                return value.matches("\\d*\\.\\d+") || value.matches("\\d+");
            case "String":
                return value.matches("\".*\"");
            case "boolean":
                return value.matches("true|false");
            default:
                return false;
        }
    }
}