Control flow
------------

::

  AgentLoader ----- attaches Agent ---------------> Target process
                    passes:                         * Agent loads Server
                    * TCP port                      * Server listens on the
                    * jarpaths                        specified TCP port

::

  AgentLoader ----- Client connects to the port --> Target process
                                                    * Server loads Repl
              ----- Keyboard input              -->
              <---- Repl output                 ---

zip directory
-------------

This is the directory that will be zipped when Scalive is released.

::

  zip/
    scalive
    scalive.cmd
    scalive-<version>-SNAPSHOT.jar -> ../../target/scala-2.11/scalive-<version>-SNAPSHOT.jar

    scala-library-2.10.4.jar
    scala-compiler-2.10.4.jar
    scala-reflect-2.10.4.jar

    scala-library-2.11.4.jar
    scala-compiler-2.11.4.jar
    scala-reflect-2.11.4.jar

While developing:

* Run ``sbt package`` to create/update scalive.jar.
* Add missing JARs as above.
* Run ``scalive`` to attach to a certain JVM process to see if it works properly.

Release
-------

Based on the ``zip`` directory above, prepare a directory to be zipped and
released (remember to remove uneccessary files, like .gitignore):

::

  scalive-<version>/
    scalive
    scalive.cmd
    scalive-<version>.jar  <-- Doesn't depend on Scala, thus doesn't follow Scala JAR naming

    scala-library-2.10.4.jar
    scala-compiler-2.10.4.jar
    scala-reflect-2.10.4.jar

    scala-library-2.11.4.jar
    scala-compiler-2.11.4.jar
    scala-reflect-2.11.4.jar

Then zip it:

::

  zip -r scalive-<version>.zip scalive-<version>
