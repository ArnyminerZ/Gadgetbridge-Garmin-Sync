package com.arnyminerz.ggs.cli.args

object GPX : CLIArgument {
    override val key: String = "gpx"
    override val shorthand: String? = "g"
    override val holdsValue: Boolean = true
}
