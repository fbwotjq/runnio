# runnio #

Runnio is a standalone Cucumber Web Runner.

The purpose of runnio is dual:

1. to allow the standalone execution of specifications without actually checking out the code (e.g. for demo purposes, or as an assistance to manual testers)
2. to enable the creation and execution of cucumber specifications on the fly.

## Build & Run ##

```sh
$ cd runnio
$ ./sbt
> container:start
> browse
```

If `browse` doesn't launch your browser, manually open [http://localhost:9999/](http://localhost:9999/) in your browser.

A sample of how Runnio works can be found at our [Jelastic](http://runnio.j.layershift.co.uk/) site.

## How to use it ##

### Maven configuration ###
Since runnio is using your maven installation if it is not as per the default configuration, you need to specify the path where maven is installed

### Jar configuration ###

First you need to package your tests by using the following plugin within your pom.xml
```
       <build>
           <plugins>
               <plugin>
                   <artifactId>maven-jar-plugin</artifactId>
                   <executions>
                       <execution>
                           <id>test-jar</id>
                           <phase>package</phase>
                           <goals>
                               <goal>test-jar</goal>
                           </goals>
                       </execution>
                   </executions>
               </plugin>
           </plugins>
       </build>
```
And run `mvn clean install`. That will produce a *-test.jar which now you will be able to upload to the machine where runnio lives.

Then you need to install the jar by uploading it. Runnio can identify your pom configuration (groupId, artifactId, version) and whether it is a test-jar or not by parsing the jar itself.
After you install the test.jar you will also be able to install any additional jars which are pre-requisites for the exeuction of your tests.

Finally you can run your specs by specifying the Cucumber tags you want.
At the end you will get a full report from the maven execution as well as a reference to the location of the Cucumber Reports.

- The creation of cucumber files on the fly is still in progress...

&copy;2013 - [MasterThought.net](http://www.masterthought.net)


