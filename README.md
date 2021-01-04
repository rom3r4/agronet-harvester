## agronet - RDF Harvester


Java application that harvests RDF repositories into a Drupal installed CMS.

### Requirements

- Java 8+
- Maven
- Git
- Working Drupal 7 installation with [drupal-contentbuider](https://github.com/julianromera/drupal-contentbuilder) module enabled
- Drush ``>= version 6.x`` 

### Installation


(Tested on Debian wheezy Linux)

    $ sudo apt-get install maven2 drush

    $ git clone https://github.com/romera-github/agronet-harvester.git
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

ont-space - The ontology-based resource metadata repository
Copyright (c) 2006-2011, Information Eng. Research Unit - Univ. of Alcalá
http://www.cc.uah.es/ie
This library is free software; you can redistribute it and/or modify it under
the terms of the GNU Lesser General Public License as published by the Free
Software Foundation; either version 2.1 of the License, or (at your option)
any later version.
This library is distributed in the hope that it will be useful, but WITHOUT
ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
details.
You should have received a copy of the GNU Lesser General Public License along
with this library; if not, write to the Free Software Foundation, Inc.,
59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
