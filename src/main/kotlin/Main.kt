package com.arnyminerz.ggs

import com.arnyminerz.ggs.cli.args.ArgumentParser
import com.arnyminerz.ggs.cli.cmd.AddHeartRateCommand
import com.arnyminerz.ggs.cli.cmd.Command
import com.arnyminerz.ggs.cli.cmd.ExportCSVCommand
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.csv.Csv
import kotlinx.serialization.json.Json
import nl.adaptivity.xmlutil.serialization.XML

@ExperimentalSerializationApi
val csv = Csv { hasHeaderRecord = true }

val json = Json { prettyPrint = true }

val xml = XML

val commands: List<Command> = listOf(ExportCSVCommand, AddHeartRateCommand)

fun main(args: Array<String>) {
    val commandArg = args[0]

    for (command in commands) {
        if (command.name.equals(commandArg, true)) {
            val arguments = ArgumentParser.parse(command.arguments, args.drop(1))
            command(arguments)
            return
        }
    }

    println("Command $commandArg unknown")
}
