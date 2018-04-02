#!/bin/bash

vpc_id=$(aws ec2 describe-vpcs --filters Name=isDefault,Values=false --output json | jq -r .Vpcs[0].VpcId | tr -d '\040\011\012\015')
sudo echo "vpc_id: $vpc_id" >> /etc/environment
