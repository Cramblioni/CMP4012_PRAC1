import lexical.Lexer;
import lexical.Token;

import java.lang.System;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Expected file path");
            return;
        }
        CharBuffer source = CharBuffer.wrap(Files.readString(Path.of(args[0])).toCharArray());

        System.out.println(source);

        Lexer lexer = new Lexer(source);
        try {
            while (!lexer.atEnd()) {
                Token token = lexer.pullToken();
                System.out.printf("'%s' %s\n",
                        source.slice(token.start(), token.end() - token.start()),
                        token.tag()
                );
            }
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}