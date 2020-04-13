# rabbit 検証

## overview

### spring amqp version

rabbitmq start

```PS
PS> docker-compse up -d
```

publisher start

```PS
PS> ./mvnw spring-boot:run -pl publisher
```

consumer-spring start

```PS
PS> ./mvnw spring-boot:run -pl consumer-spring
```

curl

```PS
PS> curl http://localhost:8081/add
```

### rabbitmq client version

rabbitmq start

```PS
PS> docker-compse up -d
```

publisher start

```PS
PS> ./mvnw spring-boot:run -pl publisher
```

consumer-spring start

```PS
PS> ./mvnw spring-boot:run -pl consumer-javaclient
```

curl

```PS
PS> curl http://localhost:8081/add
```

## VSCodeでうまいことやる方法メモ

よくわからないけど以下のファイルを全部消したうえで、eclipse的ファイルをmavenで作成
* .project
* .settings/
* .classpath
* .factorypath

```PS
PS> ./mvnw eclipse:eclipse
```

ここまでやったら、VSCodeでルートフォルダを開く。

maven pluginやらspring-boot pluginやらがこれで動く。
