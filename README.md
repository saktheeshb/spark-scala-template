# spark-scala-template
--

## Running

### Run Command

```spark-submit /tmp/spark-scala-template-assembly-0.1.jar ```

### Example command to run the job 

```spark-submit /tmp/spark-scala-template-assembly-0.1.jar /tmp/spark-data/ ```

## Unit Test

For running unit tests, just do:

```bash
$ sbt clean test
```

1.Store the final output into Pargue table as 2 of the columns are of array data type and provide the table name to the end user to view the results

2.Code Refactoring

3.Additional logs if missed anywhere

4.Null check for data , if null display it as not available

5.Integaration testing

6.Store the final output into Pargue table as 2 of the columns are of array data type and provide the table name to the end user to view the results

7. Duplicate record check (if needed, as otherwise it would hit the performance)

8. Add partitions in case future use case demands
