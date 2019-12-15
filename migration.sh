#!/usr/bin/env bash

MigrationFileEpoch=$(date +'%s')

MigrationFileDate=$(date +%Y%m%d)

MigrationFileName="migration_${MigrationFileEpoch}_${MigrationFileDate}.sql"

MigrationFilePath="./sql/${MigrationFileName}"

touch "${MigrationFilePath}"

echo -en "SET CLIENT_MIN_MESSAGES = WARNING;\rBEGIN;\r \rCOMMIT;" >> "${MigrationFilePath}"

timestamp="$(date "+[%Y-%m-%d %H:%M:%S]")"

echo -e "\e[93m${timestamp}\e[95m Logger::${MigrationFileName} have been created!"
