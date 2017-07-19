# JVM-2-JVM-Tests
Test multiple local JVMs communication techniques and try to understand the differences.

# Problem
I have two java applications running in separate applications; different JVMs.
I want to share objects between these JVMs. 
Example:
App1: Exams Application
App2: Grading Server
Now, a student fills his answer object "IAnswer" and wants to pass it to the grading server which takes the same interface "IAnswer" to print the result to the stream.
Now, how to send the class?

# Proposed Solutions
## Mapped Bus Library + Serialization
I could serialize/deserialize my objects, but with limitations: <br/>
-But I needed to name the objects the same way and also for the package!<br/>
-Non-static fields are not serialized! e.g. constants<br/>
