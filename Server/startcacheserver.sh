USER_CP=/Users/eoin/work/Gemfire/SimpleCache/out/production/Server
echo "Starting locator"
gfsh start locator --name=locator1 --port=10987
echo "Starting cacheserver"
gfsh start server --name=numberserver --locators=localhost[10987] --classpath=$USER_CP --cache-xml-file=etc/cache.xml
