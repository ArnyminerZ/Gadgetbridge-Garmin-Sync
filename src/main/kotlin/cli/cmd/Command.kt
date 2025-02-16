package com.arnyminerz.ggs.cli.cmd

import com.arnyminerz.ggs.cli.args.CLIArgument

abstract class Command(val name: String) {
    abstract operator fun invoke(arguments: MutableMap<CLIArgument, String?>)
}
