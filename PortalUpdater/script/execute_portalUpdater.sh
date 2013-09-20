#!/bin/bash
echo "update script"
DATE=$(date +%Y_%m_%d);

# CHANGE-ME pont this to the dir where runandtest.sh directory is placed

# DIR=/Users/fernandosainz/Desktop/drupal-contentuploader-master
# DIR=/opt/test/drupal-contentuploader
DIR="/opt/voa3r"

PROJECT_SOURCES="${DIR}/PortalUpdater"
logFile="${DIR}/execute_voa3r_portalUpdater_$DATE\.log;"

cd $PROJECT_SOURCES
mvn clean dependency:copy-dependencies package 
# &> $logFile
CONT=0;
# until [[ -f $PROJECT_SOURCES/etc/.storageFinished ]] 
# do
    echo "Execution: $CONT";
    ((CONT++))
    # mvn exec:java -Dexec.mainClass="es.uah.cc.ie.portalupdater.Main" &>> $logFile
    mvn -e exec:java -Dexec.mainClass="es.uah.cc.ie.portalupdater.Main"
# done  

exit 0
