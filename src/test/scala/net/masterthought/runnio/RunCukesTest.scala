package net.masterthought.runnio

import _root_.cucumber.api.junit.Cucumber
import org.junit.runner.RunWith

@RunWith(classOf[Cucumber])
@Cucumber.Options(glue = Array("features"))
class RunCukesTest {}
