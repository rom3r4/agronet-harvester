## agronet - RDF Harvester


Java application that harvests RDF repositories into a Drupal installed CMS.

### Requirements

- Working Drupal 7 installation with [drupal-contentbuider](https://github.com/julianromera/drupal-contentbuilder) module enabled
- Drush ``>= version 6.x`` 
- Maven
- Git

### Installation


(Tested on Debian wheezy Linux)

    $ sudo apt-get install maven2 drush

    $ git clone https://github.com/julianromera/agronet-harvester.git
    $ cd agronet-harvester
        
    Modify these 3 files:
    
    - Variable INIT_DIR
    1. ./runandtest.sh
    
    - Variable DIR
    2. ./PortalUpdater/script/execute_portalUpdater.sh 
    
    - Change the Drupal Directory:
    3. ./PortalUpdater/src/main/java/es/uah/cc/ie/utils/DrushUpdater.java
    

    Also, to be able to insert content on your Drupal installation, ensure:

    1. You have the 'drupal-contentbuilder' installed & enabled
    2. Create these two Drupal Content-Types: resource_agrisap & resource_dc (machine-id) 

    $ ./runandtest.sh

    [INFO] Scanning for projects...
    [INFO] Searching repository for plugin with prefix: 'dependency'.
    [INFO] ------------------------------------------------------------------------
    [INFO] Building PortalUpdater
    [INFO]    task-segment: [clean, dependency:copy-dependencies, package]
    [INFO] ------------------------------------------------------------------------
    [INFO] [clean:clean {execution: default-clean}]

    .......
    .......

    ************* Resources found in AGRIS.OLD format. ************
    Harvesting summary for this execution:
    - Number of harvested elements in AGRIS: 180
    -------------------------------------------------------------------
     done.                                                                [success]
     tests passed: 8 | errors: 0 | total tests: 8
    -------------------------------------------------------------------
     done.                                                                [success]
     tests passed: 8 | errors: 0 | total tests: 8
    -------------------------------------------------------------------

    .......
    .......

    INFO: END: Number of content items stored or update 180
    Jul 12, 2013 7:42:14 AM _ie.portalupdater.Main main
    INFO: END: Number of invalid content items 0
    [DONE]
    [INFO] ------------------------------------------------------------------------
    [INFO] BUILD SUCCESSFUL
    [INFO] ------------------------------------------------------------------------
    [INFO] Total time: 7 minutes 45 seconds
    [INFO] Finished at: Fri Jul 12 07:42:14 CEST 2013
    [INFO] Final Memory: 9M/208M
    [INFO] ------------------------------------------------------------------------


### Author

University of Alcala


### LICENSE

This is free and unemcumbered software released into the public domain. For more information, see the accompanying UNLICENSE file.  

If you're unfamiliar with public domain, that means it's perfectly fine to start with this skeleton and code away, later relicensing as you see fit.
