package com.arnyminerz.ggs

import com.arnyminerz.ggs.cli.args.ArgumentParser
import com.arnyminerz.ggs.cli.cmd.Command
import com.arnyminerz.ggs.cli.cmd.ExportCSVCommand
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.json.Json

@ExperimentalSerializationApi
val csv = Csv { hasHeaderRecord = true }

val json = Json { prettyPrint = true }

val commands: List<Command> = listOf(ExportCSVCommand)

fun main(args: Array<String>) {
    val commandArg = args[0]
    val arguments = ArgumentParser.parse(
        args.let { it.copyOfRange(0, it.size) }
    )

    for (command in commands) {
        if (command.name.equals(commandArg, true)) {
            command(arguments)
            return
        }
    }

    println("Command unknown")
}
