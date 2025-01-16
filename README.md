
# HRMS Application

## Backend Development:
Backend of HRMS will be developed by SpringBoot lasted version 3.4.x

#  Configuration:
## Database Configuration:
* Used MySQL Database as a primary storage database.
* Listing Port: default port (3306)
* Database name: hrms
* Later will be implementing redis in-memory database for better performance of the application.


# Features of HRMS
## 1) Admin/System DashBoard:
### Admin Login:
An Admin can sing-in to the application with a valid email id and password and can perform multiple tasks.
### View Candidate Profile and Update Profile::
Can view his/her own account and update also.
### Add a HR
Only those who have admin access can add a HR
### Delete a HR:
an Admin can delete an HR also.

## 2) Candidate Dashboard:
### Candidate Login:
an existing candidate can sing-in his/her account with an valid email id and password.
### View Candidate Profile and Update Profile:
Only an authenticated Candidate can view his/her profile and update also
### Apply to a Job Post:
An authenticated Candidate can apply to an existing Job post.

## 3) HR Dashboard:
### HR Login: 
an existing HR can login to the application using their emailId and password.
### View HR Profile and Update Profile:
can view his/her profile and also update their own profile.
###  Add a Job Post:
an HR can post a job with all the required details.
### Update a Job Post:
Only an authenticated HR who has posted the job, only he/she can update an existing job post.
### Delete a Job Post:
Only an authenticated HR who has posted the job, only he/she can delete an existing job post by an Unique JobID.
### Get All Posted Jobs By HR:
Retrieve all the job posts those are posted by an authenticated HR.
### Get All Applied Candidates:
Retrieve all the Candidates who has applied to a specific job with Job Id
## 4) Job  DashBoard:
All the utility methods related to Job Dashboard are non-authorized methods means no authentication is required to access utilities.
###  Get All the Jobs:
Anyone can retrieve all the Jobs, posted by any HR for any locations
### Get a Specific Job:
Anyone can get a Specific Job by querying with an Unique Job Id
###  Search Jobs By Job Title:
Anybody can retrieve list of jobs by querying with specific Job Title.
### Search Jobs By Skill Sets:
Anybody can retrieve list of jobs by querying with specific skill sets.
### Search Jobs By Location:
Anybody can retrieve list of jobs by querying with a specific  job location.


