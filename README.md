# MazeBank
MazeBank is a command line based application that simulates a real world Banking Management System. 
The data is retained using MYSql database this means that any changes made to an 'account' will be recorded and be 
available even after the program has been terminated.

### Setting up the database.
```sql
    CREATE TABLE accounts (
        id int NOT NULL AUTO_INCREMENT,
        _username varchar(32) NOT NULL,
       _password varchar(32) NOT NULL,
        account_type varchar(32) NOT NULL,
        first_name varchar(32) NOT NULL,
        last_name varchar(32) NOT NULL,
        balance double DEFAULT '0.00',
       PRIMARY KEY(id)
    );

    CREATE TABLE transaction_history(
    	id int NOT NULL,
        _transaction double NOT NULL,
        FOREIGN KEY (id) REFERENCES accounts(id)
    );
```
##### accounts
id(primary) | _username | _password | account_type | first_name | last_name | balance
------------|-----------|-----------|--------------|------------|-----------|---------
1           | Test      | 123       | Savings      | Name       | Name      | 60.01

##### transaction_history
id(foreign) | _transaction 
------------|-------------
1           | 100.00
1           | -39.99             
