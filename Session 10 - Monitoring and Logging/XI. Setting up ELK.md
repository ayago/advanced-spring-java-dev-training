# Logstash config

```conf
input {
  # Input for App 1 logs
  file {
    path => "${HOME}/logs/order-management-service/application.log"
    start_position => "beginning"
    sincedb_path => "/dev/null"
    tags => ["order-management"]
  }

  # Input for App 2 logs
  #file {
  #  path => "/path/to/app2/logs/application.log"
  #  start_position => "beginning"
  #  sincedb_path => "/dev/null"
  #  tags => ["app2"]
  #}
}

filter {
  if "order-management" in [tags] {
    # Filters specific to App 1 if necessary
    json {
      source => "message"
    }
    mutate {
      add_field => { "app_name" => "order-management" }
    }
  }

  #if "app2" in [tags] {
  #  # Filters specific to App 2 if necessary
  #  json {
  #    source => "message"
  #  }
  #  mutate {
  #    add_field => { "app_name" => "app2" }
  #  }
  #}

  # Parse the timestamp field in all logs
  date {
    match => ["timestamp", "ISO8601"]
  }
}

output {
  # Output to Elasticsearch
  elasticsearch {
    hosts => ["http://localhost:9200"]
    index => "logs-%{app_name}-%{+YYYY.MM.dd}"  # Separate index for each app
  }

  # Optionally output to the console for debugging
  stdout { codec => rubydebug }
}

```

Elasticsearch brew install https://www.elastic.co/guide/en/elasticsearch/reference/7.17/brew.html (Use 8.15)
* Disable ml: modify /opt/homebrew/etc/elasticsearch/elasticsearch.ymlxpack.ml.enabled: false
* Disable security properties
* Setup single node and disable discovery
* Setup ES_JAVA_HOME (e.g. export ES_JAVA_HOME=${JAVA_HOME}) //no need
* Run elastic search: /opt/homebrew/opt/elasticsearch-full/bin/elasticsearch or elasticsearch
* Access localhost:9200
Logstash https://www.elastic.co/downloads/logstash

* Install Logstash by downloading. Setup 8.15
* nano /path/to/logstash/conf.d/springboot-multiapp-logstash.conf
* Execute: logstash -f /path/to/logstash/conf.d/springboot-multiapp-logstash.conf
Kibana
* Download and install 8.15
* kibana —version //should be 8.15
* kibana
* Access localhost: 5601 