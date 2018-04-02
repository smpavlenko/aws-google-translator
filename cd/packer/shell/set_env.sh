#!/bin/bash

vpc_id=$(aws ec2 describe-vpcs --filters Name=tag:Project,Values=AWSGT --output json | jq -r .Vpcs[0].VpcId | tr -d '\040\011\012\015')
subnet_id=$(aws ec2 describe-subnets --filters Name=vpc-id,Values=$vpc_id --output json | jq -r .Subnets[0].SubnetId | tr -d '\040\011\012\015')

sudo echo "vpc_id: $vpc_id" >> /etc/environment
sudo echo "subnet_id: $subnet_id" >> /etc/environment
