import Lexical_Analysis.Lexical_Analyzer;
import Synatx_Analysis.Parser;

public class Main {

    public static void main(String[] args) {
        Lexical_Analyzer lexical_analyzer = new Lexical_Analyzer();
        lexical_analyzer.tokenizing("src\\input.txt");
        Parser parser = new Parser();
        parser.parse("src\\output.txt");
    }
}