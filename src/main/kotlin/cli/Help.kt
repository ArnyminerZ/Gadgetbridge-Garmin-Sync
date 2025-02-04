package cli

object Help : CLIArgument {
    override val key: String = "help"
    override val shorthand: String? = "h"
    override val holdsValue: Boolean = false
}
