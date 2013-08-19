package net.masterthought.runnio.maven

/**
 * Created with IntelliJ IDEA.
 * User: kostasmamalis
 * Date: 19/08/2013
 * Time: 14:35
 * To change this template use File | Settings | File Templates.
 */
object MavenContentFactory {

   def changeMavenLocation =
     """
        <div class="toggle-content">
               <div class="whead">
                 <strong>Change maven location</strong>
               </div>
               <div class="box holder">
                 <form class="main" action="/maven-change-location" method='POST' novalidate="novalidate">
                   <p>Maven Location:
                     <input name="maven" class="shiny required" type="text" min="1"/>
                   </p>
                   <input type='submit'/>
                 </form>
               </div>
             </div>
     """

    def mavenLocationChangeResult(config : String) =
      """
         <div class="alert alert-success fade in">
                <button type="button" class="close" data-dismiss="alert">
                  &times;
                </button>
                <strong>
                  Maven Config
                </strong>
                Maven Location set to
                """ + config + """
                .
                <p>
                  <a class="btn btn-success" href="/">
                    Back to Homepage
                  </a>
                </p>
              </div>
      """

}
