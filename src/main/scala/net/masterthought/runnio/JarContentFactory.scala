package net.masterthought.runnio

import org.scalatra.servlet.FileItem


object JarContentFactory {

  def defineJarDetails = {
       """<div class="toggle-content">
           <div class="whead">
             <strong>Define Cucumber Configuration</strong>
           </div>
           <div class="box holder">
             <form class="main" action="/config-set" method='POST' novalidate="novalidate">
               <p>
                 <label>GroupID:</label>
                 <input name="groupId" class="shiny required" type="text" min="1"/>
               </p>
               <p>
                 <label>ArtifactID:</label>
                 <input name="artifactId" class="shiny required" type="text" min="1"/>
               </p>
               <p>
                 <label>Version:</label>
                 <input name="version" class="shiny required" type="text" min="1"/>
               </p>
               <input type='submit' value="Change config"/>
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

  def successfulUpload(item : FileItem) = {
    """
      <p>Jar: """ + item.name + """ was successfully uploaded</p>
      <p>Size Of file: """ + item.size + """.</p>
      <p>Now define the Jar details:</p>
      """   + defineJarDetails
  }

  def jarConfigSuccess(config:String) = {
    """
        <div class="alert alert-success fade in">
        <button type="button" class="close" data-dismiss="alert">
          &times;
        </button>
        <strong>
          Cucumber Config
        </strong>
        Config set to
        """ + config + """
        .
        <p>
          <a class="btn btn-success" href="/">
            Back to Homepage
          </a> <a class="btn" href="/run-spec">
          Or run a spec
        </a>
        </p>
      </div>
    """
  }

}
