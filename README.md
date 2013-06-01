Gemfire Work
==============

This repo contains simple Gemfire tests to help learning how Gemfire works.

It's all very simple and assumes that you're running on *nix (including OS/X) and that you have Gemfire 7.0 installed and you've set up the environment as [they suggest`\http://pubs.vmware.com/vfabric53/index.jsp?topic=/com.vmware.vfabric.gemfire.7.0/getting_started/install_intro.html].

Once installed there are just two things to do:

* From the "Server" directory run the "startcacheserver.sh" script that starts the locator and cache server.
* From the "Client" director run the "loader.sh" to load data, "runreceiver.sh" to add items to the cache and retrieve what is in the cache.
* From the "Client" directory run the "client.sh" script as "client.sh Caller" to call the server side function.

The client is configured with a ClientCache, set to CACHING_PROXY mode.  This is to prove that function invocation results aren't cached as the timestamp comes back in the results and can be seen to change (and a message appears in the NessageServer logs in ./Server/numberserver).

