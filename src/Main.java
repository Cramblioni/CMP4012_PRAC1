import lexical.Lexer;
import lexical.LexingError;
import lexical.Location;
import lexical.Token;

import java.lang.System;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Expected file path");
            return;
        }
        CharBuffer source = CharBuffer.wrap(Files.readString(Path.of(args[0])).toCharArray());

        System.out.println(source);

        ArrayList<Token> tokens = tokeniseFile(source);
        for (Token token : tokens) {
            System.out.printf("%s\t'%s' %s\n",
                    Location.atIndex(source, token.start()),
                    source.subSequence(token.start(), token.end()).toString(),
                    token.tag()
            );
        }
    }

    private static ArrayList<Token> tokeniseFile(CharBuffer source) throws LexingError {
        Lexer lexer = new Lexer(source);
        ArrayList<Token> tokens = new ArrayList<>();
        while (!lexer.atEnd()) {
            Token token = lexer.pullToken();
            tokens.add(token);
        }
        return tokens;
    }
}