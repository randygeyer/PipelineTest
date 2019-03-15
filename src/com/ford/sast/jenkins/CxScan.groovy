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
    final def script
    final String lob
    final String projectType
    final String applicationID
    final String applicationName
    final String applicationTeam
    final String componentName
    final String branch
    final String environment

    final String exclusionFolders = 'node_modules,test,target'
    final String filterPattern = '''
            !**/_cvs/**/*, !**/.svn/**/*,   !**/.hg/**/*,   !**/.git/**/*,  !**/.bzr/**/*, !**/bin/**/*,
            !**/obj/**/*,  !**/backup/**/*, !**/.idea/**/*, !**/*.DS_Store, !**/*.ipr,     !**/*.iws,
            !**/*.bak,     !**/*.tmp,       !**/*.aac,      !**/*.aif,      !**/*.iff,     !**/*.m3u, !**/*.mid, !**/*.mp3,
            !**/*.mpa,     !**/*.ra,        !**/*.wav,      !**/*.wma,      !**/*.3g2,     !**/*.3gp, !**/*.asf, !**/*.asx,
            !**/*.avi,     !**/*.flv,       !**/*.mov,      !**/*.mp4,      !**/*.mpg,     !**/*.rm,  !**/*.swf, !**/*.vob,
            !**/*.wmv,     !**/*.bmp,       !**/*.gif,      !**/*.jpg,      !**/*.png,     !**/*.psd, !**/*.tif, !**/*.swf,
            !**/*.jar,     !**/*.zip,       !**/*.rar,      !**/*.exe,      !**/*.dll,     !**/*.pdb, !**/*.7z,  !**/*.gz,
            !**/*.tar.gz,  !**/*.tar,       !**/*.gz,       !**/*.ahtm,     !**/*.ahtml,   !**/*.fhtml, !**/*.hdm,
            !**/*.hdml,    !**/*.hsql,      !**/*.ht,       !**/*.hta,      !**/*.htc,     !**/*.htd, !**/*.war, !**/*.ear,
            !**/*.htmls,   !**/*.ihtml,     !**/*.mht,      !**/*.mhtm,     !**/*.mhtml,   !**/*.ssi, !**/*.stm,
            !**/*.stml,    !**/*.ttml,      !**/*.txn,      !**/*.xhtm,     !**/*.xhtml,   !**/*.class, !**/*.iml, !Checkmarx/Reports/*.*
            '''

    private String teamPath
    private String projectName

    public CxScan(script, String lob, String projectType, String applicationTeam, String applicationID,
            String applicationName, String componentName, String branch, String environment) {

        this.script = script
        this.lob = lob
        this.projectType = projectType
        this.applicationTeam = applicationTeam
        this.applicationID = applicationID
        this.applicationName = applicationName
        this.componentName = componentName
        this.branch = branch
        this.environment = environment
    }

    private void init() {
        this.teamPath = buildTeamPath()
        this.projectName = buildProjectName()
    }

    private String buildTeamPath() {
        return ORG + PATH_SEP + SP + PATH_SEP + lob + PATH_SEP + applicationTeam
    }

    private String buildProjectName() {
        return "${applicationName}-${componentName}-${branch}"
    }

    def doFullScan(steps) {
        doFullScan(steps, true, '', '')
    }

    def doFullScan(steps, boolean syncScan) {
        doFullScan(steps, syncScan, '', '')
    }

    def doFullScan(steps, boolean syncScan, String excludeFolders) {
        doFullScan(steps, syncScan, excludeFolders, '')
    }

    def doFullScan(steps, boolean syncScan, String excludeFolders, String filterPattern) {
        init()
        addExclusions(excludeFolders, filterPattern)
        printConfig('full', syncScan)
        def comment = "AppID: ${ApplicationID}; Jenkins build: ${script.env.BUILD_NUMBER}"
        
        //TODO: preset id lookup
        script.steps.step([$class: 'CxScanBuilder',
            useOwnServerCredentials: false, avoidDuplicateProjectScans: true, comment: "${comment}",
            teamPath: "${teamPath}", 
            exclusionsSetting: 'job', excludeFolders: "${excludeFolders}", filterPattern: "${filterPattern}",
            preset: '36', projectName: "${projectName}", sourceEncoding: '1',
            waitForResultsEnabled: "${syncScan}"])

    }
    
    def addExclusions(String excludeFolders, String filterPattern) {
        excludeFolders ?: this.exclusionFolders + ',' + excludeFolders
        filterPattern ?: this.filterPattern + ',' + filterPattern
    }

    def printConfig(String scanType, boolean syncScan) {

        def message = """
            Running $scanType scan..."
            \tSynchronous: $syncScan
            \tLineOfBusiness: $lob
            \tProjectType: $projectType
            \tApplicationID: $applicationID
            \tApplicationName: $applicationName
            \tApplicationTeam: $applicationTeam
            \tComponentName: $componentName
            \tBranch: $branch
            \tEnvironment: $environment
            \tTeamPath: $teamPath
            \tProjectName: $projectName
            \tExclusionFolders: $exclusionFolders
            \tExclusionPatterns: $exclusionPatterns
            """
        script.echo message
    }
}
