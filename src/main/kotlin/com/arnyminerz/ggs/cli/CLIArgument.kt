package com.arnyminerz.ggs.com.arnyminerz.ggs.cli

sealed interface CLIArgument {
    val key: String
    val shorthand: String?
    val holdsValue: Boolean
}
