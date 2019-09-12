#!/usr/bin/env groovy
 
/**
        * Sample Jenkinsfile for Jenkins2 Pipeline
        * from https://github.com/hotwilson/jenkins2/edit/master/Jenkinsfile
        * by wilsonmar@gmail.com 
 */
 
import hudson.model.*
import hudson.EnvVars
import groovy.json.JsonSlurperClassic
import groovy.json.JsonBuilder
import groovy.json.JsonOutput
import java.net.URL

def currentBuild = Thread.currentThread().executable

// must be run groovy post-build action AFTER harvest junit xml  if you use junit xml test results
testResult1 = currentBuild.testResultAction

currentBuild.setDescription(currentBuild.getDescription() + "\n pass:"+testResult1.result.passCount.toString()+", fail:"+testResult1.result.failCount.toString()+", skip:"+testResult1.result.skipCount.toString())

// if no pass, no fail all skip then set result to unstable
if (testResult1.result.passCount == 0 && testResult1.result.failCount == 0 && testResult1.result.skipCount > 0) {
   currentBuild.result = hudson.model.Result.UNSTABLE
}

currentBuild.setDescription(currentBuild.getDescription() + "\n" + currentBuild.result.toString())

def ws = manager.build.workspace.getRemote()
myFile = new File(ws + "/VERSION.txt")
desc = myFile.readLines()
currentBuild.setDescription(currentBuild.getDescription() + "\n" + desc)
