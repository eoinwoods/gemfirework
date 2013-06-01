ps -U eoin -fww | grep -i gemfire | grep -v grep | awk '{print $2}' | xargs kill -9 
