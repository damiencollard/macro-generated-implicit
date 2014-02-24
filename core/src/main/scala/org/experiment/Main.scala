package org.experiment

@Special
case class Dummy(s: String)

object Dummy {
  val dummy = "nothing"
}

object Main {
  def main(args: Array[String]) {
    Dummy.meta.info
    implicitly[Meta[Dummy]].info
  }
}
