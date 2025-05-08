package lexical;

public enum Tag {
    FnKeyword,
    VarKeyword,
    IfKeyword,
    ElseKeyword,
    WhileKeyword,
    ReturnKeyword,
    ImportKeyword,

    Semicolon,
    OpenParenthesis,
    CloseParenthesis,
    OpenBrace,
    CloseBrace,
    Assign,

    True,
    False,
    Identifier,
    Number,
    String,

    Dot,
    Comma,

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
