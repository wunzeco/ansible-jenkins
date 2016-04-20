JENKINS
=======

Ansible role to install and configure Jenkins master and slave.


## Example
 
Install jenkins and some plugins

```
- hosts: jenkinsmaster

  vars:
    jenkins_plugins:
      - 'ansicolor:0.4.2'
      - 'authentication-tokens:1.2'
      - 'build-monitor-plugin:1.8+build.201601112328'
      - 'build-name-setter:1.6.3'
      - 'build-pipeline-plugin:1.5.2'
      - 'claim:2.8'
      - 'copyartifact:1.38'
      - 'credentials-binding:1.7'
      - 'dashboard-view:2.9.7'
      - 'delivery-pipeline-plugin:0.9.9'  

  roles:
    - wunzeco.jenkins
```

> **Note**:
>
>    All plugins (including their dependencies) need to be listed in the form 
>    `pluginID:version` as there is no transitive dependency resolution. 
>    However, if `version` is omitted for a plugin the latest version will be
>    installed.


## Testing

```
cd ansible-jenkins
kitchen test
```


## Dependencies
None


## ToDo
- slave install and config
