package akka.http.model
package parser

import akka.parboiled2._

/**
 * For certain high-performance use-cases it is better to construct Strings
 * that the parser is to produce/extract from the input in a char-by-char fashion.
 *
 * Mixing this trait into your parser gives you a simple facility to support this.
 */
trait StringBuilding { this: Parser ⇒
  protected val sb = new java.lang.StringBuilder

  def clearSB(): Rule0 = rule { run(sb.setLength(0)) }

  def appendSB(): Rule0 = rule { run(sb.append(lastChar)) }

  def appendSB(offset: Int): Rule0 = rule { run(sb.append(charAt(offset))) }

  def appendSB(c: Char): Rule0 = rule { run(sb.append(c)) }

  def appendSB(s: String): Rule0 = rule { run(sb.append(s)) }

  def prependSB(): Rule0 = rule { run(doPrepend(lastChar)) }

  def prependSB(offset: Int): Rule0 = rule { run(doPrepend(charAt(offset))) }

  def prependSB(c: Char): Rule0 = rule { run(doPrepend(c)) }

  def prependSB(s: String): Rule0 = rule { run(doPrepend(s)) }

  def setSB(s: String): Rule0 = rule { run(doSet(s)) }

  private def doPrepend(c: Char): Unit = {
    val saved = sb.toString
    sb.setLength(0)
    sb.append(c)
    sb.append(saved)
  }

  private def doPrepend(s: String): Unit = {
    val saved = sb.toString
    sb.setLength(0)
    sb.append(s)
    sb.append(saved)
  }

  private def doSet(s: String): Unit = {
    sb.setLength(0)
    sb.append(s)
  }
}
