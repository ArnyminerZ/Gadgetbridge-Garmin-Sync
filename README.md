# Gadgetbridge Garmin Sync

> [!WARNING]
> This project is in very early stages of development, data loss is possible and expected. Use under your own responsibility.

This project takes the Gadgetbridge database, and exports a Garmin-compatible CSV file (Fitbit based), which you can
import using the [Garmin import utility](https://connect.garmin.com/modern/import-data).

## Usage
### Data processing

In order to do the data processing, you have to download the `Gadgetbridge-Garmin-Sync.jar` file from the latest release.
Then, choose one of the following options:

**Export `output.csv`:**

This is the file required to import step data into Garmin Connect. Usage:
```shell
java -jar Gadgetbridge-Sync.jar export_csv --database <database> --metadata [metadata.json] --output [output.csv]
```

## Motivation

I really like the Garmin platform, and all my friends are there, but Fenix watches are bulky, and I don't really enjoy
wearing them all daylong. I've recently bought a Colmi smart ring, which I synchronize using Gadgetbridge.

This script allows me to export the steps data from Gadgetbridge, and import it into Garmin.

## Garmin Limitations

[See](https://support.garmin.com/en-US/?faq=HfJ4xPchdD3cmZ2qtDpOR8).

Not all data can be imported this way, Garmin only allows the following fields to be imported (as of 2025-02-04):
- Historical body composition data

  **This includes weight, BMI, and fat percentage data.**

- Activity data

  **This includes calories burned, steps, distance, active minutes, and floors climbed.**

- Fitbit Activity Minutes

  **Fitbit activity minutes will be converted over to Garmin Intensity Minutes.**

## Current Project Limitations

Since this was made for my own needs, only the features I require have been implemented, though the project is modular
enough to include other ones.

Right now only Colmi Rings are supported, and only "Activity data" is exported. All the activity information is not
provided by Gadgetbridge, so it's always set to `0`.

## Building

Compile artifact using IntelliJ IDEA: Build > Build Artifacts...

(In a terminal, from repo root):
```shell
cd out/artifacts/Gadgetbridge_Garmin_Sync_jar
unzip Gadgetbridge-Garmin-Sync.jar -d gadgetbridge

rm gadgetbridge/META-INF/*.SF gadgetbridge/META-INF/*.RSA gadgetbridge/META-INF/*.DSA

rm Gadgetbridge-Garmin-Sync.jar
cd gadgetbridge
zip -r Gadgetbridge-Garmin-Sync.jar *
cd ..
mv gadgetbridge/Gadgetbridge-Garmin-Sync.jar .
```
