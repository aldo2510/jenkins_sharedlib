
import org.junit.Before
import org.junit.Test
import com.lesfurets.jenkins.unit.BasePipelineTest

class HelloSpec extends BasePipelineTest {
  @Before
  void setUp() {
    super.setUp()
    helper.registerAllowedMethod('echo', [String.class], { s -> println s })
  }

  @Test
  void testHello() {
    def script = loadScript('vars/hello.groovy')
    script.call('Test')
  }
}
