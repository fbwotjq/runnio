package net.masterthought.runnio


object ContentFactory {

  def dashboard = {
       """<div class="widget bottom-35">
       <ul class="bind-tab red auto">
          <li>
            <a class="active" href="javascript:;" data-element="char">
              <i class=" icon-leaf icon-white"></i>
            </a>
          </li>
          <li>
            <a href="javascript:;" data-element="settings">
              <i class="icon-folder-open icon-white"></i>
            </a>
          </li>
          <li>
            <a href="javascript:;" data-element="search">
              <i class="icon-certificate icon-white"></i>
            </a>
          </li>
        </ul>
        <div class="box holder-clean bind padd over-hide">
          <div class="bind-cont active" data-box="char">
            <div class="slide">
              <div class="left">
                <h2>Cucumber Execution</h2>
                <p>The purpose of this tool is to assist anyone who wants to execute Cucumber tests locally.
                  <br/>
                  You just need to
                  <a href="/upload">Upload a jar and configure</a>
                  the
                  <i>groupId</i>
                  ,
                  <i>artifactId</i>
                  and
                  <i>version</i>
                  of your test:jar.
                  Then, based on your tags, you can
                  <a href="/run-spec">run a spec</a>
                </p>
              </div>
            </div>
          </div>
          <div class="bind-cont" data-box="settings">
            <div class="slide slide-2">
              <div class="left">
                <h2>Maven Configuration</h2>
                <p>
                  You will be able to configure your M2_HOME.
                </p>
              </div>
            </div>
          </div>
          <div class="bind-cont" data-box="search">
            <div class="slide slide-3">
              <div class="left">
                <h2>Reports</h2>
                <p>
                  Shiny cucumber reports.
                </p>
              </div>
            </div>
          </div>
        </div>
      </div>"""

  }


}
