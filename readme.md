# Job Scheduler algorithm description
There are two main flows for the Job scheduling mechanism implemented here:
- Scheduling API, exposed to the client, which allows client to specify which Job and in how many milliseconds he/she wants to execute it,.
- Scheduling loop ( internal implementation ), which determines to take appropriate task at appropriate time and executes it. 

## Schedul
