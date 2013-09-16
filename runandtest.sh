#!/bin/sh

# PATH=$PATH:/
# export PATH


host_name=`cat /etc/hostname`



# CHANGE-ME
# variable pointing to the directory containig this file
# remove trailing '/' character at the end
INIT_DIR=/Users/fernandosainz/Desktop/drupal-contentuploader-master


# this aprox doesnt detect machine changes
# if [ ! -f  "${INIT_DIR}/$0" ]; then

if [ "${host_name}" = "rrm" -a "${INIT_DIR}" = "/opt/voa3r" ]; then
  echo -n ""
else  
  echo "Edit this file and change the INIT_DIR variable. Then re-run it"
  return 1
fi


lockfile="${INIT_DIR}/harvested_files/nivel_0/.stored"
execscript="${INIT_DIR}/PortalUpdater/script/execute_portalUpdater.sh"
res1=0
res2=0

if [ -f ${lockfile} ];then
  rm ${lockfile}
  res1=$?
fi

if [ -x ${execscript} ];then
  ${execscript}
  res2=$?
fi

return $res1 && $res2

