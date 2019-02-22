package koans

import scala.language.experimental.macros

import scala.annotation.{ StaticAnnotation, compileTimeOnly }
import scala.reflect.macros.whitebox.Context


@compileTimeOnly("enable macro paradise to expand macro annotations")
class derive extends StaticAnnotation {
  def macroTransform(annottees: Any*): Any = macro deriveMacro.impl
}

object deriveMacro {
  def impl(c: Context)(annottees: c.Expr[Any]*): c.Expr[Any] = {
    import c.universe._
    val inputs = annottees.map(_.tree).toList

    val result = inputs.head match {
      case q"def $name[..$tparams]: (..$tpes) => $out" =>
        val names = tpes.map { t => c.freshName() -> t }
        q"""
          def $name[..$tparams](..${names.map { case (n, t) => q"${TermName(n)}: $t" }}): $out = {
            ..${ names.map { case (n, t) =>
              q"implicit val ${TermName(s"${n}_implicit")}: $t = ${TermName(n)}"
            }}
            implicitly[$out]
          }
        """

      case q"def $name[..$tparams]: $out" =>
        q"def $name[..$tparams]: $out = implicitly[$out]"
    }

    // println(result)
    c.Expr[Any](result)
  }
}