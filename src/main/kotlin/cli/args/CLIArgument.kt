package com.arnyminerz.ggs.cli.args

sealed interface CLIArgument {
    val key: String
    val shorthand: String?
    val holdsValue: Boolean
}
