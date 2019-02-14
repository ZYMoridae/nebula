#!/usr/bin/env bash

MigrationFileEpoch=$(date +'%s')

MigrationFileDate=$(date +%Y%m%d)

#echo $MigrationFileEpoch

#echo $MigrationFileDate

touch "./sql/migration_${MigrationFileEpoch}_${MigrationFileDate}.sql"
