package net.masterthought.runnio.cucumber

/**
 * User: kostasmamalis
 */
object CucumberContentFactory {

    private val successLabel : String = """<span class="label label-success">Success</span>"""
    private val failureLabel : String = """<span class="label label-important">Failure!</span>"""

    def executionResult(tag:String,result:String) = {
      val resultSpan = if (result.startsWith("success")) successLabel else failureLabel

      """<div class="toggle-content">
        <div class="whead">
            <strong>Cucumber Execution: """ +
              tag +
            """</strong>
          </div>
          <div class="box holder">
            """ + resultSpan + """
            <p class="padd-15">
              <code>
                """ + result + """
              </code>
            </p>
          </div>
          <a href="/">
            &lt;
            Back to main page.</a>
        </div>"""
    }

   def executeSpec = {
     """
       <div class="toggle-content">
              <div class="whead">
                <strong>Run a Spec</strong>
              </div>
              <div class="box holder">
                <form class="main" action="/result" method='POST' novalidate="novalidate">
                  <p>
                    <label>Tag:</label>
                    <input name="tag" class="shiny required" type="text" min="1"/>
                  </p>
                  <p>
                    <label>System Properties: [e.g. env=cit,browser=firefox,video]</label>
                    <input name="properties" class="shiny required" type="text" min="1"/>
                  </p>
                  <input type='submit'/>
                </form>
              </div>
            </div>
     """
   }
}
