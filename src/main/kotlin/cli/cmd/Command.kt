package com.arnyminerz.ggs.cli.cmd

import com.arnyminerz.ggs.cli.args.CLIArgument

abstract class Command(val name: String) {
    abstract val arguments: List<CLIArgument>

    abstract operator fun invoke(arguments: MutableMap<CLIArgument, String?>)
}
