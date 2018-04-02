#!/bin/bash

# Shell provisioning script to clean up at the end
# 1. remove awscli
# 2. remove ansible
# 3. cleanup and remove unused packages
# 4. remove finger prints of keys
# 5. remove tmp
sudo apt-get remove -y awscli
sudo apt-get remove -y ansible
sudo apt-get autoclean -y
sudo apt-get autoremove -y
sudo rm -f /root/.ssh/authorized_keys
sudo rm -rf /tmp/*
