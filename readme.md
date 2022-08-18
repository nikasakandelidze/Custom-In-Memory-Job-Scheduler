# Job Scheduler algorithm description
There are two main flows for the Job scheduling mechanism implemented here:
- Client Scheduling API, exposed to the client, which allows client to specify which Job and in how many milliseconds he/she wants to execute it,.
- Scheduling/Executor Loop ( internal implementation ), which determines to take appropriate task at appropriate time and executes it. 


## Client Scheduling API
 Client ----(execute Job in T time)------> [Scheduler]

## Scheduling/Executor Loop
