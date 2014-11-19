Catherine McNeil - JPMorgan Technical Test

Libraries used - JDK_1.7.0_71; Apache Maven 3.2.3, IDE is Eclipse Luna

#1 - I began by writing the Message and Message Queue through TDD , fleshing out 
JpMessageQueueTest.java.  I chose to use a PriorityQueue as this provided a mechanism
by way of a Comparator, with which I could ensure control of ordering of the messages.

#2 - Next I began to write the SchedulerTest, I decided the Scheduler was responsible for 
polling the message queue, and can retrieve groups 'in process' from the Gateway, which 
informs which message is polled from the queue.

#3 - I decided that the Gateway was responsible for generating Resource objects, based on config.
These Resource objects call off to the external resources made available by the 3rd party system. 
A Factory seemed a natural choice for doing this.

#4 - Added more functionality around handling no available resources, and Cancellation.

#5 - Alternative Message Prioritisation - by using the comparator in the JpMessageQueue, there
is already a simple way of amending the prioritisation of message polling.

By this point I had already spent around 4-5 hours, so thought I should stop at this point.
However, here are my next immediate tasks I would have carried out - 
TODO - Create a cron job that called Scheduler.java on a frequent basis (once every minute?)
TODO - Deploy in local container (Jetty or Tomcat)
TODO - Create a Mock set of Resources that could be deployed in a container, mimicking the behaviour 
of the external resources. This would enable end-2-end test of the code.


