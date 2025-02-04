package cli

object Output : CLIArgument {
    override val key: String = "output"
    override val shorthand: String? = "o"
    override val holdsValue: Boolean = true
}
