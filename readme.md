
# run app
```mvn spring-boot:run```


## create deployable war file 
```mvn package```

## compress js & css files
```mvn yuicompressor:compress```

### deploy to tomcat
```mvn clean tomcat7:(re)deploy```