package com.ford.sast.jenkins

import groovy.transform.ToString

/**
 * Provides standardized methods for scanning source code in the Jenkins workspace 
 * with Checkmarx CxSAST and CxOSA scanners.
 * 
 * Based on original Cx library by Nitya Narasimhan (nnarasi3@ford.com)
 * 
 * See: https://checkmarx.atlassian.net/wiki/spaces/KC/pages/129646534/Configuring+a+CxSAST+Scan+Action+using+Jenkins+Pipeline+v8.5.0+to+v8.8.0
 *  
 * @author randy@checkmarx.com
 * @author nnarasi3@ford.com
 *
 */
@ToString(includeNames=true, includeFields=true)
class CxScan implements Serializable {

	private static final String ORG = 'CxServer'
	private static final String SP = 'Ford'
	
	final LineOfBusiness lob
	final ProjectType projectType
	final String applicationID
	final String applicationName
	final String applicationTeam
	final String componentName
	final String branch
	final String environment
	
	final String exclusionFolders = ''
	final String exclusionPatterns = ''
	
	private final String teamPath
	private final String projectName

	public CxScan(LineOfBusiness lob, ProjectType projectType, String applicationID, String applicationName,
			String applicationTeam, String componentName, String branch, String environment) {

		this.lob = lob;
		this.projectType = projectType;
		this.applicationID = applicationID;
		this.applicationName = applicationName;
		this.applicationTeam = applicationTeam;
		this.componentName = componentName;
		this.branch = branch;
		this.environment = environment;

		this.teamPath = buildTeamPath()
		this.projectName = buildProjectName()
	}

	private String buildTeamPath() {
		return "\\${ORG}\\${SP}\\${lob}\\${applicationTeam}"
	}
	
	private String buildProjectName() {
		return "${applicationName}-${componentName}-${branch}"
	}
	
	def doFullScan(dsl) {
		doFullScan(dsl, '', '')
	} 

	def doFullScan(dsl, String excludeFolders) {
		doFullScan(dsl, excludeFolders, '')
	}
	
	def doFullScan(dsl, String excludeFolders, String excludePatterns) {
		dsl.echo 'Running full scan...'
		dsl.echo this.toString()
	}
}
