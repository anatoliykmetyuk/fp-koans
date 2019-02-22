package koans.tagged

import scala.language.experimental.macros

import scala.annotation.{ StaticAnnotation, compileTimeOnly }
import scala.reflect.macros.whitebox.Context


@compileTimeOnly("enable macro paradise to expand macro annotations")
class fields extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro fieldsMacro.impl
}

object fieldsMacro {
  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    val inputs = annottees.map(_.tree).toList

    val dataObject = (c.prefix.tree, inputs.head) match {
      case (q"new $name(..$fields)", q"object $objectName extends $_") =>
        q"""
          object $objectName {
            ..${fields.flatMap { case q"$name: $tpe" => List(
              q"object ${TermName(name.toString)} extends koans.tagged.TaggedBuilder[$tpe]"
            , q"type ${TypeName(name.toString)} = $name.Type")
            }}
          }
        """
    }

    // println(dataObject)
    c.Expr(dataObject)
  }
}
