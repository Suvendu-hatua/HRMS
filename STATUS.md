
# Initial Development

## HR Management
 ### HR login
HR login will be protected by Spring Security. After successful login HR will be able to see ..

* HR DashBoard
* Job Posting
* Can take Actions

## Entity
### Person(USER):
Person entity will be a generic entity, that could be a Type of HR entity or Admin Entity.
* Table Structure

![img.png](src/main/resources/images/img.png)

### HR Table: 
HR entity has one-to-one relationship with Person Entity and person_id is a foreign key in HR Table that refers to Person's Table primary key.
* Table Structure

![img_2.png](src/main/resources/images/img_1.png)

### Admin Table:
It has also same one-to-one relationship with Person Table.

* Table Structure:
![img_2.png](src/main/resources/images/img_2.png)

### Whole Relationship:
![img_3.png](src/main/resources/images/img_3.png)

### Relationship
One-to-Many relationship (HR ---> Job)