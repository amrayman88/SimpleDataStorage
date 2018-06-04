# simple data storage

a simple data storage for persons data in JSON file

### Dependencies

to run this code you need a java IDE and you need to add json simple to your project download .jar from [here] (http://www.java2s.com/Code/Jar/j/Downloadjsonsimple11jar.htm) then add the .jar file to your project liberaries.

### Running

first you can change the path of the JSON file in Task class constructor ( by default set to be in the same folder called data.json ) then run the project.

you have 6 comands available to you when you run the program.

##### add 
here you can add a person's data to the file, after entering add you will be asked to enter the data of the person.

##### update
here you can update a person's data by choosing the number of the person you want to update (0 based) then enter the new data for this person.

##### list
here the list of persons found in the file so far will be listed infront of you in the console.

##### filter
here you will choose the field that you want to see, then the persons' data will be listed but with the chosen field only.

##### sort
here you will choose the field that you want the data to be sorted accourding to.

##### delete
here you can delete the file.

**Please note that the fields available are**
- first name
- last name
- phone
- mail
- title
- age

### Testing
there are 7 test cases added using junit 5.

1. TestAdd tests the adding function to make sure it works.
2. TestDelete: tests the delete function to make sure it works and tries to delete when there is no file (sometimes the delete have some permission errors).
3. TestFilter: testing the filter function with every possible field.
4. TestList: testing the listing function matches the data in the file.
5. TestSort: testing the sort function with every possible field.
6. TestUpdate: testing the update function and making sure it updates in the file.
7. TestUpdateSort: testing the sort function after update function to make sure they work together.
