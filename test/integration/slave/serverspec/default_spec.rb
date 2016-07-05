require 'spec_helper'

jenkins_slave_user = 'jenkins-slave'
jenkins_slave_group = jenkins_slave_user


describe package('daemon') do
  it { should be_installed }
end

describe group(jenkins_slave_group) do
  it { should exist }
end

describe user(jenkins_slave_user) do
  it { should exist }
  it { should belong_to_group jenkins_slave_group }
end

describe file('/home/jenkins-slave') do
  it { should be_directory }
  it { should be_owned_by jenkins_slave_user }
  it { should be_mode 755 }
end

describe file('/etc/default/jenkins-slave') do
  it { should be_file }
  it { should be_owned_by 'root' }
  it { should be_mode 644 }
end

describe file('/etc/init.d/jenkins-slave') do
  it { should be_file }
  it { should be_owned_by 'root' }
  it { should be_mode 755 }
end

describe service('jenkins-slave') do
  it { should be_running }
end
