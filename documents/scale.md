## Scaling

* Currently the application is running with single docker containers. 
* To server higher number of users the number of containers can be increased. 
* For the auto scaling, the infra can follow the two basic principles.
	* Any monitoring system to trigger alerts on performance parameters like CPU, memory usage or traffic volume etc.
	* Any container orchestration platform/service such as docker swarm, kubernetes, Amazon ECS etc to scale up/down number of containers based on the alert.
	

## AWS Services

* Let's take an example of auto scaling using AWS services.
* Services Used:
	* Amazon ECS
	* Application Load Balancer (ALB)
	* CloudWatch
* Architecture:
	* The ECS containers can be put behind ALB, with dynamic port forwarding enabled.
	* CloudWatch monitoring to trigger alarms based on performance parameters like CPU, memory usage or traffic volume etc.
* Auto scale can be configired in ECS, based on an alert to scale up/down number of containers.
* Since the application is served through ALB, the endpoints used by the clients should always remain same.

## Data Storage Problems

* The auto-scaling of DB instances could lead to data inconsistency.
* To avoid such problems, DB clustering approach can be adopted. 
	* Strategy is to use at least 2 MySQL containers in a cluster.
	* One of them should act as a Master accpting the Writes, the other as Slave to serve the Reads.
	* Depending the Read/Write volumes, the nmber of instances in Master or Slave Cluster can be increased.
* The other approach would be to use Amazon Aurora compatible MySQL instance, when using AWS services like as ECS/ALB.
	* Amazon Aurora by default provides Reader and Writer endpoints and manage the scaling of the instances internally.