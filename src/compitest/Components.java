package compitest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Components {
    private static final String[] DATA_TYPES = {"int", "double", "String", "boolean", "char", "float", "long"};

    //lexical analysis method
    public String lexicalAnalysis(String code) {
        StringBuilder tokenizedLines = new StringBuilder();
        Pattern pattern = Pattern.compile("\"[^\"]*\"|[a-zA-Z][a-zA-Z0-9]*|[\\d\\.]+|[=;]|\\S");
        String[] lines = code.split("\n");
        
        //data types to check in syntax
        for (String line : lines) {
            StringBuilder tokenizedLine = new StringBuilder();
            Matcher matcher = pattern.matcher(line.trim());
            
            while (matcher.find()) {
                String token = matcher.group().trim();
                if (token.isEmpty()) continue;
                
                if (isDataType(token)) {
                    tokenizedLine.append("<data_type> ");
                } else if (isValue(token)) {
                    tokenizedLine.append("<value> ");
                } else if (isIdentifier(token)) {
                    tokenizedLine.append("<identifier> ");
                } else if (isOperator(token)) {
                    tokenizedLine.append("<operator> ");
                }  else if (token.equals(";")) {
                    tokenizedLine.append("<delimiter> ");
                } else if (!token.matches("\\s+")) { 
                    tokenizedLine.append("<unknown> ");
                }
            }
            if (tokenizedLine.length() > 0) {
                tokenizedLines.append(tokenizedLine.toString().trim()).append("\n");
            }
        }
        return tokenizedLines.toString().trim();
    }

    public boolean syntaxAnalysis(String lexAnalysisResult) {
        Pattern validPattern = Pattern.compile("(<data_type> <identifier>(?: <operator> <value>)? <delimiter>)");
        Matcher matcher = validPattern.matcher(lexAnalysisResult);
        while (matcher.find()) {
        	return true;
        }
        return false;
    }

    //method for semantic analysis
    public void semanticAnalysis(String code) {
        List<String> codeLines = new ArrayList<>(Arrays.asList(code.split("\n")));
        String result = semanticAnalysis(codeLines);
        System.out.println(result);
    }

    public String semanticAnalysis(List<String> codeLines) {
        StringBuilder result = new StringBuilder();
        boolean hasError = false;

        for (String line : codeLines) {
            String lineResult = semanticAnalysisSingleLine(line);
            if (lineResult.startsWith("Error:")) {
                hasError = true;
            }
            result.append(lineResult).append("\n");
        }

        if (hasError) {
            return "Semantic Analysis Failed";
        } else {
            return "Semantic Analysis Passed";
        }
    }

    public String semanticAnalysisSingleLine(String line) {
        String[] tokens = line.trim().split("\\s+");
        if (tokens.length < 2 || tokens.length > 4) return "Error: Invalid statement";
    
        String dataType = tokens[0];
        String identifier = tokens[1];
        String value = (tokens.length == 4) ? tokens[3] : "";
    
        if (!isDataType(dataType)) return "Error: Invalid data type";
        if (!isIdentifier(identifier)) return "Error: Invalid identifier";
    
        if (tokens.length == 4 && !isValueCompatibleWithType(dataType, value)) {
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
        
        // Check different types of values (integers, decimals, strings, booleans)
        return token.matches("\\d+") || 
               token.matches("\\d*\\.\\d+") || 
               token.matches("\".*\"") || 
               token.matches("'.'") ||
               token.matches("true|false");
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
                return value.matches("\"[^\"]*\""); 
            case "char":
            	return value.matches("'.'");
            case "boolean":
                return value.matches("true|false");
            default:
                return false;
        }
    }
}