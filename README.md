# runnio #

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
