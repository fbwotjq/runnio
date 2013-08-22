package net.masterthought.runnio

import org.scalatra.servlet.FileItem


object JarContentFactory {

  def defineJarDetails(groupId: String, artifactId: String, version: String, isTestJar: Boolean) = {

    val checkedIndicator = if (isTestJar) """checked="checked"""" else ""

    """<div class="toggle-content">
           <div class="whead">
             <strong>Verify Jar Configuration</strong>
           </div>
           <div class="box holder">
             <form class="main" action="/config-set" method='POST' novalidate="novalidate">
               <p>
                 <label>GroupID:</label>
                 <input name="groupId" class="shiny required" type="text" min="1" value = """" + groupId + """"/>
               </p>
               <p>
                 <label>ArtifactID:</label>
                 <input name="artifactId" class="shiny required" type="text" min="1" value = """" + artifactId + """"/>
               </p>
               <p>
                 <label>Version:</label>
                 <input name="version" class="shiny required" type="text" min="1" value = """" + version + """"/>
               </p>
               <p>
                  <label>Test jar: </label>
                  <input name="testJar" class="shiny required" type="checkbox" """ + checkedIndicator + """ />
               </p>
               <input type='submit' value="Install Jar!"/>
             </form>
           </div>
         </div>"""

  }

  def uploadJar = {
    """
      <form action="/upload" enctype="multipart/form-data" method="post">
      <input type="file" name="document" />
      <input type="hidden" name="jar" value="jar"/>
      <input type="submit" />
      </form>
    """
  }

  def successfulUpload(item: FileItem, groupId: String, artifactId: String, version: String) = {

    val isTestJar = item.name.endsWith("-tests.jar")

    """
      <p>Jar: """ + item.name + """ was successfully uploaded</p>
      <p>Size Of file: """ + item.size + """.</p>
      <p>Now define the Jar details:</p>
                                         """ + defineJarDetails(groupId, artifactId, version, isTestJar)
  }

  def jarConfigSuccess(config: String) : String = {

    val configuration : String = if (!CucumberRunnerConfig.isNotSet) {
      """
        <div class="alert alert-success fade in">
               <button type="button" class="close" data-dismiss="alert">
                 &times;
               </button>
               <strong>
                 Cucumber is going to use the following jar for execution: """ + config + """
               </strong>
               </div>"""
    } else {
      """
            <div class="alert alert-block alert-error fade in">
        										<button type="button" class="close" data-dismiss="alert">&times;</button>
        										<h4 class="alert-heading">You haven't uploaded the test project!</h4>
        										<p>You need to upload a test project.</p>
        										<p><a class="btn btn-danger" href="/upload">Upload</a> <a class="btn" href="/">Or go to homepage</a></p>
        									</div>
      """
    }

    return """
        <div class="alert alert-success fade in">
        <button type="button" class="close" data-dismiss="alert">
          &times;
        </button>
        <strong>
          You have successfully uploaded and installed your jar File.
        </strong>
        </div>""" + configuration
  }

}
