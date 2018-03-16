package jp.shoma.calculator.enums

enum class Operator(val symbol: String) {
    PLUS("+"),
    MINUS("-"),
    MULTIPLY("*"),
    DIVIDE("/"),
    NONE("");

    companion object {
        fun valueTypeOf(symbol: String) =
                values().firstOrNull { it.symbol == symbol } ?: NONE
    }
}
