if [ -z "$GEMFIRE" ]
then
   . /Developer/Gemfire-7.0/setenv.sh
fi

if [ -z "$1" ]
then
   echo Usage: $0 classname [args]
   exit 1
fi

classname=$1
shift
java -cp $PWD/out/production/Client:$CLASSPATH com.artechra.$classname $*
