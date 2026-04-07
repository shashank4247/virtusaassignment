"""3. Core Python: The "OpsBot" Log Automator
Business Case: An IT Operations team manages a server that generates 5,000 lines of log data daily. They currently spend 2 hours a day manually looking for "Failed Login" attempts to catch hackers.
Problem Statement
Develop a Python script called OpsBot that automatically reads a raw log file, filters out the "Noise" (Info messages), and creates a "Security Alert" summary.
Student Tasks:
1.	File Parsing: Read a sample server.log file (provided or generated) line by line.
2.	Pattern Matching: Use string methods (or the re module) to find lines containing keywords: CRITICAL, ERROR, or FAILED LOGIN.
3.	Data Structuring: Count the frequency of each error type using a Dictionary { "ERROR": 5, "CRITICAL": 2 }.
4.	Report Generation: Write the filtered "Critical" lines into a new file named security_alert_[date].txt.
5.	Automation: Use the os module to print the size of the new alert file to the console to confirm it was created.
Deliverable: A .py script and a sample "Before" and "After" log file demonstrating the filtering logic.
"""
import os
import datetime
filter={"error":0, "critical":0, "failed login":0}
sent=[]
with open('server.log', 'r') as file:
    for line in file:
            line=line.lower()
            if "critical" in line or "failed login" in line or "error" in line:
                sent.append(line)
            for key in filter:
                if key in line:
                    filter[key]+=1
for ele in filter:
    if(filter[ele]==0):
        filter.pop(ele)
        break
securityfile='security_alert_{}.txt'.format(datetime.date.today())
with open(securityfile,'w') as file:
    for x in sent: 
        file.write(x)
print("Security Alert Summary:")
print(filter)
size=os.path.getsize(securityfile)
print("File size %d"%size)


