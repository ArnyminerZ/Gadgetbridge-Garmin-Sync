package cli

object Metadata : CLIArgument {
    override val key: String = "metadata"
    override val shorthand: String? = "m"
    override val holdsValue: Boolean = true
}
