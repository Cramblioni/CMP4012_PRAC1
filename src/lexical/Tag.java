package lexical;

public enum Tag {
    FnKeyword,
    VarKeyword,
    IfKeyword,
    ElseKeyword,
    WhileKeyword,
    ReturnKeyword,

    Semicolon,
    OpenParenthesis,
    CloseParenthesis,
    OpenBrace,
    CloseBrace,
    OpenBracket,
    CloseBracket,
    Assign,

    True,
    False,
    Identifier,
    Number,
    String,

    Dot,

    Plus,
    Minus,
    Star,
    Slash,
    Not,

    Equal,
    NotEqual,
    GreaterEqual,
    LesserEqual,
    Greater,
    Less,
}
