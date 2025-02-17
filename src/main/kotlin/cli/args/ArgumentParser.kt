package com.arnyminerz.ggs.cli.args

object ArgumentParser {
    fun parse(arguments: List<CLIArgument>, args: List<String>) = mutableMapOf<CLIArgument, String?>().let { result ->
        var i = 0
        while (i < args.size) {
            val arg = args[i]
            val argument = if (arg.startsWith("--")) {
                // Long identifier
                arguments.find { it.key == arg.trimStart('-') }
            } else if (arg.startsWith("-")) {
                // Short identifier
                arguments.find { it.shorthand == arg.trimStart('-') }
            } else {
                null
            }
            argument ?: error("Argument $arg unknown.")

            if (argument.holdsValue) {
                val value = args.getOrNull(i + 1) ?: error("Argument ${argument.key} requires a value to be given.")
                result += argument to value
                i += 2
            } else {
                result += argument to null
                i++
            }
        }
        result
    }
}
