package net.seichi915.seichi915elevator.util

object Implicits {
  implicit class AnyOps(any: Any) {
    def isNull: Boolean = Option(any).flatMap(_ => Some(false)).getOrElse(true)

    def nonNull: Boolean = !isNull
  }
}
