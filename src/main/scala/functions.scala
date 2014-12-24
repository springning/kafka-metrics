package functionalops.kafka.metrics

import scalaz._
import Scalaz._
import scalaz.effect._

import javax.management.MBeanServerConnection
import javax.management.remote._

trait Functions extends Instances {
  type MBeanAction[A] = MBeanServerConnection => IO[A]

  def runJMX[A](jmxUrl: JMXServiceURL)(action: MBeanAction[A]): IO[Throwable \/ A] =
    IO(initJMX(jmxUrl)).bracket(closeJMX)(toMBeanServerConn(_)(action)).catchLeft

  private def initJMX(u: JMXServiceURL): JMXConnector =
    JMXConnectorFactory.connect(u)

  private def closeJMX: JMXConnector => IO[Unit] =
    (c: JMXConnector) => IO(c.close)

  private def toMBeanServerConn[A]: JMXConnector => MBeanAction[A] => IO[A] =
    (c: JMXConnector) => (action: MBeanAction[A]) =>
      action(c.getMBeanServerConnection)
}
