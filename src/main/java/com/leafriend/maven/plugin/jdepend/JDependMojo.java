package com.leafriend.maven.plugin.jdepend;

import java.io.IOException;
import java.util.Collection;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import jdepend.framework.*;

@Mojo(name = "check", requiresProject = true)
@Execute(phase = LifecyclePhase.VERIFY)
public class JDependMojo extends AbstractMojo {

    private JDepend analyzer;

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Parameter(defaultValue = "false")
    private boolean verbose;

    public JDependMojo() {
        analyzer = new JDepend();
    }

    public void execute() {
        getLog().info("com.leafriend:jdepend-maven-plugin:check");

        String output = project.getBuild().getOutputDirectory();
        try {
            analyzer.addDirectory(output);
            debugOrInfo("Added directory: " + output);
        } catch (IOException e) {
            getLog().error("Failed to adding directory: " + output, e);
        }

        Collection<JavaPackage> packages = analyzer.analyze();
        for (JavaPackage javaPackage : packages) {

            if (javaPackage.getClasses().size() == 0)
                continue;

            debugOrInfo(
                    javaPackage.getName() + ": " + (javaPackage.containsCycle()
                            ? "has cycle!" : "has no cycle."));
        }

    }

    private void debugOrInfo(String content) {
        if (verbose)
            getLog().info(content);
        else
            getLog().debug(content);
    }

}
