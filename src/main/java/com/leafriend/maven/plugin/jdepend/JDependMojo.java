package com.leafriend.maven.plugin.jdepend;

import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugin.AbstractMojo;

@Mojo(name = "check", requiresProject = true)
@Execute(phase = LifecyclePhase.VERIFY)
public class JDependMojo extends AbstractMojo {

    public void execute() {
        getLog().info("com.leafriend:jdepend-maven-plugin:check");
    }

}
