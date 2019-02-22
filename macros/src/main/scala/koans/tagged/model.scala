package koans.tagged

import scala.language.experimental.macros

import scala.annotation.{ StaticAnnotation, compileTimeOnly }
import scala.reflect.macros.whitebox.Context


@compileTimeOnly("enable macro paradise to expand macro annotations")
class model extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro modelMacro.impl
}

object modelMacro {
  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    val inputs = annottees.map(_.tree).toList

    val entities = (c.prefix.tree, inputs.head) match {
      case (q"new $name(..$params)", q"case class $className()") =>
        q"""
          case class $className(..${params.map { param =>
            val paramName = param.toString
            q"${TermName(paramName)}: ${TermName(paramName.capitalize)}.Data"
          }})
          object ${TermName(className.toString)} {..${params.map { param =>
            val paramName = param.toString
            val tpeName   = TypeName(paramName.capitalize)
            val fieldName = TermName(paramName)
            q"implicit def ${TermName(c.freshName())}: koans.tagged.Extract[$className, $tpeName] = _.$fieldName"
          }}}
        """
    }

    // println(entities)
    c.Expr(entities)
  }
}
