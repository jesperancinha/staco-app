#!/bin/bash

# Credits to https://github.com/mrts/docker-postgresql-multiple-databases/blob/master/create-multiple-postgresql-databases.sh
# This is an improvement of that file
# To create an image, this file follows some rules
# It is inspired by the way Spring scans for script and data files
# The root folder of all script files must be docker-entrypoint-initdb.d/mutiple-postgres/
# mutiple-postgres is a choice.
# Originally, the docker entrypoint located in /user/local/bin looks for all .sql, .gz and .xz files to import on docker-entrypoint-initdb.d/.
# There is not clean way around this. Check the entrypoint details on https://github.com/docker-library/postgres/blob/master/docker-entrypoint.sh.
# This script will search for sql files in the root folder docker-entrypoint-initdb.d/mutiple-postgres/ prepended like schema-<database>.sql and data-<database>.sql
# You can disable this by setting variable POSTGRES_SCAN_DISABLED to true
# Databases are declared in POSTGRES_MULTIPLE_DATABASES as a comma separated string of database tags
# You can specify a bundle of scripts to execute in a certain folder per database like so <database>:<folder> as one of these elements
# To specify the user use POSTGRES_USER
# To specify the database use POSTGRES_PASSWORD
# This script is available to download in a small example I've created in https://github.com/jesperancinha/project-signer/tree/master/docker-templates/docker-psql

set -e
set -u

POSTGRES_SCAN_DISABLED="${POSTGRES_SCAN_DISABLED:-false}"  # If variable not set or null, use default.

function create_user_and_database() {
  database=$(echo "$command" | awk -F':' '{print $1}')
	echo "  Creating user and database '$database'"
	psql -v ON_ERROR_STOP=1 --username "$POSTGRES_USER" <<-EOSQL
	    CREATE USER $database;
	    CREATE DATABASE $database;
	    GRANT ALL PRIVILEGES ON DATABASE $database TO $database;
EOSQL
}

function importFileToDatabase() {
  echo "Importing file $2 to database $1..."
  psql -U "$POSTGRES_USER" -d "$1" -f "$2"
}

function createAndImportFiles(){
  local rootDir="docker-entrypoint-initdb.d/mutiple-postgres/"
  if [ -n "$POSTGRES_MULTIPLE_DATABASES" ]; then
    echo "Multiple database creation requested: $POSTGRES_MULTIPLE_DATABASES"
    for command in $(echo "$POSTGRES_MULTIPLE_DATABASES" | tr ',' ' '); do
      create_user_and_database "$command"
      if [[ "$command" == *":"* ]]; then
        database=$(echo "$command" | awk -F':' '{print $1}')
        directory=$rootDir$(echo "$command" | awk -F':' '{print $2}')
        echo "Database bundle $directory for database $database requested"
        for script in "$directory"/*.sql; do
            echo "Request importing file $script to database $database"
            importFileToDatabase "$database" "$script"
        done
      fi
      if [ "$POSTGRES_SCAN_DISABLED" != true ]; then
        database=$(echo "$command" | awk -F':' '{print $1}')
        echo "Auto-scanning files for database $database"
        echo "Auto-scanning schema files"
        schemaFile=$rootDir"schema-$database.sql"
        if [ -f "$schemaFile" ]; then
                importFileToDatabase "$database" "$schemaFile"
                echo "Auto-scanning data files"
                schemaData=$rootDir"data-$database.sql"
                if [ -f "$schemaData" ]; then
                        importFileToDatabase "$database" "$schemaData"
                else
                  echo "WARNING: No data file detected for database $database"
                fi
        else
            echo "WARNING: No schema file detected for database $database"
        fi
      fi
    done
    echo "Multiple databases created"
  fi
}

createAndImportFiles;
