
# OpenSearch SQL/PPL Engine Development Manual

## Introduction

+ [Architecture](intro-architecture.md): a quick overview of architecture
+ [V2 Engine](intro-v2-engine.md): introduces why we developed new V2 engine
+ Concepts
+ Quickstart

---
## Clients

+ **CLI**
+ **JDBC Driver**
+ **ODBC Driver**
+ **Query Workbench**

---
## Deployment

+ **Standalone Mode**
+ **OpenSearch Cluster**

---
## Programming Guides

+ **API**
+ **JavaDoc**

---
## Development Guides

### Language Processing

+ **SQL**
  + [Aggregate Window Function](sql-aggregate-window-function.md): aggregate window function support
+ **Piped Processing Language**

### Query Processing

+ **Query Analyzing**
  + [Semantic Analysis](query-semantic-analysis.md): performs semantic analysis to ensure semantic correctness
  + [Type Conversion](query-type-conversion.md): implement implicit data type conversion
+ **Query Planning**
  + [Logical Optimization](query-optimizier-improvement.md): improvement on logical optimizer and physical implementer
+ **Query Execution**
  + [Query Manager](query-manager.md): query management
+ **Query Acceleration**
  + [Automatic Acceleration](query-automatic-acceleration.md): workload based automatic query acceleration proposal

### Data Sources

+ **OpenSearch**
  + [Relevancy Search](opensearch-relevancy-search.md): OpenSearch relevancy search functions
  + [Sub Queries](opensearch-nested-field-subquery.md): support sub queries on OpenSearch nested field
  + [Pagination](opensearch-pagination.md): pagination implementation by OpenSearch scroll API
+ [Prometheus](datasource-prometheus.md): Prometheus query federation
+ **File System**
  + [Querying S3](datasource-query-s3.md): S3 query federation proposal

---
## Other Documents

+ **Test Framework**
  + [Doc Test](testing-doctest.md): makes our doc live and runnable to ensure documentation correctness
  + [Comparison Test](testing-comparison-test.md): compares with other databases to ensure functional correctness
+ **Operation Tools**