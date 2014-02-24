package org.experiment

import scala.reflect.macros.Context
import scala.annotation.StaticAnnotation

abstract class Meta[T] {
  def info: String
}

class MakeMeta extends StaticAnnotation {
  def macroTransform(annottees: Any*) = macro MakeMetaMacro.impl
}

object MakeMetaMacro {
  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._

    def createInstance(className: Name): ModuleDef = {
      val msg = s"meta for ${className.toString}"
      q"""
        implicit object meta extends Meta[$className] {
          def info: String = $msg
        }
      """
    }

    def augmentCompanion(companion: ModuleDef): ModuleDef = {
      val ModuleDef(mods, name, Template(parents, self, body)) = companion
      val augmentedBody = body :+ createInstance(name)
      ModuleDef(mods, name, Template(parents, self, augmentedBody))
    }

    val inputs = annottees.map(_.tree).toList
    val expandees = inputs match {
      case (klass @ ClassDef(_, _, _, _)) :: (companion @ ModuleDef(_, _, _)) :: Nil =>
        klass :: augmentCompanion(companion) :: Nil
      case _ =>
        c.abort(c.enclosingPosition, "not applicable")
    }

    println("++++++++++ EXPANDEES +++++++++++")
    println(expandees)
    println(showRaw(expandees))

    c.Expr[Any](Block(expandees, Literal(Constant((): Unit))))
  }
}
