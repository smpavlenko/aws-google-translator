#!/bin/bash

sudo apt-get remove tr
sudo apt-get remove jq
sudo apt-get remove -y awscli
sudo apt-get remove -y ansible
sudo apt-get autoclean -y
sudo apt-get autoremove -y
sudo rm -f /root/.ssh/authorized_keys
sudo rm -rf /tmp/*
