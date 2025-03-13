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
    Assign,

    True,
    False,
    Identifier,
    Number,
    String,

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
