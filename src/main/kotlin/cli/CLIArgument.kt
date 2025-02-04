package cli

sealed interface CLIArgument {
    val key: String
    val shorthand: String?
    val holdsValue: Boolean
}
