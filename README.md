# DATABASE PROJECT TO SEARCH THE EMPLOYEES

## Description

This project contains code for searching the data of employee working on the requested projects.

## Installation

1. Clone the repository.

> [!IMPORTANT] 
> 2. Use openVPN to connect to the ULM server. This step is important to access database.

> [!TIP]
> ** To connect to the ULM server, go to [this website](https://openvpn.ulm.edu) and download the corresonding file that you need to run on your platform **

## Usage

After cloning the repo, compile the java file
Steps:

```bash
javac EmployeeSearchFrame.java
```

```bash
java -cp .:mysql-connector-java-5.1.48.jar EmployeeSearchFrame
```

## Example usage:

1. Enter "COMPANY" in the database field.
2. Click Fill button
3. Select the fields company and project
4. Click on Search Button.
5. When done with searching, Click Clear to clear the result.

###


## Example of a database.props file:

###### db.url=jdbc:mysql://myserver.us-east-2.rds.amazonaws.com/
###### db.user=user123
###### db.password=password123
###### db.driver=com.mysql.jdbc.Driver

# Group MEMBERS:
## RAJAN SAPKOTA
## ROHAN CHAKRABORTY
## KAUSHAL SHAH