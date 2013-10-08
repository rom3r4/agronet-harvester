[drupal-contentuploader](#)
--

Helper Java App that Harvests Metadata-Models' repositories and inserts them it into Relational databases

Installation
--
    
    To deploy this app just follow these istructions:
    
    (from an UNIX / UNIX-like system)

    $ git clone https://github.com/julianromerajuarez/drupal-contentuploader.git
    
    (In you are using a debian-like linux distribution, type this)
    
    $ sudo apt-get install maven2
    
    (once again you should install a dependent package)
    
    $ sudo apt-get install drush
    
    (Use 'port install' command on Mac OS, or 'yum install' on RedHat Like systems)
    
    $ cd drupal-contentuploader
        
    Once installed, modify these 3 files:
    
    1. ./runandtest.sh
    2. ./PortalUpdater/script/execute_portalUpdater.sh 
    3. ./PortalUpdater/src/main/java/es/uah/cc/ie/utils/DrushUpdater.java

    Also, to be able to insert content on a Drupal setup, ensure this:

    1. You have the VOA3R Drush module installed & activated
    2. In your Drupal system you have these empty content-types: resource_agrisap & resource_dc (machine-id) 

    $ ./runandtest.sh
    
    (If the program gets installed correctly, the output will be similar to this)
    
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


License
--

Copyright University of Alcala. Licensed under GNU/GPL version 2 License  
