package functionalops.kafka.metrics

import scalaz._
import Scalaz._
import scalaz.effect._

import javax.management.MBeanServerConnection
import javax.management.remote._

object JMXRunnerMain extends App {
  import functionalops.kafka.metrics.core._

  def jmxRunner[A]: MBeanAction[A] => IO[Throwable \/ A] =
    runJMX(createJMXUrl("kafka-test", 9093)) _

  def printDomainsAction(c: MBeanServerConnection) =
    IO.putStrLn("Domains: " + c.getDomains.mkString("|"))

  def printMBeansCount(c: MBeanServerConnection) =
    IO.putStrLn("MBean count: " + c.getMBeanCount.toString)

  jmxRunner(c => printDomainsAction(c) >> printMBeansCount(c)).unsafePerformIO
}
