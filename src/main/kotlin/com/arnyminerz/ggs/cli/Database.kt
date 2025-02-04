package com.arnyminerz.ggs.com.arnyminerz.ggs.cli

object Database : CLIArgument {
    override val key: String = "database"
    override val shorthand: String? = "d"
    override val holdsValue: Boolean = true
}
