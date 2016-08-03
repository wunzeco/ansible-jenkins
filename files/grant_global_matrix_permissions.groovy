import jenkins.model.Jenkins;
import hudson.security.*


/*
  For valid permissions,
  http://javadoc.jenkins-ci.org/hudson/security/class-use/Permission.html#hudson.slaves
*/
valid_perms_map = [
   global_admin:                 Jenkins.ADMINISTER,
   global_read:                  Jenkins.READ,
   global_run_scripts:           Jenkins.RUN_SCRIPTS,
   configure_updatecenter:       hudson.PluginManager.CONFIGURE_UPDATECENTER,
   upload_plugins:               hudson.PluginManager.UPLOAD_PLUGINS,

   credentials_create:           com.cloudbees.plugins.credentials.CredentialsProvider.CREATE,
   credentials_delete:           com.cloudbees.plugins.credentials.CredentialsProvider.DELETE,
   credentials_update:           com.cloudbees.plugins.credentials.CredentialsProvider.UPDATE,
   credentials_view:             com.cloudbees.plugins.credentials.CredentialsProvider.VIEW,
   credentials_manage_domains:   com.cloudbees.plugins.credentials.CredentialsProvider.MANAGE_DOMAINS,

   slave_build:                  hudson.model.Computer.BUILD,
   slave_connect:                hudson.model.Computer.CONNECT,
   slave_configure:              hudson.model.Computer.CONFIGURE,
   slave_create:                 hudson.model.Computer.CREATE,
   slave_delete:                 hudson.model.Computer.DELETE,
   slave_disconnect:             hudson.model.Computer.DISCONNECT,

   job_build:                    hudson.model.Item.BUILD,
   job_cancel:                   hudson.model.Item.CANCEL,
   job_configure:                hudson.model.Item.CONFIGURE,
   job_create:                   hudson.model.Item.CREATE,
   job_delete:                   hudson.model.Item.DELETE,
   job_discover:                 hudson.model.Item.DISCOVER,
   job_read:                     hudson.model.Item.READ,
   job_workspace:                hudson.model.Item.WORKSPACE,

   run_delete:                   hudson.model.Run.DELETE,
   run_update:                   hudson.model.Run.UPDATE,

   view_read:                    hudson.model.View.READ,
   view_configure:               hudson.model.View.CONFIGURE,
   view_create:                  hudson.model.View.CREATE,
   view_delete:                  hudson.model.View.DELETE,
]

def instance = Jenkins.getInstance()

def hudsonRealm = new HudsonPrivateSecurityRealm(true)
instance.setSecurityRealm(hudsonRealm)

def gmas = instance.getAuthorizationStrategy()

//println "${gmas instanceof GlobalMatrixAuthorizationStrategy}"

if( !(gmas instanceof GlobalMatrixAuthorizationStrategy) ) {
   gmas = new hudson.security.GlobalMatrixAuthorizationStrategy()
    // Ensure granted permissions for other users are preserved
    //gmas.grantedPermissions.each { permission, sids ->
    //    println "$permission -> $sids"
    //    sids.each { sid ->
    //        if( sid != args[0])
    //            strategy.add(permission, sid)
    //    }
    //}
}

// Ensure admin user has ADMINISTER permission
gmas.add(Jenkins.ADMINISTER,'admin')


def args = getBinding().args

def user = args[0]
permissions = args[1..-1]
permissions.each { p ->
    if( !valid_perms_map.containsKey(p) )
        throw new RuntimeException("Invalid permission ** ${p} ** \nValid permissions: ${valid_perms_map.keySet()}")

    //println "${user} =>> ${valid_perms_map[p]}"
    gmas.add(valid_perms_map[p], user)
}

instance.setAuthorizationStrategy(gmas)

instance.save()
