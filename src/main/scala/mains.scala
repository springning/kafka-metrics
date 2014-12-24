package functionalops.kafka.metrics

import scalaz._
import Scalaz._
import scalaz.effect._

import javax.management.MBeanServerConnection
import javax.management.remote._

object JMXRunnerMain extends App {
  import functionalops.kafka.metrics.core._

  def jmxRunner[A]: MBeanAction[A] => IO[Throwable \/ A] =
    runJMX(jmxUrl("kafka-test", 9093)) _

  def myAction(c: MBeanServerConnection) = IO.putStrLn(c.getDomains.mkString("|"))

  jmxRunner(myAction).unsafePerformIO
}
