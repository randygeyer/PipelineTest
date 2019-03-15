package com.ford.sast.jenkins

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
class CxScan implements Serializable {

	private static final String ORG = 'CxServer'
	private static final String SP = 'Ford'
	private static final String PATH_SEP = '\\\\'
	
	//final LineOfBusiness lob
	//final ProjectType projectType
	final String lob
	final String projectType
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

	public CxScan(String lob, String projectType, String applicationTeam, String applicationID,  
			String applicationName, String componentName, String branch, String environment) {

		this.lob = lob;
		this.projectType = projectType;
		this.applicationTeam = applicationTeam;
		this.applicationID = applicationID;
		this.applicationName = applicationName;
		this.componentName = componentName;
		this.branch = branch;
		this.environment = environment;

		this.teamPath = buildTeamPath()
		this.projectName = buildProjectName()
	}

	private String buildTeamPath() {
		return ORG + PATH_SEP + SP + PATH_SEP + lob + PATH_SEP + applicationTeam
	}
	
	private String buildProjectName() {
		return "${applicationName}-${componentName}-${branch}"
	}
	
	def doFullScan(script) {
		doFullScan(script, '', '')
	} 

	def doFullScan(script, String excludeFolders) {
		doFullScan(script, excludeFolders, '')
	}
	
	def doFullScan(script, String excludeFolders, String excludePatterns) {
		script.echo 'Running full scan...'
		printConfig(script)
	}

	def printConfig(script) {
		script.echo '\t' + 'LineOfBusiness: ' + lob
		script.echo '\t' + 'ProjectType: ' + projectType
		script.echo '\t' + 'ApplicationID: ' + applicationID
		script.echo '\t' + 'ApplicationName: ' + applicationName
		script.echo '\t' + 'ApplicationTeam: ' + applicationTeam
		script.echo '\t' + 'ComponentName: ' + componentName
		script.echo '\t' + 'branch: ' + branch
		script.echo '\t' + 'Environment: ' + environment
		script.echo '\t' + 'TeamPath: ' + teamPath
		script.echo '\t' + 'ProjectName: ' + projectName
		script.echo '\t' + 'ExclusionFolders: ' + exclusionFolders
		script.echo '\t' + 'ExclusionPatterns: ' + exclusionPatterns
	}	
}
