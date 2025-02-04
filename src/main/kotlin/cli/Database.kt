package cli

object Database : CLIArgument {
    override val key: String = "database"
    override val shorthand: String? = "d"
    override val holdsValue: Boolean = true
}
