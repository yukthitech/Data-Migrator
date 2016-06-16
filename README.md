# DbBackup

DbBackup is a free open source tool for migrating data from source to storage and vice versa.

> The overriding design goal for DbBackup is to have a safe secure back up of 
> data's present in the source. We can migrate data from source to storage, 
> this can be easily done by just passing the source informations (example: 
> for database -> driver name, user, password) in a properties file. Please 
> check the section below to get the format of properties file. 

![](docImages/mig-block.png?raw=true)

 Supported by
 * [Rdbms](#rdbms)
 * [File](#file) 

### Installation

DbBackup is very easy to use just get the dependencies include it in your pom.xml and use it in your project.

```sh
Use (RdbmsDataSource and FileDataStorage) or (FileDataSource and RdbmsDataStorage) 
to pass your properties file containing information about source and storage. 
Then pass those source and storage to DataMigrationManager for migrating data from source to storage.
```

![](docImages/mig-class.png?raw=true)

#### command line argument
```sh
From source to storage
java DataMigration <source properties file path> <storage properties file path>
```

#### <a name="rdbms"></a>Db support 

```sh
user = userName
password = password
url = jdbc:mysql://hostName:portNumber/databaseName or schemaName
driverName = com.mysql.jdbc.Driver
source.type = com.yukthi.tools.db.rdbms.RdbmsDataSource
storage.type = com.yukthi.tools.db.rdbms.RdbmsDataStorage
```
#### <a name="file"></a>File support
```sh
fileName = yourFileName or filePath
storage.type = com.yukthi.tools.db.file.FileDataStorage
source.type = com.yukthi.tools.db.file.FileDataSource
```

###### Please follow the above formats for properties file to get proper output.

### Writing Environments
* Text editors
* IDE's

**Free Software**
***
