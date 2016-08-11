# vertx-httpclient-warning-reproducer

1. build fat jar: `./build.sh`
2. start web service: `./start.sh`
3. launch some requests to trigger the warning (run in separate console): `./test.sh`

**Note**: The first request will run fine, consecutive requests trigger the warning:
> Reusing a connection with a different context: an HttpClient is probably shared between different Verticles
