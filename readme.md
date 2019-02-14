# Kafka schema plugin example
Approaches how to use [schema-registry plugin](https://docs.confluent.io/current/schema-registry/docs/maven-plugin.html)

## Approach 1
`Schema module`  
* Check schema compatibility
* Register schema in schema-registry 
```
./mvnw -pl schema schema-registry:test-compatibility 
./mvnw -pl schema schema-registry:register 
```

`Client1 module`  
* Download schema 
* Generate pojos with this schema
```
./mvnw -pl client1 schema-registry:download
./mvnw -pl client1 avro:schema
```

## Approach 2
`Schema module`  
* Generate pojos with schema 
```
./mvnw -pl schema avro:schema
```

`Client2 module`
* Dependency to schema module
```xml
  <dependencies>
    <dependency>
      <groupId>ru.zhenik.example</groupId>
      <artifactId>schema</artifactId>
    </dependency>
  </dependency>
```

## Recommended flow by confluent 
with Approach 1  

![img](https://www.confluent.io/wp-content/uploads/Diagram-3.-Create-and-Register-Schema-1.png)


## References
* [creating-data-pipeline-kafka-connect-api-architecture-operations](https://www.confluent.io/blog/creating-data-pipeline-kafka-connect-api-architecture-operations/) 
* [securing-confluent-schema-registry-apache-kafka](https://www.confluent.io/blog/securing-confluent-schema-registry-apache-kafka/)
* [decoupling-systems-with-apache-kafka-schema-registry-and-avro](https://www.confluent.io/blog/decoupling-systems-with-apache-kafka-schema-registry-and-avro/)