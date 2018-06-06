# simple data storage

a simple data storage for persons data in JSON file or CSV file.

## Dependencies

to run this code you need a java IDE.

and for JSON "json simple" is used, to add it to your project download .jar from [here](http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm/) then add the .jar file to your project liberaries.

and to use CSV you need to add openCSV and it's dependencies to your project which can be downloaded form here:
- [openCSV](https://sourceforge.net/projects/opencsv/files/?source=navbar)
- [Apache Commons Lang](https://mvnrepository.com/artifact/org.apache.commons/commons-lang3/3.6)
- [Apache Commons Text](https://mvnrepository.com/artifact/org.apache.commons/commons-text/1.1)
- [Apache Commons BeanUtils](https://mvnrepository.com/artifact/commons-beanutils/commons-beanutils/1.9.3)
- [Apache Commons Logging](https://mvnrepository.com/artifact/commons-logging/commons-logging/1.2)

## Running

first you can change the path of the JSON file and the CSV file in Task class constructor ( by default set to be in the same folder called data.json abd data.CSV) then run the project.

by default you are using the JSON file.

you have 6 comands available to you when you run the program.

### add 
here you can add a person's data to the file, after entering add you will be asked to enter the data of the person.

### update
here you can update a person's data by choosing the number of the person you want to update (0 based) then enter the new data for this person.

### list
here the list of persons found in the file so far will be listed infront of you in the console.

### filter
here you will choose the field that you want to see, then the persons' data will be listed but with the chosen field only.

### sort
here you will choose the field that you want the data to be sorted accourding to.

### delete
here you can delete the file.

### change format
changes the current format, if the format used is CSV the new format will be JSON and vice versa.

### check format
check the current format being used.

### quit
exit the application.

**Please note that the fields available are**
- first name
- last name
- phone
- mail
- title
- age

### Testing
there are 7 test cases added using junit 5 each has a test for the JSON and CSV format.

- TestAdd: tests the adding function to make sure it works.
- TestDelete: tests the delete function to make sure it works and tries to delete when there is no file (sometimes the delete have some permission errors).
- TestFilter: testing the filter function with every possible field.
- TestList: testing the listing function matches the data in the file.
- TestSort: testing the sort function with every possible field where every field have a different output to make sure all fields work.
- TestUpdate: testing the update function and making sure it updates in the file.
- TestUpdateSort: testing the sort function after update function to make sure they work together.
