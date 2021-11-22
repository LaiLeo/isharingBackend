#!/bin/bash

echo 'Check network setting...'
ISHARING_NETWORK=isharing_network
if [ -z $(docker network ls --filter name=^${ISHARING_NETWORK}$ --format="{{.Name}}") ]; then
	echo $ISHARING_NETWORK" not exist"
	docker network create \
		--driver=bridge \
		--attachable \
		--subnet=172.26.0.0/24 $ISHARING_NETWORK
else
	echo $ISHARING_NETWORK" already exist"
fi
