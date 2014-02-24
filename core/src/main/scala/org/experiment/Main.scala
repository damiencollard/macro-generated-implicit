package org.experiment

@Special
case class Dummy(s: String)

object Dummy {
  val dummy = "nothing"
}

object Main {
  def main(args: Array[String]) {
    implicitly[Meta[Dummy]].info
  }
}
