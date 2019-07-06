#!/usr/bin/env bash




SEARCH_DIR="./sql"

migration_file_epoch=$(date +'%s')

migration_file_date=$(date +%Y%m%d)

application_yml=$(find ./src/main -type f -name "application-dev.yml")

#echo "$application_yml"

# Ref Link: https://gist.github.com/pkuczynski/8665367
parse_yaml() {
   local prefix=$2
   local s='[[:space:]]*' w='[a-zA-Z0-9_]*' fs=$(echo @|tr @ '\034')
   sed -ne "s|^\($s\)\($w\)$s:$s\"\(.*\)\"$s\$|\1$fs\2$fs\3|p" \
        -e "s|^\($s\)\($w\)$s:$s\(.*\)$s\$|\1$fs\2$fs\3|p"  $1 |
   awk -F$fs '{
      indent = length($1)/2;
      vname[indent] = $2;
      for (i in vname) {if (i > indent) {delete vname[i]}}
      if (length($3) > 0) {
         vn=""; for (i=0; i<indent; i++) {vn=(vn)(vname[i])("_")}
         printf("%s%s%s=\"%s\"\n", "'$prefix'",vn, $2, $3);
      }
   }'
}


#
#file_paths=(${application_yml})
#
#for (( i=0; i<${#file_paths[@]}; i++ ))
#do
#    echo "$i: ${file_paths[$i]}"
#done


eval $(parse_yaml "$application_yml")

findString="jdbc:postgresql://"
replacedString=""

stripped_string=${spring_datasource_url//$findString/$replacedString}

#connect to psql
#psql "postgres://$spring_datasource_username:$spring_datasource_password@$stripped_string"


#echo "$application_yml"

run_sql(){
    local sql_file_path="$1"

    echo "------------------------------------------------"
    echo "Start processing ${sql_file_path} ..."

    echo $sql_file_path

    echo "Success"
    psql "postgres://$spring_datasource_username:$spring_datasource_password@$stripped_string" -f "$sql_file_path"
}

echo "------------------------------------------------"

echo '  __  __ _                 _   _             '
echo ' |  \/  (_) __ _ _ __ __ _| |_(_) ___  _ __  '
echo " | |\/| | |/ _\` | '__/ _\` | __| |/ _ \| \'_ \ "
echo ' | |  | | | (_| | | | (_| | |_| | (_) | | | |'
echo ' |_|  |_|_|\__, |_|  \__,_|\__|_|\___/|_| |_|'
echo '           |___/                             '
# main
for entry in "$SEARCH_DIR"/*
do
    entry_array=(${entry//_/ })

    data_array=(${entry_array[2]//./ })

#    echo $migration_file_date

    if [ "${data_array[0]}" -ge "$migration_file_date" ];then
        run_sql $entry
    fi

done

echo "------------------------------------------------"
echo "Migration finished"