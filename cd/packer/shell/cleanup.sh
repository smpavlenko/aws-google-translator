#!/bin/bash

# Shell provisioning script to clean up at the end
# 1. remove ansible
# 2. cleanup and remove unused packages
# 3. remove finger prints of keys
# 4. remove tmp
sudo apt-get remove -y ansible
sudo apt-get autoclean -y
sudo apt-get autoremove -y
sudo rm -f /root/.ssh/authorized_keys
sudo rm -rf /tmp/*
