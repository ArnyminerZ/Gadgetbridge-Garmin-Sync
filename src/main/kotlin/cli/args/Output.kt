package com.arnyminerz.ggs.cli.args

object Output : CLIArgument {
    override val key: String = "output"
    override val shorthand: String? = "o"
    override val holdsValue: Boolean = true
}
