# Requirements

    mvn --version


Should at least mention `Apache Maven 3.0.0` or higher and `Java version: 1.8` or higher.

I used `Eclipse Mars.1 (4.5.1)`.

# Run in Development-Server
`cd` into the Project-Directory and run `mvn jetty:run`. The first time it will Download all dependencies and then -hopefully- start you a Development-Server on Port `8080`. It should respond on this URL: <http://localhost:8080/Urlaub/jaxrs/test> then.

If you want it on another Port, you can do something like `mvn jetty:run -Djetty.port=9999`  but before heading Straight to Production ;) consider running an ngingx Proxy in front of it. This will not only allow you to do SSL Offloading but also URL Rewriting and fast Serving of static Assets and is usualy a good thing to do.  

# Opening in Eclipse
I got best results by using `File->Import->Maven->Existing Maven Projects`. Ensure the `Project->Build Automaticaly` Menu-Item is checked, which will allow the Development-Server to find the compiled changes and reload automatically, when ever you change something in a file.
