import hudson.model.*
import jenkins.model.*
import jenkins.security.*

void create_or_update_user(String user_name, String email, String password="", String full_name="", String public_keys="") {
  def user = hudson.model.User.get(user_name)
  user.setFullName(full_name)

  def email_param = new hudson.tasks.Mailer.UserProperty(email)
  user.addProperty(email_param)

  def pw_param = hudson.security.HudsonPrivateSecurityRealm.Details.fromPlainPassword(password)
  user.addProperty(pw_param)

  if ( public_keys != "" ) {
    def keys_param = new org.jenkinsci.main.modules.cli.auth.ssh.UserPropertyImpl(public_keys)
    user.addProperty(keys_param)
  }

  user.save()
}
