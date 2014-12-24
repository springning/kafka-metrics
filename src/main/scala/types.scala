package functionalops.kafka.metrics

import scalaz._
import Scalaz._
import scalaz.effect._

import javax.management._

trait Types {
  type MBeanAction[A] = MBeanServerConnection => IO[A]
  type ListenerAction[A] = Notification => A => IO[Unit]
  type RegistrationAction[A] =
    ListenerAction[A] => ObjectName => MBeanServerConnection => A => IO[Unit]
}
