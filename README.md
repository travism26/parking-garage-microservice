# parking-garage-microservice
Expanding my knowledge in microservices just wanted to create a parking garage microservice I took some inspiration from this [blog post](https://medium.com/double-pointer/system-design-interview-parking-lot-system-ff2c58167651)
I also wrote a blog post explaining the implementation details and overview on this project link below.

- Overview of this project: [Blog Post](https://www.travisallister.com/post/parking-lot-microservice-application-implementation)

## how to start the application
There are 3 services (No UI yet):
- user-entry
- parking-service
- spot-allocation-service ( In development )
To start each service navigate to the folder and the run the below command you will need 3 terminals:
```
./gradlew quarkusdev
```

### running local kubernetes postgresql deployment
I created some kubernetes files in the `k8s` directory Note pgadmin not yet working however postgresql is working to create the resources run the following command
```commandline
# Navigate to k8s directory 
cd k8s
# Create deployment 
kubectl create -f postgresql
```
Once the postgresql deployment is set up you can run: `kubectl get all` for all the deployment details.
```
kubectl get all
NAME                                   READY   STATUS    RESTARTS   AGE
pod/allocation-psql-65dc7f9fb5-68ntm   1/1     Running   0          32s
pod/postgres-7b8c586bb6-bdjvt          1/1     Running   0          32s

NAME                      TYPE        CLUSTER-IP       EXTERNAL-IP   PORT(S)          AGE
service/allocation-psql   NodePort    10.xxx.xxx.xx    <none>        5432:30201/TCP   32s
service/kubernetes        ClusterIP   10.xx.0.1        <none>        443/TCP          85d
service/postgres          NodePort    10.xxx.xxx.xxx   <none>        5432:30200/TCP   32s

NAME                              READY   UP-TO-DATE   AVAILABLE   AGE
deployment.apps/allocation-psql   1/1     1            1           32s
deployment.apps/postgres          1/1     1            1           32s

NAME                                         DESIRED   CURRENT   READY   AGE
replicaset.apps/allocation-psql-65dc7f9fb5   1         1         1       32s
replicaset.apps/postgres-7b8c586bb6          1         1         1       32s
```
Once all the resources are up and running we need to start our microservice using a different profile I set up `kube`.
This profile has been configured to make use of the kubernetes deployment, each posgres deployment has an associated 
NotePort that allow external connections that portforward to the database, I inject this into the Quarkus configuration
allowing this connection to be made.
```
./gradlew quarkusdev -Dquarkus.profile=kube
```


### `user-entry` 
is the entry service user requests a ticket and this service passes the request off to the parking-service.

### `parking-service`
This service is responsible for taking the request made by the user-entry service, persist the ticket information as well as reach out to the spot allocation service to get a parking spot.
### `spot-allocation-service`
This service will take the request made from the `parking-service`, and find an available parking spot and send the spot back to the `parking-service` that will later be sent back to the client.

## Note:
This is kind of an overkill application but just want to demonstrate the microservice communication, how to test microservices and persistence.


# Learning Material (Not sponsored)
I will mention some resources that I used when learning this framework the author is `Antonio Goncalves` there is a great course I can recommend: 
- [Agoncal](https://www.udemy.com/share/105Cmy3@k_dxIuEXxuER_PeuIPTH63h8Qkr7bvN9p5g6dF0iwmX2lOukAeEU45C6nnbJpwPdbA==/)
- [Agnocal Personal Website](https://antoniogoncalves.org/)

# System Architecture Overview
![](screenshots/microservice_diagram_user_input.png)